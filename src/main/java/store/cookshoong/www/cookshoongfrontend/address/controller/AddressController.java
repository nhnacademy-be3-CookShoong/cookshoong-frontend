package store.cookshoong.www.cookshoongfrontend.address.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.address.model.request.CreateAccountAddressRequestDto;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;

/**
 * 주소에 대한 컨트롤러.
 *
 * @author jeongjewan
 * @since 2023.07.09
 */
@Slf4j
@Controller
@RequestMapping("/accounts/addresses/maps")
@RequiredArgsConstructor
public class AddressController {

    private final AccountAddressService accountAddressService;
    private final AccountIdAware account;

    /**
     * 회원이 주소를 등록하는 페이지와 가지고 있는 모든 주소를 보여주는 메서드.
     *
     * @param createAccountAddressRequestDto    회원이 주소를 등록하는 Dto
     * @param model                             HTML 로 보낼 데이터
     * @return                                  회원이 주소 등록과 모든 주소를 보여주는 페이지로 반환
     */
    @GetMapping
    public String getCreateAccountAddress(
        @ModelAttribute("address") CreateAccountAddressRequestDto createAccountAddressRequestDto,
        Model model) {


        AddressResponseDto address =
            accountAddressService.selectAccountAddressRenewalAt(account.getAccountId());

        List<AccountAddressResponseDto> accountAddresses =
            accountAddressService.selectAccountAddressAll(account.getAccountId());

        model.addAttribute("latitude", address.getLatitude());
        model.addAttribute("longitude", address.getLongitude());
        model.addAttribute("accountAddresses", accountAddresses);
        model.addAttribute("url", "maps");

        return "address/kakao-maps";
    }

    /**
     * 회원이 주소를 등록하는 메서드.
     *
     * @param createAccountAddressRequestDto    회원이 주소를 등록하는 Dto
     * @param bindingResult                     입력에 대한 검증 결과
     * @param model                             HTML 로 보낼 데이터
     * @return                                  현제 주소 등록 페이지로 반환
     */
    @PostMapping
    public String postDoCreateAccountAddress(
        @ModelAttribute("address") @Valid CreateAccountAddressRequestDto createAccountAddressRequestDto,
        BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("url", "maps");
            return "address/kakao-maps";
        }

        accountAddressService.createAccountAddress(account.getAccountId(), createAccountAddressRequestDto);

        return "redirect:/accounts/addresses/maps";
    }

    /**
     * 회원이 선택한 주소에 대해 갱신 날짜를 업데이트.
     *
     * @param id                        주소 아이디
     * @return                          현재 주소 등록 페이지로 반환
     */
    @PatchMapping("/{id}/select")
    public String patchSelectAccountAddress(@PathVariable Long id) {

        accountAddressService.updateSelectAccountAddressRenewalAt(account.getAccountId(), id);

        return "redirect:/accounts/addresses/maps";
    }

    /**
     * 회원이 등록한 주소를 삭제하는 메서드.
     *
     * @param id        주소 아이디
     * @return          회원이 주소 등록과 모든 주소를 보여주는 페이지로 반환
     */
    @DeleteMapping("/{id}/delete")
    public String deleteDeleteAccountAddress(@PathVariable Long id) {

        accountAddressService.removeAccountAddress(account.getAccountId(), id);

        return "redirect:/accounts/addresses/maps";
    }
}
