package store.cookshoong.www.cookshoongfrontend.account.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import store.cookshoong.www.cookshoongfrontend.account.adapter.AccountApiAdapter;
import store.cookshoong.www.cookshoongfrontend.account.exception.RegisterFailureException;
import store.cookshoong.www.cookshoongfrontend.account.exception.UpdateAccountStatusFailureException;
import store.cookshoong.www.cookshoongfrontend.account.model.request.UpdateAccountInfoRequestDto;
import store.cookshoong.www.cookshoongfrontend.account.model.vo.AccountMyPageInfo;
import store.cookshoong.www.cookshoongfrontend.account.model.request.OAuth2SignUpRequestDto;
import store.cookshoong.www.cookshoongfrontend.account.model.request.SignUpRequestDto;
import store.cookshoong.www.cookshoongfrontend.account.model.response.UpdateAccountStatusResponseDto;

/**
 * 회원 정보를 다루는 API 를 호출하기 위한 서비스 클래스.
 *
 * @author koesnam
 * @since 2023.07.08
 */
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountApiAdapter accountApiAdapter;
    private final PasswordEncoder passwordEncoder;

    /**
     * API 서버에 회원가입 요청을 보낸다.
     *
     * @param userType         회원 종류
     * @param signUpRequestDto 회원가입 요청 정보
     */
    public void requestAccountRegistration(String userType, SignUpRequestDto signUpRequestDto) {
        String authorityCode = userType.equals("cus") ? "customer" : "business";

        signUpRequestDto.encodePassword(passwordEncoder.encode(signUpRequestDto.getPassword()));

        ResponseEntity<Void> response = accountApiAdapter.executeAccountRegistration(authorityCode, signUpRequestDto);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RegisterFailureException(response.getStatusCode());
        }
        // ISSUE: 중복된 아이디일 경우 저장만 안되고 계속 페이지를 머움
    }

    /**
     * API 서버에 OAuth 회원가입 요청을 보낸다.
     *
     * @param oAuth2SignUpRequestDto the o auth 2 sign up request dto
     */
    public void requestOAuth2AccountRegistration(OAuth2SignUpRequestDto oAuth2SignUpRequestDto) {
        oAuth2SignUpRequestDto.getSignUpRequestDto()
            .encodePassword(passwordEncoder.encode(oAuth2SignUpRequestDto
                .getSignUpRequestDto()
                .getPassword()));

        ResponseEntity<Void> response = accountApiAdapter.executeOAuth2AccountRegistration(oAuth2SignUpRequestDto);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RegisterFailureException(response.getStatusCode());
        }
    }

    /**
     * API 서버에 회원 상태 변경을 요청한다.
     *
     * @param accountId  the account id
     * @param statusCode the status code
     */
    public void updateAccountStatus(Long accountId, String statusCode) {
        ResponseEntity<UpdateAccountStatusResponseDto> response = accountApiAdapter.executeChangeStatus(accountId,
            statusCode.toUpperCase());

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new UpdateAccountStatusFailureException();
        }
    }

    /**
     * 로그인 아이디 기준으로 존재여부를 확인하는 메서드.
     *
     * @param loginId the login id
     * @return the boolean
     */
    public boolean selectLoginIdExists(String loginId) {
        try {
            accountApiAdapter.fetchAccountExistence(loginId);
            return true;
        } catch (HttpClientErrorException e) {
            if (e.getRawStatusCode() == 404) {
                return false;
            }
            throw e;
        }
    }

    /**
     * 마이페이지에서 필요한 정보들을 조회하는 메서드.
     *
     * @param accountId the account id
     * @return the account my page info
     */
    public AccountMyPageInfo selectAccountMypageInfo(Long accountId) {
        return new AccountMyPageInfo(Objects.requireNonNull(accountApiAdapter
            .fetchAccountMyPageInfo(accountId)
            .getBody())
        );
    }

    public void updateAccountMyPageInfo(Long accountId, UpdateAccountInfoRequestDto updateAccountInfoRequestDto) {
        accountApiAdapter.executeChangeMyPageInfo(accountId, updateAccountInfoRequestDto);
    }
}
