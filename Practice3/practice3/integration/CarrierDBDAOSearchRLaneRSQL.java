/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CarrierDBDAOSearchRLaneRSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.05
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.05 
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

public class CarrierDBDAOSearchRLaneRSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * a
	  * </pre>
	  */
	public CarrierDBDAOSearchRLaneRSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.dou.doutraining.practice3.integration").append("\n"); 
		query.append("FileName : CarrierDBDAOSearchRLaneRSQL").append("\n"); 
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
		query.append("SELECT " ).append("\n"); 
		query.append("	DISTINCT(rlane_cd) as rlane_cd" ).append("\n"); 
		query.append("FROM JOO_CARRIER" ).append("\n"); 
		query.append("ORDER BY rlane_cd" ).append("\n"); 

	}
}