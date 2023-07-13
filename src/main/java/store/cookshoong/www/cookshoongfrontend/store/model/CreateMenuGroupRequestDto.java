package store.cookshoong.www.cookshoongfrontend.store.model;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 메뉴 그룹 등록 Dto.
 *
 * @author papel
 * @since 2023.07.13
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMenuGroupRequestDto {
    @NotBlank
    private String name;
    private String description;
}
