package com.clt.apps.opus.dou.doutraining.codemgmt.event;

import com.clt.apps.opus.dou.doutraining.codemgmt.vo.CodeVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailsVO;
import com.clt.framework.support.layer.event.EventSupport;

public class DouTrn0002Event extends EventSupport{
	private static final long serialVersionUID = 1L;
	
	CodeVO codeVO = null;
	CodeVO[] codeVOS = null;
	DetailsVO detailsVO = null;
	DetailsVO[] detailsVOS = null;
	
	public DouTrn0002Event(){}
	
	public void setCodeVO(CodeVO codeVO){
		this.codeVO = codeVO;
	}
	
	public void setCodeVOS(CodeVO[] codeVOS){
		this.codeVOS = codeVOS;
	}
	
	public CodeVO getCodeVO(){
		return codeVO;
	}
	
	public CodeVO[] getCodeVOS(){
		return codeVOS;
	}
	
	public void setDetailsVO(DetailsVO detailsVO){
		this.detailsVO = detailsVO;
	}
	public void setDetailsVOS(DetailsVO[] detailsVOS){
		this.detailsVOS = detailsVOS;
	}
	
	public DetailsVO getDetailsVO(){
		return detailsVO;
	}
	
	public DetailsVO[] getDetailsVOS(){
		return detailsVOS;
	}
	
}
