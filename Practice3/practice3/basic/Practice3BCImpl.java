package com.clt.apps.opus.dou.doutraining.practice3.basic;

import java.util.List;

import com.clt.apps.opus.dou.doutraining.practice3.integration.CarrierDBDAO;
import com.clt.apps.opus.dou.doutraining.practice3.vo.CarrierVO;
import com.clt.apps.opus.dou.doutraining.practice3.vo.OtherVO;
import com.clt.apps.opus.dou.sinhvien.sinhvien.integration.SinhVienDBDAO;
import com.clt.apps.opus.dou.sinhvien.sinhvien.vo.SinhVienVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;

public class Practice3BCImpl extends BasicCommandSupport implements Practice3BC {
	private transient CarrierDBDAO dbDao = null;
	
	public Practice3BCImpl(){
		dbDao = new CarrierDBDAO();
	}

	@Override
	public List<CarrierVO> searchCarrier(CarrierVO carrierVO, OtherVO otherVO) throws EventException,
			DAOException {
		
		try {
			return dbDao.searchCarrier(carrierVO,otherVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	
	@Override
	public List<String> searchPartner() throws EventException, DAOException {
		try {
			return dbDao.searchPartner();
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}


	@Override
	public List<String> searchTrade(CarrierVO carrierVO) throws EventException, DAOException {
		try {
			return dbDao.searchTradeByPartnerAndLane(carrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public List<String> searchLane(CarrierVO carrierVO)throws EventException, DAOException{
	try {
		return dbDao.searchLaneByPartner(carrierVO);
	} catch(DAOException ex) {
		throw new EventException(new ErrorHandler(ex).getMessage(),ex);
	} catch (Exception ex) {
		throw new EventException(new ErrorHandler(ex).getMessage(),ex);
	}
	}
}
