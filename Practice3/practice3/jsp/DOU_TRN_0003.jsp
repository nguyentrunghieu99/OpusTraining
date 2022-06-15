<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.clt.framework.component.util.JSPUtil"%>
<%@ page import="com.clt.framework.component.util.DateTime"%>
<%@ page import="com.clt.framework.component.message.ErrorHandler"%>
<%@ page import="com.clt.framework.core.layer.event.GeneralEventResponse"%>
<%@ page import="com.clt.framework.support.controller.html.CommonWebKeys"%>
<%@ page import="com.clt.framework.support.view.signon.SignOnUserAccount"%>
<%@ page import="com.clt.apps.opus.dou.doutraining.practice3.event.DouTrn0003Event"%>
<%@ page import="org.apache.log4j.Logger" %>

<%
	DouTrn0003Event  event = null;
	Exception serverException   = null;
	String strErrMsg = "";
	
	String successFlag = "";
	String codeList  = "";
	String pageRows  = "100";

	String strUsr_id		= "";
	String strUsr_nm		= "";
	
	String partnerStr			= "";
	Logger log = Logger.getLogger("com.clt.apps.DouTraining.CodeMgmt");

	try {
	   	SignOnUserAccount account=(SignOnUserAccount)session.getAttribute(CommonWebKeys.SIGN_ON_USER_ACCOUNT);
		strUsr_id =	account.getUsr_id();
		strUsr_nm = account.getUsr_nm();


		event = (DouTrn0003Event)request.getAttribute("Event");
		serverException = (Exception)request.getAttribute(CommonWebKeys.EXCEPTION_OBJECT);

		if (serverException != null) {
			strErrMsg = new ErrorHandler(serverException).loadPopupMessage();
		}

		// 초기화면 로딩시 서버로부터 가져온 데이터 추출하는 로직추가 ..
		GeneralEventResponse eventResponse = (GeneralEventResponse) request.getAttribute("EventResponse");
		partnerStr = eventResponse.getETCData("partners");


	}catch(Exception e) {
		out.println(e.toString());
	}
%>

<head>
<title>Practice3</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script language="javascript">
		var partnerCombo = "<%=partnerStr%>";
	function setupPage(){
		var errMessage = "<%=strErrMsg%>";
		if (errMessage.length >= 1) {
			ComShowMessage(errMessage);
		} // end if
		loadPage();
	}
</script>
</head>

<form name="form">
<input type="hidden" name="f_cmd">
<input type="hidden" name="partner">
<input type="hidden" name="intg_cd_id">
<input type="hidden" name="pagerows">
	
	<div class="page_title_area clear">
		<h2 class="page_title"><button type="button"><span id="title">PRACTICE 3</span></button></h2>
		
		<div class="opus_design_btn pad_8">
						   	<button type="button" class="btn_accent" name="btn_Retrieve" id="btn_Retrieve">Retrieve</button><!--
						   	--><button type="button" class="btn_accent" name="btn_New" id="btn_New">New</button><!--
						   	--><button type="button" class="btn_accent" name="btn_DownExcel" id="btn_DownExcel">Down Excel</button><!--
		   					--><button type="button" class="btn_accent" name="btn_Down" id="btn_Downl">Down</button>
		</div>
		
	    <div class="location">
	     	<span id="navigation"></span>
	    </div>
	</div>
	
	<div class="wrap_search">
	
		<div class="opus_design_inquiry" >		
		    <table>
		        <tbody>
				<tr>
					<th width="50">Year Month</th>
					<td width="200">
						<input type="text" style="width:100px; text-align: center"  class="input" value="" name="txt_fr_yrmon" id="txt_fr_yrmon"><!--
						--><button type="button" class="btn_left" name="btn_reduce_frYrMon" id="btn_reduce_frYrMon"></button><!--
		  				--><button type="button" class="btn_right" name="btn_incre_frYrMon" id="btn_incre_frYrMon"></button><!--
						--><input type="text" style="width:100px;text-align: center" class="input" value="" name="txt_to_yrmon" id="txt_to_yrmon"><!--
						--><button type="button" class="btn_left" name="btn_reduce_toYrMon" id="btn_reduce_toYrMon"></button><!--
		   				--><button type="button" class="btn_right" name="btn_incre_toYrMon" id="btn_incre_toYrMon"></button>
					</td>
					<th width="5%">Partner</th>
						<td width="2%"><script type="text/javascript">ComComboObject('s_partner',1,100, 1, 0, 0);</script></td>
					<th width="2%">Lane</th>
						<td width="2%"><script type="text/javascript">ComComboObject('s_lane',1,100, 1, 0, 0);</script></td>
					<th width="2%">Trade</th>
						<td><script type="text/javascript">ComComboObject('s_trade',1,100, 1, 0, 0);</script></td>
				</tr> 
				</tbody>
			</table>
			
			
	    <div class="location">
	     	<span id="navigation"></span>
	    </div>
			
		</div>
	</div>
	
	<div class="wrap_result">
	<div class="opus_design_tab">
			<script type="text/javascript">ComTabObject('tab1')</script>
		</div>
		<div class="opus_design_grid clear" name="tabLayer" id="tabLayer">
			<h3 class="title_design">Summary</h3>
			<script language="javascript">ComSheetObject('sheet1');</script>
		</div>
		
		<div class="opus_design_grid clear" name="tabLayer" id="tabLayer">
			<h3 class="title_design">Details</h3>
			<script language="javascript">ComSheetObject('sheet2');</script>
		</div>
		
	</div>
	
</form>
</body>