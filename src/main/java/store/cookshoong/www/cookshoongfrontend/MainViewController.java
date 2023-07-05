package store.cookshoong.www.cookshoongfrontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 각 메인 뷰 페이지를 연결하는 컨트롤러.
 *
 * @author koesnam
 * @since 2023.07.04
 */
@Controller
public class MainViewController {

    /**
     * 메인 뷰 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.05
     */
    @GetMapping("/index")
    public String indexGetMapping() {
        return "index";
    }

    /**
     * 상점 뷰 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.05
     */
    @GetMapping("store")
    public String storeGetMapping() {
        return "store";
    }

    /**
     * 상점 등록 뷰 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.05
     */
    @GetMapping("storeregistry")
    public String storeRegistryGetMapping() {
        return "storeregistry";
    }












    /**
     * 시스템 관리자를 위한 뷰 페이지를 맵핑.
     *
     * @author koesnam
     * @since 2023.07.04
     */
    @GetMapping("admin")
    public String adminMain() {
        return "/admin/index";
    }
}
