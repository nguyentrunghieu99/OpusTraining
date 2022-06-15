package com.clt.apps.opus.dou.doutraining.practice3.integration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.dou.doutraining.practice3.vo.CarrierVO;
import com.clt.apps.opus.dou.doutraining.practice3.vo.OtherVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;

public class CarrierDBDAO extends DBDAOSupport {

	//@SuppressWarnings(“unchecked”): to tell the compiler not to warn you about using an unsafe cast.
	
	/*
	 * This is the function used to get the data of carrier or details or totalSum
	 * @param CarrierVO carrierVO , OtherVO otherVO
	 * @return List<CarrierVO>
	 * @exception DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<CarrierVO> searchCarrier(CarrierVO carrierVO,OtherVO otherVO)throws DAOException {
		DBRowSet dbRowset = null;
		List<CarrierVO> list = new ArrayList<CarrierVO>();
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		param.put("fr_acct_yrmon", otherVO.getFrDate());
		param.put("to_acct_yrmon", otherVO.getToDate());
		velParam.put("fr_acct_yrmon", otherVO.getFrDate());
		velParam.put("to_acct_yrmon", otherVO.getToDate());
		try {
			//If the comboBox partner has data, then put the data into velParam
			if (!carrierVO.getJoCrrCd().isEmpty() && !carrierVO.getJoCrrCd().equals("ALL")) {
				List<String> listCrrCd = new ArrayList<String>();
				String[] carriers = carrierVO.getJoCrrCd().split(",");
				for (int i = 0; i < carriers.length; i++) {
					listCrrCd.add(carriers[i]);
				}
				velParam.put("listCrrCd", listCrrCd);
				
				//If the comboBox Rland has data, then put the data into param and velParam
				if (!carrierVO.getRlaneCd().isEmpty()) {
					param.put("rlane_cd", carrierVO.getRlaneCd());
					velParam.put("rlane_cd", carrierVO.getRlaneCd());
					
					//If the comboBox Trade has data, then put the data into param and velParam
					if (!otherVO.getTrdCd().isEmpty()) {
						param.put("trd_cd", otherVO.getTrdCd());
						velParam.put("trd_cd", otherVO.getTrdCd());
					}
				}
			}
			
			// FormCommand.SEARCH = 2 , Get data of carrier
			if(otherVO.getCommand() == 2){
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new CarrierDBDAOCarrierVORSQL(), param,velParam);
			list = (List) RowSetUtil.rowSetToVOs(dbRowset, CarrierVO.class);
			
			// FormCommand.SEARCH01 = 101 , Get data of details
			}else if(otherVO.getCommand() == 101){
				dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new CarrierDBDAOCarrierDetailsRSQL(), param,velParam);
				list = (List) RowSetUtil.rowSetToVOs(dbRowset, CarrierVO.class);
				
			// FormCommand.SEARCH04 = 104 , Get data of Total Sum
			}else if(otherVO.getCommand() == 104){
				dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new CarrierDBDAOGetTotalSumRSQL(), param,velParam);
				list = (List) RowSetUtil.rowSetToVOs(dbRowset, CarrierVO.class);
			}
			
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}	

	/*
	 * This is the function used to get the data Partner comboBox
	 * @param null
	 * @return List<String>
	 * @exception DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<String> searchPartner() throws DAOException {

		DBRowSet dbRowset = null;
		List<String> listPartner = new ArrayList();

		try {
			dbRowset = new SQLExecuter("").executeQuery(
					(ISQLTemplate) new CarrierDBDAOSearchPartnerRSQL(), null,
					null);
			while(dbRowset.next()){
				listPartner.add(dbRowset.getString(1));
			}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return listPartner;

	}
	
	/*
	 * This is the function used to get the data Lane comboBox
	 * @param null
	 * @return List<String>
	 * @exception DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<String> searchLaneByPartner(CarrierVO carrierVO)
			throws DAOException {

		DBRowSet dbRowset = null;
		List<String> listLane = new ArrayList();

		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> velParam = new HashMap<String, Object>();

		try {
			if (carrierVO != null) {
				List<String> listCrrCd = new ArrayList<>();
				if (null != carrierVO.getJoCrrCd()) {
					String[] JoCrrCds = carrierVO.getJoCrrCd().split(",");
					for (int i = 0; i < JoCrrCds.length; i++) {
						listCrrCd.add(JoCrrCds[i]);
					}
				}
				param.put("listCrrCd", listCrrCd);

				velParam.put("listCrrCd", listCrrCd);
			}

			dbRowset = new SQLExecuter("").executeQuery(
					(ISQLTemplate) new CarrierDBDAOGetLaneByPartnerRSQL(),
					param, velParam);
			while(dbRowset.next()){
				listLane.add(dbRowset.getString(1));
			}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return listLane;

	}

	/*
	 * This is the function used to get the data Trade comboBox
	 * @param null
	 * @return List<String>
	 * @exception DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<String> searchTradeByPartnerAndLane(CarrierVO carrierVO)
			throws DAOException {

		DBRowSet dbRowset = null;
		List<String> listTrade = new ArrayList<String>();

		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> velParam = new HashMap<String, Object>();

		try {
			if (carrierVO != null) {
				List<String> listCrrCd = new ArrayList<>();
				if (null != carrierVO.getJoCrrCd()) {
					String[] JoCrrCds = carrierVO.getJoCrrCd().split(",");
					for (int i = 0; i < JoCrrCds.length; i++) {
						listCrrCd.add(JoCrrCds[i]);
					}
				}
				param.put("listCrrCd", listCrrCd);
				param.put("rlane_cd", carrierVO.getRlaneCd());

				velParam.put("listCrrCd", listCrrCd);
				velParam.put("rlane_cd", carrierVO.getRlaneCd());
			}

			dbRowset = new SQLExecuter("")
					.executeQuery(
							(ISQLTemplate) new CarrierDBDAOGetTradeByPartnerAndLaneRSQL(),
							param, velParam);
			while(dbRowset.next()){
				listTrade.add(dbRowset.getString(1));
			}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return listTrade;

	}
}
