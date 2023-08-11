package store.cookshoong.www.cookshoongfrontend.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import store.cookshoong.www.cookshoongfrontend.shop.adapter.StoreAdapter;
import store.cookshoong.www.cookshoongfrontend.shop.exception.RegisterFailureException;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateStoreCategoryRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateStoreInfoRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateStoreManagerRequestDto;

/**
 * 매장의 정보 관리를 위한 서비스.
 *
 * @author seongyeon (유승연)
 * @since 2023.07.09
 */
@Service
@RequiredArgsConstructor
public class StoreInfoManagerService {
    private final StoreAdapter storeAdapter;

    /**
     * 사업자 : 해당 매장의 사업자 정보 수정.
     *
     * @param accountId  the account id
     * @param storeId    the store id
     * @param requestDto the request dto
     */
    public void updateStoreManagerInfo(Long accountId, Long storeId, UpdateStoreManagerRequestDto requestDto) {
        ResponseEntity<Void> result = storeAdapter.changeStoreManagerInfo(accountId, storeId, requestDto);
        if (!result.getStatusCode().is2xxSuccessful()) {
            throw new RegisterFailureException(result.getStatusCode());
        }

    }

    /**
     * 사업자 : 해당 매장의 영업점 정보 수정.
     *
     * @param accountId  the account id
     * @param storeId    the store id
     * @param requestDto the request dto
     */
    public void updateStoreInfo(Long accountId, Long storeId, UpdateStoreInfoRequestDto requestDto) {
        ResponseEntity<Void> result = storeAdapter.changeStoreInfo(accountId, storeId, requestDto);
        if (!result.getStatusCode().is2xxSuccessful()) {
            throw new RegisterFailureException(result.getStatusCode());
        }
    }

    /**
     * 사업자 : 해당 매장의 카테고리 변경.
     *
     * @param accountId  the account id
     * @param storeId    the store id
     * @param requestDto the request dto
     */
    public void updateStoreCategoryInfo(Long accountId, Long storeId, UpdateStoreCategoryRequestDto requestDto) {
        ResponseEntity<Void> result = storeAdapter.changeStoreCategoryInfo(accountId, storeId, requestDto);
        if (!result.getStatusCode().is2xxSuccessful()) {
            throw new RegisterFailureException(result.getStatusCode());
        }
    }

    /**
     * 사업자 : 해당 매장의 대표 이미지 수정.
     *
     * @param accountId  the account id
     * @param storeId    the store id
     * @param storeImage the store image
     */
    public void updateStoreImage(Long accountId, Long storeId, MultipartFile storeImage) {
        MultiValueMap<String, Object> fileMultiValueMap = new LinkedMultiValueMap<>();
        fileMultiValueMap.add("uploadImage", storeImage.getResource());

        ResponseEntity<Void> result = storeAdapter.changeStoreImage(accountId, storeId, fileMultiValueMap);
        if (!result.getStatusCode().is2xxSuccessful()) {
            throw new RegisterFailureException(result.getStatusCode());
        }
    }

    /**
     * 사업자 : 해당 매장의 이미지 삭제  -> 쿡슝에서 제공해주는 기본 이미지를 사용함.
     *
     * @param accountId the account id
     * @param storeId   the store id
     */
    public void removeStoreIamge(Long accountId, Long storeId) {
        ResponseEntity<Void> result = storeAdapter.eraseStoreImage(accountId, storeId);
        if (!result.getStatusCode().is2xxSuccessful()) {
            throw new RegisterFailureException(result.getStatusCode());
        }
    }

    /**
     * 매장의 상태를 변경 : 휴식/영업/폐업.
     *
     * @param accountId the account id
     * @param storeId   the store id
     * @param option    the option
     */
    public void updateStoreStatus(Long accountId, Long storeId, String option) {
        ResponseEntity<Void> result = storeAdapter.changeStoreStatus(accountId, storeId, option);
        if (!result.getStatusCode().is2xxSuccessful()) {
            throw new RegisterFailureException(result.getStatusCode());
        }
    }
}
