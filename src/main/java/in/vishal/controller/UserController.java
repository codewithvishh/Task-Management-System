package in.vishal.controller;

import in.vishal.model.User;
import in.vishal.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Redirect root to registration page
    @GetMapping("/")
    public String home() {
        return "redirect:/register";
    }

    // Show registration form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Handle registration
    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid User user,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "register";
        }

        try {
            // Save user
            User savedUser = userService.register(user);

            // Flash message for registration success
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";

        } catch (DataIntegrityViolationException e) {
            // Handle duplicate email
            model.addAttribute("error", "Email already registered! Please use a different email.");
            return "register";
        }
    }

    // Show login form
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(required = false) String error,
                                Model model,
                                @ModelAttribute("success") String success) {
        if (error != null) {
            model.addAttribute("error", "Invalid email or password");
        }
        if (success != null && !success.isEmpty()) {
            model.addAttribute("success", success);
        }
        return "login";
    }

    // Handle login
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        User user = userService.login(email.trim().toLowerCase(), password.trim());

        if (user != null) {
            session.setAttribute("user", user);

            // Flash message for login success
            redirectAttributes.addFlashAttribute("success", "Welcome back, " + user.getName() + "!");
            return "redirect:/tasks"; // handled by TaskController
        }

        // Invalid login
        redirectAttributes.addFlashAttribute("error", "Invalid email or password");
        return "redirect:/login";
    }

    // Handle logout
    @GetMapping("/logout")
    public String logout(HttpSession session,
                         RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "You have been logged out successfully.");
        return "redirect:/login";
    }
}
