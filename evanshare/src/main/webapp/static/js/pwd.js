    var submitFlag=true; 
    var msg="密码过于简单,请重新设置。";
	$('#tbPassword').focus(function () {
            $('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
            $('#tbPassword').keyup();
            
        }).blur(function(){
        		var str=$(this).val();
           	 	if(''==str||null==str){
            		return false;
            	}
        	   if(submitFlag){
        		   if(verificationPsw(document.getElementById("oldPassWord"))){
	    		  	$("#submitButton").removeAttr("disabled", "disabled");
	               	$("#submitButton").parents(".buttonDisabled").attr("class","buttonActive");
        		   }
               }else{
        		alertMsg.info(msg);
        		$("#submitButton").attr("disabled", "disabled");
               	$("#submitButton").parents(".buttonActive").attr("class", "buttonDisabled");
               }
        });
        $('#tbPassword').keyup(function () {
            var __th = $(this);

            if (!__th.val()) {
                $('#pwd_tip').hide();
                $('#pwd_err').show();
                Primary();
                return;
            }
            if (__th.val().length < 6) {
                msg="密码长度至少6-16位。";
                submitFlag=false;
                $('#pwd_tip').hide();
                $('#pwd_err').show();
                Weak();
                return;
            }
            //验证特殊密码 如:mao123，mao99999,KKKKKKK
            submitFlag=check($(this));
            if (!submitFlag) {
				return ;
			}
            var _r = checkPassword(__th);
            if (_r < 1) {
                $('#pwd_tip').hide();
                $('#pwd_err').show();
                Primary();
                return;
            }

            if (_r > 0 && _r < 2) {
                Weak();
                msg="密码强度太弱";
                submitFlag=false;
            } else if (_r >= 2 && _r < 4) {
                Medium();
            } else if (_r >= 4) {
                Tough();
            }
            
            $('#pwd_tip').hide();
            $('#pwd_err').hide();
            
        });

     
        function Primary() {
            $('#pwdLevel_1').attr('class', 'ywz_zhuce_huixian');
            $('#pwdLevel_2').attr('class', 'ywz_zhuce_huixian');
            $('#pwdLevel_3').attr('class', 'ywz_zhuce_huixian');
        }

        function Weak() {
            $('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
            $('#pwdLevel_2').attr('class', 'ywz_zhuce_huixian');
            $('#pwdLevel_3').attr('class', 'ywz_zhuce_huixian');
        }

        function Medium() {
            $('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
            $('#pwdLevel_2').attr('class', 'ywz_zhuce_hongxian2');
            $('#pwdLevel_3').attr('class', 'ywz_zhuce_huixian');
        }

        function Tough() {
            $('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
            $('#pwdLevel_2').attr('class', 'ywz_zhuce_hongxian2');
            $('#pwdLevel_3').attr('class', 'ywz_zhuce_hongxian3');
        }



        /**
         * 验证密码类型
         * @param pwdinput
         * @returns {Number}
         */
        function checkPassword(pwdinput) {
            var maths, smalls, bigs, corps, cat, num;
            var str = $(pwdinput).val()
            var len = str.length;

            var cat = /.{16}/g
            if (len == 0) return 1;
            if (len > 16) { $(pwdinput).val(str.match(cat)[0]); }
            cat = /.*[\u4e00-\u9fa5]+.*$/
            if (cat.test(str)) {
                return -1;
            }
            cat = /\d/;
            var maths = cat.test(str);
            cat = /[a-z]/;
            var smalls = cat.test(str);
            cat = /[A-Z]/;
            var bigs = cat.test(str);
            var corps = corpses(pwdinput);
            var num = maths + smalls + bigs + corps;

            if (len < 6) { return 1; }

            if (len >= 6 && len <= 8) {
                if (num == 1) return 1;
                if (num == 2 || num == 3) return 2;
                if (num == 4) return 3;
            }

            if (len > 8 && len <= 11) {
                if (num == 1) return 2;
                if (num == 2) return 3;
                if (num == 3) return 4;
                if (num == 4) return 5;
            }

            if (len > 11) {
                if (num == 1) return 3;
                if (num == 2) return 4;
                if (num > 2) return 5;
            }
        }
        
        function corpses(pwdinput) {
            var cat = /./g
            var str = $(pwdinput).val();
            var sz = str.match(cat)
            for (var i = 0; i < sz.length; i++) {
                cat = /\d/;
                maths_01 = cat.test(sz[i]);
                cat = /[a-z]/;
                smalls_01 = cat.test(sz[i]);
                cat = /[A-Z]/;
                bigs_01 = cat.test(sz[i]);
                if (!maths_01 && !smalls_01 && !bigs_01) { return true; }
            }
            return false;
 }
        
        /**
         * 验证特殊密码 如:mao123，mao99999,KKKKKKK
         * @param jqueryObj
         */
        function check(jqueryObj){
        	var str = jqueryObj.val(); 
        	var arr=str.split('');
        	var count=0;
        	var num=0;
        	var pattern = /\d/; 
        	
        	for(var i=0;i<arr.length;i++){
        	  if(i!=0 && arr[i-1]==arr[i]){
        		count++;
        		if(count>=2){
        			msg="重复字符太多";
        			return false;
    				console.log("重复字符太多");
        		}
        	  }
    	  	if(i!=0 && pattern.test(arr[i]) && pattern.test(arr[i-1])){
    		 if(arr[i]-arr[i-1]==1){
    		  num++;
    		  if(num>=2){
    			  msg="不能有连续数字";
    			  return false;
    			  console.log("不能有连续数字");
    		  }
    		 }
    		 }
        	 }
        	
        	return true;
        	} 
