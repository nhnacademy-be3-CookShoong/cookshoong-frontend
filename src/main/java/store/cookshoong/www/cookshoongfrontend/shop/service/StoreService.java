package store.cookshoong.www.cookshoongfrontend.shop.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.account.model.vo.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.shop.adapter.StoreAdapter;
import store.cookshoong.www.cookshoongfrontend.shop.exception.UpdateStatusFailureException;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateStoreRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateStoreStatusRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStatusResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresNotOutedResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.util.RestResponsePage;

/**
 * 매장의 등록 및 조회를 위한 컨트롤러.
 *
 * @author papel
 * @since 2023.07.09
 */

@Service
@RequiredArgsConstructor
public class StoreService implements AccountIdAware {
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

    /**
     * 사업자 : 사업자가 현재 가지고 있는 매장들 조회.
     *
     * @param accountId the account id
     * @param pageable  the pageable
     * @return the rest response page
     */
    public RestResponsePage<SelectAllStoresResponseDto> selectStores(Long accountId,
                                                                     Pageable pageable) {
        return storeAdapter.fetchAllStores(accountId, pageable);
    }

    public List<SelectAllStatusResponseDto> selectStatus(){
        return storeAdapter.fetchAllStatus();
    }

    public void updateStatus(Long accountId, Long storeId, UpdateStoreStatusRequestDto requestDto){
        HttpStatus httpStatus = storeAdapter.changeStatus(accountId, storeId, requestDto);
        if(!httpStatus.is2xxSuccessful()){
            throw new UpdateStatusFailureException(httpStatus);
        }
    }

    public RestResponsePage<SelectAllStoresNotOutedResponseDto> selectStoresNotOuted(Long addressId,
                                                                                     Pageable pageable) {
        return storeAdapter.fetchAllStoresNotOuted(addressId, pageable);
    }
}
