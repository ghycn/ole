$(function(){
	count = $('input[name="duration"]').eq(0).val();
	count-=0.5;
	$('.calculate').change(function(){
		var start=$('input[name="start"]').eq(0).val();
		var end=$('input[name="end"]').eq(0).val();
		var days=dateDiff(start,end);
		$('input[name="duration"]').eq(0).val(days);
		count = days;
		checkTimes();
	});
});

function dateDiff(sDate1,  sDate2){
    var  aDate,  oDate1,  oDate2,  iDays
    aDate  =  sDate1.split("-");
    oDate1  =  new  Date(aDate[1]  +  '/'  +  aDate[2]  +  '/'  +  aDate[0]);  //转换为12-18-2006格式
    var bDate  =  sDate2.split("-");
    oDate2  =  new  Date(bDate[1]  +  '/'  +  bDate[2]  +  '/'  +  bDate[0]);
    iDays  =  parseInt(Math.abs(parseInt(oDate1.getTime()  -  oDate2.getTime()))  /  1000  /  60  /  60  /24);    //把相差的毫秒数转换为天数
    return  iDays
}

function checkTimes(){
	var startSelect=$('#timeStart').val();
	var endSelect=$('#timeEnd').val();
	if((startSelect==0 && endSelect==0) || (startSelect==1 && endSelect==1)){
		$('input[name="duration"]').eq(0).val(count*1+0.5);
	}else if(startSelect==0 && endSelect==1){
		$('input[name="duration"]').eq(0).val(count*1+1);
	}else if(startSelect==1 && endSelect==0){
		$('input[name="duration"]').eq(0).val(count*1+0);
	}
}