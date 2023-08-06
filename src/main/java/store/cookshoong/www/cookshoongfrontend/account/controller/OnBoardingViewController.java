package store.cookshoong.www.cookshoongfrontend.account.controller;

import java.util.Objects;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.cookshoong.www.cookshoongfrontend.account.model.request.SignUpRequestDto;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountService;
import store.cookshoong.www.cookshoongfrontend.address.model.request.CreateAccountAddressRequestDto;

/**
 * 로그인, 회원가입 등 인증을 위한 컨트롤러.
 *
 * @author koesnam
 * @since 2023/07/04
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class OnBoardingViewController {
    private static final String REGISTER_FORM_VIEW = "account/register-form";
    private final AccountService accountService;
    private final AccountIdAware accountIdAware;

    @GetMapping("/sign-up-choice")
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
    @GetMapping("/sign-up")
    public String getCustomerSignUpForm(@RequestParam(required = false) String userType,
                                        Model model, SignUpRequestDto signUpRequestDto) {
        if (Objects.nonNull(userType) && (userType.equals("cus") || userType.equals("biz"))) {
            model.addAttribute("userType", userType);
        } else {
            return "redirect:/sign-up-choice";
        }

        if (signUpRequestDto.isAddressEmpty()) {
            signUpRequestDto.setCreateAccountAddressRequestDto(new CreateAccountAddressRequestDto());
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
    @PostMapping("/sign-up")
    public String postSignUpForm(@RequestParam String userType, @Valid SignUpRequestDto signUpRequestDto,
                                 BindingResult bindingResult, Model model) {
        // TODO: 폼 작성에 회원ID 중복여부 체크 필요.

        // ISSUE: 잦은 회원가입 실패(검증오류)로 userType 값이 없어지는 경우가 있음. 현재는 다시 선택창으로 돌아가게 하고 있음.
        if (!userType.equals("cus") && !userType.equals("biz")) {
            return "redirect:/sign-up-choice";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("signUpRequestDto", signUpRequestDto);
            return REGISTER_FORM_VIEW;
        }

        accountService.requestAccountRegistration(userType, signUpRequestDto);
        return "redirect:/";
    }

    @GetMapping("/dormancy")
    public String getDormancyAccount() {
        return "account/dormancy";
    }

    /**
     * 휴면상태인 회원을 활성상태로 바꿔주는 엔드포인트.
     *
     * @return the account active
     */
    @GetMapping("/dormancy/active")
    public String getAccountActive() {
        Long accountId = accountIdAware.getAccountId();
        accountService.updateAccountStatus(accountId, "ACTIVE");
        return "redirect:/";
    }

}
