$(function(){
	_generateTable();
	_approve();
	_reject();
	_info();
	$('#modalDiv1').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
})

function _generateTable(){
	var columns = [
		{sTitle:i18n('profile.serialNumber'), mDataProp: 'overtimeId',sClass:'center positionTd overtimeId'},
		{sTitle:i18n('holiday.name'), mDataProp: 'applicantName',sWidth:'80',sClass:'center positionTd'},
		{sTitle:i18n('customer.approver'), mDataProp: 'approvalOfPersonnelName',sWidth:"80",sClass:'center positionTd'},
		{sTitle:i18n('holiday.startDate'), mDataProp: 'startDate',sWidth:'80',sClass:'center positionTd'},
		{sTitle:i18n('holiday.endDate'), mDataProp: 'endDate',sWidth:'80',sClass:'center positionTd'},
		{sTitle:i18n('holiday.duration'), mDataProp: 'duration',sWidth:'60',sClass:'center positionTd'},
		{sTitle:i18n('global.status'), mDataProp: 'status',sWidth:'60',sClass:'center positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}},
		{sTitle:i18n('global.note'), mDataProp: 'notes',sWidth:'200',sClass:'center positionTd'},
		{sTitle:i18n('global.opera'),sWidth:'200',sClass:'center positionTd',mRender:function(data,type,full){
			return '<input class="btn btn-default btn-xs approve" type="button" value='+i18n('holiday.approve')+'>'+
			'<input class="btn btn-default btn-xs reject" type="button" value='+i18n('holiday.rejectedChange')+'>'+
			'<input class="btn btn-default btn-xs info" data-toggle="modal" data-target="#modalDiv1" data-backdrop="static" type="button" value='+i18n('global.info')+' >';
		}}
	];
	$('#wkOverTimeApproving-dataTable').createTable({
		url : ctx + '/approving/wkOvertime/data',
		columns : columns,
		columnsNum : 8,
		hiddenRowId : 'overtimeId',
		serverParems : function(data){
			data.push(
				{name : 'status',value : '0'},
				{name: "applicant", value: $('#applicant').val()}
			)
		}
	});
}

function _approve(){
	$('#wkOverTimeApproving-dataTable tbody').on( 'click', '.approve', function () {
		var overtimeId=$(this).parents('tr').find('.overtimeId').text();
		$.ajax({
			url:ctx + '/approving/wkOvertime/approve',
			data:'overtimeId='+overtimeId,
			success : function(data){
				alert(i18n('global.success'));
				window.location.reload();
			}
		});
	});
}

function _reject(){
	$('#wkOverTimeApproving-dataTable tbody').on( 'click', '.reject', function () {
		var overtimeId=$(this).parents('tr').find('.overtimeId').text();
		$.ajax({
			url:ctx + '/approving/wkOvertime/reject',
			data:'overtimeId='+overtimeId,
			success : function(data){
				alert(i18n('global.success'));
				window.location.reload();
			}
		});
	});
}
function _info(){
	$('#wkOverTimeApproving-dataTable tbody').on( 'click', '.info', function () {
		var overtimeId=$(this).parents('tr').find('.overtimeId').text();
		$(this).attr('href',ctx+'/wkOvertime/apply/info?overtimeId='+overtimeId);
	});
} 	