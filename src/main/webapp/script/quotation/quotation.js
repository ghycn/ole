$(function() {
	$('#quotationForm').validation();
	$('#quotationItemForm').validation();
	$('#modalDiv2').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
	$('#modalDiv3').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
});

function total(){
	var v_quantity = $('#quantity').val();
	var v_unitPrice = $('#unitPrice').val();
	var v_total = v_quantity * v_unitPrice;
	$('#subTotal').val(v_total);
}

function save(){
	var obj=document.getElementById('activityCode');
	var index=obj.selectedIndex; //序号，取当前选中选项的序号
	var val = obj.options[index].text;
	var params = $('#quotationForm').serialize();
	$.post(ctx+'/quotation/save', params, function(data) {
		if (data.result) {
			var tr = $('<tr><td>'+val+'</td>'+
					'<td>'+$("#customerName").val()+'</td>'+
					'<td>'+$('#applicant').val()+'</td>'+
					'<td>'+$('#caseTime').val()+'</td>'+
					'<td></td>'+
					'<td>'+$('#note').val()+'</td>'+
					'<td>'+i18n('global.audit')+'</td>'+
					'<td style="cursor: pointer;"><input class="btn btn-default btn-xs" style="width: 50%;float: left;" data-toggle="modal" data-target="#modalDiv2" data-backdrop="static" href="'+ctx+'/quotation/quotationItem?quotationId='+data.quotationId+'&status=0" type="button" value="'+i18n('quotation.viewQuotationItem')+'"></td></tr>');
			$('.quotation').append(tr);
			$('#modalDiv3').modal('hide');
		}
	});
}

function edit(){
	var params = $('#quotationForm').serialize();
	$.post(ctx+'/quotation/update', params, function(data) {
		if (data.result) {
			var tr = $('<td>'+$("#customerName").val()+'</td>'+
					'<td>'+$("#customerName").val()+'</td>'+
					'<td>'+$('#applicant').val()+'</td>'+
					'<td>'+$('#caseTime').val()+'</td>'+
					'<td>'+$('#taxTotal').val()+'</td>'+
					'<td>'+$('#note').val()+'</td>'+
					'<td>'+i18n('global.audit')+'</td>'+
					'<td style="cursor: pointer;"><input class="btn btn-default btn-xs" style="width: 50%;float: left;" data-toggle="modal" data-target="#modalDiv2" data-backdrop="static" href="'+ctx+'/quotation/quotationItem?quotationId='+data.quotationId+'&status=0" type="button" value="'+i18n('quotation.viewQuotationItem')+'"></td>');
			$('.quotation tr').eq(trIndex).html(tr);
			$('#modalDiv3').modal('hide');
		}
	});
}

function saveItem(){
	var params = $('#quotationItemForm').serialize();
//	var str = $('#supplierCode').val();
//	if(str==""||str==null){
//		alert(i18n('supplier.notNull'));
//		return false;
//	}
	$.post(ctx+'/quotationItem/saveItem', params, function(data) {
		if (data.result) {
			var tr = '<tr  style="height: 31px;"><td title="'+$('#category').val()+'">'+$('#category').val()+'</td>'+
					'<td title="'+$('#item').val()+'">'+$('#item').val()+'</td>'+
					'<td title="'+$('#quantity').val()+'">'+$('#quantity').val()+'</td>'+
					'<td title="'+$('#unitPrice').val()+'">'+$('#unitPrice').val()+'</td>'+
					'<td title="'+$('#subTotal').val()+'" class="subTotal">'+$('#subTotal').val()+'</td>'+
					'<td title="'+$('#spec').val()+'">'+$('#spec').val()+'</td>'+
					'<td title="'+$('#note').val()+'">'+$('#note').val()+'</td>'+
					'<td style="cursor: pointer;"><a href="javascript:void(0);" onclick="doDelete(this,'+data.quotationListId+','+$('#quotationId').val()+');">'+i18n('global.delete')+'</a></td></tr>';
			//alert('保存成功!');i18n('global.sZeroRecords')
			alert(i18n('global.save')+i18n('global.success'));
//			$('#quotationItemEditTable').append(tr);
			$('#quotationItem_table table ').append(tr);
			//alert(tr);
			amountCalculation();
			$('#modalDiv3').modal('hide');
			//$('#modalDiv2').modal('hide');
		}
	});
}