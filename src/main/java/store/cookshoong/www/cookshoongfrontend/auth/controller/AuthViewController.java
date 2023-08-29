package store.cookshoong.www.cookshoongfrontend.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.cookshoong.www.cookshoongfrontend.auth.model.request.LoginRequestDto;

/**
 * 로그인, 회원가입 등 인증을 위한 컨트롤러.
 *
 * @author koesnam
 * @since 2023/07/04
 */
@Controller
public class AuthViewController {

    /**
     * 로그인 페이지 진입점.
     *
     * @param loginRequestDto the login request dto
     * @param model           the model
     * @param error           the error
     * @return the string
     */
    @GetMapping("/login-page")
    public String login(LoginRequestDto loginRequestDto, Model model, @RequestParam(required = false) String error) {
        model.addAttribute("loginRequestDto", loginRequestDto);
        model.addAttribute("error", error);
        return "auth/login-page";
    }
}
