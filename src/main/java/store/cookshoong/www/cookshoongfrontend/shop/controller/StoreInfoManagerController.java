package store.cookshoong.www.cookshoongfrontend.shop.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.common.util.RestResponsePage;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateStoreRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.UpdateStoreStatusRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStatusResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoreInfoResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;

/**
 * 매장 정보 관리 페이지에서 사용될 컨트롤러.
 *
 * @author papel (윤동현)
 * @contributor seongyeon (유승연)
 * @since 2023.07.09
 */
@Controller
@RequiredArgsConstructor
public class StoreInfoManagerController {

    private final StoreService storeService;
    private final AccountIdAware accountIdAware;

    /**
     * 매장 정보 조회.
     *
     * @param storeId the store id
     * @param model   the model
     * @return 매장 정보
     */
    @GetMapping("/stores/{storeId}/store-info-manager")
    public String getStoreInfo(@PathVariable("storeId") Long storeId, Model model) {
        Long accountId = accountIdAware.getAccountId();

        SelectStoreInfoResponseDto storeInfo = storeService.selectStoreInfo(accountId, storeId);
        model.addAttribute("storeInfo", storeInfo);
        return "store/info/store-info-manager";
    }

    /**
     * 매장 수정 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.09
     */
    @PostMapping("/store-info-manager")
    public String postUpdateStore(@Valid @ModelAttribute("createStoreRequestDto") CreateStoreRequestDto createStoreRequestDto,
                                  BindingResult bindingResult) {
        return "redirect:/";
    }

    @GetMapping("/stores")
    public String getStoresForBusiness(Pageable pageable,
                                       Model model,
                                       UpdateStoreStatusRequestDto requestDto) {
        Long accountId = accountIdAware.getAccountId();
        RestResponsePage<SelectAllStoresResponseDto> stores = storeService.selectStores(accountId, pageable);
        List<SelectAllStatusResponseDto> status = storeService.selectStatus();

        model.addAttribute("status", status);
        model.addAttribute("stores", stores);
        model.addAttribute("updateStoreStatusRequestDto", requestDto);
        model.addAttribute("buttonNumber", 5);
        return "store/info/store-list";
    }
}
