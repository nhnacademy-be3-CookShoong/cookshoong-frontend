package store.cookshoong.www.cookshoongfrontend.shop.model.response;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 위치에서 3km 이내에 있는 매장을 보여줄 Dto.
 *
 * @author papel
 * @since 2023.07.17
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectAllStoresNotOutedResponseDto {
    private Long id;
    private String name;
    private String storeStatus;
    private String mainPlace;
    private String detailPlace;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String category;
}
