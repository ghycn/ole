function judgeCount(){
	var stockNum=$('#stockNum').val();
	var lendNum = $('#lendNum').val();
	if(parseInt(stockNum)<parseInt(lendNum) ){
		alert(i18n('global.judgeCount'))
		return false;
	}
	return true;
}