$(function() {
	_generateTable();
	_edit();
	_permission();
	_doDelete();
	$('#modalDiv').on('hidden.bs.modal', function() {
		$(this).removeData('bs.modal');
	});
});

function _generateTable(){
	var columns = [
		{sTitle:i18n('holiday.id'), mDataProp: 'roleId',sClass:'center positionTd roleId',bVisible:true},
		{sTitle:i18n('role.name'), mDataProp: 'name',sClass:'center positionTd'},
		{sTitle:i18n('global.note'), mDataProp: 'notes',sClass:'center positionTd'},
		{sTitle:i18n('global.opera'),mDataProp: 'roleId',sClass:'center positionTd',mRender:function(data,type,full){
			var str = '<input class="btn btn-default btn-xs editrow" data-toggle="modal" data-backdrop="static" data-target="#modalDiv" type="button" value="'+i18n('global.edit')+'">';
			if(data!=1 && data!=6){
				str += '<input class="btn btn-default btn-xs delrow" type="button" value='+i18n('global.delete')+'>';
			}
			str += '<input class="btn btn-default btn-xs permission" data-toggle="modal" data-backdrop="static" data-target="#modalDiv" type="button" value="'+i18n('role.setPermission')+'">';
			return str;
		}}
	];
	$('#role-dataTable').createTable({
		url : ctx + '/role/data',
		columns : columns,
		serverParems : function(data){
			data.push(
				{ name: "name", value: $('#name').val()}
			)
		}
	});
}

function _edit(){
	$('#role-dataTable tbody').on('click', '.editrow', function () {
		var roleId=$(this).parents('tr').find('.roleId').text();
		$(this).attr('href',ctx+'/role/edit?roleId='+roleId);
	});
}

function _permission(){
	$('#role-dataTable tbody').on('click', '.permission', function () {
		var roleId=$(this).parents('tr').find('.roleId').text();
		$(this).attr('href',ctx+'/role/permission?roleId='+roleId);
	});
}

function savePermission() {
	var params = $('#permissionForm').serialize();
	$.post(ctx+'/role/savePermissions', params, function(data) {
		if (data=='success') {
			alert(i18n('role.setPermissionSuccess'));
			$('#modalDiv').modal('hide');
		} else {
			alert(i18n('role.setPermissionFail'));
		}
	})
}

function _doDelete(){
	$('#role-dataTable tbody').on( 'click', '.delrow', function () {
		var flag=window.confirm(i18n('global.deleteConfirm'));
		if(flag){
			var roleId=$(this).parents('tr').find('.roleId').text();
			window.location.href=ctx + '/role/doDelete?roleId='+roleId;
		}
	});
}

function rightMove(){  
    var brand_sel = document.getElementById("brand_sel");  
    var choose_sel = document.getElementById("choose_sel");  
      
    var brand_options = brand_sel.options;  
    var s = choose_sel.options.length;  
    for(var i=0;i<brand_options.length;i++){  
        var is_selected = brand_options[i].selected;  
        if(is_selected){  
             var option  = new Option(brand_options[i].text  ,brand_options[i].value);   
             if(!contains(choose_sel,option)){  
                choose_sel.options[s++] = new Option(brand_options[i].text ,brand_options[i].value);  
            }  
        }  
    }  
}  


function leftMove(){  
    $("#choose_sel>option").each(function(){  
        var option= $(this);  
        if(option.attr("selected")){  
            option.remove();  
        }  
    });  
}  

function clean(){  
    
    $("#choose_sel>option").each(function(){  
         $(this).remove();  
    });  
      
}  
   
function selectChoose(){  
    $("#choose_sel>option").attr("selected","true");  
    return true;  
}  

function  contains(obj_sel,option){  
    var options = obj_sel.options;  
    for(var i=0;i<options.length;i++){  
        if(options[i].value == option.value){  
            return true;  
        }  
    }  
      
    return false;  
}  

function saveSetProjects(){
	var userCode = $("#userCode").val();
	var projectCodes = $("#choose_sel").val();
	$.ajax({
		url:ctx + '/user/saveSetProjects?projectCodes=' + projectCodes + '&userCode=' + userCode,
		success : function(data){
			alert("给用户设置项目成功！");
			window.location.reload();
		}
	});
}