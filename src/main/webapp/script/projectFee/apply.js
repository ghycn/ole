$(document).ready(function() {
	getTypes();
});

function getTypes(){
	var quotationId=$('#quotations').val();
	$.ajax({
		type: "POST",
		dataType: "json",
		data : {'quotationId':quotationId},
		url: ctx + '/projectFee/apply/getTypesData',
		success:function(data){
			$('.optgroup').find('option').remove();
			for ( var i = 0; i < data.length; i++) {
				$('.optgroup').append('<option value="'+data[i].quotationListId+'">0'+data[i].category+'</option>');
				if(data[i].children!=null){
					for ( var j = 0; j < data[i].children.length; j++) {
						$('.optgroup').append('<option value="'+data[i].children[j].quotationListId+'">1'+data[i].children[j].category+'</option>');
					}
				}
			}
			_createSelect();
		}
	});
}

function _createSelect(){
	$('#types').multiselect({
		buttonWidth: 413,
		buttonText: function(options, select) {
			if (options.length == 0) {
				return this.nonSelectedText;
			}else {
				if (options.length > this.numberDisplayed) {
					return options.length + ' ' + this.nSelectedText;
				}else {
					var selected = '';
					options.each(function() {
					var label = ($(this).attr('label') !== undefined) ? $(this).attr('label') : $(this).html();
						var pre = label.substr(0,1);
						if(pre=='0' || pre=='1'){
							selected += label.substr(1) + ', ';
						}else{
							selected += label + ', ';
						}
					});
					return selected.substr(0, selected.length - 2);
				}
			}
		}
	});
	$('#types').multiselect('rebuild');
}