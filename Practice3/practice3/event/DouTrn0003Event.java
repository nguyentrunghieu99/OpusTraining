package com.clt.apps.opus.dou.doutraining.practice3.event;

import com.clt.apps.opus.dou.doutraining.practice3.vo.CarrierVO;
import com.clt.apps.opus.dou.doutraining.practice3.vo.OtherVO;
import com.clt.apps.opus.dou.sinhvien.sinhvien.vo.SinhVienVO;
import com.clt.framework.support.layer.event.EventSupport;

public class DouTrn0003Event extends EventSupport{
	
private static final long serialVersionUID = 1L;
	
	/** Table Value Object 조회 조건 및 단건 처리  */
CarrierVO carrierVO = null;
OtherVO otherVO = null;

	public DouTrn0003Event(){}
	
	public void setCarrierVO(CarrierVO carrierVO){
		this.carrierVO = carrierVO;
	}

	public CarrierVO getCarrierVO(){
		return carrierVO;
	}	
	
	public void setOtherVO(OtherVO otherVO){
		this.otherVO = otherVO;
	}
	
	public OtherVO getOtherVO(){
		return otherVO;
	}

}
