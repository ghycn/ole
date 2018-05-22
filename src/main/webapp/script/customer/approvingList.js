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
		{sTitle:i18n('customer.serialNumber'), mDataProp: 'customerCode',sClass:'center positionTd customerCode',bVisible:true},
		{sTitle:i18n('customer.classification'), mDataProp: 'type',sClass:'center positionTd'},
		{sTitle:i18n('customer.companyName'), mDataProp: 'name',sClass:'center positionTd'},
		{sTitle:i18n('customer.address'), mDataProp: 'customerAddress',sClass:'center positionTd'},
		{sTitle:i18n('customer.officialWebsite'), mDataProp: 'officialWebsite',sClass:'center positionTd'},
		{sTitle:i18n('global.note'), mDataProp: 'note',sClass:'center positionTd'},
		{sTitle:i18n('global.status'), mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}},
		{sTitle:i18n('global.opera'),sClass:'center positionTd',mRender:function(data,type,full){
			return '<input class="btn btn-default btn-xs approve" type="button" value='+i18n('global.approve')+'>'+
			'<input class="btn btn-default btn-xs reject" type="button" value='+i18n('global.reject')+'>'+
			'<input class="btn btn-default btn-xs inforow" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('global.info')+'>';
		}}
	];
	$('#customerApproving-dataTable').createTable({
		url : ctx + '/approving/customer/data',
		columns : columns,
		columnsNum : 7,
		hiddenRowId : 'customerCode',
		serverParems : function(data){
			data.push(
				{name : 'status',value :0},
				{name : 'name',value :$('#name').val()},
				{name: "customerCode", value: $('#customerCode').val()}
			)
		}
	});
}


function _approve(){
	$('#customerApproving-dataTable tbody').on( 'click', '.approve', function () {
		var customerCode=$(this).parents('tr').find('.customerCode').text();
		$.ajax({
			url:ctx + '/approving/customer/approve',
			data:'customerCode='+customerCode,
			success : function(data){
				alert(i18n('global.success'));
				window.location.reload();
			}
		});
	});
}

function _reject(){
	$('#customerApproving-dataTable tbody').on( 'click', '.reject', function () {
		var customerCode=$(this).parents('tr').find('.customerCode').text();
		$.ajax({
			url:ctx + '/approving/customer/reject',
			data:'customerCode='+customerCode,
			success : function(data){
				alert(i18n('global.success'));
				window.location.reload();
			}
		});
	});
}

function _info(){
	$('#customerApproving-dataTable tbody').on( 'click', '.inforow', function () {
		var customerCode=$(this).parents('tr').find('.customerCode').text();
		$(this).attr('href',ctx+'/custom/info?customerCode='+customerCode);
	});
}