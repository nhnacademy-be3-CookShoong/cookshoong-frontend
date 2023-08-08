package store.cookshoong.www.cookshoongfrontend.shop.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import store.cookshoong.www.cookshoongfrontend.file.model.FileDomain;
import store.cookshoong.www.cookshoongfrontend.common.util.RestResponsePage;
import store.cookshoong.www.cookshoongfrontend.shop.adapter.StoreAdapter;
import store.cookshoong.www.cookshoongfrontend.shop.exception.RegisterStoreFailureException;
import store.cookshoong.www.cookshoongfrontend.shop.exception.SelectStoreFailException;
import store.cookshoong.www.cookshoongfrontend.shop.exception.UpdateStatusFailureException;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateStoreRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateStoreStatusRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllBanksResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStatusResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoreForUserResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoreInfoResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoresKeywordSearchResponseDto;

/**
 * 매장 등록 및 조회 서비스.
 *
 * @author papel (윤동현)
 * @since 2023.07.09
 */
@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreAdapter storeAdapter;


    /**
     * 사용자 : 3km 이내 매장 리스트 조회 메서드.
     *
     * @param addressId 주소 아이디
     * @param pageable  페이지 파라미터
     * @return the rest response page
     */
    public RestResponsePage<SelectStoresKeywordSearchResponseDto> selectStoresNotOuted(Long addressId, Pageable pageable) {
        return storeAdapter.fetchStoresNotOuted(addressId, pageable);
    }

    /**
     * 사용자 : 키워드 검색 매장 리스트 조회 메서드.
     *
     * @param keyword  키워드 단어
     * @param pageable 페이지 파라미터
     * @return the rest response page
     */
    public RestResponsePage<SelectStoresKeywordSearchResponseDto> selectStoresByKeyword(String keyword, Long addressId, Pageable pageable) {
        return storeAdapter.fetchStoresByKeyword(keyword, addressId, pageable);
    }


    /**
     * 사업자 : 매장 등록 메서드.
     *
     * @param accountId             회원 아이디
     * @param createStoreRequestDto 매장 등록 Dto
     * @param businessLicense       the business license
     * @param storeImage            the store image
     */
    public String createStore(Long accountId, CreateStoreRequestDto createStoreRequestDto,
                              MultipartFile businessLicense,
                              MultipartFile storeImage) {

        MultiValueMap<String, Object> fileMap = new LinkedMultiValueMap<>();
        fileMap.add("requestDto", createStoreRequestDto);
        fileMap.add(FileDomain.BUSINESS_INFO_IMAGE.getVariable(), businessLicense.getResource());

        if(Objects.nonNull(storeImage)){
            fileMap.add(FileDomain.STORE_IMAGE.getVariable(), storeImage.getResource());
        }

        ResponseEntity<String> response = storeAdapter.executeCreateStore(accountId, fileMap);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RegisterStoreFailureException(response.getStatusCode());
        }
        return Objects.requireNonNull(response.getHeaders().getLocation()).getPath();
    }

    /**
     * 사용자 : 매장 조회 메서드.
     *
     * @param storeId 매장 아이디
     * @return the select store for user response dto
     */
    public SelectStoreForUserResponseDto selectStoreForUser(Long storeId) {
        ResponseEntity<SelectStoreForUserResponseDto> response = storeAdapter.fetchStoreForUser(storeId);
        if(!response.getStatusCode().is2xxSuccessful()){
            throw new SelectStoreFailException(response.getStatusCode());
        }
        return response.getBody();
    }

    /**
     * 사업자 : 은행 리스트 조회.
     *
     * @return the list
     */
    public List<SelectAllBanksResponseDto> selectAllBanks() {
        ResponseEntity<List<SelectAllBanksResponseDto>> response = storeAdapter.fetchAllBanks();
        if (!response.getStatusCode().is2xxSuccessful()){
            throw new SelectStoreFailException(response.getStatusCode());
        }
        return response.getBody();
    }


    /**
     * 사업자 : 사업자가 현재 가지고 있는 매장들 조회.
     *
     * @param accountId the account id
     * @return the rest response page
     */
    public List<SelectAllStoresResponseDto> selectStores(Long accountId) {
        ResponseEntity<List<SelectAllStoresResponseDto>> response = storeAdapter.fetchAllStores(accountId);
        if(!response.getStatusCode().is2xxSuccessful()){
            throw new SelectStoreFailException(response.getStatusCode());
        }
        return response.getBody();
    }

    /**
     * 사업자 : 매장 상태 list 조회.
     *
     * @return the list
     */
    public List<SelectAllStatusResponseDto> selectStatus() {
        return storeAdapter.fetchAllStatus();
    }

    /**
     * 사업자 : 매장 상태 변경.
     *
     * @param accountId  the account id
     * @param storeId    the store id
     * @param requestDto the request dto
     */
    public void updateStatus(Long accountId, Long storeId, UpdateStoreStatusRequestDto requestDto) {
        HttpStatus result = storeAdapter.changeStatus(accountId, storeId, requestDto);
        if (!result.is2xxSuccessful()) {
            throw new UpdateStatusFailureException(result);
        }
    }

    /**
     * 사업자 : 해당 매장 정보 조회 dto.
     *
     * @param accountId the account id
     * @param storeId   the store id
     * @return 매장 정보 조회 dto
     */
    public SelectStoreInfoResponseDto selectStoreInfo(Long accountId, Long storeId) {
        ResponseEntity<SelectStoreInfoResponseDto> response = storeAdapter.fetchStoreInfo(accountId, storeId);
        if (!response.getStatusCode().is2xxSuccessful()){
            throw new SelectStoreFailException(response.getStatusCode());
        }
        return response.getBody();
    }
}
