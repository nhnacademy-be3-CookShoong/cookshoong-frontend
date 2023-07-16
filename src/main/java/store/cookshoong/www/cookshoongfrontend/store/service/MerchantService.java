package store.cookshoong.www.cookshoongfrontend.store.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.store.adapter.MerchantAdapter;
import store.cookshoong.www.cookshoongfrontend.store.model.response.SelectAllMerchantsResponseDto;

/**
 * 가맹점 관련 서비스 코드.
 *
 * @author seungyeon
 * @since 2023.07.14
 */
@Service
@RequiredArgsConstructor
public class MerchantService {
    private final MerchantAdapter merchantAdapter;

    public List<SelectAllMerchantsResponseDto> selectAllMerchants(){
        return merchantAdapter.fetchAllMerchants();
    }
}
