$(function() {
	_generateTable();
	_info();
	_edit();
	_doDelete();
	$('#modalDiv').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('customer.serialNumber'), mDataProp: 'customerCode',sClass:'center positionTd customerCode',bVisible:true},
		{sTitle:i18n('customer.classification'), mDataProp: 'type',sWidth:"60",sClass:'center positionTd'},
		{sTitle:i18n('customer.companyName'), mDataProp: 'name',sWidth:"180",sClass:'center positionTd'},
		{sTitle:i18n('customer.location'), mDataProp: 'customerAddress',sWidth:"240",sClass:'center positionTd'},
		{sTitle:i18n('customer.officialWebsite'), mDataProp: 'officialWebsite',sWidth:"180",sClass:'center positionTd'},
//		{sTitle:i18n('customer.note'), mDataProp: 'note',sClass:'center positionTd'},
		{sTitle:i18n('global.status'), mDataProp: 'status',sWidth:"60",sClass:'center positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}},
		{sTitle:i18n('global.opera'),mDataProp: 'status',sWidth:"80",sClass:'center',mRender:function(data,type,full){
			var roleId = $("#roleId").val();
			var str = '<input class="btn btn-default btn-xs inforow" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('global.info')+'>';
			if(data==2){
				str += '<input class="btn btn-default btn-xs edit" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('global.edit')+'>';
				if(roleId!='1'){
					str += '<input class="btn btn-default btn-xs delrow" type="button" value='+i18n('global.delete')+'>';
				}
			}else if(data==1){
				str +='<input class="btn btn-default btn-xs edit" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('global.edit')+'>';
			}
//			if(data==0 && roleId!='1'){
//				str +='<input class="btn btn-default btn-xs delrow" type="button" value='+i18n('global.delete')+'>';
//			}
			if(roleId==1 && data!=0){
				str +='<input class="btn btn-default btn-xs delrow" type="button" value='+i18n('global.delete')+'>';
			}
			return str;
		}}
	];
	$('#customerList-dataTable').createTable({
		url : ctx + '/custom/data',
		columns : columns,
		columnsNum : 7,
		hiddenRowId : 'customerCode',
		serverParems : function(data){
			data.push(
				{ name: 'name', value: $('#name').val()},
				{ name: 'customerCode', value: $('#customerCode').val()}
			)
		}
	});
}

function _info(){
	$('#customerList-dataTable tbody').on( 'click', '.inforow', function () {
		var customerCode=$(this).parents('tr').find('.customerCode').text();
		$(this).attr('href',ctx+'/custom/info?customerCode='+customerCode+'&yearValue='+"");
	});
}

function _doDelete(){
	$('#customerList-dataTable tbody').on( 'click', '.delrow', function () {
		var flag=window.confirm(i18n('global.deleteConfirm'));
		if(flag){
			var customerCode=$(this).parents('tr').find('.customerCode').text();
			window.location.href=ctx + '/custom/doDelete?customerCode='+customerCode;
		}
	});
}

function _edit(){
	$('#customerList-dataTable tbody').on( 'click', '.edit', function () {
		var customerCode=$(this).parents('tr').find('.customerCode').text();
		$(this).attr('href',ctx+'/custom/edit?customerCode='+customerCode);
	});
}