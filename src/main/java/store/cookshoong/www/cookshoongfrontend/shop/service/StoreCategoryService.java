package store.cookshoong.www.cookshoongfrontend.shop.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.shop.adapter.StoreAdapter;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllCategoriesResponseDto;

/**
 * 매장을 나누는 카테고리 관련 서비스.
 *
 * @author seungyeon
 * @since 2023.07.13
 */
@Service
@RequiredArgsConstructor
public class StoreCategoryService {
    private final StoreAdapter storeAdapter;

    public List<SelectAllCategoriesResponseDto> selectAllCategories(){
        return storeAdapter.fetchAllCategories();
    }
}
