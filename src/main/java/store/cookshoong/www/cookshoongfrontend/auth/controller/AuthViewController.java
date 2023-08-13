package store.cookshoong.www.cookshoongfrontend.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.cookshoong.www.cookshoongfrontend.auth.model.request.LoginRequestDto;

import javax.servlet.http.HttpServletResponse;

/**
 * 로그인, 회원가입 등 인증을 위한 컨트롤러.
 *
 * @author koesnam
 * @since 2023/07/04
 */
@Controller
public class AuthViewController {
    @GetMapping("/login-page")
    public String login(LoginRequestDto loginRequestDto, Model model, @RequestParam(required = false) String error) {
        model.addAttribute("loginRequestDto", loginRequestDto);
        model.addAttribute("error", error);
        return "auth/login-page";
    }
}
