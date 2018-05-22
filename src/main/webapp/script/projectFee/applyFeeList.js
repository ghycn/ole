$(function() {
	$('#modalDiv2').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
	$('#modalDiv3').on('hidden.bs.modal', function() {
		//$(this).removeData('bs.modal');
		window.location.href = ctx + '/projectFee/applyList';
	});
	//$('body').on('hidden.bs.modal', '#modal', function () { $(this).removeData('bs.modal'); });
});

function _generateTable(){
	var columns = [
//		{sTitle:i18n('projectFee.feeNum'), mDataProp: 'projectFeeId',sClass:'center positionTd projectFeeId'},
		{sTitle:i18n('quotation.name'), mDataProp: 'quotationName',sClass:'center positionTd'},
		{sTitle:i18n('wkLeave.applicant'), mDataProp: 'applicantName',sClass:'center positionTd'},
		{sTitle:i18n('projectFee.money'), mDataProp: 'amount',sClass:'center position',mRender:function(data,type,full){
			if(data!=null){
				return parseFloat(data).toFixed(2);
			}
			return null;
		}},
		{sTitle:i18n('projectFee.applyTime'), mDataProp: 'applyDate',sClass:'center positionTd'},
		{sTitle:i18n('projectFee.info'), mDataProp: 'name',sClass:'center positionTd'},
		{sTitle:i18n('projectFee.type'), mDataProp: 'type',sClass:'center positionTd'},
		{sTitle:i18n('global.note'), mDataProp: 'notes',sClass:'center positionTd'},
		{sTitle:i18n('global.status'), mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}}
	];
	$('#projectFee-dataTable').createTable({
		url : ctx + '/projectFee/applyFeeListData?quotationId='+$('#quotationId').val(),
		columns : columns,
		columnsNum : 8,
//		hiddenRowId : 'projectFeeId',
		serverParems : function(data){
			data.push(
				{ name: 'createStaff.userCode', value: $('#userCode').val()},
				{ name: 'prProject', value: $('#projectCode').val()},
				{ name: 'prProjectName', value: $('#prProjectName').val()},
				{ name: 'type', value: $('#type').val()},
				{ name: 'amount', value: $('#selectDropDown').val()}
			)
		},
	});
}
