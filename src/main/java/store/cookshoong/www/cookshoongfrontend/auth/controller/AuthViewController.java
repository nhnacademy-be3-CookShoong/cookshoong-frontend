package store.cookshoong.www.cookshoongfrontend.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 로그인, 회원가입 등 인증을 위한 컨트롤러.
 *
 * @author koesnam
 * @since 2023/07/04
 */
@Controller
public class AuthViewController {
    @GetMapping("login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("login")
    public String login() {
        return "redirect:/";
    }

}
