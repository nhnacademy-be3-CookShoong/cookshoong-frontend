package store.cookshoong.www.cookshoongfrontend.auth.controller;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import store.cookshoong.www.cookshoongfrontend.auth.dto.SignUpRequestDto;

/**
 * 로그인, 회원가입 등 인증을 위한 컨트롤러.
 *
 * @author koesnam
 * @since 2023/07/04
 */
@Controller
public class AuthViewController {
    @GetMapping("sign-up")
    public String signUpForm(Model model, SignUpRequestDto signUpRequestDto) {
        model.addAttribute("signUpRequestDto", signUpRequestDto);
        return "auth/registerForm";
    }

    /**
     * 회원가입 처리 메서드.
     *
     * @param signUpRequestDto 회원가입 요청데이터
     * @param bindingResult    각 입력값에 대한 검증결과
     * @param model            View 로 보낼 데이터
     * @return 랜딩페이지
     */
    @PostMapping("sign-up")
    public String signUp(@Valid SignUpRequestDto signUpRequestDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("signUpRequestDto", signUpRequestDto);
            return "auth/registerForm";
        }
        return "redirect:/";
    }
}
