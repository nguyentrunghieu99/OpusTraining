<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.clt.framework.component.util.JSPUtil"%>
<%@ page import="com.clt.framework.component.util.DateTime"%>
<%@ page import="com.clt.framework.component.message.ErrorHandler"%>
<%@ page import="com.clt.framework.core.layer.event.GeneralEventResponse"%>
<%@ page import="com.clt.framework.support.controller.html.CommonWebKeys"%>
<%@ page import="com.clt.framework.support.view.signon.SignOnUserAccount"%>
<%@ page import="com.clt.apps.opus.dou.doutraining.practice4.event.DouTrn0004Event"%>
<%@ page import="org.apache.log4j.Logger" %>

<%
	DouTrn0004Event  event = null;
	Exception serverException   = null;
	String strErrMsg = "";
	
	String successFlag = "";
	String codeList  = "";
	String pageRows  = "100";

	String strUsr_id		= "";
	String strUsr_nm		= "";
	
	String crrStr			= "";
	String rlandStr			= "";
	Logger log = Logger.getLogger("com.clt.apps.DouTraining.CodeMgmt");

	try {
	   	SignOnUserAccount account=(SignOnUserAccount)session.getAttribute(CommonWebKeys.SIGN_ON_USER_ACCOUNT);
		strUsr_id =	account.getUsr_id();
		strUsr_nm = account.getUsr_nm();


		event = (DouTrn0004Event)request.getAttribute("Event");
		serverException = (Exception)request.getAttribute(CommonWebKeys.EXCEPTION_OBJECT);

		if (serverException != null) {
			strErrMsg = new ErrorHandler(serverException).loadPopupMessage();
		}

		// 초기화면 로딩시 서버로부터 가져온 데이터 추출하는 로직추가 ..
		GeneralEventResponse eventResponse = (GeneralEventResponse) request.getAttribute("EventResponse");
		crrStr = eventResponse.getETCData("CrrCd");
		rlandStr = eventResponse.getETCData("RlandCd");


	}catch(Exception e) {
		out.println(e.toString());
	}
%>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script language="javascript">
	var crrCdCombo = "<%=crrStr%>";
	var rlandCombo = "<%=rlandStr%>";
	function setupPage(){
	var errMessage = "<%=strErrMsg%>";
		if (errMessage.length >= 1) {
			ComShowMessage(errMessage);
		}
	loadPage();
	}
</script>
</head>

<form name="form">
<input type="hidden" name="f_cmd">

<div class="page_title_area clear">
<h2 class="page_title"><button type="button"><span id="title">PRACTICE 4</span></button></h2>
<div class="opus_design_btn pad_8">
						   	<button type="button" class="btn_accent" name="btn_Retrieve" id="btn_Retrieve">Retrieve</button><!--
						   	--><button type="button" class="btn_accent" name="btn_New" id="btn_New">New</button><!--
						   	--><button type="button" class="btn_accent" name="btn_Save" id="btn_Save">Save</button><!--
						   	--><button type="button" class="btn_accent" name="btn_DownExcel" id="btn_DownExcel">Down Excel</button>
		</div>
</div>

<div class="wrap_search">
	<div class="opus_design_inquiry" >
		<table>
			<tbody>
				<tr>
					<th width="50">Carrier</th>
					<td width=100"><script type="text/javascript">ComComboObject('s_crr_cd',1,100, 1, 0, 0);</script></td>
					
					<th width="70">Vendor</th>
					<td width="100"><input type="text" style="width:100px; text-align: center"  class="input" value="" name="s_vendor" id="s_vendor"></td>
					
					<th width="90">Create Date</th>
					<td><input type="text" style="width:80px;text-align:center;ime-mode:disabled" dataformat="ymd" maxlength="8" class="input" name="s_fr_date" value="" id="s_fr_date" /><!-- 
			 		--><button type="button" name="btn_fr_date" id="btn_fr_date"  class="calendar ir"></button><!--
			 		--><input type="text" style="width:80px;text-align:center;ime-mode:disabled" dataformat="ymd" maxlength="8" class="input" name="s_to_date" value="" id="s_to_date" /><!-- 
			 		--><button type="button" name="btn_to_date" id="btn_to_date"  class="calendar ir"></button>
				</tr>		
			</tbody>
		</table>

	</div>
</div>
<div class="wrap_result">
			<div class="opus_design_grid">
				<div class="opus_design_btn">
					<button type="button" class="btn_accent" name="btn_Delete" id="btn_Delete">Row Delete</button>
					<button type="button" class="btn_accent" name="btn_Add" id="btn_Add">Row Add</button>
				</div>
				<script language="javascript">ComSheetObject('sheet1');</script>
			
			</div>		
	</div>