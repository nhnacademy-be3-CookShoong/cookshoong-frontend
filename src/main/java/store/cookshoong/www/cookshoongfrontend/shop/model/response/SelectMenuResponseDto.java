package store.cookshoong.www.cookshoongfrontend.shop.model.response;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 메뉴 조회 Dto.
 *
 * @author papel
 * @since 2023.07.11
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectMenuResponseDto {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String image;
    private Integer cookingTime;
    private BigDecimal earningRate;
    private String menuStatus;
    private Integer menuSequence;
    private Long menuGroupId;
}
