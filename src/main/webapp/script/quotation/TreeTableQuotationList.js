$(function(){
	var quotationId=$('#quotationId').val();
	$.ajax({
		type:'POST',
		dataType:'json',
		url:ctx + '/quotation/quotationItem/data?quotationId='+quotationId,
		success:function(data) {
			initTreeTable(data);
		}
	});
//	$('.treeTableCell').find("$("div:hidden") ").hidden;
//	alert($('.treeTableCell').find("img"));

});

function initTreeTable(data) {
	model = {
		store: data,
		childrenAttrs: ['children']
	};
	layout = [
	    {name: i18n('quotation.category'), field: 'category',headerClass: 'head1',style:'text-align:left;display:none'},
		{name: i18n('quotation.category'), field: 'category',headerClass: 'head',style:'text-align:center;'},
		{name: i18n('projectFee.itemName'), field: 'item',headerClass: 'head'},
		{name: i18n('quotation.amount'), field: 'quantity',headerClass: 'head'},
		{name: i18n('quotation.unitPrice'), field: 'unitPrice',headerClass: 'head'},
		{name: i18n('quotation.total'), field: 'subTotal',className:'subTotal',headerClass: 'head'},
		{name: i18n('quotation.specification'), field: 'spec',headerClass: 'head'},
		{name: i18n('global.note'), field: 'note',headerClass: 'head'},
		
		{name: i18n('global.opera'),headerClass: 'head',get: getValue},
		
	];
	var treeTable = new TreeTable(layout, model, 'quotationItem_table');
	treeTable.startup();
	amountCalculation();
}

function getValue(item, column, treeNode){
	var status=$('#status1').val();
	return '<a href="javascript:void(0);" onclick="doDelete(this,'+item.quotationListId+','+$('#quotationId').val()+','+status+');">'+i18n('global.delete')+'</a>';
}