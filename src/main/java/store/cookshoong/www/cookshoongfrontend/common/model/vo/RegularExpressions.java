package store.cookshoong.www.cookshoongfrontend.common.model.vo;

/**
 * 자주 쓰일 것 같은 정규표현식들.
 *
 * @author koesnam
 * @since 2023.07.05
 */
public class RegularExpressions {
    private RegularExpressions() {
        throw new IllegalStateException("It's util class");
    }

    // 문자(한글 + 영어)만 (공백 허용x)
    public static final String LETTER_ONLY = "^[가-힇A-Za-z]+$";
    // 문자(한글 + 영어) + 숫자 (공백 허용x)
    public static final String LETTER_WITH_NUMBER = "^[가-힇\\w]+$";
    // 문자(한글 + 영어)만 (띄어쓰기 허용) + @NotBlank 사용하셔야 공백을 막을 수 있습니다.
    public static final String LETTER_ONLY_WITH_BLANK = "^[가-힇A-Za-z ]+$";
    // 문자(한글 + 영어)만 (띄어쓰기 허용) + 숫자 + @NotBlank 사용하셔야 공백을 막을 수 있습니다.
    public static final String LETTER_WITH_NUMBER_AND_BLANK = "^[가-힇A-Za-z0-9 ]+$";
    // 문자(한글 + 영어)만 (띄어쓰기 허용) + 숫자 + 특별문자  + @NotBlank 사용하셔야 공백을 막을 수 있습니다.
    public static final String MAIN_DETAIL_ADDRESS = "^[가-힇0-9\\w\\-\\,\\(\\), ]+$";
    // 숫자만 (1개 이상)
    public static final String NUMBER_ONLY = "^\\d+$";
    // 숫자가 아닌 모든 문자(한글, 영어, 특수문자) (1개 이상)
    public static final String NOT_NUMBER = "(?=^\\D+$)(?=^\\S+$).*";
    // 문자, 숫자, 특수문자 포함
    public static final String PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
    // 로그인 아이디용 정규표현식(OAuth2 회원가입으로 인해 @를 포함)
    public static final String ENG_NUMBER_AT = "^[0-9A-Za-z@]{4,}$";
}


