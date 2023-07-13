package store.cookshoong.www.cookshoongfrontend.store.model;

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
    private String menuStatusName;
    @NotBlank
    private String name;

    @NotBlank
    private String price;

    private String description;

    private String image;

    @NotNull
    private Integer cookingTime;

    private String earningRate;


}
