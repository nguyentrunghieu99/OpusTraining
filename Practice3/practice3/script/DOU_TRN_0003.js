var sheetObjects = new Array();
var sheetCnt = 0;

var comboObjects = new Array();
var comboCnt = 0;

var tabObjects = new Array();
var tabCnt = 0;

var countDay = 0;
document.onclick = processButtonClick;


function loadPage() {
	for (i = 0; i < sheetObjects.length; i++) {
		ComConfigSheet(sheetObjects[i]);
		initSheet(sheetObjects[i], i + 1);
		ComEndConfigSheet(sheetObjects[i]);
	}
	for (var k = 0; k < comboObjects.length; k++) {
		initCombo(comboObjects[k], k + 1);
	}
	initControl();
	comboObjects[1].SetEnable(false);
	comboObjects[2].SetEnable(false);
	for (k = 0; k < tabObjects.length; k++) {
		initTab(tabObjects[k], k + 1);
		tabObjects[k].SetSelectedIndex(0);
	}
	doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
	doActionIBSheet(sheetObjects[1], document.form, IBSEARCH);

	subSum();
	setTimeout(initDate, 100);
}

function initControl() {
	var input = document.getElementById("txt_fr_yrmon");
	input.addEventListener('change', function() {
		removeSheet();
	});
}

function processButtonClick() {
	var sheetObject1 = sheetObjects[0];
	var formObject = document.form;
	try {
		var srcName = ComGetEvent("name");
		switch (srcName) {
			case "btn_Retrieve":
				var day = countDate();
				if (day > 90 && countDay == 0) {
					if (ComShowCodeConfirm("DOU10001")) {
						countDay++;
						doActionIBSheet(sheetObjects[0], formObject, IBSEARCH);
						doActionIBSheet(sheetObjects[1], formObject, IBSEARCH);
					} else {
						return;
					}
				} else {
					doActionIBSheet(sheetObjects[0], formObject, IBSEARCH);
					doActionIBSheet(sheetObjects[1], formObject, IBSEARCH);
				}

				break;
			case "btn_New":
				clearForm();

				break;
			case "btn_DownExcel":
				doActionIBSheet(sheetObject1, formObject, IBDOWNEXCEL);
				break;

			case "btn_incre_frYrMon":
				setIncreDate('txt_fr_yrmon', 'txt_to_yrmon');
				removeSheet();
				break;
			case "btn_incre_toYrMon":
				setIncreDate('txt_to_yrmon', '');
				removeSheet();
				break;

			case "btn_reduce_frYrMon":
				setReduceDate('txt_fr_yrmon', '');
				removeSheet();
				break;

			case "btn_reduce_toYrMon":
				setReduceDate('txt_to_yrmon', 'txt_fr_yrmon');
				removeSheet();
				break;
		}
	} catch (e) {
		if (e == "[object Error]") {
			ComShowMessage(OBJECT_ERROR);
		} else {
			ComShowMessage(e);
		}
	}
}

function setSheetObject(sheet_obj) {
	sheetObjects[sheetCnt++] = sheet_obj;
}

function initSheet(sheetObj, sheetNo) {
	var cnt = 0;
	switch (sheetNo) {
	case 1: // sheet1 init
		with (sheetObj) {
			var HeadTitle1 = "|Partner|Lane|Invoice No|Slip No|Approved|Curr.|Revenue|Expense|Customer/S.Provider|Customer/S.Provider";
			var HeadTitle2 = "|Partner|Lane|Invoice No|Slip No|Approved|Curr.|Revenue|Expense|Code|Name";
			/*SearchMode: configure search mode (Default: 2)
				LazyLoad mode: Search all data and display search result data on the screen by page as set in Page property value according to the scroll location
			MergeSheet: Merge type (Default=0)
				0 msNone 		 No merge is allowed;
				5 msHeaderOnly Allow merge in the header rows only
			Page: Number of rows to display in one page (Default=20)
			DataRowMerge: whether to allow horizontal merge of the entire row (Default=0)
			 */
			SetConfig({ SearchMode : 2, MergeSheet : 5, Page : 20, DataRowMerge : 0 });
			
			/*Define header functions such as sorting and column movement permissions.
			 	Sort - Boolean - Whether to allow sorting by clicking on the header (Default=1)
			 	ColMove - Boolean - Whether to allow column movement in header (Default=1)
			 	ColResize Boolean Whether to allow resizing of column width (Default=1)
			 	HeaderCheck Boolean Whether the CheckAll in the header is checked (Default=1)
			 */
			var info = { Sort : 1, ColMove : 1, HeaderCheck : 0, ColResize : 1};
			var headers = [{Text : HeadTitle1,Align : "Center"}, {Text : HeadTitle2, Align : "Center"}];
			InitHeaders(headers, info);
			var cols = [ {Type : "Status",Hidden : 1,Width : 30,Align : "Center",ColMerge : 0,SaveName : "ibflag"}, {
				Type : "Text",	Hidden : 0,	Width : 70,		Align : "left",		ColMerge : 0,SaveName : 'jo_crr_cd',		KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Text",	Hidden : 0,	Width : 40,		Align : "Center",	ColMerge : 0,SaveName : "rlane_cd",			KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Text",	Hidden : 0,	Width : 100,	Align : "Center",	ColMerge : 0,SaveName : "inv_no",			KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Text",	Hidden : 0,	Width : 210,	Align : "Left",		ColMerge : 0,SaveName : "csr_no",			KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Text",	Hidden : 0,	Widdth : 80,	Align : "Center",	ColMerge : 0,SaveName : "apro_flg",			KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Text",	Hidden : 0,	Width : 130,	Align : "Center",	ColMerge : 0,SaveName : "locl_curr_cd",		KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Float",	Hidden : 0,	Width : 125,	Align : "Center",	ColMerge : 0,SaveName : "inv_rev_act_amt",	KeyField : 0,CalcLogic : "",Format : "",PointCount : 2,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Float",	Hidden : 0,	Width : 125,	Align : "Right",	ColMerge : 0,SaveName : "inv_exp_act_amt",	KeyField : 0,CalcLogic : "",Format : "",PointCount : 2,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Text",	Hidden : 0,	Width : 120,	Align : "Center",	ColMerge : 0,SaveName : "prnr_ref_no",		KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Float",	Hidden : 0,	Width : 125,	Align : "Right",	ColMerge : 0,SaveName : "cust_vndr_eng_nm",	KeyField : 0,CalcLogic : "",Format : "",PointCount : 2,UpdateEdit : 0,InsertEdit : 0}];
				
			InitColumns(cols);
			SetEditable(1);
			resizeSheet();
			// SetWaitImageVisible: configure whether to display waiting image during processing.
        	// If you do not want to use the waiting image for any reason, set this property as 0 to remove waiting image.
			SetWaitImageVisible(0);
		}
		break;
	case 2:
		with (sheetObj) {
			var HeadTitle1 = "|Partner|Lane|Invoice No|Slip No|Approved|Rev/Exp|Item|Curr.|Revenue|Expense|Customer/S.Provider|Customer/S.Provider";
			var HeadTitle2 = "|Partner|Lane|Invoice No|Slip No|Approved|Rev/Exp|Item|Curr.|Revenue|Expense|Code|Name";
			var headCount = ComCountHeadTitle(HeadTitle2);
			// console.log(headCount);

			SetConfig({SearchMode : 2,MergeSheet : 5,Page : 20,FrozenCol : 0,DataRowMerge : 1});

			var info = {Sort : 1,ColMove : 1,HeaderCheck : 0,ColResize : 1};
			var headers = [ {Text : HeadTitle1,Align : "Center"}, {Text : HeadTitle2,Align : "Center"} ];
			InitHeaders(headers, info);

			var cols = [ {Type : "Status",Hidden : 1,Width : 30,Align : "Center",ColMerge : 0,SaveName : "ibflag"}, {
				Type : "Text",	Hidden : 0,	Width : 70,		Align : "left",		ColMerge : 0,SaveName : 'jo_crr_cd',		KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Text",	Hidden : 0,	Width : 40,		Align : "Center",	ColMerge : 0,SaveName : "rlane_cd",			KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Text",	Hidden : 0,	Width : 100,	Align : "Center",	ColMerge : 0,SaveName : "inv_no",			KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Text",	Hidden : 0,	Width : 210,	Align : "Left",		ColMerge : 0,SaveName : "csr_no",			KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Text",	Hidden : 0,	Widdth : 80,	Align : "Center",	ColMerge : 0,SaveName : "apro_flg",			KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Text",	Hidden : 0,	Width : 80,		Align : "Center",	ColMerge : 0,SaveName : "rev_exp",			KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Text",	Hidden : 0,	Width : 80,		Align : "Center",	ColMerge : 0,SaveName : "item",				KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Text",	Hidden : 0,	Width : 130,	Align : "Center",	ColMerge : 0,SaveName : "locl_curr_cd",		KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Float",	Hidden : 0,	Width : 125,	Align : "Center",	ColMerge : 0,SaveName : "inv_rev_act_amt",	KeyField : 0,CalcLogic : "",Format : "",PointCount : 2,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Float",	Hidden : 0,	Width : 125,	Align : "Right",	ColMerge : 0,SaveName : "inv_exp_act_amt",	KeyField : 0,CalcLogic : "",Format : "",PointCount : 2,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Text",	Hidden : 0,	Width : 120,	Align : "Center",	ColMerge : 0,SaveName : "prnr_ref_no",		KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Float",	Hidden : 0,	Width : 125,	Align : "Right",	ColMerge : 0,SaveName : "cust_vndr_eng_nm",	KeyField : 0,CalcLogic : "",Format : "",PointCount : 2,UpdateEdit : 0,InsertEdit : 0}];

			InitColumns(cols);
			SetEditable(1);
			SetWaitImageVisible(0);
			resizeSheet();
		}
		break;
	}
}

function resizeSheet() {
	ComResizeSheet(sheetObjects[0]);
	ComResizeSheet(sheetObjects[1]);
}

function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) {
	formatSubSum(sheetObj);
	document.form.f_cmd.value = SEARCH04;
	var Param = FormQueryString(document.form);
	var sXml = sheetObj.GetSearchData("DOU_TRN_0003GS.do", Param);

	countCurr = ComGetEtcData(sXml, "countCurr");
	for (var i = 1; i <= countCurr; i++) {
		var rowInsert = sheetObj.DataInsert(-1);
		sheetObj.SetCellValue(rowInsert, 6, ComGetEtcData(sXml, "curr" + i));
		sheetObj.SetCellFontBold(rowInsert, 6, 1);

		sheetObj.SetCellValue(rowInsert, 7, ComGetEtcData(sXml, "rev" + i));
		sheetObj.SetCellFontBold(rowInsert, 7, 1);

		sheetObj.SetCellValue(rowInsert, 8, ComGetEtcData(sXml, "exp" + i));
		sheetObj.SetCellFontBold(rowInsert, 8, 1);

		sheetObj.SetRowBackColor(rowInsert, "FFDAB9");;
		//		sheetObj.SetSubSumBackColor("#F4F4F4");

	}

	ComOpenWait(false);
}

function sheet2_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) {
	formatSubSum(sheetObj);
	document.form.f_cmd.value = SEARCH04;
	var Param = FormQueryString(document.form);
	var sXml = sheetObj.GetSearchData("DOU_TRN_0003GS.do", Param);

	countCurr = ComGetEtcData(sXml, "countCurr");
	for (var i = 1; i <= countCurr; i++) {
		var rowInsert = sheetObj.DataInsert(-1);
		sheetObj.SetCellValue(rowInsert, 8, ComGetEtcData(sXml, "curr" + i));
		sheetObj.SetCellFontBold(rowInsert, 8, 1);

		sheetObj.SetCellValue(rowInsert, 9, ComGetEtcData(sXml, "rev" + i));
		sheetObj.SetCellFontBold(rowInsert, 9, 1);

		sheetObj.SetCellValue(rowInsert, 10, ComGetEtcData(sXml, "exp" + i));
		sheetObj.SetCellFontBold(rowInsert, 10, 1);

		sheetObj.SetRowBackColor(rowInsert, "FFDAB9");;
		//		sheetObj.SetSubSumBackColor("#F4F4F4");		
	}
	ComOpenWait(false);
}

function doActionIBSheet(sheetObj, formObj, sAction) {
	switch (sAction) {
		case IBSEARCH:
			if (sheetObj.id == 'sheet1') {
				formObj.f_cmd.value = SEARCH;
				ComOpenWait(false);
				sheetObj.DoSearch("DOU_TRN_0003GS.do", FormQueryString(formObj));
			} else if (sheetObj.id == 'sheet2') {
				formObj.f_cmd.value = SEARCH01;
				ComOpenWait(false);
				sheetObj.DoSearch("DOU_TRN_0003GS.do", FormQueryString(formObj));
			}
			break;
			//	
		case IBDOWNEXCEL:
			if (sheetObj.RowCount() < 1) {
				ComShowCodeMessage("COM132501");
			} else {
				sheetObjects[0].Down2ExcelBuffer(true);
				// FileName - String - File name to save
				// SheetName - String - Excel worksheet name, Default="Sheet"
				// SheetDesign - Integer - Whether to apply IBSheet design concept to download file, Default=0
				// DownCols String Optional Connect columns to download using | Default=""(Download all)
				sheetObjects[0].Down2Excel({
					FileName: 'Excel2',
					SheetName: 'Sheet1',
					DownCols: makeHiddenSkipCol(sheetObjects[0]),
					SheetDesign: 1,
					Merge: 1
				});
				sheetObjects[1].Down2Excel({
					FileName: 'Excel2',
					SheetName: 'Sheet2',
					DownCols: makeHiddenSkipCol(sheetObjects[1]),
					SheetDesign: 1,
					Merge: 1
				});
				sheetObjects[0].Down2ExcelBuffer(false);
			}
			break;
	}
}

function sheet1_OnDblClick(sheetObj, Row, Col) {
	sheetObj1 = sheetObjects[1];
	tab1_OnChange(tabObjects[0], 1);

	var rowPartner = sheetObj1.FindText(1, sheetObj.GetCellValue(Row, 1), Row);
	var rowRlane = sheetObj1.FindText(2, sheetObj.GetCellValue(Row, 2), rowPartner);
	var rowInvoice = sheetObj1.FindText(3, sheetObj.GetCellValue(Row, 3), rowRlane);
	var rowSlipNo = sheetObj1.FindText(4, sheetObj.GetCellValue(Row, 4), rowInvoice);
	var rowApproved = sheetObj1.FindText(5, sheetObj.GetCellValue(Row, 5), rowSlipNo);
	var rowCurr = sheetObj1.FindText(8, sheetObj.GetCellValue(Row, 6), rowApproved);
	var rowCode = sheetObj1.FindText(11, sheetObj.GetCellValue(Row, 9), rowCurr);
	sheetObjects[1].SetSelectRow(rowCode);
	tabObjects[0].SetSelectedIndex(1);
}

function subSum() {
	var infos1 = [{
		StdCol: 3,
		SumCols: "7|8",
		ShowCumulate: 0,
		CaptionText: ' ',
		CaptionCol: 1
	}];
	var infos2 = [{
		StdCol: 3,
		SumCols: "9|10",
		ShowCumulate: 0,
		CaptionText: ' ',
		CaptionCol: 1
	}];
	sheetObjects[0].ShowSubSum(infos1);
	sheetObjects[1].ShowSubSum(infos2);

}

function formatSubSum(sheetObj) {
	var strRow = sheetObj.FindSubSumRow(3);
	var Rows = strRow.split("|");
	if (Rows.length > 0) {
		if (sheetObj.id == 'sheet1') {
			for (var i = 0; i < Rows.length; i++) {
				sheetObj.SetCellValue(parseInt(Rows[i]), 6, sheetObj.GetCellValue(parseInt(Rows[i]) - 1, 6));
				sheetObj.SetRangeFontBold(parseInt(Rows[i]), 6, parseInt(Rows[i]), 8, 1);
				sheetObj.SetRowFontColor(parseInt(Rows[i]), "#FF3333");
			}
		}
		if (sheetObj.id == 'sheet2') {
			for (var i = 0; i < Rows.length; i++) {
				sheetObj.SetCellValue(parseInt(Rows[i]), 8, sheetObj.GetCellValue(parseInt(Rows[i]) - 1, 8));
				sheetObj.SetRangeFontBold(parseInt(Rows[i]), 8, parseInt(Rows[i]), 10, 1);
				sheetObj.SetRowFontColor(parseInt(Rows[i]), "#FF3333");

			}
		}
	}
}

function removeSheet() {
	for (i = 0; i < sheetObjects.length; i++) {
		sheetObjects[i].RemoveAll();
	}
}

function initTab(tabObj, tabNo) {
	switch (tabNo) {
		case 1:
			with(tabObj) {
				var cnt = 0;
				InsertItem("Summary", "");
				InsertItem("Details", "");
			}
			break;
	}
}

function setTabObject(tab_obj) {
	tabObjects[tabCnt++] = tab_obj;
}

function tab1_OnChange(tabObj, nItem) {
	var objs = document.all.item("tabLayer");;
	objs[nItem].style.display = "inline";
	for (var i = 0; i < objs.length; i++) {
		if (i != nItem) {
			objs[i].style.display = "none";
		}
	}
	resizeSheet();
}

function setComboObject(combo_obj) {
	comboObjects[comboCnt++] = combo_obj;
}

function initCombo(comboObj, comboNo) {
	var formObj = document.form
	switch (comboNo) {
		case 1:
			with(comboObj) {
				SetMultiSelect(1);
				SetDropHeight(200);
				//			ValidChar(2, 1);
				comboObj.InsertItem(-1, "(ALL)", "ALL");
				generDataCombo(comboObj, partnerCombo);
				SetItemCheck(0, true);
				break;

			}

	}
}

function generDataCombo(comboObj, dataStr) {
	if (comboObj != comboObjects[0]) {
		comboObj.RemoveAll();
	}
	if (comboObj == comboObjects[1]) {
		comboObjects[2].RemoveAll();
		comboObjects[2].SetEnable(false);
	}
	if (typeof dataStr != 'undefined') {
		if (dataStr.indexOf("|") != -1) {
			var data = dataStr.split("|");
			for (var i = 0; i < data.length; i++) {
				comboObj.InsertItem(-1, data[i], data[i]);
			}
		} else if (dataStr.length > 0 && dataStr.indexOf("|") == -1) {
			comboObj.InsertItem(-1, dataStr, dataStr);
		}
	} else if (typeof dataStr == 'undefined') {
		comboObjects[0].SetItemCheck(0, true);
		comboObjects[1].SetEnable(false);
		comboObjects[2].SetEnable(false);
	}
}

function s_partner_OnCheckClick(Index, Code, Checked) {
	if (Checked != 'ALL' && comboObjects[0].GetItemCheck(0) == true) {
		comboObjects[0].SetItemCheck(0, false);
	}
	document.form.f_cmd.value = SEARCH02;
	var sParam = FormQueryString(document.form);
	var sXml = sheetObjects[0].GetSearchData("DOU_TRN_0003GS.do", sParam);
	lanes = ComGetEtcData(sXml, "lanes");
	comboObjects[1].SetEnable(true);
	generDataCombo(comboObjects[1], lanes);
}

function s_lane_OnSelect(Index, Code, Checked) {
	document.form.f_cmd.value = SEARCH03;
	var Param = FormQueryString(document.form);
	Param += '&lane=' + Checked;
	var sXml = sheetObjects[0].GetSearchData("DOU_TRN_0003GS.do", Param);
	trades = ComGetEtcData(sXml, "trades");
	console.log(trades);
	comboObjects[2].SetEnable(true);
	generDataCombo(comboObjects[2], trades);

}

function initDate() {
	var now = new Date();
	var nYear = now.getFullYear();
	var preMon = now.getMonth();
	var nowMon = now.getMonth() + 1;
	var fr_yrmon;
	var to_yrmon;

	if (nowMon == 10 && preMon == 9) {
		fr_yrmon = nYear + '-0' + preMon;
		to_yrmon = nYear + '-' + nowMon;
	} else if (nowMon < 10 && preMon < 10) {
		fr_yrmon = nYear + '-0' + preMon;
		console.log(fr_yrmon);
		to_yrmon = nYear + '-0' + nowMon;
	} else if (nowMon > 9 && preMon > 9) {
		fr_yrmon = nYear + '-' + preMon;
		to_yrmon = nYear + '-' + nowMon;
	}
	document.getElementById("txt_fr_yrmon").value = fr_yrmon;
	document.getElementById("txt_to_yrmon").value = to_yrmon;

}

function countDate() {
	var frYrMon = new Date(document.getElementById("txt_fr_yrmon").value);
	var toYrMon = new Date(document.getElementById("txt_to_yrmon").value);
	return parseInt((toYrMon - frYrMon) / (24 * 3600 * 1000));
}

function setIncreDate(name_btn1, name_btn2) {
	var yrMon1 = document.getElementById(name_btn1).value;
	var date1 = yrMon1.split("-");
	var increMon = Number(date1[1]) + 1;
	var increYr = Number(date1[0]) + 1;

	var yrMon2 = '';
	var date2 = '';

	if (name_btn2 != '') {
		var yrMon2 = document.getElementById(name_btn2).value;
		var date2 = yrMon2.split("-");
	}

	if (date2[0] == date1[0] && increMon > date2[1]) {
		ComShowCodeMessage("DOU10002");
	} else if (increMon > 12) {
		document.getElementById(name_btn1).value = increYr + '-' +
			'01';
	} else if (increMon >= 10 && increMon <= 12) {
		document.getElementById(name_btn1).value = date1[0] + '-' +
			increMon;
	} else if (increMon < 10 && increMon > 0) {
		document.getElementById(name_btn1).value = date1[0] +
			'-0' + increMon;
	}
}

function setReduceDate(name_btn1, name_btn2) {
	var yrMon1 = document.getElementById(name_btn1).value;
	var date1 = yrMon1.split("-");
	var increMon = Number(date1[1]) - 1;
	var increYr = Number(date1[0]) - 1;

	var yrMon2 = '';
	var date2 = '';

	if (name_btn2 != '') {
		var yrMon2 = document.getElementById(name_btn2).value;
		var date2 = yrMon2.split("-");
	}

	if (date2[0] == date1[0] && increMon < date2[1]) {
		ComShowCodeMessage("COM132002");
	} else if (increMon < 1) {

		document.getElementById(name_btn1).value = increYr + '-' +
			'12';

	} else if (increMon >= 10 && increMon <= 12) {
		document.getElementById(name_btn1).value = date1[0] + '-' +
			increMon;
	} else if (increMon < 10 && increMon > 0) {
		document.getElementById(name_btn1).value = date1[0] + '-0' +
			increMon;
	}
}

function clearForm() {
	initDate();
	for (var k = 0; k < comboObjects.length; k++) {
		if (k > 0) {
			comboObjects[k].RemoveAll();
			comboObjects[k].SetEnable(false);
			continue;
		}
		comboObjects[k].SetSelectIndex(-1, true);
		comboObjects[k].SetItemCheck(0, true);
	}
	removeSheet();

}

function DOU_TRN_0003() {
	this.processButtonClick = tprocessButtonClick;
	this.setSheetObject = setSheetObject;
	this.loadPage = loadPage;
	this.initSheet = initSheet;
	this.initControl = initControl;
	this.doActionIBSheet = doActionIBSheet;
	this.setTabObject = setTabObject;
}