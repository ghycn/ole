$(function() {
	_generateTable();
	_edit();
	_doDelete();
	_info();
	$('#modalDiv').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
	$('#modalDiv1').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('wkLeave.applicant'), mDataProp: 'overtimeId',sClass:'center positionTd overtimeId'},
		{sTitle:i18n('wkLeave.applicant'), mDataProp: 'applicantName',sWidth:'100',sClass:'center positionTd'},
		{sTitle:i18n('customer.approver'), mDataProp: 'approvalOfPersonnelName',sWidth:"80",sClass:'center positionTd'},
		{sTitle:i18n('wkLeave.startDate'), mDataProp: 'startDate',sWidth:'80',sClass:'center positionTd'},
		{sTitle:i18n('wkLeave.endDate'), mDataProp: 'endDate',sWidth:'80',sClass:'center positionTd'},
		{sTitle:i18n('wkLeave.appDays'), mDataProp: 'duration',sWidth:'60',sClass:'center positionTd'},
		{sTitle:i18n('wkLeave.status'), mDataProp: 'status',sWidth:'80',sClass:'center positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}},
		{sTitle:i18n('global.note'), mDataProp: 'notes',sWidth:'200',sClass:'center positionTd'},
		{sTitle:i18n('global.opera'),mDataProp: 'status',sWidth:'100',	sClass:'center positionTd',mRender:function(data,type,full){
			if(data==2){
				return '<input class="btn btn-default btn-xs edit" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('global.edit')+'>'+
				'<input class="btn btn-default btn-xs delrow" type="button" value='+i18n('global.invalid')+'>';
			}
			return '<input class="btn btn-default btn-xs info" data-toggle="modal" data-target="#modalDiv1" data-backdrop="static" type="button" value='+i18n('global.info')+' >';
		}}
	];
	$('#wkOvertime-dataTable').createTable({
		url : ctx + '/wkOvertime/applyListData',
		columns : columns,
		columnsNum : 8,
		hiddenRowId : 'overtimeId',
		serverParems : function(data){
			data.push(
				{ name: 'status', value: $('#selectDropDown').val()},
				{ name: 'applicant', value: $('#userCode').val()}
			)
		}
	});
}

function _edit(){
	$('#wkOvertime-dataTable tbody').on( 'click', '.edit', function () {
		var overtimeId=$(this).parents('tr').find('.overtimeId').text();
		$(this).attr('href',ctx+'/wkOvertime/apply/edit?overtimeId='+overtimeId);
	});
}

function _doDelete(){
	$('#wkOvertime-dataTable tbody').on( 'click', '.delrow', function () {
		var flag=window.confirm(i18n('global.deleteConfirm'));
		if(flag){
			var overtimeId=$(this).parents('tr').find('.overtimeId').text();
			window.location.href=ctx + '/wkOvertime/invalid?overtimeId='+overtimeId;
		}
	});
}	
function _info(){
	$('#wkOvertime-dataTable tbody').on( 'click', '.info', function () {
		var overtimeId=$(this).parents('tr').find('.overtimeId').text();
		//${ctx}/wkOvertime/apply/add
		$(this).attr('href',ctx+'/wkOvertime/apply/info?overtimeId='+overtimeId);
	});
} 	
