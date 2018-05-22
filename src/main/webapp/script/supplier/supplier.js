$(function() {
	i = $('#cloneDiv table').size();
	
	$('#supplierForm').validation();
	$('#supplierTab a:first').tab('show');
	$('#supplierTab a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
	});
	$('.deleteSupplier').click(function() {
		$(this).parent().parent().parent().remove();
		i--;
	});
	$("#info").on("hidden.bs.modal", function() {
		$(this).removeData("bs.modal");
	});
	$('#supplierTypeForm').validation();
	$('.deleteType').click(function() {
		$(this).parent().parent().parent().remove();
		i--;
	});
//	var status=$('#supplierStatus').val();
//	if(status==1){
//		$('#supplierInfo :input').attr('readOnly',true);
//	}
});

function addSupplier() {
	$('#cloneTableDiv .name').attr('name', 'contacts[' + i + '].name');
	$('#cloneTableDiv .tel').attr('name', 'contacts[' + i + '].tel');
	$('#cloneTableDiv .mobile').attr('name', 'contacts[' + i + '].mobile');
	$('#cloneTableDiv .email').attr('name', 'contacts[' + i + '].email');
	$('#cloneTableDiv .extension').attr('name', 'contacts[' + i + '].extension');
	$('#cloneTableDiv table').clone(true).show().appendTo('#cloneDiv');
	i++;
}

function deleteContact(id){
	$.ajax({
		url:ctx + '/supplier/deleteContact',
		data:'supplierContactId='+id
	});
}

function addType() {
	$('#typeTableDiv .name').attr('name', 'name');
	$('#typeTableDiv table').clone(true).show().appendTo('#cloneTypeDiv');
	i++;
}

function inputSupType(){
	var supTypeName = window.prompt(i18n('supplier.addSuTypeName',""));
	if(supTypeName!=null&&supTypeName!=''){
		$.ajax({
			type: "POST",
			data : {'name':supTypeName},
			url: ctx + '/supplier/inputSupType',
			success:function(result){
				if(result=='true'){
					var option = $('<option value="'+supTypeName+'">'+supTypeName+'</option>');
					$('#type').append(option);
					$('#type').val(supTypeName);
				}
			}
		});
	}
}

function deleteType(id, obj){
	$.ajax({
		url:ctx + '/supplierType/deleteType',
		type:'POST',
		data:'typeId='+id,
		success:function(data){
			$(obj).parent().parent().parent().parent().remove();
		}
	});
}

function save(){
	var size = $('#type option').size();
	var span = $('<span class="typeSpan" style="font-size:12px;color:red;">'+i18n('global.notNull')+'</span>');
	var spanSize = $('.typeSpan').size();
	if(size==0){
		if(spanSize==0){
			$('#typeTd').append(span);
		}
		return false;
	}else{
		if(spanSize>0){
			$('.typeSpan').remove();
		}
	}
	return true;
}