$(function() {
	$('#modalDiv2').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('profile.serialNumber'), mDataProp: 'quotationId',sClass:'center positionTd quotationId'},
		{sTitle:i18n('quotation.caseNum'), mDataProp: 'projectName',sClass:'center positionTd projectCode'},
		{sTitle:i18n('quotation.priceCreateTime'), mDataProp: 'date',sClass:'center positionTd'},
		{sTitle:i18n('quotation.applicant'), mDataProp: 'applicant',sClass:'center positionTd'},
		{sTitle:i18n('quotation.caseTime'), mDataProp: 'caseTime',sClass:'center positionTd'},
		{sTitle:i18n('quotation.taxTotal'), mDataProp: 'taxTotal',sClass:'center position',mRender:function(data,type,full){
			if(data!=null){
				return parseFloat(data).toFixed(2);
			}
			return null;
		}},
		{sTitle:i18n('global.note'), mDataProp: 'note',sClass:'center positionTd'},
		{sTitle:i18n('global.status'), mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}},
		{sTitle:i18n('global.opera'),mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
			var str = '<input class="btn btn-default btn-xs inforow" data-toggle="modal" data-target="#modalDiv2" data-backdrop="static" type="button" value='+i18n('quotation.viewQuotationItem')+'>';
			if(data==2){
				str += '<input class="btn btn-default btn-xs editrow" data-toggle="modal" data-target="#modalDiv2" data-backdrop="static" type="button" value='+i18n('global.edit')+'>';
			}
			return str;
		}}
	];
	$('#quotation-dataTable').createTable({
		url : ctx + '/quotation/data',
		columns : columns,
		serverParems : function(data){
			data.push(
				{ name: "projectCode", value: $('#proCode').val()},
				{ name: "applicant", value: $('#applicant').val()}
			)
		},
	});
}

function _info(){
	$('#quotation-dataTable tbody').on( 'click', '.inforow', function () {
		var quotationId=$(this).parents('tr').find('.quotationId').text();
		$(this).attr('href',ctx + '/quotation/quotationItem?quotationId='+quotationId);
	});
}

function _edit(){
	$('#quotation-dataTable tbody').on( 'click', '.editrow', function () {
		var quotationId=$(this).parents('tr').find('.quotationId').text();
		$(this).attr('href',ctx+'/quotation/edit?quotationId='+quotationId);
	});
}
