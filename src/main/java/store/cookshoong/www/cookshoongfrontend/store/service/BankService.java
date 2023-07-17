package store.cookshoong.www.cookshoongfrontend.store.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.store.adapter.BankAdapter;
import store.cookshoong.www.cookshoongfrontend.store.adapter.StoreAdapter;
import store.cookshoong.www.cookshoongfrontend.store.model.response.SelectAllBanksResponseDto;
import store.cookshoong.www.cookshoongfrontend.store.model.response.SelectAllMerchantsResponseDto;

/**
 * 은행관련 서비스 코드.
 * 은행의 리스트를 조회
 *
 * @author seungyeon
 * @since 2023.07.14
 */
@Service
@RequiredArgsConstructor
public class BankService {
    private final BankAdapter bankAdapter;

    public List<SelectAllBanksResponseDto> selectAllBanks(){
        return bankAdapter.fetchAllBanks();
    }
}
