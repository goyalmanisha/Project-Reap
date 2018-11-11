package com.projectreap.ProjectReap.controller;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.projectreap.ProjectReap.entity.User;
import com.projectreap.ProjectReap.enums.Role;
import com.projectreap.ProjectReap.service.UserService;
import com.projectreap.ProjectReap.util.EmailUtil;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EmailUtil emailUtil;

    /*
    Start Page for login and register
    * */
    @GetMapping(value = {"/", "/index","/index/forgotPassword"})
    public String showHome(User user) {
        return "index";
    }

    /*Logout Functionality*/
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:index?act=logout";
    }

    /*
    Saved data of user registered in database
    */
    @PostMapping("/register")
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        User userExists = userService.findByUsername(user.getUserName());
        System.out.println(userExists);
        if (userExists != null) {
            redirectAttributes.addFlashAttribute("message", "This Username already exists!Cannot register!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            bindingResult.rejectValue("userName", "error.user");
            return "redirect:/index";
        } else if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Error,Register Failed! Enter Valid Data");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/index";
        } else {
            userService.update(user);
            redirectAttributes.addFlashAttribute("message", "User Registered Successfully");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            return "redirect:/index";
        }
    }

    /*
     * This is the login section
     * */
    @PostMapping("/login")
    public String handleLogin(
            @ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes, Model model) {

        User loggedInUser = userService.findByUsername(user.getUserName());
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("message", "Invalid User.Please Enter the credentials");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/";
        } else {
            if (!loggedInUser.getPassword().isEmpty() && loggedInUser.getPassword().equals(user.getPassword())) {
                if (loggedInUser.getRole().equals(Role.USER.getValue())) {
                    //add user detail in session(assign session to logged in user)
                    addUserInSession(loggedInUser, session);
                    return "redirect:/user/dashboard";
                } else {
                    //add user detail in session(assign session to logged in user)
                    addUserInSession(loggedInUser, session);
                    return "redirect:/admin/dashboard";
                }
            } else {
                redirectAttributes.addFlashAttribute("message", "Error,Login Failed! Enter Valid Credentials");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                return "redirect:/index";
            }
        }

    }

    @GetMapping("/user/dashboard")
    public String getUserDashboard(HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {

        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser.getRole().equals(Role.USER.getValue())) {
            model.addAttribute("currentUser", currentUser);
            return "userDashboard";
        } else {
            redirectAttributes.addFlashAttribute("message", "Error,Login Failed! User Not exists");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/index";
        }
    }

    @GetMapping("/admin/dashboard")
    public String getAdminDashboard(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser.getRole().equals(Role.ADMIN.getValue())) {
            return "adminDashboard";
        } else if (currentUser.getRole().equals(Role.USER.getValue())){
            return "redirect:/user/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("message", "Error,Login Failed! User Not Exists");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/index";
        }
    }

    /*
    Fetch the users list on Admin page....
    */
    @GetMapping("/admin/users")
    private String getUserList(HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser.getRole().equals(Role.ADMIN.getValue())) {
            model.addAttribute("userList", userService.getAllUsers());
            return "layouts/userList";
        } else {
            redirectAttributes.addFlashAttribute("message", "Not Authorised to access the list");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/index";
        }
    }

    /*
     * Showing the badge page
     * */
    @RequestMapping(value = "/user/badge")
    public String showbadge(HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser.getRole().equals(Role.USER.getValue()))
            model.addAttribute("currentUser", currentUser);
        return "badge";
    }

    /*JSON data....................*/
    @GetMapping(value = "/users")
    @ResponseBody
    public List<User> getUsers(@RequestParam String firstName) {
        System.out.println(searchResult(firstName));
        return searchResult(firstName);
    }

    private void addUserInSession(User u, HttpSession httpSession) {
        httpSession.setAttribute("user", u);
        httpSession.setAttribute("id", u.getId());
        httpSession.setAttribute("role", u.getRole());
    }

    private List<User> searchResult(String firstname){
        List<User> result=new ArrayList<>();
       List<User> userList=userService.getAllUserList();
       for(User user:userList){
            if (user.getFirstName().contains(firstname)){
                result.add(user);
            }}
            return result;
        }
    }

