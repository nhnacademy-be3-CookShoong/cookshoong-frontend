package store.cookshoong.www.cookshoongfrontend.store.model;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 메뉴 그룹 등록 Dto.
 *
 * @author papel
 * @since 2023.07.13
 */
@Getter
@AllArgsConstructor
public class CreateMenuGroupRequestDto {
    @NotBlank
    private String name;
    private String description;
}
