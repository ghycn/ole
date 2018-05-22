$(function(){
	var size=$('#customerCode option').size();
	var temp=$('#projectCode').val().split('-');
	if(size>0 && temp[1]==''){
		var val = temp[0];
		if($('#customerCode').val()==""){
			$('#projectCode').val(val + '-' + "00000");
		}else{
			$('#projectCode').val(val + '-' + $('#customerCode').val());
		}
	}
});
function generateProjectCode(n){
	var val = $('#projectCode').val().split('-')[0];
	if($(n).val()!=""){
		$('#projectCode').val(val + '-' + $(n).val());
	}else{
		$('#projectCode').val(val + '-' + "00000");
	}
}