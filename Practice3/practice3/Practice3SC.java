package com.clt.apps.opus.dou.doutraining.practice3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.dou.doutraining.practice3.basic.Practice3BC;
import com.clt.apps.opus.dou.doutraining.practice3.basic.Practice3BCImpl;
import com.clt.apps.opus.dou.doutraining.practice3.event.DouTrn0003Event;
import com.clt.apps.opus.dou.doutraining.practice3.vo.CarrierVO;
import com.clt.apps.opus.dou.sinhvien.sinhvien.basic.SinhVienBC;
import com.clt.apps.opus.dou.sinhvien.sinhvien.basic.SinhVienBCImpl;
import com.clt.apps.opus.dou.sinhvien.sinhvien.event.SinhvienEvent;
import com.clt.apps.opus.dou.sinhvien.sinhvien.vo.SinhVienVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;

public class Practice3SC extends ServiceCommandSupport {
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
		
		if (e.getEventName().equalsIgnoreCase("DouTrn0003Event")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH) ||e.getFormCommand().isCommand(FormCommand.SEARCH01) ) {
				eventResponse = searchCarrier(e);
			}else if (e.getFormCommand().isCommand(FormCommand.DEFAULT)) {
				eventResponse = initData();
			} else if (e.getFormCommand().isCommand(FormCommand.SEARCH02)) {
				eventResponse = searchLaneByPartner(e);
			} else if (e.getFormCommand().isCommand(FormCommand.SEARCH03)) {
				eventResponse = searchTradeByPartnerAndLane(e);
			} else if (e.getFormCommand().isCommand(FormCommand.SEARCH04)) {
				eventResponse = searchTotalSum(e);
			}
		}
		return eventResponse;
	}
	
	
	//Get the data of carrier or details 
	private EventResponse searchCarrier(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0003Event event = (DouTrn0003Event) e;
		Practice3BC command = new Practice3BCImpl();

		try {
			List<CarrierVO> list = command.searchCarrier(event.getCarrierVO(), event.getOtherVO());
			eventResponse.setRsVoList(list);
		} catch (EventException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}

	//Get the data of totalSum
	private EventResponse searchTotalSum(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0003Event event = (DouTrn0003Event) e;
		Practice3BC command = new Practice3BCImpl();

		try {
			List<CarrierVO> list = command.searchCarrier(event.getCarrierVO(),event.getOtherVO());
			Map<String, String> totalSum = new HashMap<String, String>();
			int countCurr = list.size();
			totalSum.put("countCurr", String.valueOf(countCurr));
			
			int count = 0;
			if (null != list && list.size() > 0) {
				for (CarrierVO carrierVO : list) {
					count++;
					totalSum.put("curr" + count, carrierVO.getLoclCurrCd());
					totalSum.put("rev" + count, carrierVO.getInvRevActAmt());
					totalSum.put("exp" + count, carrierVO.getInvExpActAmt());
				}

			}
			eventResponse.setETCData(totalSum);

		} catch (EventException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}
	
	//Get the data for comboBox Partner
	private EventResponse initData() throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice3BC command = new Practice3BCImpl();

		try {
			List<String> listPartner = command.searchPartner();
			StringBuilder partnerBuilder = new StringBuilder();
			if (null != listPartner && listPartner.size() > 0) {
				for (int i = 0; i < listPartner.size(); i++) {
					partnerBuilder.append(listPartner.get(i));
					if (i < listPartner.size() - 1) {
						partnerBuilder.append("|");
					}
				}
				eventResponse.setETCData("partners", partnerBuilder.toString());
			}

		} catch (EventException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}
	
	//Get the data for comboBox Lane
	private EventResponse searchLaneByPartner(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0003Event event = (DouTrn0003Event) e;
		Practice3BC command = new Practice3BCImpl();

		try {
			List<String> listLane = command.searchLane(event.getCarrierVO());
			StringBuilder laneBuilder = new StringBuilder();
			
			if (!listLane.isEmpty()) {
				for (int i = 0; i < listLane.size(); i++) {
					laneBuilder.append(listLane.get(i));
					if (i < listLane.size() - 1) {
						laneBuilder.append("|");
					}
				}
				eventResponse.setETCData("lanes", laneBuilder.toString());
			}

		} catch (EventException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}
	
	//Get the data for comboBox Trade
	private EventResponse searchTradeByPartnerAndLane(Event e)
			throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0003Event event = (DouTrn0003Event) e;
		Practice3BC command = new Practice3BCImpl();

		try {
			List<String> listTrade = command.searchTrade(event.getCarrierVO());
			StringBuilder tradeBuilder = new StringBuilder();

			if (null != listTrade && listTrade.size() > 0) {
				for (int i = 0; i < listTrade.size(); i++) {
					tradeBuilder.append(listTrade.get(i));
					if (i < listTrade.size() - 1) {
						tradeBuilder.append("|");
					}
				}
				eventResponse.setETCData("trades", tradeBuilder.toString());
			}

		} catch (EventException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		return eventResponse;
	}
}
