/*=========================================================
*Copyright(c) 2020 CyberLogitec
*@FileName : CodeMgmtDBDAO.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2020.03.17
*@LastModifier : 
*@LastVersion : 1.0
* 2020.03.17 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.dou.doutraining.codemgmt.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.dou.doutraining.codemgmt.vo.CodeVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailsVO;
import com.clt.apps.opus.dou.doutraining.errmsgmgmt.basic.ErrMsgMgmtBCImpl;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;
import com.clt.apps.opus.dou.doutraining.errmsgmgmt.vo.ErrMsgVO;


/**
 * ALPS CodeMgmtDBDAO <br>
 * - ALPS-DouTraining system Business Logic을 처리하기 위한 JDBC 작업수행.<br>
 * 
 * @author Vi Nguyen
 * @see ErrMsgMgmtBCImpl 참조
 * @since J2EE 1.6
 */
public class CodeMgmtDBDAO extends DBDAOSupport {
	@SuppressWarnings("unchecked")
	public List<CodeVO> searchCode(CodeVO codeVO) throws DAOException{
		DBRowSet dbRowset = null;
		List<CodeVO> list = new ArrayList();
		
		//query parameter
		Map<String,Object> param = new HashMap<String,Object>();
		
		//velocity parameter
		Map<String,Object> velParam = new HashMap<String,Object>();
		
		try{
			if(codeVO != null){
				Map<String,String> mapVO = codeVO.getColumnValues();
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery(new CodeMgmtDBDAOCodeVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CodeVO.class);
		}catch(SQLException ex){
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
			
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<CodeVO> searchDetails(DetailsVO detailsVO) throws DAOException{
		DBRowSet dbRowset = null;
		List<CodeVO> list = new ArrayList();
		
		//query parameter
		Map<String,Object> param = new HashMap<String,Object>();
		
		//velocity parameter
		Map<String,Object> velParam = new HashMap<String,Object>();
		
		try{
			if(detailsVO!= null){
				Map<String,String> mapVO = detailsVO.getColumnValues();
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery(new DetailsMgmtDBDAODetailsVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, DetailsVO.class);
		}catch(SQLException ex){
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
			
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public int ValidateIdCodeVO(CodeVO codeVO) throws DAOException{
		DBRowSet dbRowset = null;
		int count = 0;
		Map<String,Object> param = new HashMap<String,Object>();
		
		try{
			if(codeVO!= null){
				param.put("intg_cd_id",codeVO.getIntgCdId());
			}
			dbRowset = new SQLExecuter("").executeQuery(new CodeValidateDBDAOCodeVORSQL(), param, null);
			while(dbRowset.next()){
				count = dbRowset.getInt(1);
			}
			
		}catch(SQLException ex){
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}catch(Exception ex){
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
			
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public int ValidateIdDetailsVO(DetailsVO detailsVO) throws DAOException{
		DBRowSet dbRowset = null;
		int count = 0;
		Map<String,Object> param = new HashMap<String,Object>();
		
		try{
			if(detailsVO!= null){
				param.put("intg_cd_val_ctnt",detailsVO.getIntgCdValCtnt());
			}
			dbRowset = new SQLExecuter("").executeQuery(new DetailsValidateDBDAODetailsVORSQL(), param, null);
			
			while(dbRowset.next()){
				count = dbRowset.getInt(1);
			}
			
		}catch(SQLException ex){
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}catch(Exception ex){
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
			
		return count;
	}
	
	
	
	
	public int[] addCodeS(List<CodeVO> codeVO) throws DAOException, Exception{
		int insCnt[] = null;
		try{
			SQLExecuter sqlExe = new SQLExecuter("");
			if(codeVO.size()>0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAOCodeVOCSQL(),codeVO,null);
				for(int i=0;i<insCnt.length;i++){
					if(insCnt[i] == Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		}catch(SQLException ex){
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		
		return insCnt;
	}
	
	public int[] addDetails(List<DetailsVO> detailsVO) throws DAOException, Exception{
		int insCnt[] = null;
		try{
			SQLExecuter sqlExe = new SQLExecuter("");
			if(detailsVO.size()>0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new DetailsMgmtDBDAODetailsVOCSQL(),detailsVO,null);
				for(int i=0;i<insCnt.length;i++){
					if(insCnt[i] == Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		}catch(SQLException ex){
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		
		return insCnt;
	}
	
	public int[] updateCodeS(List<CodeVO> codeVO) throws DAOException,Exception{
		int updCnt[] = null;
		try{
			SQLExecuter sqlExe = new SQLExecuter("");
			if(codeVO.size()>0){
				updCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAOCodeVOUSQL(),codeVO,null);
				for(int i=0; i<updCnt.length;i++){
					if(updCnt[i] == Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
			
		}catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
			return updCnt;
	}
	
	public int[] updateDetails(List<DetailsVO> detailsVOS) throws DAOException,Exception{
		int updCnt[] = null;
		try{
			SQLExecuter sqlExe = new SQLExecuter("");
			if(detailsVOS.size()>0){
				updCnt = sqlExe.executeBatch((ISQLTemplate)new DetailsMgmtDBDAODetailsVOUSQL(),detailsVOS,null);
				for(int i=0; i<updCnt.length;i++){
					if(updCnt[i] == Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
			
		}catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
			return updCnt;
	}
	
	public int[] deleteCodeS(List<CodeVO> codeVO)throws DAOException,Exception{
		int delCnt[] = null;
		try{
			SQLExecuter sqlExe = new SQLExecuter();
			if(codeVO.size()>0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAOCodeVODSQL(), codeVO, null);
				for(int i=0; i<delCnt.length;i++){
					if(delCnt[i] == Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
			
			
		}catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return delCnt;
	}
	
	public int[] deleteDetails(List<DetailsVO> detailsVOS)throws DAOException,Exception{
		int delCnt[] = null;
		try{
			SQLExecuter sqlExe = new SQLExecuter();
			if(detailsVOS.size()>0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new DetailsMgmtDBDAODetailsVODSQL(), detailsVOS,  null);
				for(int i=0; i<delCnt.length;i++){
					if(delCnt[i] == Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
			
			
		}catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return delCnt;
	}
	
	public int[] deleteDetailsByCDID(List<DetailsVO> detailsVOS)throws DAOException,Exception{
		int delCnt[] = null;
		try{
			SQLExecuter sqlExe = new SQLExecuter();
			if(detailsVOS.size()>0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new DetailsMgmtDAODeleteByCDIDDSQL(), detailsVOS,  null);
				for(int i=0; i<delCnt.length;i++){
					if(delCnt[i] == Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
			
			
		}catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return delCnt;
	}
		
}