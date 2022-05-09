package com.clt.apps.opus.dou.doutraining.codemgmt.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.dou.doutraining.codemgmt.vo.CodeVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailsVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;

public class DOU_TRN_0002HTMLAction extends HTMLActionSupport{
	private static final long serialVersionUID = 1L;
	
	public DOU_TRN_0002HTMLAction(){}
	
	public Event perform(HttpServletRequest request) throws HTMLActionException{
		FormCommand command = FormCommand.fromRequest(request);
		DouTrn0002Event event = new DouTrn0002Event();
		
		if(command.isCommand(FormCommand.MULTI)){
			event.setCodeVOS((CodeVO[])getVOs(request, CodeVO.class,""));
			
		}else if(command.isCommand(FormCommand.SEARCH)){
			CodeVO codeVO = new CodeVO();
			codeVO.setOwnrSubSysCd(JSPUtil.getParameter(request,"s_ownr_sub_sys_cd",""));
			codeVO.setIntgCdId(JSPUtil.getParameter(request, "s_intg_cd_id",""));
			event.setCodeVO(codeVO);
//			event.setCodeVO((CodeVO)getVO(request, CodeVO.class));
			
		}else if(command.isCommand(FormCommand.SEARCH01)){
			DetailsVO detailsVO = new DetailsVO();
			detailsVO.setIntgCdId(JSPUtil.getParameter(request,"intg_cd_id",""));
			event.setDetailsVO(detailsVO);
			
		}else if(command.isCommand(FormCommand.MULTI01)){
			event.setDetailsVOS((DetailsVO[])getVOs(request, DetailsVO.class,""));
		}
		
		return event;
	}
	
	public void doEnd(HttpServletRequest request, EventResponse eventResponse){
		request.setAttribute("EventResponse", eventResponse);
	}
	
	public void doEnd(HttpServletRequest request, Event event) {
		request.setAttribute("Event", event);
	}
}
