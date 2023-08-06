package store.cookshoong.www.cookshoongfrontend.auth.util;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import store.cookshoong.www.cookshoongfrontend.account.model.request.SignUpRequestDto;
import store.cookshoong.www.cookshoongfrontend.address.model.request.CreateAccountAddressRequestDto;

/**
 * OAuth 에서 인가된 정보를 기반으로 회원가입Dto 를 만들어주기 위한 유틸 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.08.02
 */
public class AttributeConverter {
    private AttributeConverter() {
    }

    /**
     * OAuth 회원의 정보를 통해 회원가입에 필요한 요청 객체를 생성하는 메서드.
     *
     * @param attributes the attributes
     * @return the sign-up request dto
     */
    public static SignUpRequestDto createSignUpRequest(Map<String, Object> attributes) {
        String loginId = attributes.get("idNo") + "@" + attributes.get("provider");
        String password = UUID.randomUUID().toString();
        String name = attributes.get("name") == null ? null : ((String) attributes.get("name"));
        String nickname = attributes.get("nickname") == null ? null : ((String) attributes.get("nickname"));
        String email = attributes.get("email") == null ? null : ((String) attributes.get("email"));
        LocalDate birthday = attributes.get("birthdayMMdd") == null ? null : convertMMddBirthday(attributes.get("birthdayMMdd"));
        String phoneNumber = attributes.get("mobile") == null ? null : ((String) attributes.get("mobile"));
        CreateAccountAddressRequestDto address = new CreateAccountAddressRequestDto();
        return new SignUpRequestDto(loginId, password, name, nickname, email, birthday, phoneNumber, address);
    }

    @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
    private static LocalDate convertMMddBirthday(Object birthdayMMdd) {
        String birthday = (String) birthdayMMdd;
        int month = Integer.parseInt(birthday.substring(0, 2));
        int day = Integer.parseInt(birthday.substring(2));
        return LocalDate.of(LocalDate.now().getYear(), month, day);
    }
}
