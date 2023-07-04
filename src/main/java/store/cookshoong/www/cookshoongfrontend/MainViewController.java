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
     * @since 2023.07.04
     */
    @GetMapping("index")
    public String indexGetMapping() {
        return "index";
    }

    /**
     * 상점 뷰 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.04
     */
    @GetMapping("store")
    public String menuGetMapping() {
        return "store";
    }

    /**
     * 메뉴 뷰 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.04
     */
    @GetMapping("menu")
    public String bookGetMapping() {
        return "menu";
    }

    /**
     * 이후 작성될 페이지에 대한 템플릿 페이지를 맵핑.
     *
     * @author dendroh
     * @since 2023.07.04
     */
    @GetMapping("template")
    public String aboutGetMapping() {
        return "template";
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
