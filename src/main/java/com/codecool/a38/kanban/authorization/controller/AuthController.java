package com.codecool.a38.kanban.authorization.controller;

import com.codecool.a38.kanban.authorization.model.AppData;
import com.codecool.a38.kanban.authorization.model.OAuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class AuthController {

    @Value("${gitlabServer.url}")
    private String gitlabServerUrl;

    private RestTemplate restTemplate;

    public AuthController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * With the received code and application data, it sends a request to gitlab to get the token
     * as described in gitlab's Web application OAuth2 flow (https://docs.gitlab.com/ee/api/oauth2.html).
     * After that the token is put into an HTTP only cookie and the cookie is added to the response.
     * The cookie's max age is set either to the token's expiration time (if it exists) or one week.
     *
     * @param response the HttpServletResponse
     * @param code     the code received in query parameter
     * @param appData  the body that contains appId, appSecret, redirectUri
     * @return information about the result
     */
    @CrossOrigin(origins = "https://a38-gitlab-kanban.herokuapp.com/", allowCredentials = "true")
    @PostMapping("/getToken")
    public ResponseEntity<String> getToken(HttpServletResponse response, @RequestParam String code,
                                           @RequestBody AppData appData) {
        String gitlabServerOauthTokenUrl = gitlabServerUrl + "/oauth/token";
        String parameters = "?client_id=" + appData.getAppId() +
                "&client_secret=" + appData.getAppSecret() +
                "&code=" + code +
                "&grant_type=authorization_code" +
                "&redirect_uri=" + appData.getRedirectUri();

        HttpEntity<String> request = new HttpEntity<>(null);
        String urlWithParameters = gitlabServerOauthTokenUrl + parameters;
        log.debug("Sending request for authentication token: urlWithParameters=<" + urlWithParameters + ">");
        OAuthResponse oAuthResponse = restTemplate.postForEntity(urlWithParameters,
                request, OAuthResponse.class).getBody();
        if (oAuthResponse != null) {
            String gitlabAccessToken = oAuthResponse.getAccess_token();
            log.info("gitlab access token received from gitlab: " + gitlabAccessToken);
            log.info("oAuthResponse: " + oAuthResponse);

            Cookie cookie = new Cookie("gitlabAccessToken", gitlabAccessToken);

            int weekInSeconds = 60 * 60 * 24 * 7;
            int maxAge = oAuthResponse.getExpires_in() != null ? oAuthResponse.getExpires_in() : weekInSeconds;
            log.info("oAuthResponse.getExpires_in()=<" + oAuthResponse.getExpires_in() + ">, maxAge=<" + maxAge + ">");
            cookie.setMaxAge(maxAge);
            cookie.setSecure(true);

            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.setHeader("Access-Control-Allow-Origin", "https://a38-gitlab-kanban.herokuapp.com");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, *");
            response.addCookie(cookie);

            return ResponseEntity.ok("accessToken saved in cookie: " + gitlabAccessToken);

        } else {
            log.warn("No oauth response returned");
            return ResponseEntity.ok("OAuthResponse not returned");
        }
    }

}
