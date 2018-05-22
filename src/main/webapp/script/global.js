$(function(){
	var a =0;
	var roleId = $("#rid").val();
	if(roleId==1 || roleId==6){
		$.ajax({
			url:ctx+'/project/queryCount',
			type:'post',    
	   		cache:false,
	   		dataType:'json',
	   		async: false,
	   		success:function(data) { 
					a = data;
	   		}
		});
		if(a==0){ 
			document.getElementById("divObj").innerHTML= "" ;
		}else{
			document.getElementById("divObj").innerHTML= a ;
		}
		
		var query = function(){
			$.ajax({
				url:ctx+'/project/queryCount',
				type:'post',    
		   		cache:false,
		   		dataType:'json',
		   		async: false,
		   		success:function(data) { 
						a = data;
		   		}
			});
			if(a==0){
				document.getElementById("divObj").innerHTML= "" ;
			}else{
				document.getElementById("divObj").innerHTML= a ;
			}
		}
		var time = setInterval(query, 60000);
	}
 
})
$(function(){
	_setHeight();
	$(window).resize(_windowResize);
	$('#WkLeaveCh').validation();
	$('#WkOverCh').validation();
	$('#ProjectFeeCh').validation();
	$('#badApply').validation();
	$('#prProjectForm').validation();
	$('#collectMoneyForm').validation();
	$('#applyBorrowForm').validation();

	
	$('.clear_date').datetimepicker({
		startDate: currentDate,
		weekStart: 1,
		todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
	});
});

function _windowResize() {
	_setHeight();
	_setChartWith();
}

$.fn.createTable = function(settings){
	$(this).dataTable({
		responsive : true,
		bStateSave : false,
		bSort : false,
		bAutoWidth : false,
		bProcessing: true,
		bServerSide: true,
		bLengthChange : false,
		bFilter : false,
		sPaginationType : 'full_numbers',
		sServerMethod: "POST",
		oLanguage : {
			'sLengthMenu' : i18n('global.sLengthMenu'),
			'sZeroRecords' : i18n('global.sZeroRecords'),
			'sInfo' : i18n('global.sInfo'),
			'sInfoEmpty' : i18n('global.sInfoEmtpy'),
			'sInfoFiltered' : i18n('global.sInfoFiltered'),
			'sProcessing' : i18n('global.sProcessing'),
			'sSearch' : i18n('global.sSearch'),
			'oPaginate' : {
				'sFirst' : i18n('global.sFirst'),
				'sPrevious' : i18n('global.sPrevious'),
				'sNext' : i18n('global.sNext'),
				'sLast' : i18n('global.sLast')
			}
		},
		sAjaxSource: settings.url,
		fnServerParams : settings.serverParems,
		aoColumns: settings.columns,
		fnDrawCallback: function(){
			var odd=$(this).find($('.odd')).text();
			if(odd==i18n('global.sZeroRecords')){
				if(settings.columnsNum!=undefined){
					$(this).find($('.odd td').eq(0).attr('colspan',settings.columnsNum));
				}
			}
			if(settings.hiddenRowId!=undefined){
				var temp=settings.hiddenRowId.split(',');
				for ( var i = 0; i < temp.length; i++) {
					$('.'+temp[i]).hide();
				}
			}
			if(settings.type!=undefined && settings.type!='' && settings.type==0){
				$('.'+settings.hiddenRowId).show();
			}
			_setHeight();
		}
	});
};

function search(tableId){
	var table = $('#'+tableId).dataTable();
	table.fnDraw();
}

function _setHeight(){
	var height = $(window).height();
	if ($('body').height() > height) {
		height = $('body').height();
	}
	$('#left-menu').height(height - 75);
	$('#left-menu').css('min-height', $('#side-menu').height() + 43);
	if ($(window).width() < 768) {
		$('#left-menu').hide();
	} else {
		$('#left-menu').show();
	}
}

function _setChartWith(){
	for ( var i = 0; i < myCharts.length; i++) {
		myCharts[i].resize();
	}
}

function getDate(data){
	var date = new Date(data);
	return date.getFullYear() + '-' + (date.getMonth()+1) + '-' + date.getDate();
}

function getStatus(data){
	var str = null;
	if(data!=null){
		if(data==0){
			str = i18n('global.audit');
		}else if(data==1){
			str = '<input type="hidden" value="'+data+'" class="approveStatus"/><span style="color:green;font-size:18px;">√&nbsp;&nbsp;</span>'+i18n('global.approve');
		}else if(data==2){
			str = '<span style="color:red;font-size:18px;">×&nbsp;&nbsp;</span>'+i18n('global.reject');
		}else if(data==3){
			str = i18n('global.invalid');
		}else if(data==4){
			str = i18n('global.closed');
		}
		str += '<input type="hidden" class="appStatus" value="'+data+'"/>';
	}
	return str;
}


function alert(str){
	var msgw,msgh,bordercolor;  
	msgw=350;//提示窗口的宽度  
	msgh=80;//提示窗口的高度  
	titleheight=25 //提示窗口标题高度  
	bordercolor="#336699";//提示窗口的边框颜色  
	titlecolor="#99CCFF";//提示窗口的标题颜色  
	var sWidth,sHeight;  
	//获取当前窗口尺寸  
	sWidth = document.body.offsetWidth;  
	sHeight = document.body.offsetHeight;  
//    //背景div  
	var bgObj=document.createElement("div");  
	bgObj.setAttribute('id','alertbgDiv');  
	bgObj.style.position="absolute";  
	bgObj.style.top="0";  
	bgObj.style.background="#E8E8E8";  
	bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";  
	bgObj.style.opacity="0.6";  
	bgObj.style.left="0";  
	bgObj.style.width = sWidth + "px";  
	bgObj.style.height = sHeight + "px";  
	bgObj.style.zIndex = "10000";  
	document.body.appendChild(bgObj);  
	//创建提示窗口的div  
	var msgObj = document.createElement("div")  
	msgObj.setAttribute("id","alertmsgDiv");  
	msgObj.setAttribute("align","center");  
	msgObj.style.background="white";  
	msgObj.style.border="1px solid " + bordercolor;  
	msgObj.style.position = "absolute";  
	msgObj.style.left = "50%";  
	msgObj.style.font="12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif";  
	//窗口距离左侧和顶端的距离   
	msgObj.style.marginLeft = "-225px";  
	//窗口被卷去的高+（屏幕可用工作区高/2）-150  
	msgObj.style.top = document.body.scrollTop+(window.screen.availHeight/2)-150 +"px";  
	msgObj.style.width = msgw + "px";  
	msgObj.style.height = msgh + "px";  
	msgObj.style.textAlign = "center";  
	msgObj.style.lineHeight ="25px";  
	msgObj.style.zIndex = "10001";  
	document.body.appendChild(msgObj);  
	//提示信息标题  
	var title=document.createElement("h4");  
	title.setAttribute("id","alertmsgTitle");  
	title.setAttribute("align","left");  
	title.style.margin="0";  
	title.style.padding="3px";  
	title.style.background = bordercolor;  
	title.style.filter="progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);";  
	title.style.opacity="0.75";  
	title.style.border="1px solid " + bordercolor;  
	title.style.height="22px";  
	title.style.font="12px Verdana, Geneva, Arial, Helvetica, sans-serif";  
	title.style.color="white";  
	title.innerHTML="提示信息";  
	document.getElementById("alertmsgDiv").appendChild(title);  
	//提示信息  
	var txt = document.createElement("p");  
	txt.setAttribute("id","msgTxt");  
	txt.style.margin="16px 0";  
	txt.innerHTML = str;  
	document.getElementById("alertmsgDiv").appendChild(txt);  
	//设置关闭时间  
	window.setTimeout("closewin()",1000);   
}
function closewin() {  
	document.body.removeChild(document.getElementById("alertbgDiv"));  
	document.getElementById("alertmsgDiv").removeChild(document.getElementById("alertmsgTitle"));  
	document.body.removeChild(document.getElementById("alertmsgDiv"));  
}
