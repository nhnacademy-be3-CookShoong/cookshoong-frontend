package store.cookshoong.www.cookshoongfrontend.address.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.cookshoong.www.cookshoongfrontend.address.adapter.AccountAddressAdapter;
import store.cookshoong.www.cookshoongfrontend.address.model.request.CreateAccountAddressRequestDto;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AddressResponseDto;

/**
 * 회원과 주소에 대한 Service.
 *
 * @author jeongjewan
 * @since 2023.07.09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountAddressService {

    private final AccountAddressAdapter accountAddressAdapter;

    /**
     * 회원이 주소를 등록하는 메서드.
     *
     * @param accountId                         회원 기본키
     * @param createAccountAddressRequestDto    회원이 주소를 등록하는 Dto
     */
    public void createAccountAddress(Long accountId, CreateAccountAddressRequestDto createAccountAddressRequestDto) {

        accountAddressAdapter.createAccountAddress(accountId, createAccountAddressRequestDto);
    }

    /**
     * 회원이 가지고 있는 모든 주소에 대한 메서드.
     *
     * @param accountId         회원 기본키
     * @return                  회원이 가지고 있는 모든 주소를 반환
     */
    public List<AccountAddressResponseDto> selectAccountAddressAll(Long accountId) {

        return accountAddressAdapter.selectAccountAddressAll(accountId);
    }

    /**
     * 회원이 최근에 등록한 주소를 가져오는 메서드.
     *
     * @param accountId         회원  기본키
     * @return                  회원이 최근에 등록한 주소와 좌표를 가져옴
     */
    public AddressResponseDto selectAccountAddressRecentRegistration(Long accountId) {

        return accountAddressAdapter.selectAccountAddressRecentRegistration(accountId);
    }

    /**
     * 회원에 주소를 삭제하는 메서드.
     *
     * @param accountId         회원 기본키
     * @param addressId         주소 아이디
     */
    public void deleteAccountAddress(Long accountId, Long addressId) {

        accountAddressAdapter.deleteAccountAddress(accountId, addressId);
    }
}
