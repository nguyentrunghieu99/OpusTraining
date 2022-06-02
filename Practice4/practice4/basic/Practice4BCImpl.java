package com.clt.apps.opus.dou.doutraining.practice4.basic;

import java.util.ArrayList;
import java.util.List;








import com.clt.apps.opus.dou.doutraining.practice4.vo.CarrierVOS;
import com.clt.apps.opus.dou.doutraining.practice4.integration.CarrierDBDAO;
import com.clt.apps.opus.esm.clv.practice1.errmsgp1.vo.ErrMsgVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;

public class Practice4BCImpl  extends BasicCommandSupport implements Practice4BC {
	private transient CarrierDBDAO dbDao = null;
	
	public Practice4BCImpl(){
		dbDao = new CarrierDBDAO();
	}

	@Override
	public List<CarrierVOS> getCarrier(CarrierVOS carrierVO)
			throws EventException, DAOException {
		try {
			return dbDao.getCarrier(carrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public List<CarrierVOS> getCrrCd() throws EventException, DAOException {
		try {
			return dbDao.getCrrCd();
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public void crudCarrier(CarrierVOS[] carrierVOS, SignOnUserAccount account) throws EventException {
		try {
			List<CarrierVOS> insertVoList = new ArrayList<CarrierVOS>();
			List<CarrierVOS> updateVoList = new ArrayList<CarrierVOS>();
			List<CarrierVOS> deleteVoList = new ArrayList<CarrierVOS>();
			for (int i = 0; i < carrierVOS.length; i++) {
				if (carrierVOS[i].getIbflag().equals("I")) {
					if(dbDao.checkDuplicate(carrierVOS[i]) == 1)
					{
						throw new DAOException(new ErrorHandler("ERR00000", new String[] {carrierVOS[i].getJoCrrCd() + " - " + carrierVOS[i].getRlaneCd()}).getMessage());
					}
					carrierVOS[i].setCreUsrId(account.getUsr_id());
					carrierVOS[i].setUpdUsrId(account.getUsr_id());
					insertVoList.add(carrierVOS[i]);
				} else if(carrierVOS[i].getIbflag().equals("U")) {
					carrierVOS[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(carrierVOS[i]);
				}else if (carrierVOS[i].getIbflag().equals("D")) {
					deleteVoList.add(carrierVOS[i]);
				}
			}

			if (insertVoList.size() > 0) {
				dbDao.addCarrier(insertVoList);
			}

			if (updateVoList.size() > 0) {
				dbDao.updateCarrier(updateVoList);
			}
			if (deleteVoList.size() > 0) {
				dbDao.removeCarrier(deleteVoList);
			}
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage());
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage());
		}
	}

	@Override
	public List<String> getRlandCd() throws EventException, DAOException {
		try {
			return dbDao.getRlandCd();
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}

	}
		
	}

