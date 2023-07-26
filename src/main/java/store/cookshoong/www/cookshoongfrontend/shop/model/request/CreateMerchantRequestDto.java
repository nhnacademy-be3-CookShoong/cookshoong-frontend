package store.cookshoong.www.cookshoongfrontend.shop.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 가맹점 등록 dto.
 *
 * @author seungyeon (유승연)
 * @since 2023.07.24
 */
@Getter
@AllArgsConstructor
public class CreateMerchantRequestDto {
    @NotBlank
    @Size(min = 1, max = 20, message = "1~20자 이내로 입력해주세요")
    private String merchantName;
}
