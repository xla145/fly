package com.xula.event;



import com.xula.entity.Member;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 
 * @author caixb
 *
 */
@Data
public class EventModel {

	private Map<String, Object> param;
	private Member member;
	private HttpServletRequest request;
	private HttpServletResponse response;
}
