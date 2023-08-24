package store.cookshoong.www.cookshoongfrontend.order.model.request;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 상태를 변경할 때 사용하는 dto.
 *
 * @author eora21 (김주호)
 * @since 2023.08.02
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PatchOrderRequestDto {
    @NotNull
    private UUID orderCode;
    @NotNull
    private StatusCode statusCode;


    /**
     * The enum Status code.
     */
    public enum StatusCode {
        COOKING, FOOD_OUT, ORD_FLOOD, DELIVER, COMPLETE;
    }
}
