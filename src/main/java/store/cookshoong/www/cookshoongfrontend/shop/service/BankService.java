package store.cookshoong.www.cookshoongfrontend.shop.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.shop.adapter.BankAdapter;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllBanksResponseDto;

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
