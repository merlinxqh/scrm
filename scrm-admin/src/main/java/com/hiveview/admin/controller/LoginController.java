package com.hiveview.admin.controller;

import com.hiveview.admin.commom.BaseController;
import com.hiveview.admin.commom.security.FormAuthenticationFilter;
import com.hiveview.admin.commom.util.StringUtils;
import com.hiveview.admin.commom.SystemUserUtils;
import com.hiveview.pms.dto.SysResourceDto;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2017/11/7.
 */
@Controller
public class LoginController extends BaseController {
    @RequestMapping(value="login",method= RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "redirect:/index";
        }
        return "login";
    }

    /**
     * 登录失败跳转
     * @param model
     * @return
     */
    @RequestMapping(value="login",method=RequestMethod.POST)
    public ModelAndView loginFail(HttpServletRequest request, ModelMap model){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new ModelAndView("redirect:/index");
        }
        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
        boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
        String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);

        if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
            message = "用户或密码错误, 请重试.";
        }

        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);

        return new ModelAndView("login");
    }

    @RequestMapping("/logout")
    public String logout() {
        SystemUserUtils.logout();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    @RequestMapping(value="/index")
    public ModelAndView index(ModelMap model){
        getMenuData(model);
        return new ModelAndView("index");
    }

    /**
     * 获取展示菜单
     * @param model
     */
    public void getMenuData(ModelMap model){
        List<SysResourceDto> resourceList= SystemUserUtils.getResourceList();
        List<SysResourceDto> menuList=new ArrayList<>();
        resourceList.stream().filter(menu-> menu.getIsMenu() == 1).forEach(menu->{
            //获取菜单类型资源
            menuList.add(menu);
        });

        List<SysResourceDto> firstMenu=new ArrayList<>();
        menuList.stream().filter(m-> m.getLevel() == 1).forEach(m->{
            firstMenu.add(m);
        });

        //菜单目前只支持两级
        firstMenu.forEach(f->{
            List<SysResourceDto> childList=new ArrayList<>();
            menuList.stream().filter(menu -> !menu.getCode().equals(f.getCode()) && null != menu.getParentCode()
                    && menu.getParentCode().equals(f.getCode())).forEach(menu -> {
                childList.add(menu);
            });
            f.setChildList(childList);
        });
        model.put("menuList", firstMenu);
    }
}
