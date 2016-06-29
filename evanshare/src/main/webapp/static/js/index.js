$(function(){ 
		DWZ.init("static/js/dwz/dwz.frag.xml", {
				//超时跳转到登录的URL
				 loginUrl:"login.jsp",
				 statusCode:{ok:200,info:201, error:300, timeout:301},
		 		 pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"},
				 debug:false,
				 callback:function(){
					initEnv();
					$("#themeList").theme({themeBase:"dwz/themes"});
					//在dwz渲染完页面后回调触发显示菜单
					setTimeout("openSelectedMenu()", 10);
			 	}
		});
		//客户端处理
		DWZ.ajaxError = function(xhr, ajaxOptions, thrownError){
			alertMsg.info('请求出现问题！请刷新页面，重新操作。<br/>严重问题，请联系系统维护人员。<br/>反馈邮箱：');
		};
});

/** 触发显示菜单 */
function openSelectedMenu(){
	$("#navMenu ul li:first a ").click();
	$("#navMenu ul li:first ").addClass("selected");
}

/**  */
window._initUICallbacks = {};


/**
 * 当前打开面板容器jQuery选择器
 * @param selector 选择符
 * @returns jQuery对象
 */
var _$ = function(selector) {
	return $(selector, navTab.getCurrentPanel());
};

$(function(){
	$('body').delegate('[toFire]', 'click', function() {
		var self = $(this),
			selector = self.attr('toFire');
		
		if (/^\+/.test(selector)) {
			self.siblings(selector.replace(/^./, '')).trigger('click');
		} else if (/^\>/.test(selector)) {
			self.children(selector.replace(/^./, '')).trigger('click');
		} else if (/^\^/.test(selector)) {
			self.parent().find(selector).replace(/^./, '').trigger('click');
		} else {
			_$(selector).trigger('click');
		}
	});
});


/**
 * 扩展String方法
 */
$.extend(String.prototype, {
	isPositiveInteger:function(){
		return (new RegExp(/^[1-9]\d*$/).test(this));
	},
	isInteger:function(){
		return (new RegExp(/^\d+$/).test(this));
	},
	isNumber: function(value, element) {
		return (new RegExp(/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/).test(this));
	},
	trim:function(){
		return this.replace(/(^\s*)|(\s*$)|\r|\n/g, "");
	},
	startsWith:function (pattern){
		return this.indexOf(pattern) === 0;
	},
	endsWith:function(pattern) {
		var d = this.length - pattern.length;
		return d >= 0 && this.lastIndexOf(pattern) === d;
	},
	replaceSuffix:function(index){
		return this.replace(/\[[0-9]+\]/,'['+index+']').replace('#index#',index);
	},
	trans:function(){
		return this.replace(/&lt;/g, '<').replace(/&gt;/g,'>').replace(/&quot;/g, '"');
	},
	encodeTXT: function(){
		return (this).replaceAll('&', '&amp;').replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll(" ", "&nbsp;");
	},
	replaceAll:function(os, ns){
		return this.replace(new RegExp(os,"gm"),ns);
	},
	replaceTm:function($data){
		if (!$data) return this;
		return this.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_]*})","g"), function($1){
			return $data[$1.replace(/[{}]+/g, "")];
		});
	},
	replaceTmById:function(_box){
		var $parent = _box || $(document);
		return this.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_]*})","g"), function($1){
			var $input = $parent.find("#"+$1.replace(/[{}]+/g, ""));
			return $input.val() ? $input.val() : $1;
		});
	},
	isFinishedTm:function(){
		return !(new RegExp("{[A-Za-z_]+[A-Za-z0-9_]*}").test(this)); 
	},
	skipChar:function(ch) {
		if (!this || this.length===0) {return '';}
		if (this.charAt(0)===ch) {return this.substring(1).skipChar(ch);}
		return this;
	},
	isValidPwd:function() {
		return (new RegExp(/^([_]|[a-zA-Z0-9]){6,32}$/).test(this)); 
	},
	isValidMail:function(){
		return(new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(this.trim()));
	},
	isSpaces:function() {
		for(var i=0; i<this.length; i+=1) {
			var ch = this.charAt(i);
			if (ch!=' '&& ch!="\n" && ch!="\t" && ch!="\r") {return false;}
		}
		return true;
	},
	isPhone:function() {
		return (new RegExp(/(^([0-9]{3,4}[-])?\d{3,8}(-\d{1,6})?$)|(^\([0-9]{3,4}\)\d{3,8}(\(\d{1,6}\))?$)|(^\d{3,8}$)/).test(this));
	},
	isMobile:function() {
		return (new RegExp(/^(0|86|17951)?(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$/).test(this));
	},
	isUrl:function(){
		return (new RegExp(/^[a-zA-z]+:\/\/([a-zA-Z0-9\-\.]+)([-\w .\/?%&=:]*)$/).test(this));
	},
	isExternalUrl:function(){
		return this.isUrl() && this.indexOf("://"+document.domain) == -1;
	}
});

$(function(){
	
	_initUICallbacks.processDataGridStyle = function (panel) {
		$('.grid .gridTbody', this).each(function(){
			$(this).find('tr:odd').addClass('even');
			$(this).find('tr:even').addClass('odd');
		});
	};
	
});

/** 扩展jquery-validate */
$.validator.addMethod("digits_",function(value,element){
    var score = /^[-]{0,1}[0-9]{1,}$/;;
    return this.optional(element) || (score.test(value));
},"<font color='#E47068'>请输入整数</font>");
