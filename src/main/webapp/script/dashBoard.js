function operatorFormatter(value, row) {
	return '<a href="${ctx}/user/edit?code=' + value + '">修改</a> <a href="${ctx}/user/delete?code=' + value + '">删除</a>';
}
$(document).ready(function() {
	// select 假数据
	var selectData = [ {
		value : 1,
		text : 1,
		selected : false
	}, {
		value : 2,
		text : 2,
		selected : true
	}, {
		value : 3,
		text : 3,
		selected : false
	}, {
		value : 4,
		text : 4,
		selected : false
	} ];
	/* 生成select数据方法 */
	function selectView(selectData, selectId) {
		for ( var i = 0; i < selectData.length; i++) {
			if (selectData[i].selected) {
				$('select[id=' + selectId + ']').append('<option value="' + selectData[i].value + '" selected="selected">' + selectData[i].text + '</option>');
			} else {
				$('select[id=' + selectId + ']').append('<option value="">' + selectData[i].text + '</option>');
			}
		}
	}
	selectView(selectData, 'select01');
	// 生成table
	$('.bs-example').find('table').bootstrapTable('destroy').bootstrapTable();
	$('#addButton').click(function() {
		location.href = ctx + '/user/edit';
	})
})