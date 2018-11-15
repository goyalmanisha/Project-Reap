package com.projectreap.ProjectReap.controller;

import com.projectreap.ProjectReap.entity.User;
import com.projectreap.ProjectReap.service.UserService;
import com.projectreap.ProjectReap.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class PasswordController {

    @Autowired
    UserService userService;

    @Autowired
    EmailUtil emailUtil;

    /*
    * Sending the mail to get the password for login again,in case user has forget the password.
    * */
    @PostMapping(value = "/forgot")
    public String processForgotPasswordForm( @ModelAttribute User user,RedirectAttributes redirectAttributes) {
        // Lookup user in database by e-mail
        Optional<User> optional = userService.findByEmail(user.getEmail());
        System.out.println(optional);
        if (!optional.isPresent()) {
            redirectAttributes.addFlashAttribute("message","We didn't find an account for that e-mail address.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/index/forgotPassword";
        } else {
            // Email message
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("Your new password is abcd");
            emailUtil.sendEmail(passwordResetEmail);

            User resetUser = optional.get();
//          Setting the password manually in the database.
           // userService.getPasswordById(resetUser.getId());
            resetUser.setPassword("abcd");
            userService.update(resetUser);

            // Add success message to view
            redirectAttributes.addFlashAttribute("message","A password reset link has been sent to " + user.getEmail());
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            return "redirect:/index/forgotPassword";
        }
    }

}
