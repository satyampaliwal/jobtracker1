package com.satyam.jobtracke1.service;

import com.satyam.jobtracke1.config.JwtUtil;
import com.satyam.jobtracke1.entity.User;
import com.satyam.jobtracke1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public String register(String username, String password) {
        // Check karo username already exist toh nahi karta
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Password encrypt karke save karo
        user.setRole("USER");
        userRepository.save(user);

        return "User registered successfully!";
    }

    @Override
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // Password match karo
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        // Token banao aur return karo
        return jwtUtil.generateToken(username);
    }
}
