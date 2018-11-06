package com.xula.event;

import org.springframework.context.ApplicationEvent;

/**
 * 登录事件
 *
 * @author caibin
 */
public class LoginEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    public LoginEvent(final Object content) {
        super(content);
    }
}
