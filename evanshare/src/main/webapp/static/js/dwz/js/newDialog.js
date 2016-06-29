jQuery.extend(
			$.pdialog,
		//打开一个层
			{openPost:function(url, dlgid, title, options,param) {
				var op = $.extend({},$.pdialog._op, options);
				var dialog = $("body").data(dlgid);
				//重复打开一个层
				if(dialog) {
					if(dialog.is(":hidden")) {
						dialog.show();
					}
					if(op.fresh || url != $(dialog).data("url")){
						dialog.data("url",url);
						dialog.find(".dialogHeader").find("h1").html(title);
						this.switchDialog(dialog);
						var jDContent = dialog.find(".dialogContent");
						jDContent.loadUrl(url, param, function(){
							jDContent.find("[layoutH]").layoutH(jDContent);
							$(".pageContent", dialog).width($(dialog).width()-14);
							$("button.close").click(function(){
								$.pdialog.close(dialog);
								return false;
							});
						});
					}
				
				} else { //打开一个全新的层
				
					$("body").append(DWZ.frag["dialogFrag"]);
					dialog = $(">.dialog:last-child", "body");
					dialog.data("id",dlgid);
					dialog.data("url",url);
					if(options.close) dialog.data("close",options.close);
					if(options.param) dialog.data("param",options.param);
					($.fn.bgiframe && dialog.bgiframe());
					
					dialog.find(".dialogHeader").find("h1").html(title);
					$(dialog).css("zIndex", ($.pdialog._zIndex+=2));
					$("div.shadow").css("zIndex", $.pdialog._zIndex - 3).show();
					$.pdialog._init(dialog, options);
					$(dialog).click(function(){
						$.pdialog.switchDialog(dialog);
					});
					
					if(op.resizable)
						dialog.jresize();
					if(op.drawable)
					 	dialog.dialogDrag();
					$("a.close", dialog).click(function(event){ 
						$.pdialog.close(dialog);
						return false;
					});
					if (op.maxable) {
						$("a.maximize", dialog).show().click(function(event){
							$.pdialog.switchDialog(dialog);
							$.pdialog.maxsize(dialog);
							dialog.jresize("destroy").dialogDrag("destroy");
							return false;
						});
					} else {
						$("a.maximize", dialog).hide();
					}
					$("a.restore", dialog).click(function(event){
						$.pdialog.restore(dialog);
						dialog.jresize().dialogDrag();
						return false;
					});
					if (op.minable) {
						$("a.minimize", dialog).show().click(function(event){
							$.pdialog.minimize(dialog);
							return false;
						});
					} else {
						$("a.minimize", dialog).hide();
					}
					$("div.dialogHeader a", dialog).mousedown(function(){
						return false;
					});
					$("div.dialogHeader", dialog).dblclick(function(){
						if($("a.restore",dialog).is(":hidden"))
							$("a.maximize",dialog).trigger("click");
						else
							$("a.restore",dialog).trigger("click");
					});
					if(op.max) {
	//					$.pdialog.switchDialog(dialog);
						$.pdialog.maxsize(dialog);
						dialog.jresize("destroy").dialogDrag("destroy");
					}
					$("body").data(dlgid, dialog);
					$.pdialog._current = dialog;
					$.pdialog.attachShadow(dialog);
					//load data
					var jDContent = $(".dialogContent",dialog);
					jDContent.loadUrl(url, param, function(){
						jDContent.find("[layoutH]").layoutH(jDContent);
						$(".pageContent", dialog).width($(dialog).width()-14);
						$("button.close").click(function(){
							$.pdialog.close(dialog);
							return false;
						});
					});
				}
				if (op.mask) {
					$(dialog).css("zIndex", 1000);
					$("a.minimize",dialog).hide();
					$(dialog).data("mask", true);
					$("#dialogBackground").show();
				}else {
					//add a task to task bar
					if(op.minable) $.taskBar.addDialog(dlgid,title);
				}
			}
		}



);