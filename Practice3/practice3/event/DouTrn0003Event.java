package com.clt.apps.opus.dou.doutraining.practice3.event;

import com.clt.apps.opus.dou.doutraining.practice3.vo.CarrierVO;
import com.clt.apps.opus.dou.sinhvien.sinhvien.vo.SinhVienVO;
import com.clt.framework.support.layer.event.EventSupport;

public class DouTrn0003Event extends EventSupport{
	
private static final long serialVersionUID = 1L;
	
	/** Table Value Object 조회 조건 및 단건 처리  */
CarrierVO carrierVO = null;
	
	/** Table Value Object Multi Data 처리 */
CarrierVO[] carrierVOs = null;

	public DouTrn0003Event(){}
	
	public void setCarrierVO(CarrierVO carrierVO){
		this.carrierVO = carrierVO;
	}

	public void setCarrierVOS(CarrierVO[] carrierVOs){
		this. carrierVOs = carrierVOs;
	}

	public CarrierVO getCarrierVO(){
		return carrierVO;
	}

	public CarrierVO[] getCarrierVOS(){
		return carrierVOs;
	}

}
