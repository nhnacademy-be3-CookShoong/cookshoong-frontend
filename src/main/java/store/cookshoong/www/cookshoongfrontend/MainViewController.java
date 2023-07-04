package store.cookshoong.www.cookshoongfrontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 각 랜딩페이지를 연결하는 컨트롤러.
 *
 * @author koesnam
 * @since 2023 /07/04
 */
@Controller
public class MainViewController {
    /**
     * 랜딩페이지.
     *
     * @return 랜딩페이지 html명
     */
    @GetMapping("index")
    public String indexGetMapping() {
        return "index";
    }

    /**
     * 랜딩페이지.
     *
     * @return 랜딩페이지 html명
     */
    @GetMapping("store")
    public String menuGetMapping() {
        return "store";
    }

    /**
     * 랜딩페이지.
     *
     * @return 랜딩페이지 html명
     */
    @GetMapping("menu")
    public String bookGetMapping() {
        return "menu";
    }

    /**
     * 랜딩페이지.
     *
     * @return 랜딩페이지 html명
     */
    @GetMapping("template")
    public String aboutGetMapping() {
        return "template";
    }

    /**
     * admin 랜딩페이지.
     *
     * @return 랜딩페이지 html명
     */
    @GetMapping("admin")
    public String adminMain() {
        return "/admin/index";
    }
}
