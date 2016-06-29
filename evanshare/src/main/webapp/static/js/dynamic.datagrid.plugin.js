/**
 * 动态可编辑的 Data Grid Plugin
 */
;$.fn.extend({ dynamicDataGrid: function(){
	return this.each(function(){
		var $table = $(this).css("clear","both"), $tbody = $table.find("tbody");
		var fields=[];

		$table.find("tr:first th[type]").each(function(i){
			var $th = $(this);
			var field = {
				type: $th.attr("type") || "text",
				patternDate: $th.attr("dateFmt") || "yyyy-MM-dd",
				name: $th.attr("name") || "",
				defaultVal: $th.attr("defaultVal") || "",
				size: $th.attr("size") || "12",
				enumUrl: $th.attr("enumUrl") || "",
				lookupGroup: $th.attr("lookupGroup") || "",
				lookupUrl: $th.attr("lookupUrl") || "",
				lookupPk: $th.attr("lookupPk") || "id",
				suggestUrl: $th.attr("suggestUrl"),
				suggestFields: $th.attr("suggestFields"),
				postField: $th.attr("postField") || "",
				fieldClass: $th.attr("fieldClass") || "",
				fieldAttrs: $th.attr("fieldAttrs") || "",
				options: $th.attr("options") || ""
			};
			fields.push(field);
		});

		$tbody.find("a.btnDel").click(function(){
			var $btnDel = $(this);
			if($btnDel.attr("id")=="isConfirm"){
				alertMsg.confirm("确定要删除该记录?", {	
					okCall: function(){
						if ($btnDel.is("[href^=javascript:]")){
							$btnDel.parents("tr:first").remove();
							initSuffix($tbody);
							$table.trigger('delRow', $btnDel.parents("tr:first"));
							return false;
						}
						
						function delDbData(){
							$.ajax({
								type:'POST', dataType:"json", url:$btnDel.attr('href'), cache: false,
								success: function(){
									$btnDel.parents("tr:first").remove();
									initSuffix($tbody);
									$table.trigger('delRow', $btnDel.parents("tr:first"));
								},
								error: DWZ.ajaxError
							});
						}

						if ($btnDel.attr("title")){
							alertMsg.confirm($btnDel.attr("title"), {okCall: delDbData});
						} else {
							delDbData();
						}

						return false;
					}
				});
				
			}else{
				if ($btnDel.is("[href^=javascript:]")){
					$btnDel.parents("tr:first").remove();
					initSuffix($tbody);
					$table.trigger('delRow', $btnDel.parents("tr:first"));
					return false;
				}
				
				function delDbData(){
					$.ajax({
						type:'POST', dataType:"json", url:$btnDel.attr('href'), cache: false,
						success: function(){
							$btnDel.parents("tr:first").remove();
							initSuffix($tbody);
							$table.trigger('delRow', $btnDel.parents("tr:first"));
						},
						error: DWZ.ajaxError
					});
				}

				if ($btnDel.attr("title")){
					alertMsg.confirm($btnDel.attr("title"), {okCall: delDbData});
				} else {
					delDbData();
				}

				return false;
			}

			
		});

		var $addBut = $table.prev('.panelBar').find('.add');
		//var $addBut = $('<div class="button"><div class="buttonContent"><button type="button">'+addButTxt+'</button></div></div>').insertBefore($table).find("button");
		$addBut.unbind();
		var trTm = "";
		$addBut.unbind();//动态表单添加出现2行问题
		$addBut.click(function(){
			if (! trTm) trTm = trHtml(fields);
			var rowNum = 1;

			for (var i=0; i<rowNum; i++){
				var $tr = $(trTm),
					$totalbar = $tbody.children('.totalbar');
				$table.trigger('addRow', $tr);	//触发添加行方法
				if ( $totalbar.size() ) {
					$tr.insertBefore($totalbar);
				} else {
					$tr.appendTo($tbody);
				}
				$tr.initUI().find("a.btnDel").click(function(){
					$(this).parents("tr:first").remove();
					initSuffix($tbody);
					return false;
				});
			}
			initSuffix($tbody);
		});

		//initCount
		var _initCount = parseInt($table.attr('initCount') || 1);
		for (var i=0; i<_initCount; i++) {
			$addBut.trigger('click');
		}

	});

	/**
	 * 删除时重新初始化下标
	 */
	function initSuffix($tbody) {
		$tbody.find('>tr').each(function(i){
			$(':input, a.btnLook, a.btnAttach', this).each(function(){
				var $this = $(this), name = $this.attr('name'), val = $this.val();

				if (name) $this.attr('name', name.replaceSuffix(i));

				var lookupGroup = $this.attr('lookupGroup');
				if (lookupGroup) {$this.attr('lookupGroup', lookupGroup.replaceSuffix(i));}

				var suffix = $this.attr("suffix");
				if (suffix) {$this.attr('suffix', suffix.replaceSuffix(i));}

				if (val && val.indexOf("#index#") >= 0) $this.val(val.replace('#index#',i+1));
			});
		});
	}

	function tdHtml(field){
		var html = '', suffix = '';
		if (field.name.endsWith("[#index#]")) suffix = "[#index#]";
		else if (field.name.endsWith("[]")) suffix = "[]";

		var suffixFrag = suffix ? ' suffix="' + suffix + '" ' : '';

		var attrFrag = '';
		if (field.fieldAttrs){
			var attrs = DWZ.jsonEval(field.fieldAttrs);
			for (var key in attrs) {
				if(key!=''){
					attrFrag += key+'="'+attrs[key]+'"';
				}
			}
		}
		switch(field.type){
			case 'del':
				html = '<a href="javascript:void(0)" class="btnDel '+ field.fieldClass + '">删除</a>';
				break;
			case 'lookup':
				var suggestFrag = '';
				if (field.suggestFields) {
					suggestFrag = 'autocomplete="off" lookupGroup="'+field.lookupGroup+'"'+suffixFrag+' suggestUrl="'+field.suggestUrl+'" suggestFields="'+field.suggestFields+'"' + ' postField="'+field.postField+'"';
				}

				html = '<input type="hidden" name="'+field.lookupGroup+'.'+field.lookupPk+suffix+'"/>'
					+ '<input type="text" name="'+field.name+'"'+suggestFrag+' lookupPk="'+field.lookupPk+'" size="'+field.size+'" class="'+field.fieldClass+'"/>'
					+ '<a class="btnLook" href="'+field.lookupUrl+'" lookupGroup="'+field.lookupGroup+'" '+suggestFrag+' lookupPk="'+field.lookupPk+'" title="查找带回">查找带回</a>';
				break;
			case 'attach':
				html = '<input type="hidden" name="'+field.lookupGroup+'.'+field.lookupPk+suffix+'"/>'
					+ '<input type="text" name="'+field.name+'" size="'+field.size+'" readonly="readonly" class="'+field.fieldClass+'"/>'
					+ '<a class="btnAttach" href="'+field.lookupUrl+'" lookupGroup="'+field.lookupGroup+'" '+suffixFrag+' lookupPk="'+field.lookupPk+'" width="560" height="300" title="查找带回">查找带回</a>';
				break;
			case 'enum':
				$.ajax({
					type:"POST", dataType:"html", async: false,
					url:field.enumUrl,
					data:{inputName:field.name},
					success:function(response){
						html = response;
					}
				});
				break;
			case 'date':
				html = '<input type="text" name="'+field.name+'" value="'+field.defaultVal+'" class="date '+field.fieldClass+'" dateFmt="'+field.patternDate+'" size="'+field.size+'" readonly="readonly" '+attrFrag+'/>'
					+'<a class="inputDateButton" href="javascript:void(0)">选择</a>';
				break;
			case 'delThisRow':
				html = '<a   '+attrFrag+' class="'+field.fieldClass+'"  href="javascript:void(0)"  >删除</a>';
				break;

			case 'select':
				var options = field.options.split('|'),
					optionsHtml = [];

				$.each(options, function(idx, val) {
					if (!val) return true;
					var option = val.split(':');
					if (field.defaultVal == option[1]) {
						optionsHtml.push('<option value="'+option[1].trim()+'" selected>'+option[0].trim()+'</option>');
					} else {
						optionsHtml.push('<option value="'+option[1].trim()+'">'+option[0].trim()+'</option>');
					}
				});

				html = '<select ' + attrFrag + 'name="'+field.name+'" class="combox '+field.fieldClass+'">' + optionsHtml.join('') + '</select>';
				break;
			case 'textarea':
				html = '<textarea   name="'+field.name+'" value="'+field.defaultVal+'" size="'+field.size+'" class="'+field.fieldClass+'" '+attrFrag+'></textarea>';
				break;
			default:
				html = '<input type="'+field.type+'" name="'+field.name+'" value="'+field.defaultVal+'" size="'+field.size+'" class="'+field.fieldClass+'" '+attrFrag+'/>';
				break;
		}
		return '<td>'+html+'</td>';
	}
	function trHtml(fields){
		var html = '';
		$(fields).each(function(){
			html += tdHtml(this);
		});
		return '<tr class="unitBox">'+html+'</tr>';
	}
}
});