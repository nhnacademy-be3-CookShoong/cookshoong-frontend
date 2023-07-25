package store.cookshoong.www.cookshoongfrontend.shop.service;

import java.util.List;
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

    public RestResponsePage<SelectAllBanksResponseDto> selectAllBanks(Pageable pageable){
        return adminAdapter.fetchBanksPage(pageable);
    }

    public void createBank(CreateBankRequestDto bankRequestDto){
        adminAdapter.executeCreateBank(bankRequestDto);
    }

    public RestResponsePage<SelectAllMerchantsResponseDto> selectAllMerchants(Pageable pageable){
        return adminAdapter.fetchMerchantsPage(pageable);
    }

    public void createMerchant(CreateMerchantRequestDto createMerchantRequestDto){
        adminAdapter.executeCreateMerchant(createMerchantRequestDto);
    }
    public void removeMerchant(Long merchantId){
        adminAdapter.eraseMerchant(merchantId);
    }

    public RestResponsePage<SelectAllCategoriesResponseDto> selectAllCategories(Pageable pageable){
        return adminAdapter.fetchCategoriesPage(pageable);
    }

    public void createCategory(CreateCategoriesRequestDto categoriesRequestDto){
        adminAdapter.executeCreateCategories(categoriesRequestDto);
    }
    public void removeCategory(String categoryCode){
        adminAdapter.eraseCategory(categoryCode);
    }

    public void updateCategory(UpdateCategoryRequestDto updateCategoryRequestDto,
                               String code){
        adminAdapter.changeCategory(updateCategoryRequestDto, code);
    }
}
