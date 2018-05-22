$(function() {
	_generateTable();
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('sys.userID'), mDataProp: 'userCode',sClass:'center positionTd userCode',bVisible:true},
		{sTitle:i18n('sys.userName'), mDataProp: 'name',sClass:'center positionTd'},
		{sTitle:i18n('sys.userSex'), mDataProp: 'sex',sClass:'center positionTd',mRender:function(data,type,full){
			if(data==0){
				return i18n('sys.man');
			}else if(data==1){
				return i18n('sys.women');
			}
		}},
		
		{sTitle:i18n('sys.userPosition'), mDataProp: 'type',sClass:'center positionTd'},
		{sTitle:i18n('sys.userMobile'), mDataProp: 'cellPhone',sClass:'center positionTd'}
	];
	$('#contact-dataTable').createTable({
		url : ctx + '/contact/data',
		columns : columns,
		serverParems : function(data){
			data.push(
				{ name: "name", value: $('#name').val()}
			)
		}
	});
}