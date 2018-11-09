package com.projectreap.ProjectReap.controller;

import com.projectreap.ProjectReap.entity.User;
import com.projectreap.ProjectReap.enums.Role;
import com.projectreap.ProjectReap.service.UserService;
import com.projectreap.ProjectReap.util.EmailUtil;
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

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EmailUtil emailUtil;

    /*Start Page for login and register*/
    @GetMapping(value = {"/", "/index"})
    public String showHome(User user) {
        return "index";
    }

    /*Logout Functionality*/
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:index?act=lo";
    }

    /* Saved data of user registered in DB*/
    @PostMapping("/register")
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        User userExists = userService.findByUsername(user.getUserName());
        System.out.println(userExists);
        if (userExists != null) {
            redirectAttributes.addFlashAttribute("message", "This Username already exists!Cannot register!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            bindingResult.rejectValue("userName","error.user");
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

    @PostMapping("/login")
    public String handleLogin(
            @ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes) {

        User loggedInUser = userService.findByUsername(user.getUserName());
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("message", "Invalid User.Please Enter the credentials");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/";
        } else {
            if (!loggedInUser.getPassword().isEmpty() && loggedInUser.getPassword().equals(user.getPassword())) {
//                request.getSession().setAttribute("user",user);
                if (loggedInUser.getRole() == Role.USER) {
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
    public ModelAndView getUserDashboard(HttpServletRequest request, Model model) {

        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser.getRole() == Role.USER) {
            return new ModelAndView("userDashboard");
        } else {
            return new ModelAndView().addObject("message", "User Not Exists");
        }
    }

    @GetMapping("/admin/dashboard")
    public ModelAndView getAdminDashboard(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser.getRole() == Role.ADMIN) {

            return new ModelAndView("adminDashboard");
        } else {
            return new ModelAndView().addObject("message", "User Not Exists");
        }

    }

    private void addUserInSession(User u, HttpSession httpSession) {
        httpSession.setAttribute("user", u);
        httpSession.setAttribute("id", u.getId());
        httpSession.setAttribute("role", u.getRole());
    }
}
