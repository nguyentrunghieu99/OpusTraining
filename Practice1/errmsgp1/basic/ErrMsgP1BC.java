/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ErrMsgP1BC.java
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

import java.util.List;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.practice1.errmsgp1.vo.ErrMsgVO;

/**
 * ALPS-Practice1 Business Logic Command Interface<br>
 * - ALPS-Practice1에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author Hieu Nguyen
 * @since J2EE 1.6
 */

public interface ErrMsgP1BC {

	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO	errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception EventException
	 */
	public List<ErrMsgVO> searchErrMsg(ErrMsgVO errMsgVO) throws EventException;
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO[] errMsgVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void crudErrMsg(ErrMsgVO[] errMsgVO,SignOnUserAccount account) throws EventException;
}