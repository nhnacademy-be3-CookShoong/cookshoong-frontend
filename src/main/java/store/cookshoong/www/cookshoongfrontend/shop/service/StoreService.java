package store.cookshoong.www.cookshoongfrontend.shop.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.account.model.vo.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.shop.adapter.StoreAdapter;
import store.cookshoong.www.cookshoongfrontend.shop.exception.UpdateStatusFailureException;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateStoreRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateStoreStatusRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStatusResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoreForUserResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoresNotOutedResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.util.RestResponsePage;

/**
 * 매장 등록 및 조회 서비스.
 *
 * @author papel
 * @since 2023.07.09
 */

@Service
@RequiredArgsConstructor
public class StoreService implements AccountIdAware {
    private final StoreAdapter storeAdapter;

    /**
     * 매장 등록 메서드.
     *
     * @param accountId             회원 아이디
     * @param createStoreRequestDto 매장 등록 Dto
     */
    public void createStore(Long accountId, CreateStoreRequestDto createStoreRequestDto) {
        storeAdapter.executeCreateStore(accountId, createStoreRequestDto);
    }

    /**
     * 3km 이내 매장 리스트 조회 메서드.
     *
     * @param addressId 주소 아이디
     * @param pageable  페이지 파라미터
     */
    public RestResponsePage<SelectStoresNotOutedResponseDto> selectStoresNotOuted(Long addressId, Pageable pageable) {
        return storeAdapter.fetchStoresNotOuted(addressId, pageable);
    }

    /**
     * 매장 조회 메서드.
     *
     * @param storeId 매장 아이디
     */
    public SelectStoreForUserResponseDto selectStoreForUser(Long storeId) {
        return storeAdapter.fetchStoreForUser(storeId);
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

}
