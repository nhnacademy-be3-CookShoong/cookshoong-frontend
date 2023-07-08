package store.cookshoong.www.cookshoongfrontend.payment.model.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 결제 타입과 환불 타입에 name 을 생성하는 Request Dto.
 *
 * @author jeongjewan
 * @since 2023.07.06
 */
@Getter
@AllArgsConstructor
public class CreateTypeRequestDto {

    @NotBlank
    private String name;
}

