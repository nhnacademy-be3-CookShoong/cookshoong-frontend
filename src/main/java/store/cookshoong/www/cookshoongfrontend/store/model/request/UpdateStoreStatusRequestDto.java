package store.cookshoong.www.cookshoongfrontend.store.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사업자 : 매장의 상태를 변경하는 dto.
 *
 * @author seungyeon
 * @since 2023.07.16
 */
@Getter
@AllArgsConstructor
public class UpdateStoreStatusRequestDto {
    @NotBlank
    private String statusCode;
}
