var myCharts = new Array();
var c = 0;
require.config({
	paths : {
		'echarts' : ctx + '/static/script/echarts/echarts',
		'echarts/chart/bar' : ctx + '/static/script/echarts/echarts',
		'echarts/chart/pie' : ctx + 'static/script/echarts/echarts'
	}
});
$.fn.createCategory = function(setting){
	var id = $(this).attr('id');
	require([ 'echarts', 'echarts/chart/bar' ], function(ec) {
		var myChart = ec.init(document.getElementById(id));
		myCharts[c++] = myChart;
		var option = {
			title : setting.item.title,
			tooltip : {
				show: true
			},
			legend : {
				data : setting.item.data
			},
			toolbox: {
				show : setting.toolBar,
				feature : {
//					mark : {show: true},
//					dataView : {show: true, readOnly: false},
					magicType : {show: true, type: ['line', 'bar']},
//					restore : {show: true},
//					saveAsImage : {show: true}
				}
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				position: 'bottom',
				boundaryGap: true,
				data : setting.item.XValue,
				axisLabel : {
					show:true,
					interval: 'auto',
					rotate: -45,
					margin: 6,
					formatter: '{value}',
					textStyle: {
						fontFamily: '黑体',
						fontStyle: 'italic',
					}
				}
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : setting.item.series
		};
		myChart.setOption(option);
		_click(myChart,setting);
	});
}

$.fn.createPie = function(setting){
	var id = $(this).attr('id');
	require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
		var myChart = ec.init(document.getElementById(id));
		var option = {
				tooltip : {
				show : true,
				formatter : "{a} <br />{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				data : setting.item.data
			},
			toolbox : {
				show : setting.toolBar,
				feature : {
					mark : {show : true},
					dataView : {show : true,readOnly : false},
					restore : {show : true},
					saveAsImage : {show : true}
				}
			},
			calculable : true,
			series : setting.item.series
		};
		myChart.setOption(option);
		_click(myChart,setting);
	});
}

function _click(myChart,setting){
	var clickResult;
	myChart.on('click',function(obj){
		clickResult = setting.click(obj);
		if(clickResult!=undefined){
			window.open(clickResult,'_blank');
		}
	});
}