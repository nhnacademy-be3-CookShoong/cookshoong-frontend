package store.cookshoong.www.cookshoongfrontend.payment.model.request;


import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
    private String name;
}
