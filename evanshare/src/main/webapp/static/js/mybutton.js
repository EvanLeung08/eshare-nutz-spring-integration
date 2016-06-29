(function ($) {

    var bShow = false;

    $.fn.extend({

        fnCanAnimate: function () {
                var browserInfo = window.navigator.userAgent.toLowerCase();
            if (browserInfo.indexOf("msie 6.0") != -1 || browserInfo.indexOf("msie 7.0") != -1) {
                bShow = false;
            } else {
                bShow = true;
            }
        },

        myButton: function () {
            /*plug-function---start*/
            return this.each(function () {

                var $this = $(this);
                var aCtrlCount = $this.find("a[target]").length;

                if (aCtrlCount == 1) {
                    $this.find("a[target]").wrap("<div class='button_control'><div class='button_select' style='text-align:center;'></div></div>");
                } else {
                    $this.find("a[target]").each(function () {
                        $(this).wrap("<li></li>");
                    });

                    $this.find("li").wrapAll("<ul class='button_list list_down'></ul>");
                    $this.find(".button_list").wrap("<div class='button_select'></div>");
                    $this.find(".button_select").wrap("<div class='button_control'></div>").prepend("<span class='guide'></span>").prepend("<span class='button_text'>操作选项</span>");
                        
                    $this.find(".button_select").unbind("mouseenter").live("mouseenter", function () {
                        
                        var oBtn = $(this).parent(".button_control");			//按钮插件对象
                        var oTr = oBtn.parent().parent();	                    //当前操作按钮所在的tr
                        var oUl = $(this).find(".button_list");		            //当前按钮的列表ul
                        var aLi = oUl.find("li");
                        
                        oBtn.css("zIndex",2 );
                        oBtn.addClass("btn_open");
                        
                        var oBox = $(this).parents(".gridScroller");
                        var scrollTop = oBox.scrollTop();
                        var maxHeight = oTr.position().top + scrollTop + oBtn.height() + oUl.height();
                        var scrollHeight = oBox[0].scrollHeight;
                        var aLiHeight = $(this).outerHeight() + 1;    //隐藏状态下获取不到li的高度，用button_select高度代替

                        if (maxHeight > scrollHeight) {
                            oUl.removeClass("list_down").addClass("list_left");
                            var oUlTop = -(aLiHeight) * (aLi.size() - 1) - 4;
                            oUl.css({ top: oUlTop });
                        } else {
                            oUl.removeClass("list_left").addClass("list_down");
                        }

                        if (!bShow) {
                            oUl.css({"opacity":1,"display":"block"});
                        } else {
                            oUl.css({ "left": -52 });
                            oUl.show().stop().animate({ "left": -72, "opacity": 1 }, "fast");
                        }

                    }).unbind("mouseleave").live("mouseleave", function () {
                        var oBtn = $(this).parent();					
                        var oUl = $(this).find(".button_list");	

                        oBtn.css("zIndex", 1);
                        if (!bShow) {
                            oBtn.removeClass("btn_open");
                            oUl.css({"opacity":0,"display":"none"});
                        } else {
                            oBtn.removeClass("btn_open");
                            oUl.stop().animate({"left":-62,"opacity":0},{complete:function(){
                                $(this).hide();
                            }},"fast");
                        }
                    });
                }

            });/*plug-function---end*/
        }
    });

})(jQuery);

$(document).fnCanAnimate();
