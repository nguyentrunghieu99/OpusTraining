package com.clt.apps.opus.dou.doutraining.practice4;

import java.util.List;

import com.clt.apps.opus.dou.doutraining.practice3.basic.Practice3BC;
import com.clt.apps.opus.dou.doutraining.practice3.basic.Practice3BCImpl;
import com.clt.apps.opus.dou.doutraining.practice3.vo.CarrierVO;
import com.clt.apps.opus.dou.doutraining.practice4.basic.Practice4BC;
import com.clt.apps.opus.dou.doutraining.practice4.basic.Practice4BCImpl;
import com.clt.apps.opus.dou.doutraining.practice4.event.DouTrn0004Event;
import com.clt.apps.opus.dou.doutraining.practice4.vo.CarrierVOS;
import com.clt.apps.opus.esm.clv.practice1.errmsgp1.basic.ErrMsgP1BC;
import com.clt.apps.opus.esm.clv.practice1.errmsgp1.basic.ErrMsgP1BCImpl;
import com.clt.apps.opus.esm.clv.practice1.errmsgp1.event.Practice1ViewEvent;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;

public class Practice4SC extends ServiceCommandSupport{
	private SignOnUserAccount account = null;
	
	public void doStart() {
		log.debug("CarrierSC 시작");
		try {
			// 일단 comment --> 로그인 체크 부분
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	/**
	 * SinhVien system 업무 시나리오 마감작업<br>
	 * 업무 시나리오 종료시 관련 내부객체 해제<br>
	 */
	public void doEnd() {
		log.debug("CarrierSC 종료");
	}
	
	@Override
	public EventResponse perform(Event e) throws EventException {
		EventResponse eventResponse = null;
		if (e.getEventName().equalsIgnoreCase("DouTrn0004Event")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchCarrier(e);
			}else if (e.getFormCommand().isCommand(FormCommand.DEFAULT)) {
				eventResponse = initData();
			}else if (e.getFormCommand().isCommand(FormCommand.MULTI)) {
				eventResponse = crudCarrier(e);
			}
		}
		return eventResponse;
	}
	
	private EventResponse searchCarrier(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0004Event event = (DouTrn0004Event) e;
		Practice4BC command = new Practice4BCImpl();

		try {
			List<CarrierVOS> list = command.getCarrier(event.getCarrierVO());

			eventResponse.setRsVoList(list);
		} catch (EventException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}
	
	private EventResponse initData() throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice4BC command = new Practice4BCImpl();

		try {
			List<CarrierVOS> listCrrCd = command.getCrrCd();
			StringBuilder CrrBuilder = new StringBuilder();
			if (null != listCrrCd && listCrrCd.size() > 0) {
				for (int i = 0; i < listCrrCd.size(); i++) {
					CrrBuilder.append(listCrrCd.get(i).getJoCrrCd());
					if (i < listCrrCd.size() - 1) {
						CrrBuilder.append("|");
					}
				}
			}
			
			List<String> listRLane = command.getRlandCd();
			StringBuilder RLaneBuilder = new StringBuilder();
			if (null != listRLane && listRLane.size() > 0) {
				for (int i = 0; i < listRLane.size(); i++) {
					RLaneBuilder.append(listRLane.get(i));
					if (i < listRLane.size() - 1) {
						RLaneBuilder.append("|");
					}
				}
			}
				eventResponse.setETCData("CrrCd", CrrBuilder.toString());
				eventResponse.setETCData("RlandCd", RLaneBuilder.toString());
				

		} catch (EventException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}
	
	private EventResponse crudCarrier(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0004Event event = (DouTrn0004Event) e;
		Practice4BC command = new Practice4BCImpl();;
		try {
			begin();
			command.crudCarrier(event.getCarrierVOS(), account);
			eventResponse.setUserMessage(new ErrorHandler("XXXXXXXX")
					.getUserMessage());
			commit();
		} catch (EventException ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage());
		} catch (Exception ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage());
		}
		return eventResponse;
	}


}
