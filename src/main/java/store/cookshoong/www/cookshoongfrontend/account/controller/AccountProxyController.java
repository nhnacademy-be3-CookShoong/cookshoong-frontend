package store.cookshoong.www.cookshoongfrontend.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountService;

/**
 * javascript 로 오는 API 요청을 처리하기 위한 컨트롤러.
 *
 * @author koesnam (추만석)
 * @since 2023.08.08
 */
@RestController
@RequestMapping("/proxy/accounts")
@RequiredArgsConstructor
public class AccountProxyController {
    private final AccountService accountService;

    @GetMapping("/account/exists")
    public boolean getAccountExists(@RequestHeader("X-LOGIN-ID") String loginId) {
        return accountService.selectLoginIdExists(loginId);
    }
}
