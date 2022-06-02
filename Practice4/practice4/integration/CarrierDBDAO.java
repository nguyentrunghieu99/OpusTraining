package com.clt.apps.opus.dou.doutraining.practice4.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.dou.doutraining.practice3.integration.CarrierDBDAOSearchPartnerRSQL;
import com.clt.apps.opus.dou.doutraining.practice3.vo.CarrierVO;
import com.clt.apps.opus.dou.doutraining.practice4.vo.CarrierVOS;
import com.clt.apps.opus.esm.clv.practice1.errmsgp1.integration.ErrMsgP1DBDAOErrMsgVOCSQL;
import com.clt.apps.opus.esm.clv.practice1.errmsgp1.integration.ErrMsgP1DBDAOErrMsgVODSQL;
import com.clt.apps.opus.esm.clv.practice1.errmsgp1.integration.ErrMsgP1DBDAOErrMsgVOUSQL;
import com.clt.apps.opus.esm.clv.practice1.errmsgp1.vo.ErrMsgVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;

public class CarrierDBDAO extends DBDAOSupport{
	@SuppressWarnings("unchecked")
	public List<CarrierVOS> getCarrier(CarrierVOS carrierVO)
			throws DAOException {
		
		Map<String, Object> param = new HashMap<String, Object>();
		// velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		List<CarrierVOS> list = new ArrayList();
		DBRowSet dbRowset = null;
		try {
			if(!carrierVO.getCreDt().equals(",")){
				String[] date = carrierVO.getCreDt().split(",");
				if(!date[0].isEmpty()){
					param.put("fr_date", date[0]);
					velParam.put("fr_date", date[0]);
				}
			if(date.length == 2){
				param.put("to_date", date[1]);
				velParam.put("to_date", date[1]);
			}
			}
			
			if(carrierVO.getVndrSeq()!=null && !carrierVO.getVndrSeq().isEmpty()){
				param.put("vndr_seq", carrierVO.getVndrSeq());
				velParam.put("vndr_seq", carrierVO.getVndrSeq());
			}
			
			if (!carrierVO.getJoCrrCd().isEmpty()&& !carrierVO.getJoCrrCd().equals("ALL")) {
				List<String> listCrrCd = new ArrayList<>();
				String[] crrCd = carrierVO.getJoCrrCd().split(",");
				for (int i = 0; i < crrCd.length; i++) {
					listCrrCd.add(crrCd[i]);
				}
				velParam.put("listCrrCd", listCrrCd);
			}
			
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new CarrierDBDAOCarrierVORSQL(), param,velParam);
			list = (List) RowSetUtil.rowSetToVOs(dbRowset, CarrierVOS.class);
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
	public List<CarrierVOS> getCrrCd() throws DAOException {

		DBRowSet dbRowset = null;
		List<CarrierVOS> list = new ArrayList();

		try {
			dbRowset = new SQLExecuter("").executeQuery(
					(ISQLTemplate) new CarrierDBDAOGetCrrRSQL(), null,
					null);
			list = (List) RowSetUtil.rowSetToVOs(dbRowset, CarrierVOS.class);
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
	public List<String> getRlandCd() throws DAOException {

		DBRowSet dbRowset = null;
		List<String> list = new ArrayList();

		try {
			dbRowset = new SQLExecuter("").executeQuery(
					(ISQLTemplate) new CarrierDBDAOGetRLaneRSQL(), null,
					null);
			while (dbRowset.next()) {
				list.add(dbRowset.getString(1));
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
	
	@SuppressWarnings("unchecked")
	public int checkDuplicate(CarrierVOS carrierVOS) throws DAOException {

		DBRowSet dbRowset = null;
		Map<String, Object> param = new HashMap<String, Object>();
		int count =0;

		try {
			param.put("jo_crr_cd", carrierVOS.getJoCrrCd());
			param.put("rlane_cd", carrierVOS.getRlaneCd());
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new CarrierDBDAOCheckDuplicateRSQL(), param,null);
			while(dbRowset.next()){
				count = dbRowset.getInt(1);
			}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return count;
	}
	
	public int[] addCarrier(List<CarrierVOS> carrierVOS) throws DAOException,Exception {
		int insCnt[] = null;
		try {
				SQLExecuter sqlExe = new SQLExecuter("");
				if (carrierVOS.size() > 0) {
					insCnt = sqlExe.executeBatch((ISQLTemplate) new CarrierDBDAOCarrierVOCSQL(),carrierVOS, null);
					for (int i = 0; i < insCnt.length; i++) {
						if (insCnt[i] == Statement.EXECUTE_FAILED)
							throw new DAOException("Fail to insert No" + i + " SQL");
					}
				}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
	return insCnt;
}
	public int[] updateCarrier(List<CarrierVOS> carrierVOS) throws DAOException,
	Exception {
		int updCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if (carrierVOS.size() > 0) {
				updCnt = sqlExe.executeBatch(
						(ISQLTemplate) new CarrierDBDAOCarrierVOUSQL(),
						carrierVOS, null);
				for (int i = 0; i < updCnt.length; i++) {
					if (updCnt[i] == Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to update No" + i + " SQL");
				}
			}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return updCnt;
}
	
	public int[] removeCarrier(List<CarrierVOS> carrierVOS)
			throws DAOException, Exception {
		int delCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter();
			if (carrierVOS.size() > 0) {
				delCnt = sqlExe.executeBatch(
						(ISQLTemplate) new CarrierDBDAOCarrierVODSQL(),
						carrierVOS, null);
				for (int i = 0; i < delCnt.length; i++) {
					if (delCnt[i] == Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to delete No" + i + " SQL");
				}
			}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return delCnt;
	}

}
