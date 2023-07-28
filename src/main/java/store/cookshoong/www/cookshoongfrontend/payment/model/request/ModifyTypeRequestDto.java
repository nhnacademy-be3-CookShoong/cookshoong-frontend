package store.cookshoong.www.cookshoongfrontend.payment.model.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import store.cookshoong.www.cookshoongfrontend.common.model.vo.RegularExpressions;
import store.cookshoong.www.cookshoongfrontend.common.model.vo.ValidationFailureMessages;

/**
 * 결제 타입과 환불 타입에 name을 수정하는 Request Dto.
 *
 * @author jeongjewan
 * @since 2023.07.06
 */
@Getter
@AllArgsConstructor
public class ModifyTypeRequestDto {

    @NotBlank
    @Length(min = 1, max = 30)
    @Pattern(regexp = RegularExpressions.LETTER_ONLY_WITH_BLANK, message = ValidationFailureMessages.LETTER_ONLY)
    private String name;
}
