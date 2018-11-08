package com.xula.event;
import org.springframework.context.ApplicationEvent;

/**
 * 登出事件
 * 
 * 
 * @author xla
 *
 */
public class LoginOutEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;

	public LoginOutEvent(final Object content) {
        super(content);
    }
}
