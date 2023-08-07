package store.cookshoong.www.cookshoongfrontend.account.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import store.cookshoong.www.cookshoongfrontend.address.model.request.CreateAccountAddressRequestDto;
import store.cookshoong.www.cookshoongfrontend.common.model.vo.RegularExpressions;
import store.cookshoong.www.cookshoongfrontend.common.model.vo.ValidationFailureMessages;

/**
 * 회원가입 폼에서 회원가입 요청을 위해 보내는 Dto.
 *
 * @author koesnam
 * @since 2023/07/04
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignUpRequestDto {
    @NotBlank
    @Length(min = 1, max = 100)
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank
    @Length(min = 2, max = 30)
    @Pattern(regexp = RegularExpressions.LETTER_ONLY, message = ValidationFailureMessages.LETTER_ONLY)
    private String name;
    @NotBlank
    @Length(min = 1, max = 30)
    @Pattern(regexp = RegularExpressions.LETTER_WITH_NUMBER, message = ValidationFailureMessages.LETTER_WITH_NUMBER)
    private String nickname;
    @NotBlank
    @Email
    private String email;
    @Past
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    @NotBlank
    @Pattern(regexp = RegularExpressions.NUMBER_ONLY, message = ValidationFailureMessages.NUMBER_ONLY)
    private String phoneNumber;
    @NotNull
    @Setter
    private CreateAccountAddressRequestDto createAccountAddressRequestDto;

    public void encodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    @JsonIgnore
    public boolean isAddressEmpty() {
        return Objects.isNull(createAccountAddressRequestDto);
    }
}
