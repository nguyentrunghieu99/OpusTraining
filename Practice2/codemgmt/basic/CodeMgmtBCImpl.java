package com.clt.apps.opus.dou.doutraining.codemgmt.basic;

import java.util.ArrayList;
import java.util.List;

import com.clt.apps.opus.dou.doutraining.codemgmt.integration.CodeMgmtDBDAO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.CodeVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailsVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.view.signon.SignOnUserAccount;

public class CodeMgmtBCImpl implements CodeMgmtBC {
	private transient CodeMgmtDBDAO dbDao = null;
	
	public CodeMgmtBCImpl(){
		dbDao = new CodeMgmtDBDAO();
	}
	
	
	

	public List<CodeVO> searchCode(CodeVO codeVO) throws EventException {
		try{
			return dbDao.searchCode(codeVO);
		}catch(DAOException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	public List<CodeVO> searchDetails(DetailsVO detailsVO) throws EventException {
		try{
			return dbDao.searchDetails(detailsVO);
		}catch(DAOException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	

	public void managerCode(CodeVO[] codeVOS, SignOnUserAccount account) throws EventException{
			try{
				List<CodeVO> insertVoList = new ArrayList<CodeVO>();
				List<CodeVO> updatetVoList = new ArrayList<CodeVO>();
				List<CodeVO> deleteVoList = new ArrayList<CodeVO>();
				List<DetailsVO> deleteDetails = new ArrayList<DetailsVO>();
				
				for(int i=0; i < codeVOS.length; i++){
					if(codeVOS[i].getIbflag().equals("I")){
						int count = dbDao.ValidateIdCodeVO(codeVOS[i]);
							if(count != 0){
								throw new DAOException(new ErrorHandler("ERR54321").getMessage());
							}
																	
						codeVOS[i].setCreUsrId(account.getUsr_id());
						codeVOS[i].setUpdUsrId(account.getUsr_id());
						insertVoList.add(codeVOS[i]);
					}
					
					if(codeVOS[i].getIbflag().equals("U")){
						codeVOS[i].setUpdUsrId(account.getUsr_id());
						updatetVoList.add(codeVOS[i]);
					}
					if(codeVOS[i].getIbflag().equals("D")){
						DetailsVO detailsVO = new DetailsVO();
						detailsVO.setIntgCdId(codeVOS[i].getIntgCdId());
						deleteDetails.add(detailsVO);
						deleteVoList.add(codeVOS[i]);
					}
					
				}
				
				if(insertVoList.size() >0){
					dbDao.addCodeS(insertVoList);
				}
				if(updatetVoList.size()>0){
					dbDao.updateCodeS(updatetVoList);
				}
				if(deleteVoList.size()>0){
					dbDao.deleteDetailsByCDID(deleteDetails);
					dbDao.deleteCodeS(deleteVoList);
				}
				
			}catch(DAOException ex){
				throw new EventException(new ErrorHandler(ex).getMessage());				
			}catch(Exception ex){
				throw new EventException(new ErrorHandler(ex).getMessage());				
			}
		
	}
	
	public void managerDetails(DetailsVO[] detailsVOS, SignOnUserAccount account) throws EventException{
		try{
			List<DetailsVO> insertVoList = new ArrayList<DetailsVO>();
			List<DetailsVO> updatetVoList = new ArrayList<DetailsVO>();
			List<DetailsVO> deleteVoList = new ArrayList<DetailsVO>();
			
			for(int i=0; i < detailsVOS.length; i++){
				if(detailsVOS[i].getIbflag().equals("I")){
					int count = dbDao.ValidateIdDetailsVO(detailsVOS[i]);
					if(count != 0){
						throw new DAOException(new ErrorHandler("ERR54321").getMessage());
					}
					
					detailsVOS[i].setCreUsrId(account.getUsr_id());
					detailsVOS[i].setUpdUsrId(account.getUsr_id());
					insertVoList.add(detailsVOS[i]);
				}
				
				if(detailsVOS[i].getIbflag().equals("U")){
					detailsVOS[i].setUpdUsrId(account.getUsr_id());
					updatetVoList.add(detailsVOS[i]);
				}
				if(detailsVOS[i].getIbflag().equals("D")){
					deleteVoList.add(detailsVOS[i]);
				}
				
			}
			
			if(insertVoList.size() >0){
				dbDao.addDetails(insertVoList);
			}
			if(updatetVoList.size()>0){
				dbDao.updateDetails(updatetVoList);
			}
			if(deleteVoList.size()>0){
				dbDao.deleteDetails(deleteVoList);
			}
			
		}catch(DAOException ex){
			throw new EventException(new ErrorHandler(ex).getMessage());				
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage());				
		}
	
}

}
