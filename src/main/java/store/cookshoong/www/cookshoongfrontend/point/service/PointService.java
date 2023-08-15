package store.cookshoong.www.cookshoongfrontend.point.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.point.adapter.PointAdapter;
import store.cookshoong.www.cookshoongfrontend.point.model.response.PointLogResponseDto;

/**
 * 포인트 서비스.
 *
 * @author eora21 (김주호)
 * @since 2023.08.12
 */
@Service
@RequiredArgsConstructor
public class PointService {
    private final PointAdapter pointAdapter;

    /**
     * 자신의 총 포인트 확인.
     *
     * @param accountId the account id
     * @return the int
     */
    public int selectPoint(Long accountId) {
        return pointAdapter.fetchPoint(accountId)
            .getPoint();
    }

    /**
     * 내 포인트 기록 확인.
     *
     * @param accountId the account id
     * @param pageable  the pageable
     * @return the page
     */
    public Page<PointLogResponseDto> selectOwnPoints(Long accountId, Pageable pageable) {
        return pointAdapter.fetchPointLog(accountId, pageable);
    }
}
