$(function(){
	$('#basicInfoForm').validation();
});

function validationPass(){
	var pass1=$('#pass1').val();
	var pass2=$('#pass2').val();
	var span1 = $('<span id="errorNum" style="color:red;font-size:13px;">'+i18n('global.ppasswordError')+'</span>');
	var span = $('<span id="errorSpan" style="color:red;font-size:13px;">'+i18n('global.passwordInconsistent')+'</span>');
	if(pass1.length<6 || pass1.length>16){
		if($('#errorNum').text()==''){
			$('#pass1').after(span1);
		}
		return false;
	}else if(pass1!=pass2){
		if($('#errorSpan').text()==''){
			$('#pass2').after(span);
		}
		  return false;  
	   }
	changePassword();
	return true;
}

function changePassword() {
	var params = $('#basicInfoForm');
	$.post(ctx+'/personalData/editPassword', params.serialize(), function(data) {
		if (data == 'oldPassError') {
			alert(i18n('global.passwordError'));
		} else if (data == 'success') {
			alert(i18n('admin.changepasswordsuccess'));
			window.location.reload(true);
		}else if(data=='otherSuccess'){
			alert(i18n('admin.changepasswordsuccess'));
			window.location.href=ctx + '/user/list';
		}
	});
}