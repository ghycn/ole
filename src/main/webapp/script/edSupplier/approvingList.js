$(function() {
	_generateTable();
	_approve();
	_reject();
	_info();
	$('#modalDiv').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('supplier.code'), mDataProp: 'supplierCode',sClass:'center positionTd supplierCode',bVisible:true},
		{sTitle:i18n('supplier.type'), mDataProp: 'type',sClass:'center positionTd'},
		{sTitle:i18n('supplier.name'), mDataProp: 'name',sClass:'center positionTd'},
		{sTitle:i18n('supplier.address'), mDataProp: 'address',sClass:'center positionTd'},
		{sTitle:i18n('supplier.officialWebsite'), mDataProp: 'officialWebsite',sClass:'center positionTd'},
		{sTitle:i18n('supplier.note'), mDataProp: 'note',sClass:'center positionTd'},
		{sTitle:i18n('global.status'), mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}},
		{sTitle:i18n('global.opera'),sClass:'center positionTd',mRender:function(data,type,full){
			return '<input class="btn btn-default btn-xs approve" type="button" value='+i18n('global.approve')+'>'+
			'<input class="btn btn-default btn-xs reject" type="button" value='+i18n('global.reject')+'>'+
			'<input class="btn btn-default btn-xs inforow" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('global.info')+'>';
		}}
	];
	$('#edSupplier-dataTable').createTable({
		url : ctx + '/approving/edSupplier/data',
		columns : columns,
		columnsNum : 7,
		hiddenRowId : 'supplierCode',
		serverParems : function(data){
			data.push(
				{name :'status',value :0},
				{name :'name',value :$('#name').val()},
				{name: "supplierCode", value: $('#supplierCode').val()}
			)
		}
	});
}

function _approve(){
	$('#edSupplier-dataTable tbody').on( 'click', '.approve', function () {
		var supplierCode=$(this).parents('tr').find('.supplierCode').text();
		$.ajax({
			url:ctx + '/approving/edSupplier/approve',
			data:'supplierCode='+supplierCode,
			success : function(data){
				alert(i18n('global.success'));
				window.location.reload();
			}
		});
	});
}

function _reject(){
	$('#edSupplier-dataTable tbody').on( 'click', '.reject', function () {
		var supplierCode=$(this).parents('tr').find('.supplierCode').text();
		$.ajax({
			url:ctx + '/approving/edSupplier/reject',
			data:'supplierCode='+supplierCode,
			success : function(data){
				alert(i18n('global.success'));
				window.location.reload();
			}
		});
	});
}

function _info(){
	$('#edSupplier-dataTable tbody').on( 'click', '.inforow', function () {
		var supplierCode=$(this).parents('tr').find('.supplierCode').text();
		$(this).attr('href',ctx+'/supplier/info?supplierCode='+supplierCode);
	});
}