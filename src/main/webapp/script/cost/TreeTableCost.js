$(function(){
	$.ajax({
		type : 'POST',
		url : ctx + '/quotation/quotationItem/data',
		dataType: 'json',
		data : {
			'quotationId' : $('.quotationId').val()
		},
		success : function(data) {
			initTreeTable(data);
		}
	});
});

function initTreeTable(data) {
	model = {
		store: data,
		childrenAttrs: ['children']
	};
	layout = [
	  	{name: i18n('customer.serialNumber'), field: 'title2',headerClass: 'head1',style:'text-align:left;display:none'},
		{name: i18n('customer.serialNumber'), headerClass: 'title2',style:'text-align:center;',get:getValue},
		{name: i18n('customer.serialNumber'),field: 'quotationListId', headerClass: 'idtitle',className: 'quotationListId',style:'display:none;'},
		{name: i18n('customer.serialNumber'),field: 'supplierCode', headerClass: 'idtitle',className: 'supplierCode',style:'display:none;'},
		{name: i18n('coseAnalysis.projectName'), field: 'item',headerClass: 'title2'},
//		{name: i18n('coseAnalysis.quotedPrice'), field: 'subTotal',headerClass: 'title2'},
		{name: i18n('coseAnalysis.quotedPrice'), field: 'subTotal',headerClass:'title2 positionTd'
			,get:getSubTotal
			//mRender:function(data,type,full){
//			if(data!=null){
//				var taxRate=$("#taxRate").val();
//				return parseFloat(data*(taxRate*0.01+1)).toFixed(2);
//			}
//			return data;
		//}
		},
//		{name: i18n('coseAnalysis.makeCompany'), field: 'supplierName',headerClass: 'title2'},
		{name: i18n('quotation.amount'), field: 'quantity',headerClass: 'title2'},
		{name: i18n('quotation.unitPrice'), field: 'unitPrice',headerClass: 'title2'},
//		{name: i18n('quotation.total'), field: 'subTotal',className:'subTotal',headerClass: 'title2'},
		{name: i18n('quotation.specification'), field: 'spec',headerClass: 'title2'},
	];
	var treeTable = new TreeTable(layout, model, 'quotationItem_table');
	treeTable.startup();
	_rowClick();
	//_setOutMoney();
}
i=1
function getValue(item, column, treeNode){
	
	return i++;
}

function getSubTotal(item, column, treeNode){
	var taxRate=$("#taxRate").val();
	return (item.subTotal*(taxRate*0.01+1)).toFixed(2);
}

function _rowClick(){
	$('.treeTableRow').click(function(){
		var supplierCode=$(this).find('.supplierCode').text();
		$('.treeTableRow').css({'background':'white'});
		$('.treeTableRow').each(function(){
			var supplierCode2 = $(this).find('.supplierCode').text();
			if(supplierCode2==supplierCode){
				$(this).css({'background':'#eee333'});
			}
		});
	});
	$('.treeTableRow').dblclick(function(){
		var quotationListId=$(this).find('.quotationListId').text();
		$.ajax({
			type : 'POST',
			url : ctx + '/cost/coseAnalysis/getProjectFee',
			dataType: 'json',
			data : {
				'quotationListId' : quotationListId
			},
			success : function(data) {
				$('#select1').find('.content').empty();
				for ( var i = 0; i < data.length; i++) {
					$('#select1').append('<tr class="content"><td>'+data[i].name+'</td><td>'+data[i].amount+'</td><td>'+data[i].notes+'</td></tr>')
				}
				$('#select').modal('show');
			}
		});
	});
}

function _setOutMoney(){
	var sum = 0;
	for ( var i = 0; i < $('.amount').size(); i++) {
		sum+=$('.amount').eq(i).text()*1;
	}
	$('#outMoneyTd').val(sum);
	//toCount();
}