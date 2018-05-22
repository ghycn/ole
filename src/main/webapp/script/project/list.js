$(function() {
	_generateTable();
	_info();
	_queryQuotation();
	_edit();
	_closed();
	_newClosed();
	$('#modalDiv').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
	$('#info').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
});

function _generateTable(){
	var roleId=$("#roleId").val();
	var userCode=$("#userCode").val();
	var columns = [
		{sTitle:i18n('quotation.caseNum'), mDataProp: 'projectCode',sClass:'center positionTd projectCode'},
		{sTitle:i18n('caselist.caseName'), mDataProp: 'name',sWidth:"60",sClass:'center positionTd'},
		{sTitle:i18n('caselist.companyName'), mDataProp: 'customerName', sWidth:"150",sClass:'center positionTd'},
		{sTitle:i18n('caselist.caseTime'), mDataProp: 'openTime',sWidth:"60",sClass:'center positionTd'},
		{sTitle:i18n('caselist.closeTime'), mDataProp: 'closeTime',sWidth:"50",sClass:'center positionTd'},
		{sTitle:i18n('caselist.invoice'), mDataProp: 'invoiceAmount',sWidth:"50",sClass:'center position',mRender:function(data,type,full){
			if(data!=null){
				return parseFloat(data).toFixed(2)*(full["taxRate"]/100+1);
			}
			return null;
		}},
//		{sTitle:i18n('global.note'), mDataProp: 'notes',sClass:'center positionTd'},
		{sTitle:i18n('quotation.caseNum'), mDataProp: 'openStaffCode',sClass:'center positionTd openStaffCode'},
		{sTitle:i18n('caselist.openCasePeople'), mDataProp: 'openStaffName',sWidth:"50",sClass:'center positionTd'},
		{sTitle:i18n('customer.approver'), mDataProp: 'approvalOfPersonnelName',sWidth:"50",sClass:'center positionTd'},
		{sTitle:i18n('customer.approver'), mDataProp: 'approvalOfPersonnel',sClass:'center positionTd approvalOfPersonnel'},
		{sTitle:i18n('global.status'), mDataProp: 'status',sWidth:"30",sClass:'center status positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}},
		{sTitle:i18n('global.opera'),mDataProp: 'status',sClass:'center positionTd w200',mRender:function(data,type,full){
			var str = '<input class="btn btn-default btn-xs info" data-toggle="modal" data-target="#info" data-backdrop="static" type="button" value='+i18n('global.info')+'>'+
					  '<input class="btn btn-default btn-xs queryQuotation" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value="'+i18n('quotation.viewQuotation')+'">';
			if(data==1){ 
				if(roleId==6||roleId==1){
					if(userCode==full["openStaffCode"]){
//						str+='<input class="btn btn-default btn-xs closed" type="button" value='+i18n('global.closed')+'>';
					}
				}else{
//						str+='<input class="btn btn-default btn-xs closed" type="button" value='+i18n('global.closed')+'>';
					}
			}
			if(data==2){
				str+='<input class="btn btn-default btn-xs edit" data-toggle="modal" data-target="#info" data-backdrop="static" type="button" value='+i18n('global.edit')+'>';
			}

			var ids=full["projectCode"].split("-");
				str+='<input class="btn btn-default btn-xs"  onclick="checkHistory('+ids[0]+','+ids[1]+')" type="button" value='+i18n('global.payout')+'>';
			if(roleId==6||roleId==1){
				if(data!=4){ 
					str+='<input class="btn btn-default btn-xs newClosed" type="button" value='+i18n('global.invalid')+'>';
				}
			}	
			return str
		}}
	];
	$('#prProject-dataTable').createTable({
		url : ctx + '/project/data?state='+2,
		columns : columns,
		columnsNum : 9,
		hiddenRowId : 'projectCode,openStaffCode,approvalOfPersonnel',
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
		var openStaffCode=$(this).parents('tr').find('.openStaffCode').text();
		var approvalOfPersonnel=$(this).parents('tr').find('.approvalOfPersonnel').text();
		$(this).attr('href',ctx+'/quotation/list?projectCode='+projectCode+'&status='+status+'&userCode='+openStaffCode+'&approvalOfPersonnel='+approvalOfPersonnel);
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
function _newClosed(){

	$('#prProject-dataTable tbody').on( 'click', '.newClosed', function () {
		
		if(!confirm(i18n('caselist.invalid'))){
			return false;
		}
		var projectCode=$(this).parents('tr').find('.projectCode').text();
		$.ajax({
			type: "POST",
			dataType: "text",
			data : {'projectCode':projectCode},
			url: ctx + '/project/closed',
			success:function(result){
				alert(i18n('global.success'));
				window.location.href = ctx + '/project/list';
			}
		});
	});
}

function checkHistory(arg1,arg2){//查看请款记录4
		var id=arg1+"-"+arg2;
		window.location.href=ctx +'/project/checkHistory?projectCode='+id;
}