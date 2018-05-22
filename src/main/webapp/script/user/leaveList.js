$(function() {
	_generateTable();
	_edit();
	_doDelete();
	$('#modalDiv').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
});

function _generateTable() {
	var columns = [
		{sTitle:i18n('sys.userID'), mDataProp: 'userCode',sClass:'center positionTd userCode',bVisible:true},
		{sTitle:i18n('sys.userName'), mDataProp: 'name',sClass:'center positionTd'},
		{sTitle:i18n('sys.leaveDays'), mDataProp: 'vacationDays',sClass:'center positionTd'},
		{sTitle:i18n('holiday.leaveDays'), mDataProp: 'leaveDays',sClass:'center positionTd'},
		{sTitle:i18n('global.opera'),sClass:'center positionTd',mRender:function(data,type,full){
			return '<input class="btn btn-default btn-xs editrow" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('global.edit')+'>'+
			'<input class="btn btn-default btn-xs delrow" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('global.delete')+'>'
		}}
	];
	$('#user-dataTable').createTable({
		url : ctx + '/user/data',
		columns : columns,
		serverParems : function(data){
			data.push(
				{ name: "name", value: $('#name').val()}
			)
		}
	});
}

function _edit(){
	$('#user-dataTable tbody').on( 'click', '.editrow', function () {
		var userCode=$(this).parents('tr').find('.userCode').text();
		$(this).attr('href',ctx+'/user/editDay?userCode='+userCode);
	});
}

function _doDelete(){
	$('#user-dataTable tbody').on( 'click', '.delrow', function () {
		var flag=window.confirm(i18n('global.deleteConfirm'));
		if(flag){
			var userCode=$(this).parents('tr').find('.userCode').text();
			window.location.href=ctx + '/user/doDel?userCode='+userCode+'&state=0';
		}
	});
}