package store.cookshoong.www.cookshoongfrontend.account.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.cookshoong.www.cookshoongfrontend.account.model.request.SignUpRequestDto;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountService;

/**
 * 로그인, 회원가입 등 인증을 위한 컨트롤러.
 *
 * @author koesnam
 * @since 2023/07/04
 */
@Controller
@RequiredArgsConstructor
public class OnBoardingViewController {
    private static final String REGISTER_FORM_VIEW = "account/register-form";
    private final AccountService accountService;

    @GetMapping("sign-up-choice")
    public String getSignUpChoicePage() {
        return "account/sign-up-choice";
    }

    /**
     * 회원가입 양식을 나타내는 페이지.
     *
     * @param userType         일반 회원가입 or 사업자 회원가입
     * @param model            View 로 보낼 데이터
     * @param signUpRequestDto 회원가입요청 Dto
     * @return 회원가입 폼 뷰
     */
    @GetMapping("sign-up")
    public String getCustomerSignUpForm(@RequestParam String userType,
                                        Model model, SignUpRequestDto signUpRequestDto) {
        if (userType.equals("cus")) {
            model.addAttribute("userType", "cus");
        } else if (userType.equals("biz")) {
            model.addAttribute("userType", "biz");
        } else {
            return "redirect:/sign-up-choice";
        }

        model.addAttribute("signUpRequestDto", signUpRequestDto);
        return REGISTER_FORM_VIEW;
    }

    /**
     * 회원가입 처리 요청.
     *
     * @param signUpRequestDto 회원가입 요청데이터
     * @param bindingResult    각 입력값에 대한 검증결과
     * @param model            View 로 보낼 데이터
     * @return 랜딩페이지
     */
    @PostMapping("sign-up")
    public String postSignUpForm(@RequestParam String userType, @Valid SignUpRequestDto signUpRequestDto,
                                 BindingResult bindingResult, Model model) {
        // TODO: 폼 작성에 회원ID 중복여부 체크 필요.
        if (bindingResult.hasErrors()) {
            model.addAttribute("signUpRequestDto", signUpRequestDto);
            return REGISTER_FORM_VIEW;
        }

        if (!userType.equals("cus") && !userType.equals("biz")) {
            return "redirect:/sign-up-choice";
        }

        accountService.requestAccountRegistration(userType, signUpRequestDto);
        return "redirect:/";
    }
}

