/*=========================================================
*Copyright(c) 2020 CyberLogitec
*@FileName : DouTrainingSC.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2020.03.17
*@LastModifier : 
*@LastVersion : 1.0
* 2020.03.17 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.dou.doutraining;

import java.util.List;

import com.clt.apps.opus.dou.doutraining.codemgmt.basic.CodeMgmtBC;
import com.clt.apps.opus.dou.doutraining.codemgmt.basic.CodeMgmtBCImpl;
import com.clt.apps.opus.dou.doutraining.codemgmt.event.DouTrn0002Event;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.CodeVO;
import com.clt.apps.opus.dou.doutraining.errmsgmgmt.basic.ErrMsgMgmtBC;
import com.clt.apps.opus.dou.doutraining.errmsgmgmt.basic.ErrMsgMgmtBCImpl;
import com.clt.apps.opus.dou.doutraining.errmsgmgmt.event.DouTrn001Event;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.dou.doutraining.errmsgmgmt.vo.ErrMsgVO;


/**
 * ALPS-DouTraining Business Logic ServiceCommand - ALPS-DouTraining 대한 비지니스 트랜잭션을 처리한다.
 * 
 * @author Vi Nguyen
 * @see ErrMsgMgmtDBDAO
 * @since J2EE 1.6
 */

public class DouTrainingSC extends ServiceCommandSupport {
	// Login User Information
	private SignOnUserAccount account = null;

	/**
	 * DouTraining system 업무 시나리오 선행작업<br>
	 * 업무 시나리오 호출시 관련 내부객체 생성<br>
	 */
	public void doStart() {
		log.debug("DouTrainingSC 시작");
		try {
			// 일단 comment --> 로그인 체크 부분
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	/**
	 * DouTraining system 업무 시나리오 마감작업<br>
	 * 업무 시나리오 종료시 관련 내부객체 해제<br>
	 */
	public void doEnd() {
		log.debug("DouTrainingSC 종료");
	}

	/**
	 * 각 이벤트에 해당하는 업무 시나리오 수행<br>
	 * ALPS-DouTraining system 업무에서 발생하는 모든 이벤트의 분기처리<br>
	 * 
	 * @param e Event
	 * @return EventResponse
	 * @exception EventException
	 */
	public EventResponse perform(Event e) throws EventException {
		// RDTO(Data Transfer Object including Parameters)
		EventResponse eventResponse = null;

		// SC가 여러 이벤트를 처리하는 경우 사용해야 할 부분
		if (e.getEventName().equalsIgnoreCase("DouTrn001Event")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchErrMsg(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.MULTI)) {
				eventResponse = manageErrMsg(e);
			}
		}
		
		
		if (e.getEventName().equalsIgnoreCase("DouTrn0002Event")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchCode(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.MULTI)) {
				eventResponse = manageCode(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.SEARCH01)){
				eventResponse = searchDetails(e);
			}
			else if(e.getFormCommand().isCommand(FormCommand.MULTI01)){
				eventResponse = manageDetails(e);
			}
		}
		
		return eventResponse;
	}
	/**
	 * DOU_TRN_001 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchErrMsg(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn001Event event = (DouTrn001Event)e;
		ErrMsgMgmtBC command = new ErrMsgMgmtBCImpl();

		try{
			List<ErrMsgVO> list = command.searchErrMsg(event.getErrMsgVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	private EventResponse searchCode(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0002Event event = (DouTrn0002Event)e;
		CodeMgmtBC command = new CodeMgmtBCImpl();
		
		

		try{
			List<CodeVO> list = command.searchCode(event.getCodeVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	private EventResponse searchDetails(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0002Event event = (DouTrn0002Event)e;
		CodeMgmtBC command = new CodeMgmtBCImpl();

		try{
			List<CodeVO> list = command.searchDetails(event.getDetailsVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	/**
	 * DOU_TRN_001 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 *
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse manageErrMsg(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn001Event event = (DouTrn001Event)e;
		ErrMsgMgmtBC command = new ErrMsgMgmtBCImpl();
		try{
			begin();
			command.manageErrMsg(event.getErrMsgVOS(),account);
			eventResponse.setUserMessage(new ErrorHandler("XXXXXXXXX").getUserMessage());
			commit();
		} catch(EventException ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch(Exception ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		return eventResponse;
	}
	
	private EventResponse manageCode(Event e)throws EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0002Event event = (DouTrn0002Event)e;
		CodeMgmtBC command = new CodeMgmtBCImpl();
		
		try{
			begin();
			command.managerCode(event.getCodeVOS(), account);
			eventResponse.setUserMessage(new ErrorHandler("XXXXXXXX").getUserMessage());
			commit();
			
		}catch(EventException ex){
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);		
		}catch(Exception ex){
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);		
		}
		return eventResponse;
	}
	
	private EventResponse manageDetails(Event e)throws EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0002Event event = (DouTrn0002Event)e;
		CodeMgmtBC command = new CodeMgmtBCImpl();
		
		try{
			begin();
			command.managerDetails(event.getDetailsVOS(), account);
			eventResponse.setUserMessage(new ErrorHandler("XXXXXXXXX").getUserMessage());
			commit();
			
		}catch(EventException ex){
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);		
		}catch(Exception ex){
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);		
		}
		return eventResponse;
	}
	
	
}