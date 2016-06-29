(function ($) {
    $.extend({
    	/** textarea输入内容在html中显示，将换行、空格等内容替换 */
    	replaceNowrap : function(content) {
    		//console.info(content);
    		content = content.replace(/\</g,"&lt");
    		content = content.replace(/\>/g,"&gt");
    		content = content.replace(/\n/g,"<br/>");
    		content = content.replace(/ /g,"&nbsp;");
    		return content;
        }
    });
})(jQuery);
