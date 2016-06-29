$(function(){
  var $user=$("#login_form input.user");
  var $pass1=$("#pass1");
  var $pass2=$("#pass2");
  $user.click(function(){$user.attr("value","");$user.addClass("user1");})
	.blur(function(){
		if($user.val()){
			return;
		}else{
			$user.attr("value","请输入用户名");
			$user.removeClass("user1");
			}
		}); 
		
  $pass1.focus(function() {
    $pass1.hide();
    $pass2.css("display","inline-block").focus();
});
  $pass2.blur(function(){
		if($pass2.val()){
			return;
		}else{
			$pass2.hide();
			$pass1.show();
			}
		}); 
})