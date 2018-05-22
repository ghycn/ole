$(function(){
	$.ajax({
		type: "POST",
		dataType: "json",
		url: ctx + '/wkLeave/report/data?userCode=' + $('#userCode').val(),
		success:function(result){
			var xValue = new Array();
			var data = new Array();
			if(result.length==0){
				xValue[0] = '';
				data[0] = 0;
			}
			for ( var i = 0; i < result.length; i++) {
				xValue[i] = result[i][0]+'月';
				data[i] = result[i][1];
			}
			var barItem = {
					data : [ '每月请假天数'],
					XValue : xValue,
					series : [{
						name : "每月请假天数",
						type : "line",
						data : data,
						itemStyle: {
							normal: {
								label : {
									show : true,
									textStyle : {
										fontSize : '20',
										fontFamily : '微软雅黑',
										fontWeight : 'bold'
									}
								}
							}
						},
						markLine : {
							data : [
								{type : 'max'},//最大值的水平线
								{type : 'min'}//最小值的水平线
							]
						}
					}]
				}
			$('#wkLeaveReport').createCategory({
				item : barItem,
				toolBar : true
			});
		}
	});
});