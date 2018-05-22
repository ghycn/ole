$(function() {
	$('#collectMoneyForm').validation();
	_generateTable();
	_edit();
	_info();
	$('#modalDiv').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('collectMoney.collectId'), mDataProp: 'collectId',sClass:'center positionTd collectId'},
		{sTitle:i18n('quotation.name'), mDataProp: 'quotationName',sClass:'center positionTd'},
		{sTitle:i18n('collectMoney.invoice'), mDataProp: 'invoice',sClass:'center positionTd',mRender:function(data,type,full){
			if(data){
				return i18n('global.yes');
			}
			return i18n('global.no');
		}},
		{sTitle:i18n('collectMoney.sum'), mDataProp: 'amount',sClass:'center position',mRender:function(data,type,full){
			if(data!=null){
				return parseFloat(data).toFixed(2);
			}
			return null;
		}},
		{sTitle:i18n('collectMoney.date'), mDataProp: 'date',sClass:'center positionTd'},
		{sTitle:i18n('collectMoney.toAccount'), mDataProp: 'toAccount',sClass:'center positionTd',mRender:function(data,type,full){
			if(data){
				return i18n('global.yes');
			}
			return i18n('global.no');
		}},
		{sTitle:i18n('collectMoney.notes'), mDataProp: 'notes',sClass:'center positionTd'},
		{sTitle:i18n('global.opera'),sClass:'center positionTd',mRender:function(data,type,full){
			return '<input class="btn btn-default btn-xs editrow" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('global.edit')+'>'
		}}
	];
	$('#collectMoney-dataTable').createTable({
		url : ctx + '/collectMoney/data',
		columns : columns,
		columnsNum : 7,
		hiddenRowId : 'collectId',
		serverParems : function(data){
			data.push(
				{name: "activityCode", value: $('#activityCode').val()}
			)
		}
	});
}

function _edit(){
	$('#collectMoney-dataTable tbody').on( 'click', '.editrow', function () {
		var collectId = $(this).parents('tr').find('.collectId').text();
		$(this).attr('href',ctx+'/collectMoney/edit?collectId='+collectId);
	});
}

function _info(){
	$('#collectMoney-dataTable tbody').on( 'click', '.info', function () {
		var projectCode=$(this).parents("tr").find("td").eq(1).text();
		window.location.href=ctx+'/project/coseAnalysis?projectCode='+projectCode;
	});
}