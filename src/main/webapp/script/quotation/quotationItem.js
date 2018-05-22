$(function() {
	$('#modalDiv3').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
});

function doDelete(n,quotationListId,quotationId,status){
	if(status==1){
		alert(i18n('projectFee.notDelete'));
		return ;
	}
	var flag=window.confirm(i18n('global.deleteConfirm'));
	if(flag){
		$.ajax({
			type: "POST",
			dataType: "text",
			data : {'quotationListId':quotationListId,'quotationId':quotationId},
			url: ctx + '/quotationItem/doDelete',
			success:function(){
				$(n).parent().parent().remove();
				amountCalculation();
			}
		});
	}
}

function amountCalculation(){
	var tax = taxRate;
	tax = tax*1/100;
	var sum = 0;
	for ( var i = 0; i < $('.subTotal').size(); i++) {
		sum += $('.subTotal').eq(i).text()*1;
	}
	$('#notTaxMoney').text(sum.toFixed(2));
	$('#tax').text((sum*tax).toFixed(2));
	$('#taxMoney').text(($('#notTaxMoney').text()*1+$('#tax').text()*1).toFixed(2));
}