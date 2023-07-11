package store.cookshoong.www.cookshoongfrontend.payment.model.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 결제 타입과 환불 타입에 name 을 응답하는 Response Dto.
 *
 * @author jeongjewan
 * @since 2023.07.06
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TypeResponseDto {

    private String id;
    private String name;
}
