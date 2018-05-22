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
		{sTitle:i18n('projectFee.feeNum'), mDataProp: 'projectFeeId',sClass:'center positionTd projectFeeId'},
		{sTitle:i18n('caselist.caseName'), mDataProp: 'projectName',sWidth:"80",sClass:'center positionTd'},
		{sTitle:i18n('quotation.name'), mDataProp: 'quotationName',sWidth:"120",sClass:'center positionTd'},
		{sTitle:i18n('quotation.applicant'), mDataProp: 'applicantName',sWidth:"60",sClass:'center positionTd'},
		{sTitle:i18n('projectFee.money'), mDataProp: 'amount',sClass:'center position',mRender:function(data,type,full){
			if(data!=null){
				return parseFloat(data).toFixed(2);
			}
			return null;
		}},
		{sTitle:i18n('projectFee.applyTime'), mDataProp: 'applyDate',sClass:'center positionTd'},
		{sTitle:i18n('projectFee.info'), mDataProp: 'name',sClass:'center positionTd'},
		{sTitle:i18n('projectFee.type'), mDataProp: 'quotationListId',sWidth:"150",sClass:'center positionTd quotationListId'},
		{sTitle:i18n('projectFee.type'), mDataProp: 'type',sWidth:"150",sClass:'center positionTd'},
		{sTitle:i18n('global.status'), mDataProp: 'status',sClass:'center positionTd',mRender:function(data,type,full){
			return getStatus(data);
		}},
		{sTitle:i18n('global.opera'),sWidth:"150",sClass:'center positionTd',mRender:function(data,type,full){
			return '<input class="btn btn-default btn-xs approve" type="button" value='+i18n('global.approve')+'>'+
			'<input class="btn btn-default btn-xs reject" type="button" value='+i18n('global.reject')+'>'+
			'<input class="btn btn-default btn-xs info" data-toggle="modal" data-target="#info" data-backdrop="static" type="button" value='+i18n('global.info')+'>';
		}}
	];
	$('#projectFeeApproving-dataTable').createTable({
		url : ctx + '/approving/projectFee/data',
		columns : columns,
		columnsNum : 9,
		hiddenRowId : 'projectFeeId,quotationListId',
		serverParems : function(data){
			data.push(
				{name:"applicant",value:$('#applicant').val()},
				{name:"status", value:'0'},
				{name:"typeId", value:$('#typeId').val()},
				{name:"flag", value:true}
			)
		}
	});
}

function _approve(){
	$('#projectFeeApproving-dataTable tbody').on( 'click', '.approve', function () {
		var quotationListId=$(this).parents('tr').find('.quotationListId').text();
		var projectFeeId=$(this).parents('tr').find('.projectFeeId').text();
		$.ajax({
			url:ctx + '/approving/projectFee/approve?projectFeeId='+projectFeeId+'&quotationListId='+quotationListId,
			success : function(data){
				if(data=="1"){
					alert(i18n('global.success'));
					window.location.reload();
				}else{
//					alert(i18n('global.moreThan'));global.confirmToSubmit
					if(confirm(i18n('global.moreThan'),i18n('global.confirmToSubmit'))){
						window.location.href = ctx + '/approving/projectFee/pass?projectFeeId='+projectFeeId; 
//						window.location.reload();
					}
				}
			}
		});
	});
}

function _reject(){
	$('#projectFeeApproving-dataTable tbody').on( 'click', '.reject', function () {
		var projectFeeId=$(this).parents('tr').find('.projectFeeId').text();
		$.ajax({
			url:ctx + '/approving/projectFee/reject',
			data:'projectFeeId='+projectFeeId,
			success : function(data){
				alert(i18n('global.success'));
				window.location.reload();
			}
		});
	});
}

function _info(){
	$('#projectFeeApproving-dataTable tbody').on( 'click', '.info', function () {
		var projectFeeId=$(this).parents('tr').find('.projectFeeId').text();
		$(this).attr('href',ctx+'/projectFee/apply/info?projectFeeId='+projectFeeId);
	});
}