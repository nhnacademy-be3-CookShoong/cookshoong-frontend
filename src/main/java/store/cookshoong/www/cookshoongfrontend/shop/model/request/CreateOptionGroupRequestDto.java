package store.cookshoong.www.cookshoongfrontend.shop.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 옵션 그룹 등록 Dto.
 *
 * @author papel (윤동현)
 * @since 2023.07.13
 */
@Getter
@AllArgsConstructor
public class CreateOptionGroupRequestDto {
    @NotBlank
    private String name;
    @NotNull
    private Integer minSelectCount;
    @NotNull
    private Integer maxSelectCount;
    private Long targetOptionGroupId;
}
