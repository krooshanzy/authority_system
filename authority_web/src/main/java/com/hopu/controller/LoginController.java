package com.hopu.controller;

import com.hopu.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @PostMapping("/user/login")
    public String login(User user, HttpServletRequest request) {
        // 登录校验
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();

        try {
            // shiro登录处理
            subject.login(token);
            // 用户放在session中
            HttpSession session = WebUtils.toHttp(request).getSession();
            User principal = (User) subject.getPrincipal();
            session.setAttribute("user",principal);
            // 登录成功，跳转到后台首页
            return "admin/index";
//            response.sendRedirect("/admin/index");
//            return "redirect:/admin/index";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            String msg = "账户["+ token.getPrincipal() + "]的用户名或密码错误！";
            request.setAttribute("msg", msg);
            return "forward:/login.jsp";
        }
    }

//    @GetMapping("/admin/index")
//    public String index() {
//        return "admin/index";
//    }


//    //退出
//    @RequestMapping(value = "/logout",name="用户登出")
//    public String logout(){
//        return "forward:/login.jsp";
//    }
}
