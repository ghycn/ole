$(function() {
	_generateTable();
	_edit();
	_info();
	_event();
	$('#modalDiv').on('hidden.bs.modal', function() {
//		window.location.href=ctx+'/projectFee/applyList';
		window.location.reload();
	});
	$('#modalDiv2').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
	$('#modalDiv3').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
	$('#modalDiv4').on('hidden.bs.modal', function() {
//		window.location.href=ctx+'/projectFee/applyList';
		window.location.reload();
	});
});

function _generateTable(){
/*	var columns = [
		{sTitle:i18n('projectFee.feeNum'), mDataProp: 'projectFeeId',sClass:'center positionTd projectFeeId'},
		{sTitle:i18n('quotation.name'), mDataProp: 'quotationName',sClass:'center positionTd'},
		{sTitle:i18n('wkLeave.applicant'), mDataProp: 'applicantName',sClass:'center positionTd'},
		{sTitle:i18n('projectFee.money'), mDataProp: 'amount',sClass:'center position',mRender:function(data,type,full){
			if(data!=null){
				return parseFloat(data).toFixed(2);
			}
			return null;
		}},
		{sTitle:i18n('projectFee.applyTime'), mDataProp: 'applyDate',sClass:'center positionTd'},
		{sTitle:i18n('projectFee.info'), mDataProp: 'name',sClass:'center positionTd'},
		{sTitle:i18n('projectFee.type'), mDataProp: 'type',sClass:'center positionTd'},
		{sTitle:i18n('global.note'), mDataProp: 'notes',sClass:'center positionTd'},
		{sTitle:i18n('global.status'), mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}},
		{sTitle:i18n('global.opera'),mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
			var str = '<input class="btn btn-default btn-xs info" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('global.info')+'>';
			if(data==2){
				str += '<input class="btn btn-default btn-xs edit" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value='+i18n('global.edit')+'>';
			}
			return str;
		}}
	];
	$('#projectFee-dataTable').createTable({
		url : ctx + '/projectFee/applyListData',
		columns : columns,
		columnsNum : 9,
		hiddenRowId : 'projectFeeId',
		serverParems : function(data){
			data.push(
				{ name: 'createStaff.userCode', value: $('#userCode').val()},
				{ name: 'prProject', value: $('#projectCode').val()},
				{ name: 'prProjectName', value: $('#prProjectName').val()},
				{ name: 'type', value: $('#type').val()},
				{ name: 'amount', value: $('#selectDropDown').val()}
			)
		},
	});*/
	var columns = [
	       		{sTitle:i18n('quotation.caseNum'), mDataProp: 'projectCode',sWidth:"80",sClass:'center positionTd projectCode '},
	       		{sTitle:i18n('caselist.caseName'), mDataProp: 'name',sWidth:"120",sClass:'center positionTd'  },
	       		{sTitle:i18n('caselist.companyName'), mDataProp: 'customerName',sWidth:"200",sClass:'center positionTd'},
	       		{sTitle:i18n('caselist.openCasePeople'), mDataProp: 'openStaffName',sClass:'center positionTd'},
	       		{sTitle:i18n('caselist.caseTime'), mDataProp: 'openTime',sClass:'center positionTd'},
	       		{sTitle:i18n('caselist.closeTime'), mDataProp: 'closeTime',sClass:'center positionTd'},
	       		{sTitle:i18n('caselist.invoice'), mDataProp: 'invoiceAmount',sClass:'center position',mRender:function(data,type,full){
	       			if(data!=null){
	       				return parseFloat(data*(full["taxRate"]*0.01+1)).toFixed(2);
	       			}
	       			return null;
	       		}},
	       		{sTitle:i18n('global.note'), mDataProp: 'notes',sClass:'center positionTd'},
	       		{sTitle:i18n('global.status'), mDataProp: 'status',sClass:'center status positionTd',mRender:function(data,type,full){
	       			return getStatus(data);
	       		}}
/*	       		{sTitle:i18n('global.opera'),mDataProp: 'status',sClass:'center positionTd w200',mRender:function(data,type,full){
	       			var str = '<input class="btn btn-default btn-xs info" data-toggle="modal" data-target="#info" data-backdrop="static" type="button" value='+i18n('global.info')+'>'+
	       					  '<input class="btn btn-default btn-xs queryQuotation" data-toggle="modal" data-target="#modalDiv" data-backdrop="static" type="button" value="'+i18n('quotation.viewQuotation')+'">';
	       			if(data==1){
	       				str+='<input class="btn btn-default btn-xs closed" type="button" value='+i18n('global.closed')+'>';
	       			}
	       			if(data==2){
	       				str+='<input class="btn btn-default btn-xs edit" data-toggle="modal" data-target="#info" data-backdrop="static" type="button" value='+i18n('global.edit')+'>';
	       			}
	       			return str
	       		}}*/
	       	];
	       	$('#projectFee-dataTable').createTable({
	       		url : ctx + '/project/data?state='+3,
	       		columns : columns,
	       		columnsNum : 9,
//	       		hiddenRowId : 'projectCode',
	       		serverParems : function(data){
	       			data.push(
	       				{ name: "projectState", value: $('#projectState').val()},
	       				{ name: "selectDropDown", value: $('#selectDropDown').val()}
//	       				{ name: "openStaff", value: $('#openStaff').val()},
//	       				{ name: "status", value: $('#status').val()}
	       			)
	       		}
	       	});
//			var table=$("#projectFee-dataTable").DataTable();
//		    var tr = $(this).closest('tr');
//		    tr.style="cursor: pointer;";
}

function _edit(){
	$('#projectFee-dataTable tbody').on( 'click', '.edit', function () {
		var projectFeeId=$(this).parents('tr').find('.projectFeeId').text();
		$(this).attr('href',ctx+'/projectFee/apply/edit?projectFeeId='+projectFeeId);
	});
}

function _info(){
	$('#projectFee-dataTable tbody').on( 'click', '.info', function () {
		var projectFeeId=$(this).parents('tr').find('.projectFeeId').text();
		$(this).attr('href',ctx+'/projectFee/apply/info?projectFeeId='+projectFeeId);
	});
}

function _event(){
// Add event listener for opening and closing details
	$('#projectFee-dataTable tbody').on('click', 'tr', function () {
		var table=$("#projectFee-dataTable").DataTable();
	    var tr = $(this).closest('tr');
	    var row = table.row( tr );
		var projectCode=$(this).find('.projectCode').text();
		var status=$(this).find('.status .appStatus').val();
		$.ajax({
			type: "POST",
			dataType: "json",
			url: ctx+'/quotation/listData?projectCode='+projectCode+'&status='+status,
			success:function(data){
			    if (row.child.isShown() ) {
			        // This row is already open - close it
			        row.child.hide();
			        tr.removeClass('shown');
			    }else {
			        // Open this row
			        row.child(format(data)).show();
			        tr.addClass('shown');
			        //style="cursor: pointer;"
			    }
			}
		});
	
	} );
}

/* Formatting function for row details - modify as you need */
function format (data) {
    // `d` is the original data object for the row
	var result="";
	if(data.length>0){
	  result='<table class="quotation" cellpadding="2" cellspacing="2" border="1" style="margin-top: 2px;padding-left:50px;width:98%;">';
	  result+='<thead><tr>'+
	  		'<th>'+i18n('quotation.name')+'</th>'+
//          '<th>'+i18n('caselist.caseName')+'</th>'+
//          '<th>'+i18n('quotation.priceCreateTime')+'</th>'+
          '<th>'+i18n('quotation.applicant')+'</th>'+
          '<th>'+i18n('quotation.caseTime')+'</th>'+
          '<th>'+i18n('quotation.taxTotal')+'</th>'+
          '<th>'+i18n('projectFee.amountTotal')+'</th>'+
//          '<th>'+i18n('global.note')+'</th>'+
          '<th>'+i18n('global.status')+'</th>'+
         // '<th>'+i18n('coseAnalysis.costs')+i18n('global.status')+'</th>'+
          '<th style="width:200px;">'+i18n('global.opera')+'</th>'+
      '</tr></thead>'
	  	for(var i=0;i<data.length;i++){
	  		var taxTotal="";
	  		if(data[i].taxTotal!=null&&"null"!=data[i].taxTotal){
	  			taxTotal=parseFloat(data[i].taxTotal).toFixed(2);
	  		}else{
	  			taxTotal="0.0";
	  		}
	  		var amountTotal="";
	  		if(data[i].amountTotal!=null&&"null"!=data[i].amountTotal){
	  			amountTotal=parseFloat(data[i].amountTotal).toFixed(2);
	  		}else{
	  			amountTotal="0.0";
	  		}
	  		var status=null;
	  		if(data[i].status!=null){
	  			status=getStatus(data[i].status);
	  		}
	  		var costStatus=null;
	  		if(data[i].costStatus!=null){
	  			costStatus=getStatus(data[i].costStatus);
	  		}

	  		var coststatu=data[i].costStatus;
	  		if(coststatu=="null"){
	  			coststatu="";
	  		}
	  		var pAmount=(parseFloat(taxTotal)-parseFloat(amountTotal)).toFixed(2);
	  		//alert(costStatus);
	  		 result+='<tr>'+
//	            '<td class="center positionTd quotationId" sytle="dispaly:none">'+data[i].quotationId+'</td>'+
	            '<td class="center positionTd" ><input type="hidden" value="'+data[i].quotationId+'"/>'+data[i].quotationName+'</td>'+
//	            '<td class="center positionTd projectCode">'+data[i].projectName+'</td>'+
//	            '<td class="center positionTd">'+data[i].date+'</td>'+
	            '<td class="center positionTd">'+data[i].applicant+'</td>'+
	            '<td class="center positionTd">'+data[i].caseTime+'</td>'+
	            '<td class="center positionTd">'+taxTotal+'</td>'+
	            '<td class="center positionTd">'+amountTotal+'</td>'+
//	            '<td class="center positionTd">'+data[i].note+'</td>'+
	            '<td class="center positionTd">'+status+'</td>'+
	 //           '<td class="center positionTd">'+costStatus+'</td>'+
	            '<td class="center positionTd" style="width:240px;cursor: pointer;">';
//	            '&nbsp;&nbsp;<a href="#"' +
//	            'onclick="${ctx}/quotation/quotationItem?quotationId='+data[i].quotationId+'&status='+data[i].status+'">'+
//	            i18n('quotation.viewQuotationItem')+'</a>'+
	            if(data[i].status!=null&&data[i].status==1){
	            	
	            	if(data[i].costStatus=="null"||data[i].costStatus==0){
			            result+='<input type="button" style="width: 30%; float: left; cursor: pointer;" value="'+
				            i18n('projectFee.projectFeeAmount')+'"'+
								'class="btn btn-default btn-sm" data-toggle="modal"'+
								'data-target="#modalDiv" data-backdrop="static"'+
								'href="'+ctx+'\/projectFee\/apply\/add?quotationId='+data[i].quotationId+'&pAmount='+pAmount+'" />';
		            	result+='<input type="button" style="width: 30%; float: left; cursor: pointer;" value="'+
			            i18n('projectFee.otherAmount')+'"'+
							'class="btn btn-default btn-sm" data-toggle="modal"'+
							'data-target="#modalDiv4" data-backdrop="static"'+
							'href="'+ctx+'\/projectFee\/otherApply\/add?quotationId='+data[i].quotationId+'&costStatus='+coststatu+'&pAmount='+pAmount+'" />';
	            	}
	            	result+='<input type="button" style="width: 40%; float: left; cursor: pointer;" value="'+
			            i18n('projectFee.viewProjectFee')+'"'+
							'class="btn btn-default btn-sm" data-toggle="modal"'+
							'data-target="#modalDiv2" data-backdrop="static"'+
							'href="'+ctx+'\/projectFee\/applyFeeList?quotationId='+data[i].quotationId+'&costStatus='+coststatu+'&pAmount='+pAmount+'" />';
	            }
//	            '<a href="'+ctx+'\/projectFee\/apply\/add"' +
//	            'onclick="${ctx}\/projectFee\/apply\/add"'+
//	            '>'+
//	            i18n('projectFee.addApply')+'</a>'
	            result+=' </td>'+
	        '</tr>';
		}
	  	result+='</table>';
	}
	else{
		result= i18n('global.sZeroRecords');
			
	}
	return result;
}