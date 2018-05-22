$(function() {
	_generateTable();
	_info();
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('quotation.number'), mDataProp: 'quotationId',sClass:'center positionTd quotationId'},
		{sTitle:i18n('quotation.caseNum'), mDataProp: 'projectCode',sClass:'center positionTd projectCode'},
		{sTitle:i18n('caselist.caseName'), mDataProp: 'projectName',sWidth:"60",sClass:'center positionTd'},
		{sTitle:i18n('quotation.name'), mDataProp: 'quotationName',sWidth:"150",sClass:'center positionTd'},
		{sTitle:i18n('quotation.applicant'), mDataProp: 'applicant',sClass:'center positionTd'},
		{sTitle:i18n('quotation.applicant'), mDataProp: 'applicantCode',sClass:'center positionTd applicantCode'},
		{sTitle:i18n('customer.approver'), mDataProp: 'approvalOfPersonnelName',sClass:'center positionTd'},
		{sTitle:i18n('quotation.customer'), mDataProp: 'customerName',sWidth:"180",sClass:'center positionTd'},
		{sTitle:i18n('caselist.caseTime'), mDataProp: 'caseTime',sClass:'center positionTd'},
		{sTitle:i18n('quotation.taxTotal'), mDataProp: 'taxTotal',sClass:'center positionTd',mRender:function(data,type,full){
			if(data!=null){
				return parseFloat(data*(full["taxRate"]*0.01+1)).toFixed(2);
			}
			return data;
		}},
		{sTitle:i18n('global.note'), mDataProp: 'note',sClass:'center positionTd'},
		{sTitle:i18n('global.status'), mDataProp: 'costStatus',sClass:'center positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}},
		{sTitle:i18n('global.opera'),sClass:'center positionTd',mRender:function(data,type,full){
			return '<input class="btn btn-default btn-xs info" type="button" value='+i18n('menu.costAnalysisOfSingle')+'>';
		}}
	];
	$('#coseAnalysisList-dataTable').createTable({
		url : ctx + '/quotation/data',
		columns : columns,
		columnsNum : 10,
		hiddenRowId : 'quotationId,projectCode,applicantCode',
		serverParems : function(data){
			data.push(
				{name : 'prProject',value :$('#projectCode').val()},
				{name: "projectName", value: $('#projectName').val()},
				{name: "applicant", value: $('#applicant').val()}
//				{name: "status",value: 1}
			)
		}
	});
}

function _info(){
	$('#coseAnalysisList-dataTable tbody').on( 'click', '.info', function () {
		var projectCode=$(this).parents('tr').find('.projectCode').text();
		var quotationId=$(this).parents('tr').find('.quotationId').text();
		var applicantCode=$(this).parents('tr').find('.applicantCode').text();
		window.location.href=ctx+'/cost/coseAnalysis?projectCode='+projectCode+'&quotationId='+quotationId+'&applicantCode='+applicantCode;
	});
}