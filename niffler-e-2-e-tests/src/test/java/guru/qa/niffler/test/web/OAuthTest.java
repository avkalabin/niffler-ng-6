package guru.qa.niffler.test.web;

import guru.qa.niffler.api.AuthApi;
import guru.qa.niffler.api.core.RestClient;
import guru.qa.niffler.api.core.ThreadSafeCookieStore;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.utils.OauthUtils;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OAuthTest {
    private static final Config CFG = Config.getInstance();
    private final AuthApi authApi = new RestClient.EmptyClient(CFG.authUrl(), true).create(AuthApi.class);

    String codeVerifier = OauthUtils.generateCodeVerifier();
    String codeChallenge = OauthUtils.generateCodeChallenge(codeVerifier);
    String codeChallengeMethod = "S256";
    String responseType = "code";
    String clientId = "client";
    String scope = "openid";
    String redirectUri = "http://127.0.0.1:3000/authorized";
    String grantType = "authorization_code";

    @Test
    void oauthTest() throws IOException, URISyntaxException {

        authApi.authorize(
                responseType,
                clientId,
                scope,
                redirectUri,
                codeChallenge,
                codeChallengeMethod
        ).execute();

        Response<Void> response = authApi.login(
                ThreadSafeCookieStore.INSTANCE.cookieValue("XSRF-TOKEN"),
                "test1",
                "111"
        ).execute();

        String url = response.raw().request().url().toString();
        URI uri = new URI(url);
        String query = uri.getQuery();
        String code = "";
        String[] keyValue = query.split("=");
        if (keyValue[0].equals("code")) {
            code = keyValue[1];
        }

        String token = Objects.requireNonNull(authApi.token(
                code,
                redirectUri,
                codeVerifier,
                grantType,
                clientId
        ).execute().body()).get("id_token").toString().replace("\"", "");
        assertNotNull(token);
        System.out.println(token);
    }
}

