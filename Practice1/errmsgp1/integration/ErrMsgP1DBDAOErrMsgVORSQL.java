/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ErrMsgP1DBDAOErrMsgVORSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.04.24
*@LastModifier : 
*@LastVersion : 1.0
* 2022.04.24 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice1.errmsgp1.integration;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author Hieu Nguyen
 * @see DAO 참조
 * @since J2EE 1.6
 */

public class ErrMsgP1DBDAOErrMsgVORSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  *    
	  * </pre>
	  */
	public ErrMsgP1DBDAOErrMsgVORSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		String tmp = null;
		String[] arrTmp = null;
		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("err_msg",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("err_msg_cd",new String[]{arrTmp[0],arrTmp[1]});

		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.practice1.errmsgp1.integration").append("\n"); 
		query.append("FileName : ErrMsgP1DBDAOErrMsgVORSQL").append("\n"); 
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
		query.append("select err_msg_cd, err_tp_cd, err_lvl_cd,err_msg,err_desc,cre_usr_id, upd_usr_id" ).append("\n"); 
		query.append("from com_err_msg" ).append("\n"); 
		query.append("where 1 = 1 " ).append("\n"); 
		query.append("#if (${err_msg_cd} != '')" ).append("\n"); 
		query.append("and UPPER(err_msg_cd) like '%'||UPPER(@[err_msg_cd])||'%'" ).append("\n"); 
		query.append("#end" ).append("\n"); 
		query.append("#if(${err_msg} != '')" ).append("\n"); 
		query.append("and UPPER(err_msg) like'%'||UPPER(@[err_msg])||'%'" ).append("\n"); 
		query.append("#end" ).append("\n"); 

	}
}