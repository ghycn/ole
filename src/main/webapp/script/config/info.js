function edit(){
	$('#taxEditButton').hide();
	$('#taxInfo').hide();
	$('#taxEdit').show();
	$('#taxSaveButton').show();
}

function cancel(){
	$('#taxEditButton').show();
	$('#taxInfo').show();
	$('#taxEdit').hide();
	$('#taxSaveButton').hide();
}