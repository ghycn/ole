$(function() {
	_generateTable();
	_approve();
	_reject();
	_info();
	$('#info').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('quotation.caseNum'), mDataProp: 'projectCode',sClass:'center positionTd projectCode',bVisible:true},
		{sTitle:i18n('caselist.caseName'), mDataProp: 'name',sWidth:"120",sClass:'center positionTd'},
		{sTitle:i18n('caselist.companyName'), mDataProp: 'customerName',sWidth:"180",sClass:'center positionTd'},
		{sTitle:i18n('caselist.openCasePeople'), mDataProp: 'openStaffName',sWidth:"50",sClass:'center positionTd'},
		{sTitle:i18n('customer.approver'), mDataProp: 'approvalOfPersonnelName',sWidth:"50",sClass:'center positionTd'},
		{sTitle:i18n('caselist.caseTime'), mDataProp: 'openTime',sWidth:"60",sClass:'center positionTd'},
		{sTitle:i18n('caselist.closeTime'), mDataProp: 'closeTime',sWidth:"60",sClass:'center positionTd'},
		{sTitle:i18n('caselist.invoice'), mDataProp: 'invoiceAmount',sWidth:"50",sClass:'center position',mRender:function(data,type,full){
			if(data!=null){
				return parseFloat(data*(full["taxRate"]*0.01+1)).toFixed(2);
			}
			return null;
		}},
//		{sTitle:i18n('global.note'), mDataProp: 'notes',sClass:'center positionTd'},
		{sTitle:i18n('global.status'), mDataProp: 'status',sWidth:"50",sClass:'center positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}},
		{sTitle:i18n('global.opera'),sClass:'center positionTd',mRender:function(data,type,full){
			return '<input class="btn btn-default btn-xs approve" type="button" value='+i18n('global.approve')+'>'+
			'<input class="btn btn-default btn-xs reject" type="button" value='+i18n('global.reject')+'>'+
			'<input class="btn btn-default btn-xs info" data-toggle="modal" data-target="#info" data-backdrop="static" type="button" value='+i18n('global.info')+'>';
		}}
	];
	$('#projectApproving-dataTable').createTable({
		url : ctx + '/approving/project/data?state='+1,
		columns : columns,
		columnsNum : 9,
		hiddenRowId : 'projectCode',
		serverParems : function(data){
			data.push(
				{name : 'projectCode',value :$('#projectCode').val()},
				{name: "projectName", value: $('#projectName').val()},
				{name: "openStaff", value: $('#openStaff').val()},
				{name: "status", value: '0'},
				{name: "flag", value: true}
			)
		}
	});
}

function _approve(){
	$('#projectApproving-dataTable tbody').on( 'click', '.approve', function () {
		var projectCode=$(this).parents('tr').find('.projectCode').text();
		$.ajax({
			url:ctx + '/approving/project/approve',
			data:'projectCode='+projectCode,
			success : function(data){
				alert(i18n('global.success'));
				window.location.reload();
			}
		});
	});
}

function _reject(){
	$('#projectApproving-dataTable tbody').on( 'click', '.reject', function () {
		var projectCode=$(this).parents('tr').find('.projectCode').text();
		$.ajax({
			url:ctx + '/approving/project/reject',
			data:'projectCode='+projectCode,
			success : function(data){
				alert(i18n('global.success'));
				window.location.reload();
			}
		});
	});
}

function _info(){
	$('#projectApproving-dataTable tbody').on( 'click', '.info', function () {
		var projectCode=$(this).parents('tr').find('.projectCode').text();
		$(this).attr('href',ctx+'/project/info?projectCode='+projectCode);
	});
}