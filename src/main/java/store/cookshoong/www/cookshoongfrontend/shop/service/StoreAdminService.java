package store.cookshoong.www.cookshoongfrontend.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.common.util.RestResponsePage;
import store.cookshoong.www.cookshoongfrontend.shop.adapter.AdminAdapter;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateBankRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateCategoriesRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateMerchantRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateCategoryRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllBanksResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllCategoriesResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllMerchantsResponseDto;

/**
 * 관리자 : 매장관련 관리자 페이지에서 필요한 사항들.
 * 은행, 가맹점, 카테고리 관련 조회, 수정, 삭제, 추가.
 *
 * @author seungyeon
 * @since 2023.07.24
 */
@Service
@RequiredArgsConstructor
public class StoreAdminService {
    private final AdminAdapter adminAdapter;

    /**
     * 은행 페이지 조회 서비스.
     *
     * @param pageable the pageable
     * @return the rest response page
     */
    public RestResponsePage<SelectAllBanksResponseDto> selectAllBanks(Pageable pageable) {
        return adminAdapter.fetchBanksPage(pageable);
    }

    /**
     * 은행 등록 서비스 코드.
     *
     * @param bankRequestDto the bank request dto
     */
    public void createBank(CreateBankRequestDto bankRequestDto) {
        adminAdapter.executeCreateBank(bankRequestDto);
    }

    /**
     * 가맹점 페이지 조회 서비스.
     *
     * @param pageable the pageable
     * @return the rest response page
     */
    public RestResponsePage<SelectAllMerchantsResponseDto> selectAllMerchants(Pageable pageable) {
        return adminAdapter.fetchMerchantsPage(pageable);
    }

    /**
     * 가맹점 등록 서비스.
     *
     * @param createMerchantRequestDto the create merchant request dto
     */
    public void createMerchant(CreateMerchantRequestDto createMerchantRequestDto) {
        adminAdapter.executeCreateMerchant(createMerchantRequestDto);
    }

    /**
     * 가맹점 삭제 서비스.
     *
     * @param merchantId the merchant id
     */
    public void removeMerchant(Long merchantId) {
        adminAdapter.eraseMerchant(merchantId);
    }

    /**
     * 카테고리 조회 페이지 서비스.
     *
     * @param pageable the pageable
     * @return the rest response page
     */
    public RestResponsePage<SelectAllCategoriesResponseDto> selectAllCategories(Pageable pageable) {
        return adminAdapter.fetchCategoriesPage(pageable);
    }

    /**
     * 카테고리 등록 서비스.
     *
     * @param categoriesRequestDto the categories request dto
     */
    public void createCategory(CreateCategoriesRequestDto categoriesRequestDto) {
        adminAdapter.executeCreateCategories(categoriesRequestDto);
    }

    /**
     * 카테고리 삭제 서비스.
     *
     * @param categoryCode the category code
     */
    public void removeCategory(String categoryCode) {
        adminAdapter.eraseCategory(categoryCode);
    }

    /**
     * 카테고리 수정 서비스.
     *
     * @param updateCategoryRequestDto the update category request dto
     * @param code                     the code
     */
    public void updateCategory(UpdateCategoryRequestDto updateCategoryRequestDto,
                               String code) {
        adminAdapter.changeCategory(updateCategoryRequestDto, code);
    }
}
