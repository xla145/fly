package com.xula.event;

import org.springframework.context.ApplicationEvent;

/**
 * 用户消息
 * 
 * @author xla
 *
 */
public class MemberMessageEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public MemberMessageEvent(final Object content) {
		super(content);
	}
}
