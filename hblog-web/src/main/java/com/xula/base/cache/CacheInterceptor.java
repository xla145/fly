//package com.xula.base.cache;
//
//import com.xula.base.utils.Md5Utils;
//import org.apache.commons.lang.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.ObjectOutputStream;
//
///**
// * 缓存拦截器 （memcache）
// * <p>
// * 用于 使用注解的方式添加缓存
// *
// * @author caibin
// */
//@Aspect
//@Component
//public class CacheInterceptor {
//
//    private Logger logger = LoggerFactory.getLogger(CacheInterceptor.class);
//
//    /**
//     * 设置缓存注解切入点
//     */
//    @Pointcut("execution(* com.xula.service.*.*.*(..)) && @annotation(com.xula.base.cache.MCache)")
//    private void theMethod() {
//    }
//
//
//    /**
//     * 添加缓存
//     * @param pjp
//     * @param mcache
//     * @return
//     * @throws Throwable
//     */
//    @Around(value = "@annotation(mcache)", argNames = "mcache")
//    public Object doBasicProfiling(ProceedingJoinPoint pjp, MCache mcache) throws Throwable {
//        String key = StringUtils.isBlank(mcache.key()) ? createKey(pjp) : mcache.key();
//        int expire = mcache.expire();
//        /**
//         * 缓存去取
//         */
//        Object object = MCacheKit.get(key);
//
//        if (object != null) {
//            return object;
//        }
//
//        /**
//         * 执行调用
//         */
//        object = pjp.proceed();
//
//        //add 结果到缓存
//        if (object != null && expire > 1 && StringUtils.isNotBlank(key)) {
//            MCacheKit.add(key, expire, object);
//        }
//
//        return object;
//    }
//
//    /**
//     * 清除缓存
//     * @param cleanCache
//     */
//    @After(value = "@annotation(cleanCache)", argNames = "cleanCache")
//    public void doBasicProfiling(CleanCache cleanCache){
//        String key = cleanCache.key();
//        if (StringUtils.isBlank(key)) {
//            new Throwable("key不能为空！");
//        }
//        // 缓存去取
//        Object object = MCacheKit.get(key);
//        if (object != null) {
//            MCacheKit.delete(key);
//        }
//    }
//
//    /**
//     * 生成缓存key
//     *
//     * @param pjp
//     * @return
//     */
//    private String createKey(ProceedingJoinPoint pjp) {
//        StringBuffer crudeKey = new StringBuffer(pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName());
//        Object[] params = pjp.getArgs();
//        if (params != null) {
//            ByteArrayOutputStream byt = null;
//            ObjectOutputStream oos = null;
//            try {
//                byt = new ByteArrayOutputStream();
//                oos = new ObjectOutputStream(byt);
//                for (Object obj : params) {
//                    oos.writeObject(obj);
//                    String str = byt.toString("ISO-8859-1");
//                    str = java.net.URLEncoder.encode(str, "UTF-8");
//                    crudeKey.append("." + str);
//                    oos.reset();
//                    byt.reset();
//                }
//            } catch (Exception e) {
//                logger.error("生成 cache key 失败:" + e);
//                return null;
//            } finally {
//                try {
//                    oos.close();
//                    byt.close();
//                } catch (IOException e) {
//                }
//            }
//        }
//        //防止　key 太长
//        return Md5Utils.md5(crudeKey.toString());
//    }
//}
