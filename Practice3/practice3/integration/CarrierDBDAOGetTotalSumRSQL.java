/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CarrierDBDAOGetTotalSumRSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.19
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.19 
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

public class CarrierDBDAOGetTotalSumRSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * DESC Enter..
	  * </pre>
	  */
	public CarrierDBDAOGetTotalSumRSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		String tmp = null;
		String[] arrTmp = null;
		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("to_acct_yrmon",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("fr_acct_yrmon",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("rlane_cd",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("trd_cd",new String[]{arrTmp[0],arrTmp[1]});

		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.dou.doutraining.practice3.integration").append("\n"); 
		query.append("FileName : CarrierDBDAOGetTotalSumRSQL").append("\n"); 
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
		query.append("SELECT INV.LOCL_CURR_CD" ).append("\n"); 
		query.append("" ).append("\n"); 
		query.append("     , SUM(INV.REV_ACT_AMT) AS INV_REV_ACT_AMT" ).append("\n"); 
		query.append("     , SUM(INV.EXP_ACT_AMT) AS INV_EXP_ACT_AMT " ).append("\n"); 
		query.append("  FROM (  " ).append("\n"); 
		query.append("    SELECT  NVL(INV.INV_CURR_CD, INV.LOCL_CURR_CD) AS LOCL_CURR_CD" ).append("\n"); 
		query.append("     , DECODE('R', STL.RE_DIVR_CD, NVL(DTL.LOCL_AMT, DTL.ACT_AMT), 0) AS REV_ACT_AMT" ).append("\n"); 
		query.append("     , DECODE('E', STL.RE_DIVR_CD, NVL(DTL.LOCL_AMT, DTL.ACT_AMT), 0) AS EXP_ACT_AMT" ).append("\n"); 
		query.append("    FROM JOO_INVOICE INV" ).append("\n"); 
		query.append("     , JOO_INV_DTL DTL" ).append("\n"); 
		query.append("     , JOO_STL_TGT STL" ).append("\n"); 
		query.append("     , JOO_CSR     CSR" ).append("\n"); 
		query.append("    WHERE 1=1" ).append("\n"); 
		query.append("    AND NVL(STL.THEA_STL_FLG, 'N') = 'N'" ).append("\n"); 
		query.append("	" ).append("\n"); 
		query.append("	AND INV.ACCT_YRMON   BETWEEN REPLACE(@[fr_acct_yrmon],'-','') AND REPLACE(@[to_acct_yrmon],'-','')" ).append("\n"); 
		query.append("" ).append("\n"); 
		query.append("" ).append("\n"); 
		query.append("		#if (${listCrrCd} != '')" ).append("\n"); 
		query.append("        AND INV.JO_CRR_CD    IN ( #foreach($key IN ${listCrrCd})#if($velocityCount < $listCrrCd.size()) '$key', #else '$key' #end #end)" ).append("\n"); 
		query.append("		#end" ).append("\n"); 
		query.append("" ).append("\n"); 
		query.append("		#if (${rlane_cd}!='')" ).append("\n"); 
		query.append("		AND STL.RLANE_CD   = @[rlane_cd]" ).append("\n"); 
		query.append("		#end" ).append("\n"); 
		query.append("" ).append("\n"); 
		query.append("		#if (${trd_cd}!='')" ).append("\n"); 
		query.append("	   	AND EXISTS   (   SELECT 'X'" ).append("\n"); 
		query.append("						  FROM JOO_CARRIER CRR" ).append("\n"); 
		query.append("						 WHERE 1=1" ).append("\n"); 
		query.append("						   AND CRR.DELT_FLG         = 'N'" ).append("\n"); 
		query.append("						   AND CRR.JO_CRR_CD        = STL.JO_CRR_CD" ).append("\n"); 
		query.append("						   AND CRR.RLANE_CD         = STL.RLANE_CD" ).append("\n"); 
		query.append("						   AND CRR.MODI_COST_CTR_CD  = @[trd_cd]" ).append("\n"); 
		query.append("					 )" ).append("\n"); 
		query.append("		#end" ).append("\n"); 
		query.append("	" ).append("\n"); 
		query.append("    AND INV.RJCT_CMB_FLG  = 'N'" ).append("\n"); 
		query.append("    AND DTL.ACCT_YRMON    = INV.ACCT_YRMON" ).append("\n"); 
		query.append("    AND DTL.JO_CRR_CD     = INV.JO_CRR_CD" ).append("\n"); 
		query.append("    AND DTL.INV_NO        = INV.INV_NO" ).append("\n"); 
		query.append("    AND DTL.RE_DIVR_CD    = INV.RE_DIVR_CD" ).append("\n"); 
		query.append("    AND STL.THEA_STL_FLG  = INV.THEA_STL_FLG" ).append("\n"); 
		query.append("    AND STL.VSL_CD        = DTL.VSL_CD" ).append("\n"); 
		query.append("    AND STL.SKD_VOY_NO    = DTL.SKD_VOY_NO" ).append("\n"); 
		query.append("    AND STL.SKD_DIR_CD    = DTL.SKD_DIR_CD" ).append("\n"); 
		query.append("    AND STL.REV_DIR_CD    = DTL.REV_DIR_CD" ).append("\n"); 
		query.append("    AND STL.REV_YRMON     = DTL.REV_YRMON" ).append("\n"); 
		query.append("    AND STL.STL_VVD_SEQ   = DTL.STL_VVD_SEQ" ).append("\n"); 
		query.append("    AND INV.SLP_TP_CD     = CSR.SLP_TP_CD(+)" ).append("\n"); 
		query.append("    AND INV.SLP_FUNC_CD   = CSR.SLP_FUNC_CD(+)" ).append("\n"); 
		query.append("    AND INV.SLP_OFC_CD    = CSR.SLP_OFC_CD(+)" ).append("\n"); 
		query.append("    AND INV.SLP_ISS_DT    = CSR.SLP_ISS_DT(+)" ).append("\n"); 
		query.append("    AND INV.SLP_SER_NO    = CSR.SLP_SER_NO(+)" ).append("\n"); 
		query.append("    )INV" ).append("\n"); 
		query.append("    WHERE 1=1" ).append("\n"); 
		query.append("    GROUP BY INV.LOCL_CURR_CD" ).append("\n"); 

	}
}