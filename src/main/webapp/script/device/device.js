$(function() {
	$('#modalDiv').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
	_generateTable();
	_doDelete();
	_info();
	_edit();
	_applyBorrow();
});

function checkTotal(){
	var total = $('#total').val();
	var staffHoldNum = $('#staffHoldNum').val();
	var stockNum = $('#stockNum').val();
	if(parseInt(total)<parseInt(staffHoldNum)){
		alert(i18n('device.checkTotal'));
		return false;
	}
	if(parseInt(total)<parseInt(stockNum)){
		alert(i18n('device.checkEdit'));
		return false;
	}
	if(parseInt(total)>parseInt(stockNum) && parseInt(total)>parseInt(staffHoldNum)){
		var stockCount = total-staffHoldNum;
		$('#stockNum').val(stockCount);
		return true;
	}
}

function _generateTable(){
	var columns = [
		{sTitle:i18n('device.code'), mDataProp: 'deviceCode',sClass:'center positionTd deviceCode',bVisible:true},
		{sTitle:i18n('device.name'), mDataProp: 'name',sClass:'center positionTd'},
		{sTitle:i18n('device.total'), mDataProp: 'total',sClass:'center positionTd'},
		{sTitle:i18n('device.staffHoldNum'), mDataProp: 'staffHoldNum',sClass:'center positionTd'},
		{sTitle:i18n('device.stockNum'), mDataProp: 'stockNum',sClass:'center positionTd stockNum'},
		{sTitle:i18n('device.buyingTime'), mDataProp: 'buyingTime',sClass:'center positionTd'},
		{sTitle:i18n('device.expirationDate'), mDataProp: 'expirationDate',sClass:'center positionTd '},
		{sTitle:i18n('global.opera'),sClass:'center positionTd',mRender:function(data,type,full){
			return '<input class="btn btn-default btn-xs editrow" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('global.edit')+'>'+
			'<input class="btn btn-default btn-xs delrow" type="button" value='+i18n('global.delete')+'>'+
			'<input class="btn btn-default btn-xs inforow" type="button" value='+i18n('device.record')+'>'+
			'<input class="btn btn-default btn-xs applyBorrowrow" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('device.applyBorrow')+'>'
		}}
	];
	$('#device-dataTable').createTable({
		url : ctx + '/device/data',
		columns : columns,
		serverParems : function(data){
			data.push(
				{ name: "name", value: $('#name').val()}
			)
		}
	});
}

function _doDelete(){
	$('#device-dataTable tbody').on( 'click', '.delrow', function () {
		var flag=window.confirm(i18n('global.deleteConfirm'));
		if(flag){
			var deviceCode=$(this).parents('tr').find('.deviceCode').text();
			window.location.href=ctx + '/device/doDelete?deviceCode='+deviceCode;
		}
	});
}

function _info(){
	$('#device-dataTable tbody').on( 'click', '.inforow', function () {
		var deviceCode=$(this).parents('tr').find('.deviceCode').text();
		window.location.href = ctx + '/device/borrowRecord?deviceCode='+deviceCode;
	});
}

function _edit(){
	$('#device-dataTable tbody').on( 'click', '.editrow', function () {
		var deviceCode=$(this).parents('tr').find('.deviceCode').text();
		$(this).attr('href',ctx+'/device/edit?deviceCode='+deviceCode);
	});
}

function _applyBorrow(){
	$('#device-dataTable tbody').on( 'click', '.applyBorrowrow', function () {
		var deviceCode=$(this).parents('tr').find('.deviceCode').text();
		var stockNum=$(this).parents('tr').find('.stockNum').text();
		$(this).attr('href',ctx+'/device/applyBorrow?deviceCode='+deviceCode+'&stockNum='+stockNum);
	});
}
