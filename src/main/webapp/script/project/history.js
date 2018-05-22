$(function() {
	_generateTable();
//	_info();
//	_queryQuotation();
//	_edit();
//	_closed();
//	$('#modalDiv').on('hidden.bs.modal', function() {
//		$(this).removeData('bs.modal');
//	});
//	$('#info').on('hidden.bs.modal', function() {
//		$(this).removeData('bs.modal');
//	});
});

function _generateTable(){
	var columns = [
	       		{sTitle:i18n('projectFee.feeNum'), mDataProp: 'projectFeeId',sClass:'center positionTd projectFeeId'},
	    		{sTitle:i18n('quotation.name'), mDataProp: 'quotationName',sClass:'center positionTd'},
	    		{sTitle:i18n('quotation.applicant'), mDataProp: 'applicantName',sClass:'center positionTd'},
	    		{sTitle:i18n('projectFee.amountTotal'), mDataProp: 'amount',sClass:'center position',mRender:function(data,type,full){
	    			if(data!=null){
	    				var taxRate = $("#taxRate").val();
	    				return parseFloat(data).toFixed(2);
	    			}
	    			return null;
	    		}},
	    		{sTitle:i18n('projectFee.applyTime'), mDataProp: 'applyDate',sClass:'center positionTd'},
	    		{sTitle:i18n('projectFee.info'), mDataProp: 'name',sClass:'center positionTd'},
	    		{sTitle:i18n('projectFee.type'), mDataProp: 'type',sClass:'center positionTd'},
	    		{sTitle:i18n('global.status'), mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
	    			return getStatus(data);
	    		}}
	];
	var projectCode = $("#projectCode").val();
	$('#prProject-dataTable').createTable({
		url : ctx + '/project/getHistoryData?projectCode='+projectCode,
		columns : columns, 
		columnsNum : 7,
		hiddenRowId : 'projectFeeId',
		serverParems : function(data){
			data.push(
				{ name: "projectCode", value: $('#proCode').val()},
				{ name: "projectName", value: $('#proName').val()},
				{ name: "openStaff", value: $('#openStaff').val()},
				{ name: "status", value: $('#status').val()}
			)
		}
	});
}

function _info(){
	$('#prProject-dataTable tbody').on( 'click', '.info', function () {
		var projectCode=$(this).parents('tr').find('.projectCode').text();
		$(this).attr('href',ctx+'/project/info?projectCode='+projectCode);
	});
}

function _queryQuotation(){
	$('#prProject-dataTable tbody').on( 'click', '.queryQuotation', function () {
		var projectCode=$(this).parents('tr').find('.projectCode').text();
		var status=$(this).parents('tr').find('.status .appStatus').val();
		$(this).attr('href',ctx+'/quotation/list?projectCode='+projectCode+'&status='+status);
	});
}

function _edit(){
	$('#prProject-dataTable tbody').on( 'click', '.edit', function () {
		var projectCode=$(this).parents('tr').find('.projectCode').text();
		$(this).attr('href',ctx+'/project/edit?projectCode='+projectCode);
	});
}

function _closed(){
	$('#prProject-dataTable tbody').on( 'click', '.closed', function () {
		var projectCode=$(this).parents('tr').find('.projectCode').text();
		$.ajax({
			type: "POST",
			dataType: "text",
			data : {'projectCode':projectCode},
			url: ctx + '/cost/closed',
			success:function(result){
				if(result==-1){
					alert(i18n('coseAnalysis.closedError'))
				}else if(result==0){
					alert(i18n('coseAnalysis.underReview'));
				}else if(result==1){
					alert('Success');
				}else if(result==2){
					alert(i18n('coseAnalysis.rejected'))
				}else if(result==4){
					alert(i18n('coseAnalysis.accountClosed'));
				}
			}
		});
	});
}
function checkHistory(arg1,arg2){//查看请款记录4
		var id=arg1+"-"+arg2;
		window.location.href=ctx +'/project/checkHistory?projectCode='+id;
}