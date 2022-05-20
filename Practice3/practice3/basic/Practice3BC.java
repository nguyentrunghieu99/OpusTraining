package com.clt.apps.opus.dou.doutraining.practice3.basic;

import java.util.List;

import com.clt.apps.opus.dou.doutraining.practice3.vo.CarrierVO;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;

public interface Practice3BC {
	public List<CarrierVO> searchCarrier(CarrierVO carrierVO) throws EventException, DAOException;
	public List<CarrierVO> searchTotalSum(CarrierVO carrierVO) throws EventException, DAOException;
	
	public List<CarrierVO> searchPartner() throws EventException, DAOException;
	public List<CarrierVO> searchLane(CarrierVO carrierVO) throws EventException, DAOException;
	public List<CarrierVO> searchTrade(CarrierVO carrierVO) throws EventException, DAOException;
	
}
