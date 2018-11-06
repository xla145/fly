package com.xula.event;
import org.springframework.context.ApplicationEvent;

/**
 * 注册事件
 * 
 * @author caixb
 *
 */
public class RegisterEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;

	public RegisterEvent(final Object content) {
        super(content);
    }
}
