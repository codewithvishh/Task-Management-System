package in.vishal.service;

import in.vishal.model.User;
import in.vishal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    // Register user with unique email check
    public User register(User user) {
        String email = user.getEmail().trim().toLowerCase();
        user.setEmail(email);
        user.setPassword(user.getPassword().trim());

        // Check if email already exists
        if (userRepo.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email already registered: " + email);
        }

        // Save and flush to generate ID immediately
        return userRepo.saveAndFlush(user);
    }

    // Login method
    public User login(String email, String password) {
        User user = userRepo.findByEmail(email.trim().toLowerCase());
        if (user != null && user.getPassword().trim().equals(password.trim())) {
            return user;
        }
        return null;
    }

    // Helper to fetch user by ID
    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }
}
