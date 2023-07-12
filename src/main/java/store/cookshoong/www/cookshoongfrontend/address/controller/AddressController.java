package store.cookshoong.www.cookshoongfrontend.address.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import store.cookshoong.www.cookshoongfrontend.address.model.request.CreateAccountAddressRequestDto;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
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

    /**
     * 회원이 주소를 등록하는 페이지와 가지고 있는 모든 주소를 보여주는 메서드.
     *
     * @param createAccountAddressRequestDto    회원이 주소를 등록하는 Dto
     * @param model                             HTML 로 보낼 데이터
     * @return                                  회원이 주소 등록과 모든 주소를 보여주는 페이지로 반환
     */
    @GetMapping
    public String getCreateAccountAddress(
        @ModelAttribute("address") CreateAccountAddressRequestDto createAccountAddressRequestDto, Model model) {

        List<AccountAddressResponseDto> accountAddresses = accountAddressService.selectAccountAddressAll(1L);

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
     * @return                                  회원이 주소 등록과 모든 주소를 보여주는 페이지로 반환
     */
    @PostMapping
    public String postDoCreateAccountAddress(
        @ModelAttribute("address") @Valid CreateAccountAddressRequestDto createAccountAddressRequestDto,
        BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("url", "maps");
            return "address/kakao-maps";
        }

        accountAddressService.createAccountAddress(1L, createAccountAddressRequestDto);

        return "redirect:/accounts/addresses/maps";
    }

    /**
     * 회원이 등록한 주소를 삭제하는 메서드.
     *
     * @param id        주소 아이디
     * @return          회원이 주소 등록과 모든 주소를 보여주는 페이지로 반환
     */
    @GetMapping("/{id}/delete")
    public String getDeleteAccountAddress(@PathVariable Long id) {

        accountAddressService.deleteAccountAddress(1L, id);

        return "redirect:/accounts/addresses/maps";
    }
}
