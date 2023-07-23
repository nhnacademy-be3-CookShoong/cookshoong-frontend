package store.cookshoong.www.cookshoongfrontend.shop.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateOptionGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateOptionRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionGroupResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.response.SelectOptionResponseDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreOptionManagerService;

/**
 * 매장 옵션 관리 페이지 컨트롤러.
 *
 * @author papel (윤동현)
 * @since 2023.07.11
 */
@Controller
@RequiredArgsConstructor
public class StoreOptionManagerController {
    private final StoreOptionManagerService storeOptionManagerService;

    /**
     * 매장 옵션 관리 페이지 맵핑.
     *
     * @author papel
     * @since 2023.07.11
     */
    @GetMapping("/stores/{storeId}/store-option-manager")
    public String getSelectStoreMenuRegister(
        @ModelAttribute("createOptionRequestDto") CreateOptionRequestDto createOptionRequestDto,
        @ModelAttribute("createOptionGroupRequestDto") CreateOptionGroupRequestDto createOptionGroupRequestDto,
        @PathVariable("storeId") Long storeId,
        Model model) {

        List<SelectOptionResponseDto> options = storeOptionManagerService.selectOptions(storeId);
        model.addAttribute("options", options);
        List<SelectOptionGroupResponseDto> optionGroups = storeOptionManagerService.selectOptionGroups(storeId);
        model.addAttribute("optionGroups", optionGroups);

        model.addAttribute("createOptionRequestDto", createOptionRequestDto);
        model.addAttribute("createOptionGroupRequestDto", createOptionGroupRequestDto);
        return "store/menu/store-option-manager";
    }

    /**
     * 매장 옵션 그룹 등록 요청 맵핑.
     *
     * @author papel
     * @since 2023.07.13
     */
    @PostMapping("/stores/{storeId}/store-option-group-manager")
    public String postCreateOptionGroup(
        @Valid @ModelAttribute("createOptionGroupRequestDto") CreateOptionGroupRequestDto createOptionGroupRequestDto,
        @PathVariable("storeId") Long storeId,
        BindingResult bindingResult) {
        storeOptionManagerService.createOptionGroup(storeId, createOptionGroupRequestDto);
        return "redirect:/store-option-manager";
    }

    /**
     * 매장 옵션 등록 요청 맵핑.
     *
     * @author papel
     * @since 2023.07.13
     */
    @PostMapping("/stores/{storeId}/store-option-manager")
    public String postCreateOption(
        @Valid @ModelAttribute("createOptionRequestDto") CreateOptionRequestDto createOptionRequestDto,
        @PathVariable("storeId") Long storeId,
        BindingResult bindingResult) {
        storeOptionManagerService.createOption(1L, storeId, createOptionRequestDto);
        return "redirect:/store-option-manager";
    }
}
