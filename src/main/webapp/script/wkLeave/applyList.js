$(function() {
	_generateTable();
	_edit();
	_doDelete();
	$('#modalDiv').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('wkLeave.leaveId'), mDataProp: 'leaveId',sClass:'center positionTd leaveId'},
		{sTitle:i18n('wkLeave.applicant'), mDataProp: 'applicantName',sClass:'center positionTd'},
		{sTitle:i18n('wkLeave.startDate'), mDataProp: 'startDate',sClass:'center positionTd'},
		{sTitle:i18n('wkLeave.endDate'), mDataProp: 'endDate',sClass:'center positionTd'},
		{sTitle:i18n('wkLeave.appDays'), mDataProp: 'duration',sClass:'center positionTd'},
		{sTitle:i18n('wkLeave.status'), mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}},
		{sTitle:i18n('global.opera'),mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
			if(data==2){
				return '<input class="btn btn-default btn-xs edit" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('global.edit')+'>'+
				'<input class="btn btn-default btn-xs delrow" type="button" value='+i18n('global.invalid')+'>';
			}
			return '';
		}}
	];
	$('#wkLeaveApply-dataTable').createTable({
		url : ctx + '/wkLeave/applyListData',
		columns : columns,
		columnsNum : 6,
		hiddenRowId : 'leaveId',
		serverParems : function(data){
			data.push(
				{ name: 'status', value: $('#selectDropDown').val()},
				{ name: 'applicant', value: $('#userCode').val()}
			)
		}
	});
}

function _edit(){
	$('#wkLeaveApply-dataTable tbody').on( 'click', '.edit', function () {
		var leaveId=$(this).parents('tr').find('.leaveId').text();
		$(this).attr('href',ctx+'/wkLeave/apply/edit?leaveId='+leaveId);
	});
}

function _doDelete(){
	$('#wkLeaveApply-dataTable tbody').on( 'click', '.delrow', function () {
		var flag=window.confirm(i18n('global.deleteConfirm'));
		if(flag){
			var leaveId=$(this).parents('tr').find('.leaveId').text();
			window.location.href=ctx + '/wkLeave/invalid?leaveId='+leaveId;
		}
	});
}