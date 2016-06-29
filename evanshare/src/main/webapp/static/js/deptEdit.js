/**
 * 
 */
function dialogAjaxDone1(json){
	dialogAjaxDone(json);
	var frame = window.frames["OrgTreeIframe"];
	frame = frame.contentWindow || frame;
	frame.updateChildNodes();
	frame.updateNodesState(json.isenabled);
	if(json.deptId==1){
		frame.updateNodeName(json.newName);
	}
}
