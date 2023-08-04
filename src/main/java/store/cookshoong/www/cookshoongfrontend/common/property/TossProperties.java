package store.cookshoong.www.cookshoongfrontend.common.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import store.cookshoong.www.cookshoongfrontend.payment.model.request.TossPaymentsDto;

/**
 * Redis 기본 설정들을 담고 있는 Properties 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.07.18
 */
@Getter
@AllArgsConstructor
public class TossProperties {
    private TossSecret tossSecret;
    private TossUrl tossUrl;

    /**
     *  Toss 결제 API 에 필요한 데이터 가져오는 메서드.
     *
     * @return      toss Api 필요한 값 반환
     */
    public TossPaymentsDto tossPaymentsDto() {

        return new TossPaymentsDto(tossSecret.getClientId(), tossSecret.getSecret(),
            tossUrl.getSuccessUrl(), tossUrl.getFailUrl());
    }
}
