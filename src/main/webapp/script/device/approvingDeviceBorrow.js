$(function() {
	$('#modalDiv').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
	_generateTable();
	_returnBack();
	_info();
	_doDelete();
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('device.code'), mDataProp: 'borrowCode',sClass:'center positionTd borrowCode',bVisible:true},
		{sTitle:i18n('device.borrow.borrower'), mDataProp: 'applicantName',sClass:'center positionTd'},
		{sTitle:i18n('device.borrow.borrowTime'), mDataProp: 'borrowTime',sClass:'center positionTd'},
		{sTitle:i18n('device.borrow.returnTime'), mDataProp: 'returnTime',sClass:'center positionTd'},
		{sTitle:i18n('device.borrow.status'), mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
			if(data==0){
				return i18n('device.borrow.return');
			}else if(data==1){
				return i18n('device.borrow.noReturn');
			}
		}},
		{sTitle:i18n('global.opera'),sClass:'center positionTd', mDataProp: 'status',mRender:function(data,type,full){
			var str = '<input class="btn btn-default btn-xs inforow" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('global.info')+'>';
			
			if(data==1){
				str += '<input class="btn btn-default btn-xs returnBack" type="button" value='+i18n('device.borrow.return')+'>';
			}
			if(data==0){
				str += '<input class="btn btn-default btn-xs delrow" type="button" value='+i18n('global.delete')+'>';
			}
			return str;
		}}
	];
	$('#deviceBorrow-dataTable').createTable({
		url : ctx + '/approving/deviceBorrow/data',
		columns : columns,
		serverParems : function(data){
			data.push(
				{ name: "code", value: $('#deviceCode').val()},
				{ name: "lender", value: $('#lender').val()},
				{ name: "status", value: $('#status').val()}
			)
		}
	});
}

function _returnBack(){
	$('#deviceBorrow-dataTable tbody').on( 'click', '.returnBack', function () {
		var flag=window.confirm(i18n('device.revert'));
		if(flag){
			var borrowCode=$(this).parents('tr').find('.borrowCode').text();
			window.location.href=ctx + '/approving/deviceBorrow/retuBack?borrowCode='+borrowCode;
		}
	});
}

function _info(){
	$('#deviceBorrow-dataTable tbody').on( 'click', '.inforow', function () {
		var borrowCode=$(this).parents('tr').find('.borrowCode').text();
		$(this).attr('href',ctx+'/approving/deviceBorrow/info?borrowCode='+borrowCode);
		//window.location.href=ctx + '/approving/deviceBorrow/info?borrowCode='+borrowCode;
	});
}

function _doDelete(){
	$('#deviceBorrow-dataTable tbody').on( 'click', '.delrow', function () {
		var flag=window.confirm(i18n('global.deleteConfirm'));
		if(flag){
			var borrowCode=$(this).parents('tr').find('.borrowCode').text();
			window.location.href=ctx + '/approving/deviceBorrow/delete?borrowCode='+borrowCode;
		}
	});
}