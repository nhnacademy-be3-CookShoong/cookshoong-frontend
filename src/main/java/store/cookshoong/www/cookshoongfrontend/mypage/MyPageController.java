package store.cookshoong.www.cookshoongfrontend.mypage;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import store.cookshoong.www.cookshoongfrontend.account.service.AccountIdAware;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectAllStoresResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreService;

/**
 * 마이페이지 컨트롤러.
 *
 * @author papel (윤동현)
 * @since 2023.08.06
 */
@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final StoreService storeService;
    private final AccountIdAware accountIdAware;

    @GetMapping("/my-page")
    public String getMyPage(Model model) {
        Long accountId = accountIdAware.getAccountId();
        List<SelectAllStoresResponseDto> businessStoreList = storeService.selectStores(accountId);
        model.addAttribute("businessStoreList", businessStoreList);

        return "account/my-page";
    }
}
