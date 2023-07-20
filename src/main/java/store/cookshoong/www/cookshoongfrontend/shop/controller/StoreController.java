package store.cookshoong.www.cookshoongfrontend.shop.controller;

import java.net.MalformedURLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.common.model.PathVo;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectMenuResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoreForUserResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectStoreInfoResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreMenuManagerService;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;

/**
 * 매장 뷰 페이지 컨트롤러.
 *
 * @author papel
 * @since 2023.07.18
 */
@Controller
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final StoreMenuManagerService storeMenuManagerService;
    private final AccountIdAware accountIdAware;
    private final PathVo pathVo;

    /**
     * 매장 페이지 맵핑.
     *
     * @author papel
     * @since 2023.07.16
     */
    @GetMapping({"/index/store/{storeId}"})
    public String getIndexStore(Model model, @PathVariable("storeId") Long storeId) {
        SelectStoreForUserResponseDto store = storeService.selectStoreForUser(storeId);
        model.addAttribute("store", store);
        List<SelectMenuResponseDto> menus = storeMenuManagerService.selectMenus(storeId);
        model.addAttribute("menus", menus);
        return "index/store";
    }

    @GetMapping("/stores/{storeId}")
    public String getStoreInfo(@PathVariable("storeId") Long storeId,
                               Model model) {
        Long accountId = accountIdAware.getAccountId();

        SelectStoreInfoResponseDto storeInfo = storeService.selectStoreInfo(accountId, storeId);

        model.addAttribute("storeInfo", storeInfo);
        return "store/info/store-info-manager";
    }

    @ResponseBody
    @GetMapping("/images/{imageName}")
    public Resource getImage(@PathVariable("imageName") String imageName) throws MalformedURLException {
        return new UrlResource("file:"+ pathVo.getMyBasePath()+imageName);
    }
}
