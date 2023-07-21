package store.cookshoong.www.cookshoongfrontend.common.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 파일 업로드 경로 설정.
 *
 * @author seungyeon
 * @since 2023.07.20
 */
@Getter
@Setter
@Component
@ConfigurationProperties("files")
public class PathVo {
    private String saveBasePath;
}
