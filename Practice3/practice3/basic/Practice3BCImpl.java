package com.clt.apps.opus.dou.doutraining.practice3.basic;

import java.util.List;

import com.clt.apps.opus.dou.doutraining.practice3.integration.CarrierDBDAO;
import com.clt.apps.opus.dou.doutraining.practice3.vo.CarrierVO;
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
	public List<CarrierVO> searchCarrier(CarrierVO carrierVO) throws EventException,
			DAOException {
		
		try {
			return dbDao.searchCarrier(carrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public List<CarrierVO> searchPartner() throws EventException, DAOException {
		try {
			return dbDao.searchPartner();
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}


	@Override
	public List<CarrierVO> searchTrade(CarrierVO carrierVO) throws EventException, DAOException {
		try {
			return dbDao.searchTradeByPartnerAndLane(carrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public List<CarrierVO> searchLane(CarrierVO carrierVO)throws EventException, DAOException{
	try {
		return dbDao.searchLaneByPartner(carrierVO);
	} catch(DAOException ex) {
		throw new EventException(new ErrorHandler(ex).getMessage(),ex);
	} catch (Exception ex) {
		throw new EventException(new ErrorHandler(ex).getMessage(),ex);
	}

}

	@Override
	public List<CarrierVO> searchTotalSum(CarrierVO carrierVO)
			throws EventException, DAOException {
		try {
			return dbDao.searchTotalSum(carrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
}
