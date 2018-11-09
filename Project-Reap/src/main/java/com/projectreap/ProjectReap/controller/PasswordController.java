package com.projectreap.ProjectReap.controller;

import com.projectreap.ProjectReap.entity.User;
import com.projectreap.ProjectReap.service.UserService;
import com.projectreap.ProjectReap.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PasswordController {

    @Autowired
    UserService userService;

    @Autowired
    EmailUtil emailUtil;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // Process form submission from forgotPassword page
    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @ModelAttribute("user") User user, HttpServletRequest request) {

        //System.out.println(userEmail);
        // Lookup user in database by e-mail
        Optional<User> optional = userService.findByEmail(user.getEmail());
        System.out.println(optional);
        if (!optional.isPresent()) {
            modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
        } else {

            // Generate random 36-character string token for reset password
             user = optional.get();
            user.setResetToken(UUID.randomUUID().toString());

            // Save token to database
            userService.update(user);

            String appUrl = request.getScheme() + "://" + request.getServerName();

            // Email message
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("reapreap795@gmail.com");
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                    + "/reset?token=" + user.getResetToken());

            emailUtil.sendEmail(passwordResetEmail);

            // Add success message to view
            modelAndView.addObject("successMessage", "A password reset link has been sent to " + user.getEmail());
        }

        modelAndView.setViewName("forgotPassword");
        return modelAndView;

    }

    // Process reset password form
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {

        // Find the user associated with the reset token
        Optional<User> user = userService.findUserByResetToken(requestParams.get("token"));

        // This should always be non-null but we check just in case
        if (user.isPresent()) {

            User resetUser = user.get();

            // Set new password
            resetUser.setPassword((requestParams.get("password")));

            // Set the reset token to null so it cannot be used again
            resetUser.setResetToken(null);

            // Save user
            userService.update(resetUser);

            // In order to set a model attribute on a redirect, we must use
            // RedirectAttributes
            redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");

            modelAndView.setViewName("redirect:/index");
            return modelAndView;

        } else {
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
            modelAndView.setViewName("index");
        }

        return modelAndView;
    }

}
