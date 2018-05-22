$(function() {
	_generateTable();
	_approve();
	_reject();
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('wkLeave.leaveId'), mDataProp: 'leaveId',sClass:'center leaveId'},
		{sTitle:i18n('wkLeave.applicant'), mDataProp: 'applicantName',sClass:'center'},
		{sTitle:i18n('wkLeave.startDate'), mDataProp: 'startDate',sClass:'center'},
		{sTitle:i18n('wkLeave.endDate'), mDataProp: 'endDate',sClass:'center'},
		{sTitle:i18n('wkLeave.duration'), mDataProp: 'duration',sClass:'center'},
		{sTitle:i18n('wkLeave.status'), mDataProp: 'status',sClass:'center',mRender:function(data,type,full){
			return getStatus(data);
		}}
//		,
//		{sTitle:i18n('global.opera'),mDataProp: 'status',sClass:'center',mRender:function(data,type,full){
//			if(data==0){
//				return '<input class="btn btn-default btn-xs approve" type="button" value='+i18n('holiday.approve')+'>'+
//				'<input class="btn btn-default btn-xs reject" type="button" value='+i18n('holiday.rejectedChange')+'>'
//			}
//			return '';
//		}}
	];
	$('#wkLeave-dataTable').createTable({
		url : ctx + '/wkLeave/data',
		columns : columns,
		columnsNum : 6,
		hiddenRowId : 'leaveId',
		serverParems : function(data){
			data.push(
				{ name: "applicant", value: $('#TTname').val()},
				{ name: "status", value: $('#selectDropDown').val()}
			)
		}
	});
}

function _approve(){
	$('#wkLeave-dataTable tbody').on( 'click', '.approve', function () {
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
	$('#wkLeave-dataTable tbody').on( 'click', '.reject', function () {
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