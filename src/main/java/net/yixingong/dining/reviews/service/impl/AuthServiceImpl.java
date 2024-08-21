package net.yixingong.dining.reviews.service.impl;

import net.yixingong.dining.reviews.entity.Role;
import net.yixingong.dining.reviews.entity.User;
import net.yixingong.dining.reviews.exception.DiningReviewsAPIException;
import net.yixingong.dining.reviews.payload.LoginDto;
import net.yixingong.dining.reviews.payload.RegisterDto;
import net.yixingong.dining.reviews.repository.UserRepository;
import net.yixingong.dining.reviews.security.JwtTokenProvider;
import net.yixingong.dining.reviews.service.AuthService;
import net.yixingong.dining.reviews.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    @Transactional
    public String register(RegisterDto registerDto) {
        // Check if username exists in database
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new DiningReviewsAPIException(HttpStatus.BAD_REQUEST, "Username is already in use.");
        }

        // Check if email exists in database
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new DiningReviewsAPIException(HttpStatus.BAD_REQUEST, "Email is already in use.");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        if (userRole.isEmpty()) {
            throw new DiningReviewsAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Default role not found.");
        }
        roles.add(userRole.get());
        user.setRoles(roles);
        userRepository.save(user);

        return "User registered Successfully!";
    }
}
