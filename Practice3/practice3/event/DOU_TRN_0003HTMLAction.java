package com.clt.apps.opus.dou.doutraining.practice3.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.dou.doutraining.practice3.vo.CarrierVO;
import com.clt.apps.opus.dou.sinhvien.sinhvien.event.SinhvienEvent;
import com.clt.apps.opus.dou.sinhvien.sinhvien.vo.SinhVienVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;

public class DOU_TRN_0003HTMLAction extends HTMLActionSupport {
	public DOU_TRN_0003HTMLAction() {
	}

	@Override
	public Event perform(HttpServletRequest request) throws HTMLActionException {

		FormCommand command = FormCommand.fromRequest(request);
		DouTrn0003Event event = new DouTrn0003Event();

		if (command.isCommand(FormCommand.MULTI)) {
			// event.setSinhVienVOS((SinhVienVO[])getVOs(request, SinhVienVO
			// .class,""));
		} else if (command.isCommand(FormCommand.SEARCH)) {
			CarrierVO carrierVO = new CarrierVO();
			carrierVO.setJoCrrCd(JSPUtil.getParameter(request, "s_partner", ""));
			carrierVO.setRlaneCd(JSPUtil.getParameter(request, "s_lane"));
			carrierVO.setTrdCd(JSPUtil.getParameter(request, "s_trade"));
			String Yrmon = JSPUtil.getParameter(request, "txt_fr_yrmon") + "," + JSPUtil.getParameter(request, "txt_to_yrmon");
			carrierVO.setAcctYrmon(Yrmon);

			event.setCarrierVO(carrierVO);
			// event.setCarrierVO((CarrierVO)getVO(request, CarrierVO.class));
		} else if (command.isCommand(FormCommand.SEARCH01)) {
			CarrierVO carrierVO = new CarrierVO();
			carrierVO
					.setJoCrrCd(JSPUtil.getParameter(request, "s_partner", ""));
			event.setCarrierVO(carrierVO);
			
			String Yrmon = JSPUtil.getParameter(request, "txt_fr_yrmon") + "," + JSPUtil.getParameter(request, "txt_to_yrmon");
			carrierVO.setAcctYrmon(Yrmon);
			// event.setCarrierVO((CarrierVO)getVO(request, CarrierVO.class));
		} else if (command.isCommand(FormCommand.SEARCH02)) {
			CarrierVO carrierVO = new CarrierVO();
			carrierVO
					.setJoCrrCd(JSPUtil.getParameter(request, "s_partner", ""));
			carrierVO.setRlaneCd(JSPUtil.getParameter(request, "lane"));
			String Yrmon = JSPUtil.getParameter(request, "txt_fr_yrmon") + "," + JSPUtil.getParameter(request, "txt_to_yrmon");
			carrierVO.setAcctYrmon(Yrmon);
			event.setCarrierVO(carrierVO);
			// event.setCarrierVO((CarrierVO)getVO(request, CarrierVO.class));
		}else if (command.isCommand(FormCommand.SEARCH03)) {
			CarrierVO carrierVO = new CarrierVO();
			carrierVO.setJoCrrCd(JSPUtil.getParameter(request, "s_partner", ""));
			carrierVO.setRlaneCd(JSPUtil.getParameter(request, "s_lane"));
			carrierVO.setTrdCd(JSPUtil.getParameter(request, "s_trade"));
			String Yrmon = JSPUtil.getParameter(request, "txt_fr_yrmon") + "," + JSPUtil.getParameter(request, "txt_to_yrmon");
			carrierVO.setAcctYrmon(Yrmon);

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
