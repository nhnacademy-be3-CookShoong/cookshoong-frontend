package store.cookshoong.www.cookshoongfrontend.address.model.response;

import java.math.BigDecimal;
import lombok.Getter;

/**
 * 회원 위치에서 3km 이내에 있는 매장을 보여줄 Dto.
 *
 * @author jeongjewan
 * @since 2023.07.14
 */
@Getter
public class SelectAllStoresNotOutedResponseDto {
    private Long id;
    private String name;
    private String storeStatus;
    private String mainPlace;
    private String detailPlace;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
