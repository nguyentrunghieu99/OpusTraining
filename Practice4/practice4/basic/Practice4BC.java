package com.clt.apps.opus.dou.doutraining.practice4.basic;

import java.util.List;

import com.clt.apps.opus.dou.doutraining.practice3.vo.CarrierVO;
import com.clt.apps.opus.dou.doutraining.practice4.vo.CarrierVOS;
import com.clt.apps.opus.esm.clv.practice1.errmsgp1.vo.ErrMsgVO;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.view.signon.SignOnUserAccount;

public interface Practice4BC {
	public List<CarrierVOS> getCarrier(CarrierVOS carrierVO) throws EventException, DAOException;
	public List<CarrierVOS> getCrrCd() throws EventException, DAOException;
	public List<String> getRlandCd() throws EventException, DAOException;
	public void crudCarrier(CarrierVOS[] carrierVOS,SignOnUserAccount account) throws EventException;
}
