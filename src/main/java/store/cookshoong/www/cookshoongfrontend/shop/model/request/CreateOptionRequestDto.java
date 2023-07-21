package store.cookshoong.www.cookshoongfrontend.shop.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사업자 : 옵션 등록 Dto.
 *
 * @author papel (윤동현)
 * @since 2023.07.11
 */
@Getter
@AllArgsConstructor
public class CreateOptionRequestDto {
    @NotBlank
    private String name;
    @NotNull
    private Integer price;
}
