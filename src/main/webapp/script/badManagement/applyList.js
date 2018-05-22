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
		{sTitle:i18n('profile.serialNumber'), mDataProp: 'badId',sClass:'center positionTd badId'},
		{sTitle:i18n('quotation.name'), mDataProp: 'quotationName',sClass:'center positionTd'},
		{sTitle:i18n('quotation.applicant'), mDataProp: 'people',sClass:'center positionTd'},
		{sTitle:i18n('quotation.reason'), mDataProp: 'reason',sClass:'center positionTd'},
		{sTitle:i18n('projectFee.money'), mDataProp: 'total',sClass:'center position',mRender:function(data,type,full){
			if(data!=null){
				return parseFloat(data*(full["taxRate"]*0.01+1)).toFixed(2);
			}
			return null;
		}},
		{sTitle:i18n('global.note'), mDataProp: 'note',sClass:'center'},
		{sTitle:i18n('global.status'), mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
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
	$('#badAccount-dataTable').createTable({
		url : ctx + '/badAccount/applyListData',
		columns : columns,
		columnsNum : 7,
		hiddenRowId : 'badId',
		serverParems : function(data){
			data.push(
				{ name: 'prProject', value: $('#projectCode').val()},
				{name: "people", value: $('#people').val()},
				{name: "projectName", value: $('#projectName').val()},
				{name: "status", value: $('#selectDropDown').val()}
			)
		},
	});
}

function _edit(){
	$('#badAccount-dataTable tbody').on( 'click', '.edit', function () {
		var badId=$(this).parents('tr').find('.badId').text();
		$(this).attr('href',ctx+'/badAccount/apply/edit?badId='+badId);
	});
}

function _doDelete(){
	$('#badAccount-dataTable tbody').on( 'click', '.delrow', function () {
		var flag=window.confirm(i18n('global.deleteConfirm'));
		if(flag){
			var badId=$(this).parents('tr').find('.badId').text();
			window.location.href=ctx + '/badAccount/invalid?badId='+badId;
		}
	});
}