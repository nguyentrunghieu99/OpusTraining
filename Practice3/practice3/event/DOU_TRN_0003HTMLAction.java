package com.clt.apps.opus.dou.doutraining.practice3.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.dou.doutraining.practice3.vo.CarrierVO;
import com.clt.apps.opus.dou.doutraining.practice3.vo.OtherVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;

public class DOU_TRN_0003HTMLAction extends HTMLActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DOU_TRN_0003HTMLAction() {
	}

	@Override
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		FormCommand command = FormCommand.fromRequest(request);
		DouTrn0003Event event = new DouTrn0003Event();
		
		//SEARCH :  Get carrier
		//SEARCH01: Get details
		//SEARCH04: Get total sum
		if (command.isCommand(FormCommand.SEARCH)||command.isCommand(FormCommand.SEARCH01)||command.isCommand(FormCommand.SEARCH04)) {
			CarrierVO carrierVO = new CarrierVO();
			OtherVO otherVO = new OtherVO();
			carrierVO.setJoCrrCd(JSPUtil.getParameter(request, "s_partner", ""));
			carrierVO.setRlaneCd(JSPUtil.getParameter(request, "s_lane",""));
			otherVO.setTrdCd(JSPUtil.getParameter(request, "s_trade"));
			otherVO.setFrDate(JSPUtil.getParameter(request, "txt_fr_yrmon"));
			otherVO.setToDate(JSPUtil.getParameter(request, "txt_to_yrmon"));
			otherVO.setCommand(command.getCommand());
			event.setCarrierVO(carrierVO);
			event.setOtherVO(otherVO);	
			
		//SEARCH02 get Rlane
		}else if (command.isCommand(FormCommand.SEARCH02)) {
			CarrierVO carrierVO = new CarrierVO();
			carrierVO.setJoCrrCd(JSPUtil.getParameter(request, "s_partner", ""));
			event.setCarrierVO(carrierVO);
			
		//SEARCH03 get Trade	
		} else if (command.isCommand(FormCommand.SEARCH03)) {
			CarrierVO carrierVO = new CarrierVO();
			carrierVO.setJoCrrCd(JSPUtil.getParameter(request, "s_partner", ""));
			carrierVO.setRlaneCd(JSPUtil.getParameter(request, "lane"));
			event.setCarrierVO(carrierVO);			
		}

		return event;
	}

	public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
		request.setAttribute("EventResponse", eventResponse);
	}

	public void doEnd(HttpServletRequest request, Event event) {
		request.setAttribute("Event", event);
	}
}
