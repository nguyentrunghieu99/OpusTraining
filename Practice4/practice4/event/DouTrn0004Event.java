package com.clt.apps.opus.dou.doutraining.practice4.event;

import com.clt.apps.opus.dou.doutraining.practice4.vo.CarrierVOS;
import com.clt.framework.support.layer.event.EventSupport;

public class DouTrn0004Event extends EventSupport {
private static final long serialVersionUID = 1L;
	
	/** Table Value Object 조회 조건 및 단건 처리  */
CarrierVOS carrierVO = null;
	
	/** Table Value Object Multi Data 처리 */
CarrierVOS[] carrierVOs = null;

	public DouTrn0004Event(){}
	
	public void setCarrierVO(CarrierVOS carrierVO){
		this.carrierVO = carrierVO;
	}

	public void setCarrierVOS(CarrierVOS[] carrierVOs){
		this. carrierVOs = carrierVOs;
	}

	public CarrierVOS getCarrierVO(){
		return carrierVO;
	}

	public CarrierVOS[] getCarrierVOS(){
		return carrierVOs;
	}

}
