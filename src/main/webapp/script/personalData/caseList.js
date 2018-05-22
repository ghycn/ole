$(function() {
	_generateTable();
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('caselist.companyName'), mDataProp: 'customerName',sClass:'center positionTd w150'},
		{sTitle:i18n('caselist.caseName'), mDataProp: 'name',sClass:'center positionTd'},
		{sTitle:i18n('caselist.openCasePeople'), mDataProp: 'openStaffName',sClass:'center positionTd openStaffName'},
		{sTitle:i18n('caselist.caseTime'), mDataProp: 'openTime',sClass:'center positionTd'},
		{sTitle:i18n('caselist.closeTime'), mDataProp: 'closeTime',sClass:'center positionTd'},
		{sTitle:i18n('caselist.invoice'), mDataProp: 'invoiceAmount',sClass:'center positionTd',mRender:function(data,type,full){
			if(data!=null){
				return parseFloat(data).toFixed(2);
			}
			return null;
		}},
		{sTitle:i18n('global.note'), mDataProp: 'notes',sClass:'center positionTd'},
		{sTitle:i18n('global.status'), mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}}
	];
	$('#caseList-dataTable').createTable({
		url : ctx + '/project/data',
		columns : columns,
		serverParems : function(data){
			data.push(
				{ name: "projectName", value: $('#proName').val()},
				{ name: "openStaff", value:$('#openStaff').val() },
				{ name: "status", value: $('#selectDropDown').val()}
			)
		}
	});
}