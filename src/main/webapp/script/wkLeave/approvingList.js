$(function() {
	_generateTable();
	_approve();
	_reject();
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('wkLeave.leaveId'), mDataProp: 'leaveId',sClass:'center positionTd leaveId'},
		{sTitle:i18n('holiday.name'), mDataProp: 'applicantName',sClass:'center positionTd'},
		{sTitle:i18n('holiday.startDate'), mDataProp: 'startDate',sClass:'center positionTd'},
		{sTitle:i18n('holiday.endDate'), mDataProp: 'endDate',sClass:'center positionTd'},
		{sTitle:i18n('wkLeave.appDays'), mDataProp: 'duration',sClass:'center positionTd'},
		{sTitle:i18n('holiday.duration'), mDataProp: 'surplusLeaveDays',sClass:'center positionTd'},
		{sTitle:i18n('global.status'), mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}},
		{sTitle:i18n('global.opera'),sClass:'center positionTd',mRender:function(data,type,full){
			return '<input class="btn btn-default btn-xs approve" type="button" value='+i18n('holiday.approve')+'>'+
			'<input class="btn btn-default btn-xs reject" type="button" value='+i18n('holiday.rejectedChange')+'>'
		}}
	];
	$('#wkLeaveApproving-dataTable').createTable({
		url : ctx + '/approving/wkLeave/data',
		columns : columns,
		columnsNum : 6,
		hiddenRowId : 'leaveId',
		serverParems : function(data){
			data.push(
				{name : 'status',value : '0'},
				{name: "applicant", value: $('#applicant').val()}
			)
		}
	});
}

function _approve(){
	$('#wkLeaveApproving-dataTable tbody').on( 'click', '.approve', function () {
		var leaveId=$(this).parents('tr').find('.leaveId').text();
		$.ajax({
			url:ctx + '/approving/wkLeave/approve',
			data:'leaveId='+leaveId,
			success : function(data){
				alert(i18n('global.success'));
				window.location.reload();
			}
		});
	});
}

function _reject(){
	$('#wkLeaveApproving-dataTable tbody').on( 'click', '.reject', function () {
		var leaveId=$(this).parents('tr').find('.leaveId').text();
		$.ajax({
			url:ctx + '/approving/wkLeave/reject',
			data:'leaveId='+leaveId,
			success : function(data){
				alert(i18n('global.success'));
				window.location.reload();
			}
		});
	});
}