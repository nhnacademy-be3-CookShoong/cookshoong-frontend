package store.cookshoong.www.cookshoongfrontend.account.model.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원가입 폼에서 회원가입 요청을 위해 보내는 Dto.
 *
 * @author koesnam
 * @since 2023/07/04
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OAuth2SignUpRequestDto {
    @Setter
    private SignUpRequestDto signUpRequestDto;
    private String accountCode;
    private String provider;

    /**
     * OAuth2 유저 정보를 담은 객체를 뷰에 들렸다가 왔을 때 사라진 데이터를 채워넣기 위해 만든 메서드.
     * 주소를 등록하는 Dto 는 입력된 값을 사용해야하므로 무조건 업데이트 시켜준다.
     *
     * @param updatedSignUpRequestDto the updated sign up request dto
     */
    public void updateIfAbsent(SignUpRequestDto updatedSignUpRequestDto) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Map<String, Object> signUpRequestDtoMap = objectMapper.convertValue(this.signUpRequestDto,
            new TypeReference<HashMap<String, Object>>() {
            });
        Map<String, Object> updatedSignUpRequestDtoMap = objectMapper.convertValue(updatedSignUpRequestDto,
            new TypeReference<HashMap<String, Object>>() {
            });

        updatedSignUpRequestDtoMap.forEach((field, value) -> {
            if (signUpRequestDtoMap.get(field) == null || field.equals("birthday")
                || field.equals("createAccountAddressRequestDto")) {
                signUpRequestDtoMap.put(field, value);
            }
        });
        this.signUpRequestDto = objectMapper.convertValue(signUpRequestDtoMap, SignUpRequestDto.class);
    }
}
