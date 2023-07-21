package store.cookshoong.www.cookshoongfrontend.shop.model.response;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 3km 이내 매장 조회 Dto.
 *
 * @author papel (윤동현)
 * @since 2023.07.17
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectStoresNotOutedResponseDto {
    private Long id;
    private String name;
    private String storeStatus;
    private String mainPlace;
    private String detailPlace;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String category;
    private String savedName;
}
