package com.clt.apps.opus.dou.doutraining.practice4.event;

import javax.servlet.http.HttpServletRequest;

import com.bea.core.repackaged.jdt.internal.compiler.apt.util.Util;
import com.clt.apps.opus.dou.doutraining.practice3.vo.CarrierVO;
import com.clt.apps.opus.dou.doutraining.practice4.vo.CarrierVOS;
import com.clt.apps.opus.esm.clv.practice1.errmsgp1.vo.ErrMsgVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;

public class DOU_TRN_0004HTMLAction extends HTMLActionSupport {
	public DOU_TRN_0004HTMLAction() {}

	@Override
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		FormCommand command = FormCommand.fromRequest(request);
		DouTrn0004Event event = new DouTrn0004Event();
		
		if (command.isCommand(FormCommand.SEARCH)) {
//			event.setCarrierVO((CarrierVOS)getVO(request,CarrierVOS.class,""));
			CarrierVOS carrierVO = new CarrierVOS();
			carrierVO.setJoCrrCd(JSPUtil.getParameter(request, "s_crr_cd", ""));
			carrierVO.setVndrSeq(JSPUtil.getParameter(request, "s_vendor", ""));
			String frDate = JSPUtil.getParameter(request, "s_fr_date");
			String toDate = JSPUtil.getParameter(request, "s_to_date");
			String Date = frDate.concat(",").concat(toDate);
			carrierVO.setCreDt(Date);
			event.setCarrierVO(carrierVO);
		}else if (command.isCommand(FormCommand.MULTI)) {
			event.setCarrierVOS((CarrierVOS[]) getVOs(request, CarrierVOS.class, ""));
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
