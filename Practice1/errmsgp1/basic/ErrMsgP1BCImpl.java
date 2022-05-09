/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ErrMsgP1BCImpl.java
*@FileTitle : Practice1 Training
*Open Issues :
*Change history :
*@LastModifyDate : 2022.04.19
*@LastModifier : 
*@LastVersion : 1.0
* 2022.04.19 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice1.errmsgp1.basic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.axis.transport.java.Handler;

import com.clt.apps.opus.esm.clv.practice1.errmsgp1.integration.ErrMsgP1DBDAO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.practice1.errmsgp1.vo.ErrMsgVO;

/**
 * ALPS-Practice1 Business Logic Command Interface<br>
 * - ALPS-Practice1에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author Hieu Nguyen
 * @since J2EE 1.6
 */
public class ErrMsgP1BCImpl extends BasicCommandSupport implements ErrMsgP1BC {

	// Database Access Object
	private transient ErrMsgP1DBDAO dbDao = null;

	/**
	 * ErrMsgP1BCImpl 객체 생성<br>
	 * ErrMsgP1DBDAO를 생성한다.<br>
	 */
	public ErrMsgP1BCImpl() {
		dbDao = new ErrMsgP1DBDAO();
	}
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception EventException
	 */
	public List<ErrMsgVO> searchErrMsg(ErrMsgVO errMsgVO) throws EventException {
		try {
			return dbDao.searchErrMsg(errMsgVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO[] errMsgVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void crudErrMsg(ErrMsgVO[] errMsgVO, SignOnUserAccount account) throws EventException{
		
		try {
			List<ErrMsgVO> insertVoList = new ArrayList<ErrMsgVO>();
			List<ErrMsgVO> updateVoList = new ArrayList<ErrMsgVO>();
			List<ErrMsgVO> deleteVoList = new ArrayList<ErrMsgVO>();
			String regex ="^([A-Z]{3})([0-9]{5})$";	
			for ( int i=0; i<errMsgVO .length; i++ ) {
				if(errMsgVO[i].getErrMsgCd().matches(regex)==false){
					throw new DAOException(new ErrorHandler("ERR12345").getMessage());
				}
				
				
				if ( errMsgVO[i].getIbflag().equals("I")){
					int count = dbDao.ValidateErrMsg(errMsgVO[i]);
					if(count != 0){
						throw new DAOException(new ErrorHandler("ERR54321").getMessage());
					}
					
					
					errMsgVO[i].setCreUsrId(account.getUsr_id());
					errMsgVO[i].setUpdUsrId(account.getUsr_id());
					insertVoList.add(errMsgVO[i]);
				} else if ( errMsgVO[i].getIbflag().equals("U")){
					errMsgVO[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(errMsgVO[i]);
				} else if ( errMsgVO[i].getIbflag().equals("D")){
					deleteVoList.add(errMsgVO[i]);
				}
			}
			
			if ( insertVoList.size() > 0 ) {
				dbDao.addcrudErrMsgS(insertVoList);
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.modifycrudErrMsgS(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dbDao.removecrudErrMsgS(deleteVoList);
			}
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage());
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage());
		}
	}
	
}