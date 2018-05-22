$(function(){
	$('.formDiv form input[type="text"]').attr('readonly',true);
	$('#basicInfoForm').validation();
});

function editInfo(n){
	$('#saveTr').show();
	$('#editTr').hide();
	$('.formDiv form input[type="text"]').removeAttr('readonly');
}

function cancel(){
	$('#saveTr').hide();
	$('#editTr').show();
	$('.formDiv form input[type="text"]').attr('readonly',true);
}

function clearInfo() {
	$('.info #w').val('');
	$('.info #h').val('');
};

function updateInfo(e) {
	$('#x').val(parseInt(e.x));
	$('#y').val(parseInt(e.y));
	$('#w').val(parseInt(e.w));
	$('#h').val(parseInt(e.h));
};

function fileSelectHandler() {
	var width=$(window).width();
	var height=$(window).height();
	$('#imgContent').html('');
	$('#imgContent').html('<img id="img"/>');
	var oFile = $('#image_file')[0].files[0];
	var oImage = document.getElementById('img');
	var oReader = new FileReader();
	oReader.onload = function(e) {
		oImage.src = e.target.result;
		var width = oImage.width;
		var height = oImage.height;
		if(width>500 || height>500){
			if(width>height){
				for ( var i = 1; i < 20; i++) {
					if ((width / i) <= 500) {
						oImage.width = width/i;
						oImage.height = height/i;
						break;
					}
				}
			}else{
				for ( var i = 1; i < 20; i++) {
					if ((height / i) <= 500) {
						oImage.width = width/i;
						oImage.height = height/i;
						break;
					}
				}
			}
		}else{
			if(width>height){
				for ( var i = 1; i < 20; i++) {
					if ((width * i) >= 400) {
						oImage.width = width*i;
						oImage.height = height*i;
						break;
					}
				}
			}else{
				for ( var i = 1; i < 20; i++) {
					if ((height * i) >= 400) {
						oImage.width = width*i;
						oImage.height = height*i;
						break;
					}
				}
			}
		}
		$('#imgDialog').width(oImage.width);
		oImage.onload = function() {
			$('.step2').fadeIn(500);
		};
		$('#img').Jcrop({
			bgFade : true,
			aspectRatio : 1,
			keySupport : false,
			trackDocument: true,
			onChange : updateInfo,
			onSelect : updateInfo,
			onRelease : clearInfo
		});
		$('#imgButton').click();
	}
	oReader.readAsDataURL(oFile);
}