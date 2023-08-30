package store.cookshoong.www.cookshoongfrontend.common.controller;

import java.util.StringJoiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 운영환경에서 로그레벨 설정을 위한 컨트롤러.
 *
 * @author koesnam (추만석)
 * @since 2023.08.25
 */
@Slf4j
@RestController
@RequestMapping("/management/logger")
public class LoggerLevelController {

    /**
     * 설정된 로깅레벨로 확인가능한 로그들을 보여주는 엔드포인트.
     *
     * @return the string
     */
    @GetMapping
    public String checkLoggingLevel() {
        StringJoiner displayedLevel = new StringJoiner(", ");
        if (log.isTraceEnabled()) {
            displayedLevel.add("trace");
        }
        if (log.isDebugEnabled()) {
            displayedLevel.add("debug");
        }
        if (log.isInfoEnabled()) {
            displayedLevel.add("info");
        }
        if (log.isWarnEnabled()) {
            displayedLevel.add("warn");
        }
        if (log.isErrorEnabled()) {
            displayedLevel.add("error");
        }
        return displayedLevel.toString();
    }
}
