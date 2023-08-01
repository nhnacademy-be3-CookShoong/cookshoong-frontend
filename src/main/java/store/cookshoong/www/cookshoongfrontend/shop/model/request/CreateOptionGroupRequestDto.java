package store.cookshoong.www.cookshoongfrontend.shop.model.request;

import javax.validation.constraints.NotBlank;
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
    private Integer minSelectCount;
    private Integer maxSelectCount;
    private Long targetOptionGroupId;
}
