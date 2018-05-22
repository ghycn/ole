$(function() {
	i = $('#cloneDiv table').size();
//	if(i==0){
//		$('#cloneTableDiv table').clone(true).show().appendTo('#cloneDiv');
//		i+=1;
//	}
	$('#customForm').validation();
	$('#customTab a:first').tab('show');
	$('#customTab a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
	});
	$('.deleteCustom').click(function() {
		$(this).parent().parent().parent().remove();
		i--;
	});
	
	$('#cusTypeForm').validation();
	$('.delCusType').click(function() {
		$(this).parent().parent().parent().remove();
		i--;
	});
	
/*	var status=$('#customerStatus').val();
	if(status==1){
		$('#customInfo :input').attr('readOnly',true);
	}*/
});

function addCustom() {
	$('#cloneTableDiv .name').attr('name', 'contacts[' + i + '].customContactName');
	$('#cloneTableDiv .tel').attr('name', 'contacts[' + i + '].tel');
	$('#cloneTableDiv .mobile').attr('name', 'contacts[' + i + '].mobile');
	$('#cloneTableDiv .email').attr('name', 'contacts[' + i + '].email');
	$('#cloneTableDiv .extension').attr('name', 'contacts[' + i + '].extension');
	$('#cloneTableDiv table').clone(true).show().appendTo('#cloneDiv');
	i++;
}

function addType() {
	$('#typeTableDiv .name').attr('name', 'name');
	$('#typeTableDiv table').clone(true).show().appendTo('#cloneTypeDiv');
	i++;
}

function inputCusType(){
	var cusTypeName = window.prompt(i18n('customer.addCusTypeName'),"");
	if(cusTypeName!=null&&cusTypeName!=''){
		$.ajax({
			type: "POST",
			data : {'name':cusTypeName},
			url: ctx + '/custom/inputCusType',
			success:function(result){
				if(result=='true'){
					var option = $('<option value="'+cusTypeName+'">'+cusTypeName+'</option>');
					$('#type').append(option);
					$('#type').val(cusTypeName);
				}
			}
		});
	}
}


function deleteType(id, obj){
	$.ajax({
		url:ctx + '/customType/deleteType',
		type:'POST',
		data:'typeId='+id,
		success:function(data){
			$(obj).parent().parent().parent().parent().remove();
		}
	});
}

function deleteContact(id){
	$.ajax({
		url:ctx + '/custom/deleteContact',
		data:'customerContactId='+id
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