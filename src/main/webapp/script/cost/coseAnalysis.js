$(function(){
	tdId = '';
	selectTr = null;
	arrRight = new Array();

	//toCount();
	//clickTr();
	//move();
	//setTaxes();
	var quotationText=$('#notTaxesQuotationId').val().replace(",","");
	//var taxes=$('#taxes').val();
	var taxRate=$("#taxRate").val().replace(",","");
	var invoiceAmount=$("#invoiceAmount").val().replace(",","");
	var costs=$("#costs").val().replace(",","");
//	alert(invoiceAmount+costs+taxRate);
//	alert(parseFloat(invoiceAmount.replace(",","")));
//	alert((parseFloat(invoiceAmount)-parseFloat(costs)));
	$('#grossProfit').val((parseFloat(quotationText)*(1+taxRate*0.01)-parseFloat(costs)).toFixed(2));
	//$('#grossProfit2').val($('#grossProfit').val());
	$('#grossProfit2').val((parseFloat(invoiceAmount)-parseFloat(costs)).toFixed(2));
	$("#taxes").val((parseFloat(quotationText)*(taxRate*0.01)).toFixed(2));
	//alert(invoiceAmount+((quotationText*(taxRate/100)).toFixed(2)));
	$('#quotationId').val((parseFloat(quotationText)*(1+taxRate*0.01)).toFixed(2));
	$('#profitRatio2').val(((parseFloat(invoiceAmount)-parseFloat(costs))/parseFloat(invoiceAmount)).toFixed(2)+"%");
	$('#profitRatio').val(((parseFloat(quotationText)*(1+taxRate*0.01)-parseFloat(costs))/(parseFloat(quotationText)*(1+taxRate*0.01))).toFixed(2)+"%");
	$('#enterpriseTax').val(((parseFloat(quotationText)-parseFloat(costs))*0.25).toFixed(2));
})

/*function toCount(){
	$('#quotationId').val($('#notTaxesQuotationId').val());
	setInnerCost();
	setTaxes();
	setEnterpriseTax();
	setGrossProfit();
	setProfitRatio();
	//setCosts();
	$('.innerMoneyKey').keyup(function(){
		setInnerCost();
		setEnterpriseTax();
		setGrossProfit();
		setProfitRatio();
		//setCosts();
	});
	
	$('#taxes').keyup(function(){
		setTaxes();
		setEnterpriseTax();
		setGrossProfit();
		setProfitRatio();
		//setCosts();
	});
}*/

/*function setTaxes(){
	var quotationText=$('#notTaxesQuotationId').val();
	//var taxes=$('#taxes').val();
	var taxRate=$("#taxRate").val();
	alert(quotationText+":"+taxRate);
	alert((quotationText*(taxRate/100)).toFixed(2));
	$("#taxes").val((quotationText*(taxes/100)).toFixed(2));
//	if(taxes!=''){
//		$('#quotationId').val((quotationText*1+taxes*1).toFixed(2));
//		
//	}else{
//		$('#quotationId').val($('#notTaxesQuotationId').val());
//	}
}*/

/*function setEnterpriseTax(){
	var quotationVal=$('#quotationId').val();
	var outMoneyVal=$('#outMoneyTd').val();
	var innerMoneyVal=$('#innerMoneyTd').val();
	var taxesVal=$('#taxes').val();
	$('#enterpriseTax').val(((quotationVal*1-outMoneyVal*1-innerMoneyVal*1-taxesVal*1).toFixed(2)*0.25).toFixed(2));
}*/

/*function setGrossProfit(){
	var quotationVal=$('#quotationId').val();
	var outMoneyVal=$('#outMoneyTd').val();
	var innerMoneyVal=$('#innerMoneyTd').val();
	var invoiceAmount=$("#invoiceAmount").val();
	var costs=$("#costs").val();
	var taxesVal=$('#taxes').val();
	var enterpriseTax=$('#enterpriseTax').val();
	//$('#grossProfit').val((quotationVal*1-outMoneyVal*1-innerMoneyVal*1-taxesVal*1-enterpriseTax*1).toFixed(2));
	$('#grossProfit').val((invoiceAmount-costs).toFixed(2));
	//$('#grossProfit2').val($('#grossProfit').val());
	$('#grossProfit2').val((invoiceAmount-costs).toFixed(2));
}*/

/*function setProfitRatio(){
	var grossProfit=$('#grossProfit').val();
	var quotationVal=$('#quotationId').val();
	var text = (grossProfit*1)/(quotationVal*1);
	var invoiceAmount=$("#invoiceAmount").val();
	var costs=$("#costs").val();
	if(grossProfit!='0.00' && quotationVal!='0'){
		var b = text.toFixed(4);
		var c = b.slice(2,4)+"."+b.slice(4,6)+"%";
		$('#profitRatio').val(c);
		$('#profitRatio2').val($('#profitRatio').val());
	}else{
		$('#profitRatio').val('0.0%');
		$('#profitRatio2').val($('#profitRatio').val());
	}
	if(invoiceAmount!='0.00' && costs!='0'){
		var b = text.toFixed(4);
		var c = b.slice(2,4)+"."+b.slice(4,6)+"%";
		$('#profitRatio').val(c);
		//$('#profitRatio2').val($('#profitRatio').val());
		$('#profitRatio2').val(((invoiceAmount-costs)/invoiceAmount).toFixed(2)+"%");
	}else{
		$('#profitRatio').val('0.0%');
		$('#profitRatio2').val($('#profitRatio').val());
	}
}*/

/*function setCosts(){
	var innerMoney = $('#innerMoneyTd').val();
	var outMoney = $('#outMoneyTd').val();
	var taxes=$('#taxes').val();
	var enterpriseTax = $('#enterpriseTax').val();
	$('#costs').val((innerMoney*1+outMoney*1+taxes*1+enterpriseTax*1).toFixed(2));
}*/

/*function setInnerCost(){
	var money1 = $('.innerMoney').eq(0).val();
	var money2 = $('.innerMoney').eq(1).val();
	var money3 = $('.innerMoney').eq(2).val();
	var money4 = $('.innerMoney').eq(3).val();
	var val = money1*1 + money2*1 + money3*1 + money4*1;
	if(val==0){
		$('#innerMoneyTd').val('0.00');
	}else{
		$('#innerMoneyTd').val(val);
	}
}
*/
/*function clickTr(){
	$('.selectbox table .trData').click(function(){
		$('.selectbox table .trData').removeClass('selected');
		$(this).addClass('selected');
		selectTr = $(this);
	});
}*/

/*function move(){
	//移到右边
	$('#add').click(function(){
		//获取选中的选项，删除并追加给对方
		selectTr.appendTo('#select2');
	});

	//移到左边
	$('#remove').click(function(){
		selectTr.appendTo('#select1');
	});

	//全部移到右边
	$('#add_all').click(function(){
		//获取全部的选项,删除并追加给对方
		$('#select1 .trData').appendTo('#select2');
	});

	//全部移到左边
	$('#remove_all').click(function(){
		$('#select2 .trData').appendTo('#select1');
	});
}*/

//点击含税报价对应的TD
/*function selectQuoteprice(id){
	tdId = id;
	$('#select2 .trData').remove();
	//根据每个报价项的选择进行回显
	if(arrRight[id] != undefined){
		for ( var i = 0; i < arrRight[id].length; i++) {
			arrRight[id][i].appendTo('#select2');
		}
	}
	clickTr();
	move();
}*/

//选择含税报价确定
/*function selectBj(){
	var array = new Array();
	$('#select2 .trData').each(function(i,val){
		var tr = $('<tr class="trData">'+$(this).html()+'</tr>');
		array[i] = tr;
	});
	var val = 0;
	//根据一维数组new出二维数组
	arrRight[tdId] = new Array();
	for ( var i = 0; i < array.length; i++) {
		//把右边的所有项加起来的和
		var temp=$(array[i]).html().split('</td>');
		val += (temp[2].split('>')[1])*1;
		//根据不同报价项存储不同的二维数组
		arrRight[tdId][i] = array[i];
	}
	//显示到页面中TD列中
	$('#selectTd'+tdId).val(val);
	var price = 0;
	$('.quoteprice').each(function(i,val){
		price += $(this).val()*1;
	});
	$('#outMoneyTd').val(price);
	toCount();
}*/