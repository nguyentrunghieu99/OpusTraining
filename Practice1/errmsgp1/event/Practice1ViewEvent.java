/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : Practice1ViewEvent.java
*@FileTitle : Practice1 Training
*Open Issues :
*Change history :
*@LastModifyDate : 2022.04.19
*@LastModifier : 
*@LastVersion : 1.0
* 2022.04.19 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice1.errmsgp1.event;

import com.clt.framework.support.layer.event.EventSupport;
import com.clt.apps.opus.esm.clv.practice1.errmsgp1.vo.ErrMsgVO;


/**
 * PRACTICE1_VIEW 에 대한 PDTO(Data Transfer Object including Parameters)<br>
 * -  PRACTICE1_VIEWHTMLAction에서 작성<br>
 * - ServiceCommand Layer로 전달하는 PDTO로 사용<br>
 *
 * @author Hieu Nguyen
 * @see PRACTICE1_VIEWHTMLAction 참조
 * @since J2EE 1.6
 */

public class Practice1ViewEvent extends EventSupport {

	private static final long serialVersionUID = 1L;
	
	/** Table Value Object 조회 조건 및 단건 처리  */
	ErrMsgVO errMsgVO = null;
	
	/** Table Value Object Multi Data 처리 */
	ErrMsgVO[] errMsgVOs = null;

	public Practice1ViewEvent(){}
	
	public void setErrMsgVO(ErrMsgVO errMsgVO){
		this. errMsgVO = errMsgVO;
	}

	public void setErrMsgVOS(ErrMsgVO[] errMsgVOs){
		this. errMsgVOs = errMsgVOs;
	}

	public ErrMsgVO getErrMsgVO(){
		return errMsgVO;
	}

	public ErrMsgVO[] getErrMsgVOS(){
		return errMsgVOs;
	}

}