package store.cookshoong.www.cookshoongfrontend.address.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import store.cookshoong.www.cookshoongfrontend.account.model.vo.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.account.model.vo.AccountIdOnly;
import store.cookshoong.www.cookshoongfrontend.address.model.request.CreateAccountAddressRequestDto;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.util.CookieUtils;

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
public class AddressController implements AccountIdAware {

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
        @ModelAttribute("address") CreateAccountAddressRequestDto createAccountAddressRequestDto,
        Model model, AccountIdOnly account, @RequestParam(required = false) Long ai) {

        // addressId를 사용하여 선택한 주소 정보를 가져옴
        AddressResponseDto addressResponse = null;
        if (ai != null) {
            addressResponse = accountAddressService.selectAccountChoiceAddress(ai);
        }

        AddressResponseDto address =
            accountAddressService.selectAccountAddressRecentRegistration(account.getAccountId());

        List<AccountAddressResponseDto> accountAddresses =
            accountAddressService.selectAccountAddressAll(account.getAccountId());

        if (addressResponse == null) {
            model.addAttribute("latitude", address.getLatitude());
            model.addAttribute("longitude", address.getLongitude());
        } else {
            model.addAttribute("latitude", addressResponse.getLatitude());
            model.addAttribute("longitude", addressResponse.getLongitude());
        }

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
     * @param account                           회원 정보
     * @param redirectAttributes                ?파라미터를 붙여서 값 전달
     * @param response                          서버에 응답 메시지
     * @return                                  현제 주소 등록 페이지로 반환
     */
    @PostMapping
    public String postDoCreateAccountAddress(
        @ModelAttribute("address") @Valid CreateAccountAddressRequestDto createAccountAddressRequestDto,
        BindingResult bindingResult, Model model, AccountIdOnly account, RedirectAttributes redirectAttributes,
        HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("url", "maps");
            return "address/kakao-maps";
        }

        accountAddressService.createAccountAddress(account.getAccountId(), createAccountAddressRequestDto);

        AddressResponseDto address =
            accountAddressService.selectAccountAddressRecentRegistration(account.getAccountId());

        log.info("ADDRESS ID: {}", address.getId());

        redirectAttributes.addAttribute("ai", address.getId());

        CookieUtils.addCookie(response, "addressId", address.getId().toString());

        return "redirect:/accounts/addresses/maps";
    }

    /**
     * 회원이 주소록 중 선택한 주소.
     *
     * @param id                        주소 아이디
     * @param redirectAttributes        ?파라미터를 붙여서 값 전달
     * @param response                  서버에 응답 메시지
     * @return                          현재 주소 등록 페이지로 반환
     */
    @GetMapping("/{id}/select")
    public String getSelectAccountAddress(@PathVariable Long id,
                                          RedirectAttributes redirectAttributes,
                                          HttpServletResponse response) {

        redirectAttributes.addAttribute("ai", id);

        CookieUtils.addCookie(response, "addressId", id.toString());

        return "redirect:/accounts/addresses/maps";
    }

    /**
     * 회원이 회원 가입을 하면 현재 url 로 Redirect 된다.
     * 이유: 회원 가입 하면 바로 3km 이내에 위치한 주소를 가져오기 위해서 만듬.
     * 이렇게 한번 들려야 주소 아이디를 쿠키에 저장하고 저장한 값을 이용해 3km 이내 매장을 볼 수 있음.
     *
     * @return          회원이 주소 등록과 모든 주소를 보여주는 페이지로 반환
     */
    /**
     * 회원이 회원 가입을 하면 현재 url 로 Redirect 된다.
     * 이유: 회원 가입 하면 바로 3km 이내에 위치한 주소를 가져오기 위해서 만듬.
     * 이렇게 한번 들려야 주소 아이디를 쿠키에 저장하고 저장한 값을 이용해 3km 이내 매장을 볼 수 있음.
     *
     * @param account           회원 정보
     * @param response          서버에 응답 메시지
     * @return                  랜딩 페이지로 반환
     */
    @GetMapping("/create")
    public String getCreateAccountAddressId(AccountIdOnly account,
                                            HttpServletResponse response) {

        AddressResponseDto address = accountAddressService.selectAccountAddressRecentRegistration(account.getAccountId());

        CookieUtils.addCookie(response, "addressId", address.getId().toString());

        return "redirect:/";
    }


    /**
     * 회원이 등록한 주소를 삭제하는 메서드.
     *
     * @param id        주소 아이디
     * @param account           회원 정보
     * @return          회원이 주소 등록과 모든 주소를 보여주는 페이지로 반환
     */
    @GetMapping("/{id}/delete")
    public String getDeleteAccountAddress(@PathVariable Long id, AccountIdOnly account) {

        accountAddressService.removeAccountAddress(account.getAccountId(), id);

        return "redirect:/accounts/addresses/maps";
    }
}
