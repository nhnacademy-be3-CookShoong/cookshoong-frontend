package store.cookshoong.www.cookshoongfrontend.account.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.cookshoong.www.cookshoongfrontend.account.model.request.OAuth2SignUpRequestDto;
import store.cookshoong.www.cookshoongfrontend.account.model.request.SignUpRequestDto;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountService;
import store.cookshoong.www.cookshoongfrontend.address.model.request.CreateAccountAddressRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;

/**
 * 로그인, 회원가입 등 인증을 위한 컨트롤러.
 *
 * @author koesnam
 * @since 2023 /07/04
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class OnBoardingViewController {
    private static final String REGISTER_FORM_VIEW = "account/register-form";
    private final AccountService accountService;
    private final AccountIdAware accountIdAware;
    private final StoreService storeService;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

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
    @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
    @GetMapping("/sign-up")
    public String getCustomerSignUpForm(@RequestParam(required = false) String userType,
                                        Model model, SignUpRequestDto signUpRequestDto,
                                        HttpServletRequest request) {
        if (Objects.nonNull(userType) && (userType.equals("cus") || userType.equals("biz"))) {
            model.addAttribute("userType", userType);
        } else {
            return "redirect:/sign-up-choice";
        }

        if (signUpRequestDto.isAddressEmpty()) {
            signUpRequestDto.setCreateAccountAddressRequestDto(new CreateAccountAddressRequestDto());
        }

        model.addAttribute("signUpRequestDto", signUpRequestDto);

        SignUpRequestDto signUpRequestDtoFromOAuthUser = (SignUpRequestDto) request.getAttribute("signUpRequestDto");
        if (Objects.nonNull(signUpRequestDtoFromOAuthUser)) {
            model.addAttribute("signUpRequestDto", signUpRequestDtoFromOAuthUser);
        }
        return REGISTER_FORM_VIEW;
    }

    /**
     * OAuth2 회원가입 양식을 나타내는 페이지.
     *
     * @param model View 로 보낼 데이터
     * @return 회원가입 폼 뷰
     */
    @SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:LocalVariableName"})
    @GetMapping("/sign-up-oauth2")
    public String getOAuth2CustomerSignUpForm(Model model, HttpServletRequest request, HttpSession session) throws JsonProcessingException {
        SignUpRequestDto signUpRequestDto = (SignUpRequestDto) request.getAttribute("signUpRequestDto");
        String accountCode = signUpRequestDto.getLoginId().split("@")[0];
        String provider = signUpRequestDto.getLoginId().split("@")[1];
        OAuth2SignUpRequestDto oAuth2SignUpRequestDto = new OAuth2SignUpRequestDto(signUpRequestDto, accountCode, provider);
        model.addAttribute("oAuth2SignUpRequestDto", oAuth2SignUpRequestDto);

        session.setAttribute("oAuth2SignUpRequestDto", objectMapper.writeValueAsString(oAuth2SignUpRequestDto));
        return "account/register-form-oauth2";
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
        if (!userType.equals("cus") && !userType.equals("biz")) {
            return "redirect:/sign-up-choice";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("signUpRequestDto", signUpRequestDto);
            model.addAttribute("userType", userType);
            return REGISTER_FORM_VIEW;
        }

        accountService.requestAccountRegistration(userType, signUpRequestDto);
        return "redirect:/login-page";
    }

    @SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:ParameterName"})
    @PostMapping("/sign-up-oauth2")
    public String postOAuth2SignUpForm(@Valid OAuth2SignUpRequestDto oAuth2SignUpRequestDto,
                                       BindingResult bindingResult, Model model, HttpSession session) throws JsonProcessingException {
        // TODO: 폼 작성에 회원ID 중복여부 체크 필요.
        if (bindingResult.hasErrors()) {
            model.addAttribute("oAuth2SignUpRequestDto", oAuth2SignUpRequestDto);
            return "account/register-form-oauth2";
        }
        OAuth2SignUpRequestDto oAuth2SignUpRequestDtoWithCredential = objectMapper.readValue((String) session.getAttribute("oAuth2SignUpRequestDto"), OAuth2SignUpRequestDto.class);
        oAuth2SignUpRequestDtoWithCredential.updateIfAbsent(oAuth2SignUpRequestDto.getSignUpRequestDto());
        accountService.requestOAuth2AccountRegistration(oAuth2SignUpRequestDtoWithCredential);

        session.removeAttribute("oAuth2SignUpRequestDto");
        session.invalidate();
        return "redirect:/login-page";
    }

    /**
     * 휴면상태 페이지를 보여주는 엔드포인트.
     *
     * @return the dormancy account
     */
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
