package store.cookshoong.www.cookshoongfrontend.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.util.Base64Utils;
import store.cookshoong.www.cookshoongfrontend.auth.model.vo.ParsedAccessToken;

/**
 * .
 *
 * @author koesnam (추만석)
 * @since 2023.07.16
 */
class JwtResolverTest {

    @Test
    void resolveToken() throws JsonProcessingException {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJhdXRob3JpdHkiOiJDVVNUT01FUiIsImp0aSI6ImRiNmRlYWQ2LTMzZTEtNDhiOC1iOTkyLWJhNmQyZGVjMzdiMCIsImV4cCI6MTY4OTQ4ODI3N30.Rj4m-_7m7BFuD3nT_7-Fpd3_L_8ftLvheRBQ1vMHcPE";
        String payload = token.split("\\.")[1];
        String claims = new String(Base64Utils.decodeFromString(payload));
        System.out.println(claims);

        ObjectMapper objectMapper = new ObjectMapper();
        ParsedAccessToken parsedAccessToken = objectMapper.readValue(claims, ParsedAccessToken.class);
        System.out.println(parsedAccessToken.getJti());
        System.out.println(parsedAccessToken.getAuthority());
        System.out.println(parsedAccessToken.getExp());

        assertThat(parsedAccessToken.getJti(), is("db6dead6-33e1-48b8-b992-ba6d2dec37b0"));
        assertThat(parsedAccessToken.getAuthority(), is("CUSTOMER"));
        assertThat(parsedAccessToken.getExp(), is("1689488277"));
    }
}
