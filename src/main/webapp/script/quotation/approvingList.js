$(function() {
	_generateTable();
	_approve();
	_reject();
	_info();
	$('#info').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('quotation.number'), mDataProp: 'quotationId',sClass:'center positionTd quotationId'},
		{sTitle:i18n('caselist.caseName'), mDataProp: 'projectName',sWidth:"120",sClass:'center positionTd'},
		{sTitle:i18n('quotation.name'), mDataProp: 'quotationName',sWidth:"120",sClass:'center positionTd'},
		{sTitle:i18n('quotation.customer'), mDataProp: 'customerName',sWidth:"180",sClass:'center positionTd'},
		{sTitle:i18n('quotation.caseTime'), mDataProp: 'caseTime',sWidth:"60",sClass:'center positionTd'},
		{sTitle:i18n('quotation.applicant'), mDataProp: 'applicant',sWidth:"60",sClass:'center positionTd'},
		{sTitle:i18n('quotation.taxTotal'), mDataProp: 'taxTotal',sWidth:"60",sClass:'center position',mRender:function(data,type,full){
			if(data!=null){
				return parseFloat(data*(full["taxRate"]*0.01+1)).toFixed(2);
			}
			return null;
		}},
//		{sTitle:i18n('supplier.note'), mDataProp: 'note',sClass:'center positionTd'},
		{sTitle:i18n('global.status'), mDataProp: 'status',sWidth:"60",sClass:'center positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}},
		{sTitle:i18n('global.opera'),sClass:'test center  positionTd',mRender:function(data,type,full){
			var str= '<input class="btn btn-default btn-xs approve" type="button" value='+i18n('global.approve')+'>'+
			'<input class="btn btn-default btn-xs reject" type="button" value='+i18n('global.reject')+'>'+
			'<input class="btn btn-default btn-xs info" data-toggle="modal" data-target="#info" data-backdrop="static" type="button" value='+i18n('quotation.viewQuotationItem')+'>';
//			var roleId = $("#roleId").val();
//			if(roleId=='1' && roleId=='6'){
//				return str;
//			}else{
//				return  "";
//			}
			return str;
		}}
	];
	var a = "",b="";
	var roleId = $("#roleId").val();
	if(roleId=="1" || roleId=='6'){
		a = 'quotationId';
		b=8;
	}else{
		a = 'quotationId,test';
		b=7;
	}
	$('#quotationApproving-dataTable').createTable({
		url : ctx + '/approving/quotation/data',
		columns : columns,
		columnsNum : b,
		hiddenRowId : a,
		serverParems : function(data){
			data.push(
			{ name: 'prProject', value: $('#projectCode').val()},
			{name:"projectName",value :$('#projectName').val()},
			{name:"applicant",value :$('#openStaff').val()},
			{name:"status",value :'0'},
			{name:"flag",value :true}
			
			)
		}
	});
}

function _approve(){
	$('#quotationApproving-dataTable tbody').on( 'click', '.approve', function () {
		var quotationId=$(this).parents('tr').find('.quotationId').text();
		$.ajax({
			url:ctx + '/approving/quotation/approve',
			data:'quotationId='+quotationId,
			success : function(data){
				alert(i18n('global.success'));
				window.location.reload();
			}
		});
	});
}

function _reject(){
	$('#quotationApproving-dataTable tbody').on( 'click', '.reject', function () {
		var quotationId=$(this).parents('tr').find('.quotationId').text();
		$.ajax({
			url:ctx + '/approving/quotation/reject',
			data:'quotationId='+quotationId,
			success : function(data){
				alert(i18n('global.success'));
				window.location.reload();
			}
		});
	});
}

function _info(){
	$('#quotationApproving-dataTable tbody').on( 'click', '.info', function () {
		var quotationId=$(this).parents('tr').find('.quotationId').text();
		$(this).attr('href',ctx+'/quotation/quotationItem?quotationId='+quotationId+'&status=1');
	});
}