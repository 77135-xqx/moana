package lab.io.rush.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lab.io.rush.model.User;
import lab.io.rush.service.UserService;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.WeiboException;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/goto_weibo")
	public void goto_weibo(HttpServletResponse response){
		Oauth oauth = new Oauth();
		String weibo_url="";
		try {
			//获取微博登录界面的链接
			weibo_url = oauth.authorize("code",null);
		} catch (WeiboException e1) {
			e1.printStackTrace();
		}
		try {
			response.getWriter().print(weibo_url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/weibo_login")
	public void weibo_login(@RequestParam("code")String code,HttpSession session,HttpServletResponse response){
		Oauth oauth = new Oauth();
		try {
			AccessToken token = oauth.getAccessTokenByCode(code);
			User user = userService.getUserByWeiboID(token.getUid());
			
			//如果用户已经用微博登录过
			if(user!=null){
				//保存登录状态
				session.setAttribute("user", user);
				//如果用户已经绑定了邮箱，则进入订购页面
				if(user.getMail()!=null&&user.getMail().length()!=0){
					try {
						response.sendRedirect("/rush/main.html");
						return;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			//如果用户第一次用此微博登录，则新增用户
			else {
				Users users = new Users() ;	
		        users.client.setToken(token.getAccessToken()) ;
				weibo4j.model.User weibo_user = users.showUserById(token.getUid());
				user = new User();
				user.setWeibo_name(weibo_user.getScreenName());
				user.setWeibo_id(token.getUid());
				user = userService.addUser(user);
				//保存登录状态
				session.setAttribute("user", user);
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		try {
			response.sendRedirect("/rush/bind_mail.html");
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/submit_mail")
	public void submit_mail(@RequestParam("mail")String mail,HttpSession session,HttpServletResponse response){
		User user = (User) session.getAttribute("user");
		//如果已经登录微博
		if(user.getWeibo_id()!=null&&user.getWeibo_id().length()!=0){
			if(userService.bindMailByWeibo(mail, user.getWeibo_id())==true){
				try {
					//绑定成功进入购买主页
					response.sendRedirect("/rush/main.html");
					return;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else {
		}
		try {
			//绑定成功进入购买主页
			response.sendRedirect("/rush/login.html");
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}


