package store.cookshoong.www.cookshoongfrontend.auth.model.response;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Payco 로그인으로부터 인가받은 후 전달받은 객체를 다루는 클래스.
 *
 * @author koesnam (추만석)
 * @since 2023.08.01
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaycoAuthenticationResponseDto {
    @JsonProperty("data")
    private Attribute body;

    private Attribute getBody() {
        return body;
    }

    public Map<String, Object> getAttributes() {
        return getBody().getAttributes();
    }

    @Getter
    @JsonAutoDetect(creatorVisibility = JsonAutoDetect.Visibility.ANY)
    private static class Attribute {
        @JsonProperty("member")
        private Map<String, Object> attributes;
    }
}
