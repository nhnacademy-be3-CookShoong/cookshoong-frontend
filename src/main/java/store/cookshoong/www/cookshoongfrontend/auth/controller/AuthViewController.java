package store.cookshoong.www.cookshoongfrontend.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import store.cookshoong.www.cookshoongfrontend.auth.model.request.LoginRequestDto;

/**
 * 로그인, 회원가입 등 인증을 위한 컨트롤러.
 *
 * @author koesnam
 * @since 2023/07/04
 */
@Controller
public class AuthViewController {
    @GetMapping("/login-page")
    public String login(LoginRequestDto loginRequestDto, Model model) {
        model.addAttribute("loginRequestDto", loginRequestDto);
        return "auth/login-page";
    }

    @GetMapping("/auth/test")
    @ResponseBody
    public String authenticatedTest() {
        return "authenticated success!";
    }

    @GetMapping("/auth")
    @ResponseBody
    public String authenticatedTest2() {
        return "authenticated is not required!";
    }
}
