package store.cookshoong.www.cookshoongfrontend.shop.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 은행 등록 dto.
 *
 * @author seungyeon (유승연)
 * @since 2023.07.24
 */
@Getter
@AllArgsConstructor
public class CreateBankRequestDto {
    @NotBlank
    private String bankCode;
    @NotBlank
    @Size(min = 1, max = 30)
    private String bankName;
}
