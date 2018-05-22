$(function() {
	_generateTable();
	_approve();
	_reject();
	_info();
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('supplier.code'), mDataProp: 'costAnalysisId',sClass:'center positionTd costAnalysisId'},
		{sTitle:i18n('quotation.number'), mDataProp: 'quotationId',sClass:'center positionTd quotationId'},
		{sTitle:i18n('quotation.caseNum'), mDataProp: 'projectCode',sClass:'center positionTd projectCode'},
		{sTitle:i18n('caselist.caseName'), mDataProp: 'projectName',sWidth:"100",sClass:'center positionTd'},
		{sTitle:i18n('quotation.name'), mDataProp: 'quotationName',sWidth:"130",sClass:'center positionTd'},
		{sTitle:i18n('caselist.companyName'), mDataProp: 'projectCustomerName',sWidth:"180",sClass:'center positionTd'},
		{sTitle:i18n('caselist.openCasePeople'), mDataProp: 'projectOpenStaffName',sClass:'center positionTd'},
		{sTitle:i18n('customer.approver'), mDataProp: 'approvalOfPersonnelName',sClass:'center positionTd'},
		{sTitle:i18n('caselist.caseTime'), mDataProp: 'projectOpenTime',sClass:'center positionTd'},
//		{sTitle:i18n('global.note'), mDataProp: 'projectNotes',sClass:'center positionTd'},
		{sTitle:i18n('global.status'), mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}},
		{sTitle:i18n('global.opera'),sWidth:"120",sClass:'center positionTd',mRender:function(data,type,full){
			return '<input class="btn btn-default btn-xs approve" type="button" value='+i18n('global.approve')+'>'+
			'<input class="btn btn-default btn-xs reject" type="button" value='+i18n('global.reject')+'>'+
			'<input class="btn btn-default btn-xs info" type="button" value='+i18n('global.info')+'>';
		}}
	];
	$('#costAnalysisApproving-dataTable').createTable({
		url : ctx + '/approving/costAnalysis/data',
		columns : columns,
		columnsNum : 8,
		hiddenRowId : 'costAnalysisId,quotationId,projectCode',
		serverParems : function(data){
			data.push(
//				{name: "status",value: 0}
			)
		}
	});
}

function _approve(){
	$('#costAnalysisApproving-dataTable tbody').on( 'click', '.approve', function () {
		var costAnalysisId=$(this).parents('tr').find('.costAnalysisId').text();
		$.ajax({
			url:ctx + '/approving/costAnalysis/approve',
			data:'costAnalysisId='+costAnalysisId,
			success : function(data){
				alert(i18n('global.success'));
				window.location.reload();
			}
		});
	});
}

function _reject(){
	$('#costAnalysisApproving-dataTable tbody').on( 'click', '.reject', function () {
		var costAnalysisId=$(this).parents('tr').find('.costAnalysisId').text();
		$.ajax({
			url:ctx + '/approving/costAnalysis/reject',
			data:'costAnalysisId='+costAnalysisId,
			success : function(data){
				alert(i18n('global.success'));
				window.location.reload();
			}
		});
	});
}

function _info(){
	$('#costAnalysisApproving-dataTable tbody').on( 'click', '.info', function () {
		var projectCode=$(this).parents('tr').find('.projectCode').text();
		var quotationId=$(this).parents('tr').find('.quotationId').text();
		window.location.href=ctx + '/cost/coseAnalysis?projectCode='+projectCode+'&quotationId='+quotationId;
	});
}