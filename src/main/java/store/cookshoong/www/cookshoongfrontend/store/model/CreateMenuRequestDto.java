package store.cookshoong.www.cookshoongfrontend.store.model;

import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 메뉴 등록 Dto.
 *
 * @author papel
 * @since 2023.07.11
 */
@Getter
@AllArgsConstructor
public class CreateMenuRequestDto {
    @NotBlank
    private String name;
    @NotNull
    private Integer price;
    private String description;
    private String image;
    @NotNull
    private Integer cookingTime;
    @Digits(integer = 3, fraction = 1)
    private BigDecimal earningRate;


}
