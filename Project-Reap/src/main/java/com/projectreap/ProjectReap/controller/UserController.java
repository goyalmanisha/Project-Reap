package com.projectreap.ProjectReap.controller;

import com.projectreap.ProjectReap.entity.User;
import com.projectreap.ProjectReap.enums.Role;
import com.projectreap.ProjectReap.pojo.AppreciatedData;
import com.projectreap.ProjectReap.pojo.UserUpdatedData;
import com.projectreap.ProjectReap.service.AppreciationService;
import com.projectreap.ProjectReap.service.UserService;
import com.projectreap.ProjectReap.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserController implements ErrorController {

    @Autowired
    UserService userService;

    @Autowired
    EmailUtil emailUtil;

    @Autowired
    private AppreciationService appreciationService;
    private static final String path = "/error";



    /*
    Start Page for login and register
    * */
    @GetMapping(value = {"/", "/index", "/index/forgotPassword"})
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
            @ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes) {

        User loggedInUser = userService.findByUsername(user.getUserName());
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("message", "Invalid User.Please Enter the credentials");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/";
        } else {
            if (loggedInUser.getStatus() == true) {
                if (!loggedInUser.getPassword().isEmpty() && loggedInUser.getPassword().equals(user.getPassword())) {
                    if (loggedInUser.getRole().equals(Role.ADMIN.getValue())) {
                        //add user detail in session(assign session to logged in user)
                        addUserInSession(loggedInUser, session);
                        return "redirect:/admin/dashboard";
                    } else {
                        //add user detail in session(assign session to logged in user)

                        addUserInSession(loggedInUser, session);
//                        model.addAttribute("user",loggedInUser);
                        return "redirect:/user/dashboard";
                    }
                } else {
                    redirectAttributes.addFlashAttribute("message", "Error,Login Failed! Enter Valid Credentials");
                    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                    return "redirect:/index";
                }
            } else {
                redirectAttributes.addFlashAttribute("message", "Inactive user");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                return "redirect:/index";
            }
        }

    }

    @GetMapping("/user/dashboard")
    public String getUserDashboard(HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {

        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null) {
            if (!currentUser.getRole().equals(Role.ADMIN.getValue())) {
                model.addAttribute("currentUser", currentUser);
                model.addAttribute("appreciatedData", new AppreciatedData());
                model.addAttribute("badgeCount", appreciationService.handlingBadge(currentUser));
                model.addAttribute("WallofFameList",appreciationService.findAll());
                return "userDashboard";
            } else {
                redirectAttributes.addFlashAttribute("message", "Error,Login Failed! Not Authorised User");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                return "redirect:/index";
            }
        } else {
            return "redirect:/index";
        }
    }

    @GetMapping("/admin/dashboard")
    public String getAdminDashboard(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        User currentUser = (User) request.getSession().getAttribute("user");

        if (currentUser != null) {
            if (currentUser.getRole().equals(Role.ADMIN.getValue())) {
                return "adminDashboard";
            } else {
                return "redirect:/user/dashboard";
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Error,Login Failed! Not Authorised User");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/index";
        }
    }


    /*
    Fetch the users list on Admin page....
    */
    @GetMapping(value = {"/admin/users", "/admin/updatedUser"})
    private String getUserList(HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null) {
            if (currentUser.getRole().equals(Role.ADMIN.getValue())) {
                System.out.println("userlist"+userService.getAllUsersList());
                model.addAttribute("userList", userService.getAllUsersList());
                return "layouts/userList";
            } else {
                redirectAttributes.addFlashAttribute("message", "Not Authorised to access the list");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                return "redirect:/index";
            }
        } else {
            return "redirect:/index";
        }

    }

    /*
     * Showing the badge page
     * */
    @RequestMapping(value = "/user/badge")
    public String showbadge(HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null) {
            if (currentUser.getRole().equals(Role.ADMIN.getValue())) {
                redirectAttributes.addFlashAttribute("message", "You don't have any badges.");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                return "redirect:/index";

            } else {
                System.out.println("Total points on badge page" +currentUser.getTotalPoints());
                model.addAttribute("currentUser", currentUser);
                model.addAttribute("badgeCount", appreciationService.handlingBadge(currentUser));

                return "badge";
            }
        } else {
            return "redirect:/index";
        }
    }

    /*AutoComplete Search......*/
    @GetMapping(value = "/users")
    @ResponseBody
    public List<Map> getUsers(@RequestParam String term) {
        List<User> result = userService.getUsersFirstName(term);
        return result.stream().map(user -> {
            Map response = new HashMap();
            response.put("label", user.getFirstName());
            response.put("value", user.getFirstName() + " " + user.getLastName());
            response.put("userId", user.getId());
            return response;
        }).collect(Collectors.toList());
    }

    private void addUserInSession(User u, HttpSession httpSession) {
        httpSession.setAttribute("user", u);
        httpSession.setAttribute("id", u.getId());
        httpSession.setAttribute("role", u.getRole());
    }

    /*
     Update the user details by admin.
     */
    @RequestMapping(value = "/admin/update-user", method = RequestMethod.POST)
    public String updateUserDetailsByAdmin(@ModelAttribute UserUpdatedData userUpdatedData) {
        if (userUpdatedData.getUserStatus() == null) {
            userUpdatedData.setUserStatus(false);
        }
        userService.updateUserDetails(userUpdatedData.getUserRole(), userUpdatedData.getUserStatus(), userUpdatedData.getUserId());

        return "redirect:/admin/updatedUser";
    }

    /*
     * Delete the particular row from user..
     * */
    @GetMapping("/admin/users/{id}/delete")
    public ModelAndView deleteUser(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return new ModelAndView("redirect:/admin/users");
    }

    @GetMapping(value = path)
    @ResponseBody
    public String errorMessage(){
        return "This Page is not accessible.Please Check your URL!!!!!!!";
    }

    @Override
    public String getErrorPath() {
        return path;
    }
}
