var sheetObjects = new Array();
var sheetCnt = 0;
var rowcount = 0;
document.onclick = processButtonClick;

document.onkeydown = logKey;
var comboObjects = new Array();
var comboCnt = 0;
var comboValues = "";
var tabObjects = new Array();
var tabCnt = 0;
var beforetab = 1;
var countDay = 0;

function logKey(key) {
	var sheetObject1 = sheetObjects[0];
	var formObj = document.form;
	if (key.code == 'Enter') {
		doActionIBSheet(sheetObject1, formObj, IBSEARCH);
	}
}

function processButtonClick() {
	var sheetObject1 = sheetObjects[0];
	var formObject = document.form;
	try {
		var srcName = ComGetEvent("name");
		switch (srcName) {
		case "btn_Retrieve":
			var Day = countDate();

			if (Day > 90 && countDay == 0) {
				if (confirm("Year Month over 3 months, do you realy want to get data?")) {
					countDay++;
					doActionIBSheet(sheetObjects[0], formObject, IBSEARCH);

					doActionIBSheet(sheetObjects[1], formObject, IBSEARCH);
				} else {
					return;
				}
			}else{
				doActionIBSheet(sheetObjects[0], formObject, IBSEARCH);

				doActionIBSheet(sheetObjects[1], formObject, IBSEARCH);
			}

			break;
		case "btn_New":
			clearForm()
			
			
			break;
		case "btn_DownExcel":
			doActionIBSheet(sheetObject1, formObject, IBDOWNEXCEL);
			break;
		case "btn_rowadd_ms":
			doActionIBSheet(sheetObject1, formObject, IBINSERT);
			break;
		case "btn_rowdelete_ms":
			if (sheetObjects[1].SearchRows() == 0) {
				doActionIBSheet(sheetObject1, formObject, IBDELETE);
			} else {
				if (confirm("Do you delete detail table?")) {
					doActionIBSheet(sheetObject1, formObject, IBDELETE);
				} else {
					return;
				}
			}
			break;

		case "btn_rowadd_dl":
			doActionIBSheet(sheetObjects[1], formObject, IBINSERT);
			break;
		case "btn_rowdelete_dl":
			doActionIBSheet(sheetObjects[1], formObject, IBDELETE);
			break;

		case "btn_incre_frYrMon":
			var frYrMon = document.getElementById("txt_fr_yrmon").value;
			var toYrMon = document.getElementById("txt_to_yrmon").value;

			var frDate = frYrMon.split("-");
			var toDate = toYrMon.split("-");

			var increMon = Number(frDate[1]) + 1;
			var increYr = Number(frDate[0]) + 1;

			if (frDate[0] == toDate[0] && increMon > toDate[1]) {
				alert('Over limit');
			}
			else if (increMon > 12) {

				document.getElementById("txt_fr_yrmon").value = increYr + '-'
						+ '01';

			} else if (increMon >= 10 && increMon <= 12) {
				document.getElementById("txt_fr_yrmon").value = frDate[0] + '-'
						+ increMon;
			} else if (increMon < 10 && increMon > 0) {
				document.getElementById("txt_fr_yrmon").value = frDate[0]
						+ '-0' + increMon;
			}

			break;
		case "btn_incre_toYrMon":
			var toYrMon = document.getElementById("txt_to_yrmon").value;
			var Date = toYrMon.split("-");

			var increMon = Number(Date[1]) + 1;
			var increYr = Number(Date[0]) + 1;

			if (increMon > 12) {

				document.getElementById("txt_to_yrmon").value = increYr + '-'
						+ '01';

			} else if (increMon >= 10 && increMon <= 12) {
				document.getElementById("txt_to_yrmon").value = Date[0] + '-'
						+ increMon;
			} else if (increMon < 10 && increMon > 0) {
				document.getElementById("txt_to_yrmon").value = Date[0] + '-0'
						+ increMon;
			}

			break;

		case "btn_reduce_frYrMon":
			var frYrMon = document.getElementById("txt_fr_yrmon").value;
			var Date = frYrMon.split("-");

			var increMon = Number(Date[1]) - 1;
			var increYr = Number(Date[0]) - 1;

			if (increMon < 1) {

				document.getElementById("txt_fr_yrmon").value = increYr + '-'
						+ '12';

			} else if (increMon >= 10 && increMon <= 12) {
				document.getElementById("txt_fr_yrmon").value = Date[0] + '-'
						+ increMon;
			} else if (increMon < 10 && increMon > 0) {
				document.getElementById("txt_fr_yrmon").value = Date[0] + '-0'
						+ increMon;
			}

			break;

		case "btn_reduce_toYrMon":
			var toYrMon = document.getElementById("txt_to_yrmon").value;
			var frYrMon = document.getElementById("txt_fr_yrmon").value;
			
			var toDate = toYrMon.split("-");
			
			var frDate = frYrMon.split("-");

			var increMon = Number(toDate[1]) - 1;
			var increYr = Number(toDate[0]) - 1;
			
			if(toDate[0]==frDate[0] && increMon < frDate[1]){
				alert('Over limit');
			}

			else if (increMon < 1) {

				document.getElementById("txt_to_yrmon").value = increYr + '-'
						+ '12';

			} else if (increMon >= 10 && increMon <= 12) {
				document.getElementById("txt_to_yrmon").value = toDate[0] + '-'
						+ increMon;
			} else if (increMon < 10 && increMon > 0) {
				document.getElementById("txt_to_yrmon").value = toDate[0] + '-0'
						+ increMon;
			}

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

function loadPage() {
	initDate();

	for (i = 0; i < sheetObjects.length; i++) {
		ComConfigSheet(sheetObjects[i]);
		initSheet(sheetObjects[i], i + 1);
		ComEndConfigSheet(sheetObjects[i]);
	}
	// console.log(comboObjects);
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
	subSum();
	doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
	doActionIBSheet(sheetObjects[1], document.form, IBSEARCH);	

}

function initControl() {

}

/**
 * setting Combo basic info param : comboObj, comboNo initializing sheet
 */

function clearForm(){
	initDate();
	for (var k = 0; k < comboObjects.length; k++) {
		if(k>0){
			comboObjects[k].RemoveAll();
			comboObjects[k].SetEnable(false);
			continue;
		}
		comboObjects[k].SetSelectIndex(-1, true);
		comboObjects[k].SetItemCheck(0, true);
	}
		
	for(i=0;i<sheetObjects.length;i++){
		sheetObjects[i].RemoveAll();
	}

}

function initCombo(comboObj, comboNo) {
	var formObj = document.form
	switch (comboNo) {
	case 1:
		with (comboObj) {
			SetMultiSelect(1);
			SetDropHeight(200);
//			ValidChar(2, 1);
			var comboItems = partnerCombo.split("|");
			comboObj.InsertItem(-1, "(ALL)", "ALL");
			addComboItem(comboObj, comboItems);
			SetItemCheck(0,true);
			break;
		
		}
		
	}
}

function addComboItem(comboObj, comboItems) {
	for (var i = 0; i < comboItems.length; i++) {
		var comboItem = comboItems[i].split(",");
		// comboObj.InsertItem(i, comboItem[0] + "|" + comboItem[1],
		// comboItem[1]);
		// NYK Modify 2014.10.21
		if (comboItem.length == 1) {
			comboObj.InsertItem(-1, comboItem[0], comboItem[0]);
		} else {
			comboObj.InsertItem(-1, comboItem[0] + "|" + comboItem[1],
					comboItem[1]);
		}

	}
}

function subSum(){
	var infos1 = [
{StdCol:3, SumCols:"7|8", ShowCumulate:0, CaptionText:' ', CaptionCol:1}
];
	var infos2 = [
{StdCol:3, SumCols:"9|10", ShowCumulate:0, CaptionText:' ', CaptionCol:1}
];
	sheetObjects[0].ShowSubSum(infos1);
	sheetObjects[1].ShowSubSum(infos2);
}

function setComboObject(combo_obj) {
	comboObjects[comboCnt++] = combo_obj;
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
			SetConfig({
				SearchMode : 2,
				MergeSheet : 5,
				Page : 20,
				DataRowMerge : 0
			});
			var info = {
				Sort : 1,
				ColMove : 1,
				HeaderCheck : 0,
				ColResize : 1
			};
			var headers = [ {
				Text : HeadTitle1,
				Align : "Center"
			}, {
				Text : HeadTitle2,
				Align : "Center"
			} ];
			InitHeaders(headers, info);
			var cols = [ {
				Type : "Status",
				Hidden : 1,
				Width : 30,
				Align : "Center",
				ColMerge : 0,
				SaveName : "ibflag"
			}, {
				Type : "Text",
				Hidden : 0,
				Width : 70,
				Align : "left",
				ColMerge : 0,
				SaveName : 'jo_crr_cd',
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 0,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Text",
				Hidden : 0,
				Width : 40,
				Align : "Center",
				ColMerge : 0,
				SaveName : "rlane_cd",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 0,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Text",
				Hidden : 0,
				Width : 100,
				Align : "Center",
				ColMerge : 0,
				SaveName : "inv_no",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 0,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Text",
				Hidden : 0,
				Width : 210,
				Align : "Left",
				ColMerge : 0,
				SaveName : "csr_no",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 0,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Text",
				Hidden : 0,
				Width : 80,
				Align : "Center",
				ColMerge : 0,
				SaveName : "apro_flg",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 0,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Text",
				Hidden : 0,
				Width : 130,
				Align : "Center",
				ColMerge : 0,
				SaveName : "locl_curr_cd",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 0,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Float",
				Hidden : 0,
				Width : 125,
				Align : "Center",
				ColMerge : 0,
				SaveName : "inv_rev_act_amt",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 2,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Float",
				Hidden : 0,
				Width : 125,
				Align : "Right",
				ColMerge : 0,
				SaveName : "inv_exp_act_amt",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 2,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Text",
				Hidden : 0,
				Width : 120,
				Align : "Center",
				ColMerge : 0,
				SaveName : "prnr_ref_no",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 0,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Float",
				Hidden : 0,
				Width : 125,
				Align : "Right",
				ColMerge : 0,
				SaveName : "cust_vndr_eng_nm",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 2,
				UpdateEdit : 0,
				InsertEdit : 0
			} ];
			InitColumns(cols);
			SetEditable(1);
			resizeSheet();
			SetWaitImageVisible(0);
		}
		break;
	case 2:
		with (sheetObj) {
			var HeadTitle1 = "|Partner|Lane|Invoice No|Slip No|Approved|Rev/Exp|Item|Curr.|Revenue|Expense|Customer/S.Provider|Customer/S.Provider";
			var HeadTitle2 = "|Partner|Lane|Invoice No|Slip No|Approved|Rev/Exp|Item|Curr.|Revenue|Expense|Code|Name";
			var headCount = ComCountHeadTitle(HeadTitle2);
			// console.log(headCount);

			SetConfig({
				SearchMode : 2,
				MergeSheet : 5,
				Page : 20,
				FrozenCol : 0,
				DataRowMerge : 1
			});

			var info = {
				Sort : 1,
				ColMove : 1,
				HeaderCheck : 0,
				ColResize : 1
			};
			var headers = [ {
				Text : HeadTitle1,
				Align : "Center"
			}, {
				Text : HeadTitle2,
				Align : "Center"
			} ];
			InitHeaders(headers, info);

			var cols = [ {
				Type : "Status",
				Hidden : 1,
				Width : 30,
				Align : "Center",
				ColMerge : 0,
				SaveName : "ibflag"
			}, {
				Type : "Text",
				Hidden : 0,
				Width : 70,
				Align : "left",
				ColMerge : 0,
				SaveName : 'jo_crr_cd',
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 0,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Text",
				Hidden : 0,
				Width : 40,
				Align : "Center",
				ColMerge : 0,
				SaveName : "rlane_cd",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 0,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Text",
				Hidden : 0,
				Width : 100,
				Align : "Center",
				ColMerge : 0,
				SaveName : "inv_no",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 0,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Text",
				Hidden : 0,
				Width : 210,
				Align : "Left",
				ColMerge : 0,
				SaveName : "csr_no",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 0,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Text",
				Hidden : 0,
				Width : 80,
				Align : "Center",
				ColMerge : 0,
				SaveName : "apro_flg",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 0,
				UpdateEdit : 0,
				InsertEdit : 0
			},

			{
				Type : "Text",
				Hidden : 0,
				Width : 80,
				Align : "Center",
				ColMerge : 0,
				SaveName : "rev_exp",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 0,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Text",
				Hidden : 0,
				Width : 80,
				Align : "Center",
				ColMerge : 0,
				SaveName : "item",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 0,
				UpdateEdit : 0,
				InsertEdit : 0
			},

			{
				Type : "Text",
				Hidden : 0,
				Width : 130,
				Align : "Center",
				ColMerge : 0,
				SaveName : "locl_curr_cd",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 0,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Float",
				Hidden : 0,
				Width : 80,
				Align : "Center",
				ColMerge : 0,
				SaveName : "inv_rev_act_amt",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 2,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Float",
				Hidden : 0,
				Width : 125,
				Align : "Right",
				ColMerge : 0,
				SaveName : "inv_exp_act_amt",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 2,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Text",
				Hidden : 0,
				Width : 120,
				Align : "Center",
				ColMerge : 0,
				SaveName : "prnr_ref_no",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 0,
				UpdateEdit : 0,
				InsertEdit : 0
			}, {
				Type : "Text",
				Hidden : 0,
				Width : 125,
				Align : "Right",
				ColMerge : 0,
				SaveName : "cust_vndr_eng_nm",
				KeyField : 0,
				CalcLogic : "",
				Format : "",
				PointCount : 2,
				UpdateEdit : 0,
				InsertEdit : 0
			} ];

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
// function resizeSheet(){
// ComResizeSheet(sheetObjects[0]);
// }

function doActionIBSheet(sheetObj, formObj, sAction) {
	switch (sAction) {
	case IBSEARCH:
		if (sheetObj.id == 'sheet1') {
			formObj.f_cmd.value = SEARCH;
			ComOpenWait(false);
			sheetObj.DoSearch("DOU_TRN_0003GS.do", FormQueryString(formObj));
//			 var sParam=FormQueryString(formObj);
//			 var sXml=sheetObj.GetSearchData("DOU_TRN_0003GS.do", sParam);
//			 sheetObj.LoadSearchData(sXml,{Sync:1});
		} else if (sheetObj.id == 'sheet2') {
			formObj.f_cmd.value = SEARCH;
			ComOpenWait(false);
			sheetObj.DoSearch("DOU_TRN_0003GS.do", FormQueryString(formObj));
		}

		break;
	
	case IBDOWNEXCEL:
		if (sheetObj.RowCount() < 1) {
			ComShowCodeMessage("COM132501");
		} else {
			sheetObjects[0].Down2ExcelBuffer(true);
			sheetObjects[0].Down2Excel({
				FileName : 'excel2',
				SheetName : 'sheet1',
				DownCols : makeHiddenSkipCol(sheetObjects[0]),
				SheetDesign : 1,
				Merge : 1
			});
			sheetObjects[1].Down2Excel({
				FileName : 'excel2',
				SheetName : 'sheet2',
				DownCols : makeHiddenSkipCol(sheetObjects[1]),
				SheetDesign : 1,
				Merge : 1
			});
			sheetObjects[0].Down2ExcelBuffer(false);
		}
		break;
	case IBDELETE:
		var j = sheetObj.GetSelectRow();
		// console.log(j);
		sheetObj.SetCellValue(j, "ibflag", "D");
		// sheetObj.SetRowHidden(j,1);
		if (sheetObj.id == "sheet1") {
			var codeid = sheetObj.GetCellValue(j, "intg_cd_id");
			// console.log(codeid);
			// console.log(sheetObjects[1].RowCount());
			if (sheetObjects[1].RowCount() > 0
					&& codeid == sheetObjects[1].GetCellValue(1, "intg_cd_id")) {
				for (i = sheetObjects[1].LastRow(); i > 0; i--) {
					sheetObjects[1].SetCellValue(i, "ibflag", "D");
					// sheetObjects[1].SetRowHidden(i,1);
				}
			}
		}
		break;
	case IBSAVE:
		if (validateForm(sheetObj, formObj, sAction) == false) {
			return false;
		}
		formObj.f_cmd.value = MULTI;
		sheetObj.DoSave("DOU_TRN_0002GS.do", FormQueryString(formObj));
		break;
	case 'IBSAVE01':
		formObj.f_cmd.value = MULTI01;
		sheetObj.DoSave("DOU_TRN_0002GS.do", FormQueryString(formObj));
		break;
	}
}

function validateForm(sheetObj, formObj, sAction) {
	var regex = new RegExp("^[0-9]+$");
	if (!regex.test(sheetObj.GetCellValue(sheetObj.GetSelectRow(),
			"intg_cd_len"))) {
		// console.log(sheetObj.GetSelectRow());
		alert('Length must be number!!');
		return false;
	}
	return true;
}

function sheet1_OnDblClick() {
//	document.form.intg_cd_id.value = sheetObjects[0].GetCellValue(Row,
//			"intg_cd_id");
//	doActionIBSheet(sheetObjects[1], document.form, IBSEARCH);
	tab1_OnChange(tabObjects[0], 1);
	sheetObjects[1].SetSelectRow(sheetObjects[0].GetSelectRow());
	tabObjects[0].SetSelectedIndex(1);
	
}


function sheet1_OnSaveEnd(code, msg) {
	// console.log(msg);
	if (msg >= 0) {
		alert('Save Master Table Successfully'); // saving success message
		doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
	} else {
		alert('Save Master Table Failed'); // saving failure message
	}
}

function sheet2_OnSaveEnd(code, msg) {
	if ((sheetObjects[0].RowCount("I") + sheetObjects[0].RowCount("U") + sheetObjects[0]
			.RowCount("D")) == 0) {
		// console.log((sheetObjects[0].RowCount("I")+sheetObjects[0].RowCount("U")+sheetObjects[0].RowCount("D")));
		console.log('Message');
		// if(msg >= 0) {
		// alert('Save Details Table Successfully'); // saving success message
		// doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
		// } else {
		// alert('Save Details Table Failed'); // saving failure message
		// }
	}
}

function setTabObject(tab_obj) {
	tabObjects[tabCnt++] = tab_obj;
}

function initTab(tabObj, tabNo) {
	switch (tabNo) {
	case 1:
		with (tabObj) {
			var cnt = 0;
			InsertItem("Summary", "");
			InsertItem("Details", "");
		}
		break;
	}
}

function tab1_OnChange(tabObj, nItem) {
	var objs = document.all.item("tabLayer");;
	objs[nItem].style.display = "inline";
	// --------------- this is important! --------------------------//
	for (var i = 0; i < objs.length; i++) {
		if (i != nItem) {
			objs[i].style.display = "none";
//			objs[beforetab].style.zIndex = objs[nItem].style.zIndex - 1;
		}
	}
	// ------------------------------------------------------//
//	beforetab = nItem;
	resizeSheet();
}

// function searchByPartner(id) {
// document.form.f_cmd.value = SEARCH;
// ComOpenWait(true);
// var sParam=FormQueryString(document.form);
// sParam+="&jo_crr_cd=" + id;
// var sXml= sheetObjects[1].GetSearchData("DOU_TRN_0003GS", sParam);
// sheetObjects[1].LoadSearchData(sXml,{Sync:1});
// ComOpenWait(false);
// }

// function comboObject_OnChange(comboObject) {
// comboObjects[1].deleteItemm(0);
// }

function s_partner_OnCheckClick(Index, Code, Checked) {
	// console.log(Code);
	// console.log(Checked);
	// comboObject_OnChange(comboObjects[0]);
	if(Checked != 'ALL' && comboObjects[0].GetItemCheck(0)==true){
		comboObjects[0].SetItemCheck(0,false);
	}
//	var count =0;
//	for(var i = 0; i<comboObjects[0].GetItemCount();i++){
//		if(comboObjects[0].GetItemCheck(i) == false)
//			count++;
//	}
//	console.log(count);
//	if(count==0){
//		comboObjects[0].SetItemCheck(0,true);
//	}
//	
	document.form.f_cmd.value = SEARCH01;
	var sParam = FormQueryString(document.form);
	console.log(sParam);
	// sParam += "&s_partner = " + Checked;
	var sXml = sheetObjects[0].GetSearchData("DOU_TRN_0003GS.do", sParam);
	// console.log(sXml);
	lanes = ComGetEtcData(sXml, "lanes");
	// console.log(lanes);
	comboObjects[1].SetEnable(true);
	generDataCombo(comboObjects[1], lanes);
}

function s_lane_OnSelect(Index, Code, Checked) {
	document.form.f_cmd.value = SEARCH02;
	var Param = FormQueryString(document.form);
	Param += '&lane=' + Checked;
	console.log(Param);
	var sXml = sheetObjects[0].GetSearchData("DOU_TRN_0003GS.do", Param);
	trades = ComGetEtcData(sXml, "trades");
	console.log(trades);
	comboObjects[2].SetEnable(true);
	generDataCombo(comboObjects[2], trades);

}

function generDataCombo(comboObj, dataStr) {
//	count = comboObj.GetItemCount();
//		for (var i = count - 1; i >= 0; i--) {
//			comboObj.DeleteItem(i);
//			console.log('da xoa ' + i);
//		}
		comboObj.RemoveAll();
		comboObjects[2].DeleteItem(0);


	if (typeof dataStr != 'undefined') {
		if (dataStr.indexOf("|") != -1) {
			var data = dataStr.split("|");
			// console.log(data);
			for (var i = 0; i < data.length; i++) {
				console.log(data[i]);
				comboObj.InsertItem(-1, data[i], data[i]);
			}
		}
		if (dataStr.length > 0 && dataStr.indexOf("|") == -1) {
			comboObj.InsertItem(-1, dataStr, dataStr);
		}

	} else if (typeof dataStr == 'undefined') {
		comboObjects[0].SetItemCheck(0,true);
		comboObjects[1].SetEnable(false);
		comboObjects[2].SetEnable(false);
	}
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
	// FormQueryString(document.form);

}

function countDate() {
	var frYrMon = new Date(document.getElementById("txt_fr_yrmon").value);
	var toYrMon = new Date(document.getElementById("txt_to_yrmon").value);
	return parseInt((toYrMon - frYrMon) / (24 * 3600 * 1000));
}

function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) {
	document.form.f_cmd.value = SEARCH03;	
	var Param = FormQueryString(document.form);
	var sXml = sheetObj.GetSearchData("DOU_TRN_0003GS.do", Param);
	formatSubSum(sheetObj);
	
	countCurr = ComGetEtcData(sXml, "countCurr");
	for(var i=1;i<=countCurr;i++){
		var rowInsert =sheetObj.DataInsert(-1);
		sheetObj.SetCellValue(rowInsert, 6, ComGetEtcData(sXml, "Curr" + i));
		sheetObj.SetCellFontBold(rowInsert,6,1);
		
		sheetObj.SetCellValue(rowInsert, 7, ComGetEtcData(sXml, "Rev" +i));
		sheetObj.SetCellFontBold(rowInsert,7,1);
		
		sheetObj.SetCellValue(rowInsert, 8, ComGetEtcData(sXml, "Exp" + i));
		sheetObj.SetCellFontBold(rowInsert,8,1);
		
		sheetObj.SetRowBackColor(rowInsert,"FFDAB9");;
//		sheetObj.SetSubSumBackColor("#F4F4F4");
		
	}
	

	ComOpenWait(false);
}

function formatSubSum(sheetObj){
	var strRow = sheetObj.FindSubSumRow(3);
	var Rows = strRow.split("|");
	if(Rows.length > 0){
		if(sheetObj.id == 'sheet1'){
			for(var i=0; i<Rows.length;i++){
				sheetObj.SetCellValue(parseInt(Rows[i]), 6, sheetObj.GetCellValue(parseInt(Rows[i])-1,6));
				sheetObj.SetRangeFontBold(parseInt(Rows[i]),6,parseInt(Rows[i]),8,1);
				sheetObj.SetRowFontColor(parseInt(Rows[i]),"#FF3333");
			}
		}
		if(sheetObj.id == 'sheet2'){
			for(var i=0; i<Rows.length;i++){
				sheetObj.SetCellValue(parseInt(Rows[i]), 8, sheetObj.GetCellValue(parseInt(Rows[i])-1,8));
				sheetObj.SetRangeFontBold(parseInt(Rows[i]),8,parseInt(Rows[i]),10,1);
				sheetObj.SetRowFontColor(parseInt(Rows[i]),"#FF3333");

			}
		}
	}
}
function sheet2_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) {
	document.form.f_cmd.value = SEARCH03;	
	var Param = FormQueryString(document.form);
	var sXml = sheetObj.GetSearchData("DOU_TRN_0003GS.do", Param);
	formatSubSum(sheetObj);
	
	countCurr = ComGetEtcData(sXml, "countCurr");
	for(var i=1;i<=countCurr;i++){
		var rowInsert =sheetObj.DataInsert(-1);
		sheetObj.SetCellValue(rowInsert, 8, ComGetEtcData(sXml, "Curr" + i));
		sheetObj.SetCellFontBold(rowInsert,8,1);
		
		sheetObj.SetCellValue(rowInsert, 9, ComGetEtcData(sXml, "Rev" +i));
		sheetObj.SetCellFontBold(rowInsert,9,1);
		
		sheetObj.SetCellValue(rowInsert, 10, ComGetEtcData(sXml, "Exp" + i));
		sheetObj.SetCellFontBold(rowInsert,10,1);
		
		sheetObj.SetRowBackColor(rowInsert,"FFDAB9");;
//		sheetObj.SetSubSumBackColor("#F4F4F4");
		
	}
	

	ComOpenWait(false);
}


function DOU_TRN_0003() {
	this.processButtonClick = tprocessButtonClick;
	this.setSheetObject = setSheetObject;
	this.loadPage = loadPage;
	this.initSheet = initSheet;
	this.initControl = initControl;
	this.doActionIBSheet = doActionIBSheet;
	this.setTabObject = setTabObject;
	this.validateForm = validateForm;
}