package com.evanshare.module;

import com.evanshare.bean.User;
import com.evanshare.entity.PagerModel;
import com.evanshare.service.UserService;
import com.evanshare.spring.service.SpUserService;
import org.nutz.dao.Cnd;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.view.JspView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 用户处理类
 */
@IocBean
@At("/user")
@Ok("json:{locked:'password|salt',ignoreNull:true}")
@Fail("http:500")
public class UserModule extends BaseModule {
    @Inject
    private UserService userService;
	//注入spring service
	@Autowired
	private SpUserService spUserService;
	/**
	 * 获取用户总数
	 * @return
     */
	@At
	public int count() {
		return userService.getTotalUserCount();
	}

	/**
	 * 用户登录
	 * 
	 * @param name
	 * @param password
	 * @param session
	 * @return
	 */
	@At
	public View login(@Param("username") String name, @Param("password") String password, HttpSession session) {
		User user = userService.findByNameAndPsw(name,password);
		if (user == null) {
			return new JspView("/page/error/500");
		} else {
			session.setAttribute("user", user);
			return new JspView("jsp/common/index_menu");
		}
	}

	/**
	 * 获取用户列表
	 * 
	 * @param session
	 * @return
	 */
	@At("/list")
	public View list(@Param("pageNum") int pageNum,@Param("numPerPage") int numPerPage ,@Param("keyword") String keyword,HttpSession session,HttpServletRequest req) {
	     QueryResult result  = userService.getUserList(pageNum, numPerPage,keyword);
	    List<User> users =  result.getList(User.class);
	    PagerModel pager = new PagerModel();
	    pager.setCurrentPage(pageNum);
	    pager.setNumPerPage(numPerPage);
	    pager.setPageNumShown(result.getPager().getPageSize());
	    pager.setTotalCount(result.getPager().getRecordCount());
	    pager.setPageSize(result.getPager().getPageSize());
	    req.setAttribute("users", users);
	    req.setAttribute("pager", pager);
		return new JspView("jsp/user/user_list");
	}

	@At
	@Ok("json")
	public Object add(@Param("::user.") User user) {
		NutMap re = new NutMap();
		//添加用户
		userService.addUser(user);
		//通过spring service 去添加用户
		//spUserService.addUser(user);
		return re.setv("statusCode", "200")
				.setv("message", "添加成功")
				.setv("navTabId", "")
				.setv("rel", "")
				.setv("callbackType", "closeCurrent")
				.setv("forwardUrl", "")
				.setv("confirmMsg", "");

	}

	@At
	@Ok(">>:/")
	public void logout(HttpSession session) {
		session.invalidate();

	}

	@At("/toRegister")
	@Ok(">>:/register.jsp")
	public void toRegister() {
		System.out.println("toRegister");
	}

	
	@At("/toAdd")
	public View toAdd(HttpSession session) {
		return new JspView("jsp/user/user_add");
	}
	

	@At
	public Object update(@Param("..") User user) {
		NutMap re = new NutMap();
		String msg = checkUser(user, false);
		if (msg != null) {
			return re.setv("ok", false).setv("msg", msg);
		}
		user.setName(null);// 不允许更新用户名
		user.setCreateTime(null);// 也不允许更新创建时间
		user.setUpdateTime(new Date());// 设置正确的更新时间
		dao.updateIgnoreNull(user);// 真正更新的其实只有password和salt
		return re.setv("ok", true);
	}

	@At
	public Object delete(@Param("id") int id, @Attr("me") int me) {
		if (me == id) {
			return new NutMap().setv("ok", false).setv("msg", "不能删除该用户");
		}
		dao.delete(User.class, id);
		return new NutMap().setv("ok", true);
	}

	@At
	public Object query(@Param("name") String name, @Param("..") Pager pager) {
		Cnd cnd = Strings.isBlank(name) ? null : Cnd.where("name", "like", "%" + name + "%");
		QueryResult qr = new QueryResult();
		qr.setList(dao.query(User.class, cnd, pager));
		pager.setRecordCount(dao.count(User.class));
		qr.setPager(pager);
		return qr;

	}

	@At("/")
	@Ok(">>:/login.jsp") // 真实路径是 /WEB-INF/jsp/user/list.jsp
	public void index() {
	}

	protected String checkUser(User user, boolean create) {
		if (user == null) {
			return "空对象";
		}
		if (create) {
			if (Strings.isBlank(user.getName()) || Strings.isBlank(user.getPassword()))
				return "用户名/密码不能为空";
		} else {
			if (Strings.isBlank(user.getPassword()))
				return "密码不能为空";
		}
		String passwd = user.getPassword().trim();
		if (6 > passwd.length() || passwd.length() > 12) {
			return "密码长度错误";
		}
		user.setPassword(passwd);
		if (create) {
			int count = dao.count(User.class, Cnd.where("name", "=", user.getName()));
			if (count != 0) {
				return "用户名已经存在";
			}
		} else {
			if (user.getId() < 1) {
				return "用户Id非法";
			}
		}
		if (user.getName() != null)
			user.setName(user.getName().trim());
		return null;
	}

}
