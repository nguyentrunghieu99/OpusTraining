package com.clt.apps.opus.dou.doutraining.practice3.basic;

import java.util.List;

import com.clt.apps.opus.dou.doutraining.practice3.vo.CarrierVO;
import com.clt.apps.opus.dou.doutraining.practice3.vo.OtherVO;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;

public interface Practice3BC {
	public List<CarrierVO> searchCarrier(CarrierVO carrierVO, OtherVO otherVO) throws EventException, DAOException;
	public List<String> searchPartner() throws EventException, DAOException;
	public List<String> searchLane(CarrierVO carrierVO) throws EventException, DAOException;
	public List<String> searchTrade(CarrierVO carrierVO) throws EventException, DAOException;
	
}
