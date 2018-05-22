$(function() {
	$('#modalDiv').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
	_generateTable();
	_edit();
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('activity.code'), mDataProp: 'activityCode',sClass:'center positionTd activityCode'},
		{sTitle:i18n('activity.name'), mDataProp: 'name',sClass:'center positionTd'},
		{sTitle:i18n('global.opera'),sClass:'center positionTd',mRender:function(data,type,full){
			return '<input class="btn btn-default btn-xs editrow" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('global.edit')+'>'
		}}
	];
	$('#activity-dataTable').createTable({
		url : ctx + '/activity/data',
		columns : columns,
		serverParems : function(data){
			data.push(
				{ name: "activityCode", value: $('#activityCode').val()},
				{ name: "name", value: $('#name').val()}
			)
		}
	});
}

function _edit(){
	$('#activity-dataTable tbody').on( 'click', '.editrow', function () {
		var activityCode=$(this).parents('tr').find('.activityCode').text();
		$(this).attr('href',ctx+'/activity/edit?activityCode='+activityCode);
	});
}