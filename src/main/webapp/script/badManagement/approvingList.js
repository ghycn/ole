$(function() {
	_generateTable();
	_approve();
	_reject();
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('profile.serialNumber'), mDataProp: 'badId',sClass:'center positionTd badId'},
		{sTitle:i18n('quotation.name'), mDataProp: 'quotationName',sClass:'center positionTd'},
		{sTitle:i18n('quotation.applicant'), mDataProp: 'people',sClass:'center positionTd'},
		{sTitle:i18n('quotation.reason'), mDataProp: 'reason',sClass:'center positionTd'},
		{sTitle:i18n('projectFee.money'), mDataProp: 'total',sClass:'center position',mRender:function(data,type,full){
			if(data!=null){
				return parseFloat(data*(full["taxRate"]*0.01+1)).toFixed(2);
			}
			return null;
		}},
		{sTitle:i18n('global.status'), mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}},
		{sTitle:i18n('global.note'), mDataProp: 'note',sClass:'center positionTd'},
		{sTitle:i18n('global.opera'),sClass:'center positionTd',mRender:function(data,type,full){
			return '<input class="btn btn-default btn-xs approve" type="button" value='+i18n('global.approve')+'>'+
			'<input class="btn btn-default btn-xs reject" type="button" value='+i18n('global.reject')+'>';
		}}
	];
	$('#badAccountApproving-dataTable').createTable({
		url : ctx + '/approving/badAccount/data',
		columns : columns,
		columnsNum : 7,
		hiddenRowId : 'badId',
		serverParems : function(data){
			data.push(
				{ name: 'prProject', value: $('#projectCode').val()},
				{name: "people", value: $('#people').val()},
				{name: "projectName", value: $('#projectName').val()},
				{name: "status", value:'0'},
				{name: "flag", value:true}
			)
		}
	});
}

function _approve(){
	$('#badAccountApproving-dataTable tbody').on( 'click', '.approve', function () {
		var badId=$(this).parents('tr').find('.badId').text();
		$.ajax({
			url:ctx + '/approving/badAccount/approve',
			data:'badId='+badId,
			success : function(data){
				alert(i18n('global.success'));
				window.location.reload();
			}
		});
	});
}

function _reject(){
	$('#badAccountApproving-dataTable tbody').on( 'click', '.reject', function () {
		var badId=$(this).parents('tr').find('.badId').text();
		$.ajax({
			url:ctx + '/approving/badAccount/reject',
			data:'badId='+badId,
			success : function(data){
				alert(i18n('global.success'));
				window.location.reload();
			}
		});
	});
}