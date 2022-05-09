package com.clt.apps.opus.dou.doutraining.codemgmt.basic;

import java.util.List;

import com.clt.apps.opus.dou.doutraining.codemgmt.vo.CodeVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailsVO;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;

public interface CodeMgmtBC {
	public List<CodeVO> searchCode(CodeVO codeVO) throws EventException;
	
	public void managerCode(CodeVO[] codeVOS, SignOnUserAccount account) throws EventException;
	
	public List<CodeVO> searchDetails(DetailsVO detailsVO) throws EventException;
	
	public void managerDetails(DetailsVO[] detailsVOS, SignOnUserAccount account) throws EventException;
	
}
