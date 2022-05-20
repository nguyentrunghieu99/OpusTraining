package com.clt.apps.opus.dou.doutraining.practice3.integration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.dou.doutraining.practice3.vo.CarrierVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;

public class CarrierDBDAO extends DBDAOSupport {
	@SuppressWarnings("unchecked")
	public List<CarrierVO> searchCarrier(CarrierVO carrierVO)
			throws DAOException {

		DBRowSet dbRowset = null;
		List<CarrierVO> list = new ArrayList();
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		String[] yrMon = carrierVO.getAcctYrmon().split(",");
		param.put("fr_acct_yrmon", yrMon[0]);
		param.put("to_acct_yrmon",yrMon[1]);
		
		try {
			if (!carrierVO.getJoCrrCd().isEmpty()
					&& !carrierVO.getJoCrrCd().equals("ALL")) {
				List<String> listCrrCd = new ArrayList<>();
				String[] cari = carrierVO.getJoCrrCd().split(",");
				for (int i = 0; i < cari.length; i++) {
					listCrrCd.add(cari[i]);
				}
				velParam.put("listCrrCd", listCrrCd);
				if (!carrierVO.getRlaneCd().isEmpty()
						|| carrierVO.getRlaneCd() != "") {
					param.put("rlane_cd", carrierVO.getRlaneCd());
					velParam.put("rlane_cd", carrierVO.getRlaneCd());

					if (!carrierVO.getTrdCd().isEmpty()
							|| carrierVO.getTrdCd() != "") {
						param.put("trd_cd", carrierVO.getTrdCd());
						velParam.put("trd_cd", carrierVO.getTrdCd());
					}

				}
			}

			dbRowset = new SQLExecuter("").executeQuery(
					(ISQLTemplate) new CarrierDBDAOCarrierVORSQL(), param,
					velParam);
			list = (List) RowSetUtil.rowSetToVOs(dbRowset, CarrierVO.class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;

	}
	
	@SuppressWarnings("unchecked")
	public List<CarrierVO> searchTotalSum(CarrierVO carrierVO)
			throws DAOException {

		DBRowSet dbRowset = null;
		List<CarrierVO> list = new ArrayList();
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		String[] yrMon = carrierVO.getAcctYrmon().split(",");
		param.put("fr_acct_yrmon", yrMon[0]);
		param.put("to_acct_yrmon",yrMon[1]);
		
		try {
			if (!carrierVO.getJoCrrCd().isEmpty()
					&& !carrierVO.getJoCrrCd().equals("ALL")) {
				List<String> listCrrCd = new ArrayList<>();
				String[] cari = carrierVO.getJoCrrCd().split(",");
				for (int i = 0; i < cari.length; i++) {
					listCrrCd.add(cari[i]);
				}
				velParam.put("listCrrCd", listCrrCd);
				if (!carrierVO.getRlaneCd().isEmpty()
						|| carrierVO.getRlaneCd() != "") {
					param.put("rlane_cd", carrierVO.getRlaneCd());
					velParam.put("rlane_cd", carrierVO.getRlaneCd());

					if (!carrierVO.getTrdCd().isEmpty()
							|| carrierVO.getTrdCd() != "") {
						param.put("trd_cd", carrierVO.getTrdCd());
						velParam.put("trd_cd", carrierVO.getTrdCd());
					}

				}
			}

			dbRowset = new SQLExecuter("").executeQuery(
					(ISQLTemplate) new CarrierDBDAOGetTotalSumRSQL(), param,
					velParam);
			list = (List) RowSetUtil.rowSetToVOs(dbRowset, CarrierVO.class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	public List<CarrierVO> searchPartner() throws DAOException {

		DBRowSet dbRowset = null;
		List<CarrierVO> list = new ArrayList();

		try {
			dbRowset = new SQLExecuter("").executeQuery(
					(ISQLTemplate) new CarrierDBDAOSearchPartnerRSQL(), null,
					null);
			list = (List) RowSetUtil.rowSetToVOs(dbRowset, CarrierVO.class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	public List<CarrierVO> searchLaneByPartner(CarrierVO carrierVO)
			throws DAOException {

		DBRowSet dbRowset = null;
		List<CarrierVO> list = new ArrayList();

		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> velParam = new HashMap<String, Object>();

		try {
			if (carrierVO != null) {
				// Map<String, String> mapVO = carrierVO.getColumnValues();
				List<String> listCrrCd = new ArrayList<>();
				if (null != carrierVO.getJoCrrCd()) {
					String[] JoCrrCds = carrierVO.getJoCrrCd().split(",");
					for (int i = 0; i < JoCrrCds.length; i++) {
						listCrrCd.add(JoCrrCds[i]);
					}
				}
				// param.putAll(mapVO);
				param.put("listCrrCd", listCrrCd);

				// velParam.putAll(mapVO);
				velParam.put("listCrrCd", listCrrCd);
			}

			dbRowset = new SQLExecuter("").executeQuery(
					(ISQLTemplate) new CarrierDBDAOGetLaneByPartnerRSQL(),
					param, velParam);
			list = (List) RowSetUtil.rowSetToVOs(dbRowset, CarrierVO.class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	public List<CarrierVO> searchTradeByPartnerAndLane(CarrierVO carrierVO)
			throws DAOException {

		DBRowSet dbRowset = null;
		List<CarrierVO> list = new ArrayList();

		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> velParam = new HashMap<String, Object>();

		try {
			if (carrierVO != null) {
				// Map<String, String> mapVO = carrierVO.getColumnValues();
				List<String> listCrrCd = new ArrayList<>();
				if (null != carrierVO.getJoCrrCd()) {
					String[] JoCrrCds = carrierVO.getJoCrrCd().split(",");
					for (int i = 0; i < JoCrrCds.length; i++) {
						listCrrCd.add(JoCrrCds[i]);
					}
				}
				// param.putAll(mapVO);
				param.put("listCrrCd", listCrrCd);
				param.put("rlane_cd", carrierVO.getRlaneCd());

				// velParam.putAll(mapVO);
				velParam.put("listCrrCd", listCrrCd);
				velParam.put("rlane_cd", carrierVO.getRlaneCd());
			}

			dbRowset = new SQLExecuter("")
					.executeQuery(
							(ISQLTemplate) new CarrierDBDAOGetTradeByPartnerAndLaneRSQL(),
							param, velParam);
			list = (List) RowSetUtil.rowSetToVOs(dbRowset, CarrierVO.class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;

	}
}
