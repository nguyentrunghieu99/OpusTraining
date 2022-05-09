/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PRACTICE1_VIEWHTMLAction.java
*@FileTitle : Practice1 Training
*Open Issues :
*Change history :
*@LastModifyDate : 2022.04.19
*@LastModifier : 
*@LastVersion : 1.0
* 2022.04.19 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice1.errmsgp1.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.apps.opus.esm.clv.practice1.errmsgp1.vo.ErrMsgVO;

/**
 * HTTP Parser<br>
 * - com.clt.apps.opus.esm.clv.practice1 화면을 통해 서버로 전송되는 HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
 * - Parsing 한 정보를 Event로 변환, request에 담아 Practice1SC로 실행요청<br>
 * - Practice1SC에서 View(JSP)로 실행결과를 전송하는 EventResponse를 request에 셋팅<br>
 * @author Hieu Nguyen
 * @see Practice1Event 참조
 * @since J2EE 1.6
 */

public class PRACTICE1_VIEWHTMLAction extends HTMLActionSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * PRACTICE1_VIEWHTMLAction 객체를 생성
	 */
	public PRACTICE1_VIEWHTMLAction() {}

	/**
	 * HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
	 * HttpRequst의 정보를 Practice1Event로 파싱하여 request에 셋팅<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return Event Event interface를 구현한 객체
	 * @exception HTMLActionException
	 * 
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		
    	FormCommand command = FormCommand.fromRequest(request);
		Practice1ViewEvent event = new Practice1ViewEvent();
		
		if(command.isCommand(FormCommand.MULTI)) {
//			String regex ="^([A-Z]{3})([0-9]{5})$";
			event.setErrMsgVOS((ErrMsgVO[])getVOs(request, ErrMsgVO .class,""));
//			ErrMsgVO[] ErrMsgVOs = event.getErrMsgVOS();
//			for(int i=0; i<ErrMsgVOs.length;i++){
//				if(ErrMsgVOs[i].getErrMsgCd().matches(regex)==false){
//					throw new HTMLActionException("8 characters are required, the first 3 characters are uppercase letters, the last 5 characters are numbers");
//					
//				}
//			}
			
		}
		else if(command.isCommand(FormCommand.SEARCH)) {
//			event.setErrMsgVO((ErrMsgVO)getVO(request, ErrMsgVO .class));
			
//Lấy data từ form html
			
			ErrMsgVO errmsgVO = new ErrMsgVO();
			errmsgVO.setErrMsgCd(JSPUtil.getParameter(request, "s_err_msg_cd",""));
			errmsgVO.setErrMsg(JSPUtil.getParameter(request, "s_err_msg", ""));
			event.setErrMsgVO(errmsgVO);
		}

		return  event;
	}

	/**
	 * HttpRequest의 attribute에 업무시나리오 수행결과 값 저장<br>
	 * ServiceCommand에서 View(JSP)로 실행결과를 전송하는 ResultSet을 request에 셋팅<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param eventResponse EventResponse interface를 구현한 객체
	 */
	public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
		request.setAttribute("EventResponse", eventResponse);
	}

	/**
	 * HttpRequest의 attribute에 HttpRequest 파싱 수행결과 값 저장<br>
	 * HttpRequest 파싱 수행결과 값 request에 셋팅<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param event Event interface를 구현한 객체
	 */
	public void doEnd(HttpServletRequest request, Event event) {
		request.setAttribute("Event", event);
	}
}