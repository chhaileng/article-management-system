$(document).ready(function() {
	$('#btnAdminMode').each(function(){
        if ($(this).is(':checked')) {
            $("#adminMode").show();
            $("#userMode").hide();
        }
    });

    $('input.pokazkontakt').click(function () {
        $(this).parent().nextAll('.pkbox:first').toggle('fast');
    });
	$("#btnAdminMode").click(function() {
		if ($('#btnAdminMode').attr('checked')) {
			/* console.log("Disable"); */
			$('#btnAdminMode').removeAttr('checked');
			$('#adminMode').hide();
			$('#userMode').fadeIn(500);
		} else {
			/* console.log("Enabled"); */
			$('#btnAdminMode').attr('checked','true');
			$('#adminMode').fadeIn(500);
			$('#userMode').hide();
		}
	});
});