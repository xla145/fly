package com.xula.event;

import org.springframework.context.ApplicationEvent;

/**
 * 登录事件
 * 
 * @author xla
 *
 */
public class AccessArticleEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public AccessArticleEvent(final Object content) {
		super(content);
	}
}
