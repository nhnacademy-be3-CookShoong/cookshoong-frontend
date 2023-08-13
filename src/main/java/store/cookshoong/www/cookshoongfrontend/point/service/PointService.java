package store.cookshoong.www.cookshoongfrontend.point.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.point.adapter.PointAdapter;

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
     * 자신의 포인트 확인.
     *
     * @param accountId the account id
     * @return the int
     */
    public int selectPoint(Long accountId) {
        return pointAdapter.fetchPoint(accountId)
            .getPoint();
    }
}
