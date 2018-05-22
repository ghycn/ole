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
		{sTitle:i18n('supplier.code'), mDataProp: 'supplierCode',sClass:'center positionTd supplierCode',bVisible:true},
		{sTitle:i18n('supplier.type'), mDataProp: 'type',sWidth:"60",sClass:'center positionTd'},
		{sTitle:i18n('supplier.name'), mDataProp: 'name',sWidth:"180",sClass:'center positionTd'},
		{sTitle:i18n('supplier.address'), mDataProp: 'address',sWidth:"220",sClass:'center positionTd'},
		{sTitle:i18n('supplier.officialWebsite'), mDataProp: 'officialWebsite',sWidth:"80",sClass:'center positionTd'},
//		{sTitle:i18n('supplier.note'), mDataProp: 'note',sClass:'center positionTd'},
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
				str += '<input class="btn btn-default btn-xs edit" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('global.edit')+'>';
			}
//			if(data==0 && roleId!='1'){
//				str += '<input class="btn btn-default btn-xs delrow" type="button" value='+i18n('global.delete')+'>';
//			}
			if(roleId==1 && data!=0){
				str +='<input class="btn btn-default btn-xs delrow" type="button" value='+i18n('global.delete')+'>';
			}
			return str;
		}}
	];
	$('#supplier-dataTable').createTable({
		url : ctx + '/supplier/data',
		columns : columns,
		columnsNum : 7,
		hiddenRowId : 'supplierCode',
		serverParems : function(data){
			data.push(
				{ name: "name", value: $('#name').val()},
				{ name: "supplierCode", value: $('#supplierCode').val()}
			)
		}
	});
}

function _info(){
	$('#supplier-dataTable tbody').on( 'click', '.inforow', function () {
		var supplierCode=$(this).parents('tr').find('.supplierCode').text();
		$(this).attr('href',ctx+'/supplier/info?supplierCode='+supplierCode+'&yearValue='+"");
	});
}

function _doDelete(){
	$('#supplier-dataTable tbody').on( 'click', '.delrow', function () {
		var flag=window.confirm(i18n('global.deleteConfirm'));
		if(flag){
			var supplierCode=$(this).parents('tr').find('.supplierCode').text();
			window.location.href=ctx + '/supplier/doDelete?supplierCode='+supplierCode;
		}
	});
}

function _edit(){
	$('#supplier-dataTable tbody').on( 'click', '.edit', function () {
		var supplierCode=$(this).parents('tr').find('.supplierCode').text();
		$(this).attr('href',ctx+'/supplier/edit?supplierCode='+supplierCode);
	});
}