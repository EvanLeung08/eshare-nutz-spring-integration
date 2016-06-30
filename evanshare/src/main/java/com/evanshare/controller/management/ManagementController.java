package com.evanshare.controller.management;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.view.JspView;

import com.evanshare.bean.User;
import com.evanshare.controller.BaseModule;
@IocBean
@At("/management")
@Ok("json:{locked:'password|salt',ignoreNull:true}")
@Fail("http:500")
public class ManagementController extends BaseModule {
	
	/**
	 * @param name
	 * @param password
	 * @param session
	 * @return
	 */
	@At
	@Filters
	public View login(@Param("username") String name, @Param("password") String password, HttpSession session) {
		User user = dao.fetch(User.class, Cnd.where("name", "=", name).and("password", "=", password));
		if (user == null) {
		    return new JspView( "/page/error/500" );

		} else {
			session.setAttribute("me", user.getId());
			return new JspView( "/page/common/index_menu" );
		}
	}
	
	@At("/")
	public View index() {
		
		return new JspView( "/page/management/login" );
	}

}
