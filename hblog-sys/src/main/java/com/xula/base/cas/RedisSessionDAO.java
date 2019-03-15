package com.xula.base.cas;

import com.xula.base.utils.CommonUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * 基于redis操作
 * @author xla
 */
public class RedisSessionDAO extends AbstractSessionDAO {

	private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

	/**
	 * key的前缀标识
	 */
	private static final String DEFAULT_SESSION_KEY_PREFIX = "shiro:session:";

	private String keyPrefix = DEFAULT_SESSION_KEY_PREFIX;

	/**
	 * 超时时间
	 */
	private static final long DEFAULT_SESSION_IN_MEMORY_TIMEOUT = 1000L;

	/**
	 * doReadSession be called about 10 times when login.
	 * Save Session in ThreadLocal to resolve this problem. sessionInMemoryTimeout is expiration of Session in ThreadLocal.
	 * The default value is 1000 milliseconds (1s).
	 * Most of time, you don't need to change it.
	 */
	private long sessionInMemoryTimeout = DEFAULT_SESSION_IN_MEMORY_TIMEOUT;

	private static final boolean DEFAULT_SESSION_IN_MEMORY_ENABLED = true;

	private boolean sessionInMemoryEnabled = DEFAULT_SESSION_IN_MEMORY_ENABLED;

	// expire time in seconds
	private static final int DEFAULT_EXPIRE = -2;
	private static final int NO_EXPIRE = -1;

	/**
	 * Please make sure expire is longer than sesion.getTimeout()
	 */
	private int expire = DEFAULT_EXPIRE;

	private static final int MILLISECONDS_IN_A_SECOND = 1000;

	private RedisTemplate<String,Session> redisManager;

	private static ThreadLocal sessionsInThread = new ThreadLocal();
	
	@Override
	public void update(Session session) throws UnknownSessionException {
		this.saveSession(session);
		if (this.sessionInMemoryEnabled) {
			this.setSessionToThreadLocal(session.getId(), session);
		}
	}

	/**
	 * save session
	 * @param session
	 * @throws UnknownSessionException
	 */
	private void saveSession(Session session) throws UnknownSessionException {
		if (session == null || session.getId() == null) {
			logger.error("session or session id is null");
			throw new UnknownSessionException("session or session id is null");
		}


		String key = getRedisSessionKey(session.getId());

		if (expire == DEFAULT_EXPIRE) {
			this.redisManager.boundValueOps(key).set(session, (int) (session.getTimeout()),TimeUnit.MILLISECONDS);
			return;
		}
		if (expire != NO_EXPIRE && expire * MILLISECONDS_IN_A_SECOND < session.getTimeout()) {
			logger.warn("Redis session expire time: "
					+ (expire * MILLISECONDS_IN_A_SECOND)
					+ " is less than Session timeout: "
					+ session.getTimeout()
					+ " . It may cause some problems.");
		}
		this.redisManager.boundValueOps(key).set(session, expire,TimeUnit.SECONDS);
	}

	@Override
	public void delete(Session session) {
		if (session == null || session.getId() == null) {
			logger.error("session or session id is null");
			return;
		}
		redisManager.delete(getRedisSessionKey(session.getId()));
	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<String> keys = redisManager.keys(this.keyPrefix + "*");
		Set<Session> sessions = keys.stream().map(s -> redisManager.boundValueOps(s).get()).collect(Collectors.toSet());
		return sessions;
	}

	@Override
	protected Serializable doCreate(Session session) {
		if (session == null) {
			logger.error("session is null");
			throw new UnknownSessionException("session is null");
		}
		Serializable sessionId = this.generateSessionId(session);  
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if (sessionId == null) {
			logger.warn("session id is null");
			return null;
		}

		if (this.sessionInMemoryEnabled) {
			Session session = getSessionFromThreadLocal(sessionId);
			if (session != null) {
				return session;
			}
		}

		Session session = null;
		logger.debug("read session from redis");
		session = redisManager.boundValueOps(getRedisSessionKey(sessionId)).get();
		if (this.sessionInMemoryEnabled) {
			setSessionToThreadLocal(sessionId, session);
		}
		return session;
	}

	/**
	 * 将session 存入当前线程的缓存中，减少频繁调redis
	 * @param sessionId
	 * @param s
	 */
	private void setSessionToThreadLocal(Serializable sessionId, Session s) {
		Map<Serializable, SessionInMemory> sessionMap = (Map<Serializable, SessionInMemory>) sessionsInThread.get();
		if (sessionMap == null) {
            sessionMap = new HashMap<>();
            sessionsInThread.set(sessionMap);
        }
		sessionMap = removeExpiredSessionInMemory(sessionMap);
		SessionInMemory sessionInMemory = new SessionInMemory();
		sessionInMemory.setCreateTime(new Date());
		sessionInMemory.setSession(s);
		sessionMap.put(sessionId, sessionInMemory);
	}

	private Map<Serializable, SessionInMemory> removeExpiredSessionInMemory(Map<Serializable, SessionInMemory> sessionMap){
		Iterator<Serializable> it = sessionMap.keySet().iterator();
		while(it.hasNext()) {
			Serializable sessionId = it.next();
			SessionInMemory sessionInMemory = sessionMap.get(sessionId);
			if (sessionInMemory == null) {
				it.remove();
				continue;
			}
			long liveTime = getSessionInMemoryLiveTime(sessionInMemory);
			if (liveTime > sessionInMemoryTimeout) {
				it.remove();
			}
		}
		return sessionMap;
	}

	private Session getSessionFromThreadLocal(Serializable sessionId) {
		if (sessionsInThread.get() == null) {
			return null;
		}
		Map<Serializable, SessionInMemory> sessionMap = (Map<Serializable, SessionInMemory>) sessionsInThread.get();
		SessionInMemory sessionInMemory = sessionMap.get(sessionId);
		if (sessionInMemory == null) {
			return null;
		}
		long liveTime = getSessionInMemoryLiveTime(sessionInMemory);
		if (liveTime > sessionInMemoryTimeout) {
			sessionMap.remove(sessionId);
			return null;
		}
		logger.debug("read session from memory");
		return sessionInMemory.getSession();
	}

	private long getSessionInMemoryLiveTime(SessionInMemory sessionInMemory) {
		return System.currentTimeMillis() - sessionInMemory.getCreateTime().getTime();
	}

	private String getRedisSessionKey(Serializable sessionId) {
		return this.keyPrefix + sessionId;
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public long getSessionInMemoryTimeout() {
		return sessionInMemoryTimeout;
	}

	public void setSessionInMemoryTimeout(long sessionInMemoryTimeout) {
		this.sessionInMemoryTimeout = sessionInMemoryTimeout;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public boolean getSessionInMemoryEnabled() {
		return sessionInMemoryEnabled;
	}

	public void setSessionInMemoryEnabled(boolean sessionInMemoryEnabled) {
		this.sessionInMemoryEnabled = sessionInMemoryEnabled;
	}

	public static ThreadLocal getSessionsInThread() {
		return sessionsInThread;
	}

	public RedisTemplate<String, Session> getRedisManager() {
		return redisManager;
	}

	public void setRedisManager(RedisTemplate<String, Session> redisManager) {
		this.redisManager = redisManager;
	}
}
