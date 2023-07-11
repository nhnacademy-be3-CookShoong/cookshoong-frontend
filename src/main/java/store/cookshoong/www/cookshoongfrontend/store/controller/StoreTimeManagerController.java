package store.cookshoong.www.cookshoongfrontend.store.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import store.cookshoong.www.cookshoongfrontend.store.model.CreateBusinessHourRequestDto;
import store.cookshoong.www.cookshoongfrontend.store.model.CreateHolidayRequestDto;
import store.cookshoong.www.cookshoongfrontend.store.service.StoreTimeManagerService;

/**
 * 매장 영업시간 관리 페이지에서 사용될 컨트롤러.
 *
 * @author papel
 * @since 2023.07.09
 */
@Controller
@RequiredArgsConstructor
public class StoreTimeManagerController {

    private final StoreTimeManagerService storeTimeManagerService;

    /**
     * 매장 영업시간 관리 페이지를 맵핑.
     *
     * @author papel
     * @since 2023.07.10
     */
    @GetMapping("/store-time-manager")
    public String getSelectStoreTimeManager(@ModelAttribute("createBusinessHourRequestDto") CreateBusinessHourRequestDto createBusinessHourRequestDto,
                                            @ModelAttribute("createHolidayRequestDto") CreateHolidayRequestDto createHolidayRequestDto,
                                            Model model) {
        model.addAttribute("createBusinessHourRequestDto", createBusinessHourRequestDto);
        model.addAttribute("createHolidayRequestDto", createHolidayRequestDto);
        return "store/info/store-time-manager";
    }

    /**
     * 매장 영업시간 추가 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.10
     */
    @PostMapping("/store-time-manager-businesshour")
    public String postCreateBusinessHour(@Valid @ModelAttribute("createBusinessHourRequestDto") CreateBusinessHourRequestDto createBusinessHourRequestDto,
                                    BindingResult bindingResult) {
        storeTimeManagerService.createBusinessHour(createBusinessHourRequestDto);
        return "redirect:/";
    }

    /**
     * 매장 휴업일 추가 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.10
     */
    @PostMapping("/store-time-manager-holiday")
    public String postCreateHoliday(@Valid @ModelAttribute("createHolidayRequestDto") CreateHolidayRequestDto createHolidayRequestDto,
                                         BindingResult bindingResult) {
        storeTimeManagerService.createHoliday(createHolidayRequestDto);
        return "redirect:/";
    }


    /**
     * 매장 영업시간 삭제 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.10
     */
    @DeleteMapping("/store-time-manager-businesshour")
    public String deleteBusinessHour() {
        return "redirect:/";
    }

    /**
     * 매장 휴업일 삭제 요청을 맵핑.
     *
     * @author papel
     * @since 2023.07.10
     */
    @DeleteMapping("/store-time-manager-holiday")
    public String deleteHoliday() {
        return "redirect:/";
    }
}
