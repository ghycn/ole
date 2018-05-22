$(function() {
	_generateTable();
	_edit();
	_role();
	_invocation();
	_lock();
	_reset();
	_doDelete();
	$('#modalDiv').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
});

function _generateTable() {
	var columns = [
		{sTitle:i18n('sys.userID'), mDataProp: 'userCode',sClass:'center positionTd userCode w102',bVisible:true},
		{sTitle:i18n('sys.userName'), mDataProp: 'name',sClass:'center positionTd w63'},
		{sTitle:i18n('sys.userSex'), mDataProp: 'sex',sClass:'center positionTd w63',mRender:function(data,type,full){
			if(data==0){
				return i18n('sys.man');
			}else if(data==1){
				return i18n('sys.women');
			}
		}},

		{sTitle:i18n('sys.userPosition'), mDataProp: 'type',sClass:'center positionTd w90'},
		{sTitle:i18n('customer.mobile'), mDataProp: 'cellPhone',sClass:'center positionTd w94'},
		{sTitle:i18n('global.status'), mDataProp: 'status',sClass:'center positionTd w63',mRender:function(data,type,full){
			if(data==0){
				return i18n('user.lock');
			}else if(data==1){
				return i18n('user.invocation');
			}
		}},
		{sTitle:i18n('global.opera'),mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
			var str = '<input class="btn btn-default btn-xs editrow" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('global.edit')+'>';
			if(data==0){
				str += '<input class="btn btn-default btn-xs invocation" type="button" value='+i18n('user.invocation')+'>';
			}else{
				str += '<input class="btn btn-default btn-xs lock" type="button" value='+i18n('user.lock')+'>';
			}
			str += '<input class="btn btn-default btn-xs resetrow" type="button" value='+i18n('global.resetPassword')+'>' +
			'<input class="btn btn-default btn-xs delrow" type="button" value='+i18n("global.delete")+'>';
			return str;
		}}
	];
	$('#user-dataTable').createTable({
		url : ctx + '/user/data',
		columns : columns,
		serverParems : function(data){
			data.push(
				{ name: "name", value: $('#name').val()},
				{ name: "cellPhone", value: $('#cellPhone').val()}
			)
		},
		type : type
	});
}

function _edit(){
	$('#user-dataTable tbody').on( 'click', '.editrow', function () {
		var userCode=$(this).parents('tr').find('.userCode').text();
		$(this).attr('href',ctx+'/user/edit?userCode='+userCode);
	});
}

function _role(){
	$('#user-dataTable tbody').on( 'click', '.rolerow', function () {
		var userCode=$(this).parents('tr').find('.userCode').text();
		$(this).attr('href',ctx+'/user/setRole?userCode='+userCode);
	});
}

function _invocation(){
	$('#user-dataTable tbody').on( 'click', '.invocation', function () {
		var flag=window.confirm(i18n('user.setStatus'));
		if(flag){
			var userCode=$(this).parents('tr').find('.userCode').text();
			window.location.href=ctx + '/user/changeStatus?userCode='+userCode+'&status=true';
		}
	});
}

function _lock(){
	$('#user-dataTable tbody').on( 'click', '.lock', function () {
		var flag=window.confirm(i18n('user.setStatus'));
		if(flag){
			var userCode=$(this).parents('tr').find('.userCode').text();
			window.location.href=ctx + '/user/changeStatus?userCode='+userCode+'&status=false';
		}
	});
}

function _reset(){
	$('#user-dataTable tbody').on( 'click', '.resetrow', function () {
		var userCode=$(this).parents('tr').find('.userCode').text();
		window.location.href=ctx + '/personalData/changePassword?userCode='+userCode;
	});
}

function _doDelete(){
	$('#user-dataTable tbody').on( 'click', '.delrow', function () {
		var flag=window.confirm(i18n('global.deleteConfirm'));
		if(flag){
			var userCode=$(this).parents('tr').find('.userCode').text();
			window.location.href=ctx + '/user/doDelete?userCode='+userCode+'&state=0';
		}
	});
}