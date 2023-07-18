package store.cookshoong.www.cookshoongfrontend.shop.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateOptionGroupRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.model.request.CreateOptionRequestDto;
import store.cookshoong.www.cookshoongfrontend.shop.service.StoreOptionManagerService;

/**
 * 매장 옵션 관리 페이지 컨트롤러.
 *
 * @author papel
 * @since 2023.07.11
 */
@Controller
@RequiredArgsConstructor
public class StoreOptionManagerController {
    private final StoreOptionManagerService storeOptionManagerService;

    /**
     * 매장 옵션 관리 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.11
     */
    @GetMapping("/store-option-manager")
    public String getSelectStoreMenuRegister(
        @ModelAttribute("createOptionRequestDto") CreateOptionRequestDto createOptionRequestDto,
        @ModelAttribute("createOptionGroupRequestDto") CreateOptionGroupRequestDto createOptionGroupRequestDto,
        Model model) {
        model.addAttribute("createOptionRequestDto", createOptionRequestDto);
        model.addAttribute("createOptionGroupRequestDto", createOptionGroupRequestDto);
        return "store/menu/store-option-manager";
    }

    /**
     * 매장 옵션 그룹 등록 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.13
     */
    @PostMapping("/store-option-group-manager")
    public String postCreateOptionGroup(
        @Valid @ModelAttribute("createOptionGroupRequestDto") CreateOptionGroupRequestDto createOptionGroupRequestDto,
        BindingResult bindingResult) {
        storeOptionManagerService.createOptionGroup(1L, createOptionGroupRequestDto);
        return "redirect:/";
    }

    /**
     * 매장 옵션 등록 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.13
     */
    @PostMapping("/store-option-manager")
    public String postCreateOption(
        @Valid @ModelAttribute("createOptionRequestDto") CreateOptionRequestDto createOptionRequestDto,
        BindingResult bindingResult) {
        storeOptionManagerService.createOption(1L, 1L, createOptionRequestDto);
        return "redirect:/";
    }
}
