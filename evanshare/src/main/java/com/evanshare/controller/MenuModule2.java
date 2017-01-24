package com.evanshare.controller;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.view.JspView;

import com.evanshare.bean.User;

@IocBean
@At("/menu")
@Ok("json:{locked:'password|salt',ignoreNull:true}")
@Fail("http:500")
public class MenuModule2 extends BaseModule{
	/**
	 * 获取用户列表
	 * @param session
	 * @return
	 */
	@At("/sidebar_1")
	public View list(HttpSession session){
		
		return new JspView("jsp/common/sidebar_1");
	}



}
