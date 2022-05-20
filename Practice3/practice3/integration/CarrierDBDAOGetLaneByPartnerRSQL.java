/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CarrierDBDAOGetLaneByPartnerRSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.12
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.12 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.dou.doutraining.practice3.integration;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author HieuNguyen
 * @see DAO 참조
 * @since J2EE 1.6
 */

public class CarrierDBDAOGetLaneByPartnerRSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * .
	  * </pre>
	  */
	public CarrierDBDAOGetLaneByPartnerRSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.dou.doutraining.practice3.integration").append("\n"); 
		query.append("FileName : CarrierDBDAOGetLaneByPartnerRSQL").append("\n"); 
		query.append("*/").append("\n"); 
	}
	
	public String getSQL(){
		return query.toString();
	}
	
	public HashMap<String,String[]> getParams() {
		return params;
	}

	/**
	 * Query 생성
	 */
	public void setQuery(){
		query.append("select DISTINCT RLANE_CD" ).append("\n"); 
		query.append("from JOO_CARRIER" ).append("\n"); 
		query.append("WHERE" ).append("\n"); 
		query.append("JO_CRR_CD    IN ( #foreach($key IN ${listCrrCd})#if($velocityCount < $listCrrCd.size()) '$key', #else '$key' #end #end)" ).append("\n"); 

	}
}