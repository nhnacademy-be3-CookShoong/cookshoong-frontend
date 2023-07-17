package store.cookshoong.www.cookshoongfrontend.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.account.adapter.AccountApiAdapter;
import store.cookshoong.www.cookshoongfrontend.account.exception.RegisterFailureException;
import store.cookshoong.www.cookshoongfrontend.account.model.request.SignUpRequestDto;

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

    /**
     * API 서버에 회원가입 요청을 보낸다.
     *
     * @param userType         회원 종류
     * @param signUpRequestDto 회원가입 요청 정보
     */
    public void requestAccountRegistration(String userType, SignUpRequestDto signUpRequestDto) {
        String authorityCode = userType.equals("cus") ? "customer" : "business";

        ResponseEntity<Void> response = accountApiAdapter.executeRegistration(authorityCode, signUpRequestDto);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RegisterFailureException(response.getStatusCode());
        }
        // ISSUE: 중복된 아이디일 경우 저장만 안되고 계속 페이지를 머움
    }
}
