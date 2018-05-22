$(function() {
	_generateTable();
	//_revert();
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('device.code'), mDataProp: 'borrowCode',sClass:'center positionTd borrowCode',bVisible:true},
		{sTitle:i18n('device.borrow.borrower'), mDataProp: 'applicantName',sClass:'center positionTd'},
		{sTitle:i18n('device.borrow.borrowTime'), mDataProp: 'borrowTime',sClass:'center positionTd'},
		{sTitle:i18n('device.borrow.returnTime'), mDataProp: 'returnTime',sClass:'center positionTd'},
		{sTitle:i18n('device.borrow.status'), mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
			if(data==0){
				return i18n('device.borrow.return');
			}else if(data==1){
				return i18n('device.borrow.noReturn');
			}
		}}
	];
	$('#usedDevice-dataTable').createTable({
		url : ctx + '/deviceBorrow/data',
		columns : columns,
		serverParems : function(data){
			data.push(
				{ name: "lender", value: $('#lender').val()},
				{ name: "status", value: $('#status').val()}
			)
		}
	});
}