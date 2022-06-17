document.onclick = processButtonClick;
document.onkeydown = logKey;
var sheetObjects = new Array();
var sheetCnt = 0;

var comboObjects = new Array();
var comboCnt = 0;


function logKey(key) {
	var sheetObject1 = sheetObjects[0];
	var formObj = document.form;
	if (key.code == 'Enter') {
		doActionIBSheet(sheetObject1, formObj, IBSEARCH);
	}
}

function processButtonClick() {
    /*****Case more than two additional sheets tab sheet is used to specify a variable *****/
    /** **************************************************** */
	var formObject = document.form;
    try {
        var srcName=ComGetEvent("name");
        switch (srcName) {
        case "btn_fr_date" :		// Agreement Date (To Date)
			var cal=new ComCalendar();
         	cal.select(formObject.s_fr_date, 'yyyy-MM-dd');
	    		break;
	    
        case "btn_to_date" :		// Agreement Date (To Date)
			var cal=new ComCalendar();
         	cal.select(formObject.s_to_date, 'yyyy-MM-dd');
	    		break;
	    		
        case "btn_Retrieve" :
        	doActionIBSheet(sheetObjects[0], formObject, IBSEARCH);
        	break;
        
        case "btn_Add" :
        	doActionIBSheet(sheetObjects[0],formObject,IBINSERT);
        	break;
        	
        case "btn_Save":
        	doActionIBSheet(sheetObjects[0],formObject,IBSAVE);
        	break;
        case "btn_Delete":
        	doActionIBSheet(sheetObjects[0],formObject,IBDELETE);
        	break;
        case "btn_New":
        	cleanData(sheetObjects[0]);
        	break;
        case "btn_DownExcel":
        	doActionIBSheet(sheetObjects[0],formObject,IBDOWNEXCEL);
        	break;
        }
        
             
    } catch (e) {
        if (e == "[object Error]") {
            ComShowMessage(OBJECT_ERROR);
        } else {
            ComShowMessage(e.message);
        }
    }
    	
}
function loadPage() {
	for (i = 0; i < sheetObjects.length; i++) {
		ComConfigSheet(sheetObjects[i]);
		initSheet(sheetObjects[i], i + 1);
		ComEndConfigSheet(sheetObjects[i]);
	}
	
	for (var k = 0; k < comboObjects.length; k++) {
		initCombo(comboObjects[k], k + 1);
	}
	
	doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
}

function setSheetObject(sheet_obj) {
	sheetObjects[sheetCnt++] = sheet_obj;
}

function initSheet(sheetObj, sheetNo) {
	var cnt = 0;
	switch (sheetNo) {
	case 1: // sheet1 init
		with (sheetObj) {
			var HeadTitle1 = "|Chk|Carrier Copy|Rev.Lane Copy|Carrier|Rev.Lane|Vendor Code|Customer Code|Customer Code|Trade|Delete Flag|Create Date|Create User ID|Update Date|Update User ID";
			SetConfig({ SearchMode : 2, MergeSheet : 5, Page : 20, DataRowMerge : 0 });
			var info = { Sort : 1, ColMove : 1, HeaderCheck : 0, ColResize : 1};
			var headers = [{Text : HeadTitle1,Align : "Center"}];
			InitHeaders(headers, info);
			var cols = [ {Type : "Status",Hidden : 1,Width : 30,Align : "Center",ColMerge : 0,SaveName : "ibflag"},{
				Type : "DelCheck",	Hidden : 0,	Width : 70,	Align : "left",		ColMerge : 0,SaveName : 'del_chk'}, {
				Type : "Text",	Hidden : 1,	Width : 70,		Align : "Center",	ColMerge : 0,SaveName : "jo_crr_cd_copy"}, {
				Type : "Text",	Hidden : 1,	Width : 70,		Align : "Center",	ColMerge : 0,SaveName : "rlane_cd_copy"}, {
				Type : "PopupEdit",	Hidden : 0,	Width : 70,		Align : "Center",	ColMerge : 0,SaveName : "jo_crr_cd",	KeyField : 1,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 1,InsertEdit : 1}, {
				Type : "Combo",	Hidden : 0,	Width : 100,	Align : "Center",	ColMerge : 0,SaveName : "rlane_cd",		KeyField : 1,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 1,InsertEdit : 1, ComboCode: rlandCombo,ComboText: rlandCombo}, {
				Type : "Popup",	Hidden : 0,	Width : 210,	Align : "Left",		ColMerge : 0,SaveName : "vndr_seq",		KeyField : 1,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 1,InsertEdit : 1}, {
				Type : "Popup",	Hidden : 0,	Widdth : 80,	Align : "Center",	ColMerge : 0,SaveName : "cust_cnt_cd",	KeyField : 1,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 1,InsertEdit : 1}, {
				Type : "Popup",	Hidden : 0,	Width : 130,	Align : "Center",	ColMerge : 0,SaveName : "cust_seq",		KeyField : 1,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 1,InsertEdit : 1}, {
				Type : "Popup",	Hidden : 0,	Width : 125,	Align : "Center",	ColMerge : 0,SaveName : "trd_cd",		KeyField : 1,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 1,InsertEdit : 1}, {
				Type : "Combo",	Hidden : 0,	Width : 125,	Align : "Right",	ColMerge : 0,SaveName : "delt_flg",		KeyField : 1,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 1,InsertEdit : 1, ComboCode: "N|Y",ComboText: "N|Y"}, {
				Type : "Text",	Hidden : 0,	Width : 220,	Align : "Center",	ColMerge : 0,SaveName : "cre_dt",		KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Text",	Hidden : 0,	Width : 225,	Align : "Right",	ColMerge : 0,SaveName : "cre_usr_id",	KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Text",	Hidden : 0,	Width : 225,	Align : "Right",	ColMerge : 0,SaveName : "upd_dt",		KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}, {
				Type : "Text",	Hidden : 0,	Width : 220,	Align : "Center",	ColMerge : 0,SaveName : "upd_usr_id",	KeyField : 0,CalcLogic : "",Format : "",PointCount : 0,UpdateEdit : 0,InsertEdit : 0}];
			InitColumns(cols);
			SetEditable(1);
			resizeSheet();
			SetWaitImageVisible(1);
		}
		break;
	}
}
function resizeSheet() {
	ComResizeSheet(sheetObjects[0]);
}

function setComboObject(combo_obj) {
	comboObjects[comboCnt++] = combo_obj;
}

function initCombo(comboObj, comboNo) {
	var formObj = document.form
	switch (comboNo) {
	case 1:
		with (comboObj) {
			SetMultiSelect(1);
			SetDropHeight(200);
//			ValidChar(2, 1);
			var comboItems = crrCdCombo.split("|");
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
function s_crr_cd_OnCheckClick(Index, Code, Checked) {
	var checkStr = comboObjects[0].GetSelectIndex();
	if(checkStr == -1){
		comboObjects[0].SetItemCheck(0,true);
	}else if(Checked != 'ALL'){
		comboObjects[0].SetItemCheck(0,false);
	}
}


function doActionIBSheet(sheetObj, formObj, sAction) {
	switch (sAction) {
	case IBSEARCH:
			formObj.f_cmd.value = SEARCH;
//			ComOpenWait(true);
			sheetObj.DoSearch("DOU_TRN_0004GS.do", FormQueryString(formObj));
//			ComOpenWait(false);
//			 var sParam=FormQueryString(formObj);
//			 var sXml=sheetObj.GetSearchData("DOU_TRN_0003GS.do", sParam);
//			 sheetObj.LoadSearchData(sXml,{Sync:1});
		break;
		
	case IBINSERT:
			sheetObj.GetCellValue();
			sheetObj.DataInsert(-1);
		break;
	case IBSAVE: // retrieve
		formObj.f_cmd.value = MULTI;
		sheetObj.DoSave("DOU_TRN_0004GS.do", FormQueryString(formObj));
		break;
	case IBDELETE: // retrieve
		if (ComShowCodeConfirm("COM12165")) {
			formObj.f_cmd.value = MULTI;
			var sParam=FormQueryString(formObj);
			var saveStr = sheetObj.GetSaveString(0);
			var saveData = sheetObj.GetSaveData("DOU_TRN_0004GS.do",saveStr,sParam);
			sheetObj.LoadSaveData(saveData);
			break;
		} else {
			return;
		}
	case IBDOWNEXCEL:
		if(sheetObj.RowCount() < 1){
			ComShowCodeMessage("COM132501");
		}
		else{
			sheetObjects[0].Down2ExcelBuffer(true);
			sheetObjects[0].Down2Excel({FileName:'excel_practice4',SheetName:'sheet1',DownCols: makeHiddenSkipCol(sheetObjects[0]), SheetDesign:1, Merge:1, AutoSizeColumn:1 });
			sheetObjects[0].Down2ExcelBuffer(false);
		}
		break;
	}
}

function cleanData(sheetObj){
		var formObject = document.form;
		comboObjects[0].SetSelectIndex(-1);
		comboObjects[0].SetItemCheck(0, true);
		formObject.s_vendor.value = '';
		formObject.s_fr_date.value = '';
		formObject.s_to_date.value = '';
		sheetObj.RemoveAll();
}
function sheet1_OnSaveEnd(code,msg) {
		if(msg >= 0) {
			ComShowCodeMessage("COM132601"); // saving success message
			doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
			} else {
			ComShowCodeMessage("COM130103"); // saving failure message
			}
}

function getTrade(rowArray) {
	var sheetObj=sheetObjects[0];
    var formObj=document.form;
   	var colArray=rowArray[0];
   	sheetObj.SetCellValue(sheetObj.GetSelectRow(),9,colArray[3]);
}

function getVendor(rowArray) {
	var sheetObj=sheetObjects[0];
    var formObj=document.form;
   	var colArray=rowArray[0];
   	sheetObj.SetCellValue(sheetObj.GetSelectRow(),6,colArray[2]);
}

function getCustomer(rowArray) {
	var sheetObj=sheetObjects[0];
    var formObj=document.form;
   	var colArray=rowArray[0];
   	sheetObj.SetCellValue(sheetObj.GetSelectRow(),7,colArray[2]);
   	sheetObj.SetCellValue(sheetObj.GetSelectRow(),8,colArray[3]);
}

function getCarrier(rowArray) {
	var sheetObj=sheetObjects[0];
    var formObj=document.form;
   	var colArray=rowArray[0];
   	sheetObj.SetCellValue(sheetObj.GetSelectRow(),4,colArray[3]);
}



function sheet1_OnPopupClick(Row,Col,CellX) {
	console.log(CellX);
	if(CellX == 9){
		ComOpenPopup("COM_COM_0012.do", 830, 410, "getTrade", "1,0,1,1,1,1", 1, 1, 1, true);
	}else if(CellX == 7|| CellX == 8){
		ComOpenPopup("COM_COM_0006.do", 830, 410, "getCustomer", "1,0,1,1,1,1", 1, 1, 1, true);
	}else if(CellX == 6){
		ComOpenPopup("COM_COM_0007.do", 830, 410, "getVendor", "1,0,1,1,1,1", 1, 1, 1, true);
	}else if(CellX == 4 ){
		ComOpenPopup("COM_ENS_0N1.do", 830, 410, "getCarrier", "1,0,1,1,1,1", 1, 1, 1, true);
	}
}

