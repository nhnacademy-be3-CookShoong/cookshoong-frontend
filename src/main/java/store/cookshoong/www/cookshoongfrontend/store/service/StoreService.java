package store.cookshoong.www.cookshoongfrontend.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.account.model.vo.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.store.adapter.StoreAdapter;
import store.cookshoong.www.cookshoongfrontend.store.model.CreateStoreRequestDto;

/**
 * 매장의 등록 및 조회를 위한 컨트롤러.
 *
 * @author papel
 * @since 2023.07.09
 */
@Service
@RequiredArgsConstructor
public class StoreService implements AccountIdAware{
    private final StoreAdapter storeAdapter;

    /**
     * 매장 신규등록 요청을 보냄.
     *
     * @param accountId             회원 기본키
     * @param createStoreRequestDto 매장 신규등록 요청 정보
     */
    public void createStore(Long accountId, CreateStoreRequestDto createStoreRequestDto) {
        storeAdapter.executeCreateStore(accountId, createStoreRequestDto);
    }
}
