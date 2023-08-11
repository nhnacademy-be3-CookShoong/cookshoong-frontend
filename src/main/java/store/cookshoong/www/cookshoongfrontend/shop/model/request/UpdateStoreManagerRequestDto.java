package store.cookshoong.www.cookshoongfrontend.shop.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.cookshoong.www.cookshoongfrontend.common.model.vo.RegularExpressions;
import store.cookshoong.www.cookshoongfrontend.common.model.vo.ValidationFailureMessages;

/**
 * 매장 사업자 정보에 대한 수정 dto.
 *
 * @author seungyeon
 * @since 2023.08.09
 */
@Getter
@AllArgsConstructor
public class UpdateStoreManagerRequestDto {
    @NotBlank
    @Pattern(regexp = RegularExpressions.LETTER_ONLY, message = ValidationFailureMessages.LETTER_ONLY)
    private String representativeName;
    @NotBlank
    private String bankCode;
    @NotBlank
    @Pattern(regexp = RegularExpressions.NUMBER_ONLY, message = ValidationFailureMessages.NUMBER_ONLY)
    private String bankAccount;
}
