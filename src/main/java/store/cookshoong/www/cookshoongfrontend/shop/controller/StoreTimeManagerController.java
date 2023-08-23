package store.cookshoong.www.cookshoongfrontend.shop.controller;

import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.CART_COUNT_ZERO;
import static store.cookshoong.www.cookshoongfrontend.cart.utils.CartConstant.NO_MENU;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AccountAddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.cart.model.vo.CartMenuCountDto;
import store.cookshoong.www.cookshoongfrontend.cart.service.CartService;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateBusinessHourRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateHolidayRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectBusinessHourResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectHolidayResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreTimeManagerService;

/**
 * 매장 영업시간 관리 페이지에서 사용될 컨트롤러.
 *
 * @author papel (윤동현)
 * @since 2023.07.09
 */
@Controller
@RequiredArgsConstructor
public class StoreTimeManagerController {

    private final StoreService storeService;
    private final StoreTimeManagerService storeTimeManagerService;
    private final AccountIdAware accountIdAware;
    private final AccountAddressService accountAddressService;
    private final CartService cartService;

    /**
     * 매장 영업시간 관리 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.10
     */
    @GetMapping("/stores/{storeId}/store-time-manager")
    public String getSelectStoreTimeManager(
        @ModelAttribute("createBusinessHourRequestDto") CreateBusinessHourRequestDto createBusinessHourRequestDto,
        @ModelAttribute("createHolidayRequestDto") CreateHolidayRequestDto createHolidayRequestDto,
        @PathVariable("storeId") Long storeId,
        Model model) {

        Long accountId = accountIdAware.getAccountId();
        List<SelectAllStoresResponseDto> businessStoreList = storeService.selectStores(accountId);
        model.addAttribute("businessStoreList", businessStoreList);
        String storeName = storeService.selectStoreInfo(accountId, storeId).getStoreName();
        model.addAttribute("storeName", storeName);

        List<SelectBusinessHourResponseDto> businessHourList = storeTimeManagerService.selectBusinessHours(storeId);
        List<SelectHolidayResponseDto> holidayList = storeTimeManagerService.selectHolidays(storeId);

        model.addAttribute("nowTime", LocalDate.now());
        model.addAttribute("storeId", storeId);
        model.addAttribute("businessHours", businessHourList);
        model.addAttribute("holidays", holidayList);

        commonInfo(model, accountId);

        return "store/info/store-time-manager";
    }

    /**
     * 매장 영업시간 추가 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.10
     */
    @PostMapping("/stores/{storeId}/business-hour")
    public String postCreateBusinessHour(
        @PathVariable("storeId") Long storeId,
        @Valid @ModelAttribute("createBusinessHourRequestDto") CreateBusinessHourRequestDto createBusinessHourRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "redirect:/stores/"+storeId+"/store-time-manager";
        }
        boolean result = storeTimeManagerService.createBusinessHour(storeId, createBusinessHourRequestDto);
        if (!result){
            return "redirect:/stores/"+storeId+"/store-time-manager?duplicate=true";
        }
        return "redirect:" + "/stores/" + storeId + "/store-time-manager";
    }

    /**
     * 매장 휴업일 추가 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.10
     */
    @PostMapping("/stores/{storeId}/holiday")
    public String postCreateHoliday(
        @Valid @ModelAttribute("createHolidayRequestDto") CreateHolidayRequestDto createHolidayRequestDto,
        @PathVariable("storeId") Long storeId,
        BindingResult bindingResult) {
        storeTimeManagerService.createHoliday(storeId, createHolidayRequestDto);
        return "redirect:" + "/stores/" + storeId + "/store-time-manager";
    }

    /**
     * 매장 영업시간 삭제 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.10
     */
    @DeleteMapping("/stores/{storeId}/business-hour/{businessHourId}")
    public String deleteBusinessHour(
        @PathVariable("storeId") Long storeId, @PathVariable("businessHourId") Long businessHourId) {
        storeTimeManagerService.deleteBusinessHour(storeId, businessHourId);
        return "redirect:" + "/stores/" + storeId + "/store-time-manager";
    }

    /**
     * 매장 휴업일 삭제 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.10
     */
    @DeleteMapping("/stores/{storeId}/holiday/{holidayId}")
    public String deleteHoliday(
        @PathVariable("storeId") Long storeId, @PathVariable("holidayId") Long holidayId) {
        storeTimeManagerService.deleteHoliday(storeId, holidayId);
        return "redirect:" + "/stores/" + storeId + "/store-time-manager";
    }

    private void commonInfo(Model model, Long accountId) {

        List<AccountAddressResponseDto> accountAddresses =
            accountAddressService.selectAccountAddressAll(accountId);
        CartMenuCountDto cartMenuCountDto =
            cartService.selectCartMenuCountAll(String.valueOf(accountId));

        if (cartService.existMenuInCartRedis(String.valueOf(accountId), NO_MENU)) {
            model.addAttribute("count", CART_COUNT_ZERO);
        } else {
            model.addAttribute("count", cartMenuCountDto.getCount());
        }
        model.addAttribute("accountAddresses", accountAddresses);
    }
}
