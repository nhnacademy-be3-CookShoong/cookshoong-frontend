package store.cookshoong.www.cookshoongfrontend.address.controller;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.cookshoong.www.cookshoongfrontend.address.model.response.AddressResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.model.response.SelectAllStoresNotOutedResponseDto;
import store.cookshoong.www.cookshoongfrontend.address.service.AccountAddressService;
import store.cookshoong.www.cookshoongfrontend.common.util.CookieUtils;
import store.cookshoong.www.cookshoongfrontend.common.util.RestResponsePage;

/**
 * 매장 조회에 대한 Controller.
 *
 * @author jeongjewan
 * @since 2023.07.16
 */
@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreSearchController {

    private final AccountAddressService accountAddressService;

    /**
     * 매장 카테고리 선택.
     *
     * @return      매장 카테고리 선택 페이지 반환
     */
    @GetMapping("/store-choice")
    public String getStoreChoice() {

        return "address/store-category-choice";
    }

    /**
     * 선택된 매장들에 위치를 맵에 찍어주는 메서드.
     *
     * @param storeCategoryCode     매장 카테고리 코드
     * @param pageable              페이지
     * @param model                 HTML 로 보낼 데이터
     * @param request               요청 정보를 서블릿에게 전달
     * @return                      지도 북마크 페이지로 반환
     */
    @GetMapping("/store-search")
    public String getStoreSearch(@RequestParam String storeCategoryCode,
                                 Pageable pageable, Model model,
                                 HttpServletRequest request) {

        String addressId = CookieUtils.getCookieValue(request, "addressId");

        assert addressId != null;

        // addressId를 사용하여 선택한 주소 정보를 가져옴
        AddressResponseDto addressResponse = accountAddressService.selectAccountChoiceAddress(Long.valueOf(addressId));

        RestResponsePage<SelectAllStoresNotOutedResponseDto> store =
            accountAddressService.selectAllStoresNotOutedResponseDto(83L, storeCategoryCode, pageable);

        model.addAttribute("latitude", addressResponse.getLatitude());
        model.addAttribute("longitude", addressResponse.getLongitude());
        model.addAttribute("store", store.getContent());

        return "address/kakao-maps-markers";
    }

}
