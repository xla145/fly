package com.xula.event;

import org.springframework.context.ApplicationEvent;

/**
 * 登出事件
 * <p>
 * 目前登出事件里只有uid参数
 *
 * @author caibin
 */
public class LoginOutEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    public LoginOutEvent(final Object content) {
        super(content);
    }
}
