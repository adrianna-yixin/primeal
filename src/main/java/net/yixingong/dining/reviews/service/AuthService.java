package net.yixingong.dining.reviews.service;

import net.yixingong.dining.reviews.payload.LoginDto;
import net.yixingong.dining.reviews.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
