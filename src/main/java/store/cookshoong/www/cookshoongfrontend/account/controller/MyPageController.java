package store.cookshoong.www.cookshoongfrontend.account.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import store.cookshoong.www.cookshoongfrontend.account.model.request.UpdateAccountInfoRequestDto;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountService;

/**
 * 마이페이지 컨트롤러.
 *
 * @author papel (윤동현), koesnam (추만석)
 * @since 2023.08.06
 *
 */
@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final AccountService accountService;
    private final AccountIdAware accountIdAware;

    /**
     * 회원정보 페이지 진입.
     *
     * @param updateAccountInfoRequestDto the update account info request dto
     * @param model                       the model
     * @return the my-page
     */
    @GetMapping("/my-page")
    public String getMyPage(UpdateAccountInfoRequestDto updateAccountInfoRequestDto, Model model) {
        model.addAttribute("updateAccountInfoRequestDto", updateAccountInfoRequestDto);
        model.addAttribute("accountInfo", accountService.selectAccountMypageInfo(accountIdAware.getAccountId()));
        return "account/my-page";
    }

    /**
     * 회원 정보 수정 요청.
     *
     * @param updateAccountInfoRequestDto the update account info request dto
     * @param bindingResult               the binding result
     * @param model                       the model
     * @return the string
     */
    @PostMapping("/my-page")
    public String postMyPage(@Valid UpdateAccountInfoRequestDto updateAccountInfoRequestDto, BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(updateAccountInfoRequestDto);
            return "/my-page";
        }
        accountService.updateAccountMyPageInfo(accountIdAware.getAccountId(), updateAccountInfoRequestDto);
        return "redirect:/my-page";
    }
}
