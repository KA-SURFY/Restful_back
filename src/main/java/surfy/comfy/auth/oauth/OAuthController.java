package surfy.comfy.auth.oauth;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import surfy.comfy.auth.JwtTokenProvider;
import surfy.comfy.auth.oauth.OAuthService;
import surfy.comfy.data.token.TokenResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class OAuthController {
    private final OAuthService oAuthService;
    Logger logger= LoggerFactory.getLogger(OAuthController.class);
    private final JwtTokenProvider jwtTokenProvider;

    @RequestMapping("/auth")
    public void requestAuth(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {

        logger.info("==============================================");
        logger.info("preHandle method: {}",request.getMethod());
        logger.info("JwtToken 호출");
        String accessToken = request.getHeader("accesstoken");
        logger.info("AccessToken: {}",accessToken);
        String refreshToken = request.getHeader("refreshtoken");
        logger.info("RefreshToken: {}",refreshToken);
        logger.info("Request URL: {}",request.getRequestURL());


        if(accessToken.equals("null") && refreshToken.equals("null")){
            logger.info("tokens are null");
            response.setStatus(401);
        }
        else if (!accessToken.equals("null") && jwtTokenProvider.isValidAccessToken(accessToken)) { // 유효한 accessToken
            logger.info(">JwtTokenInterceptor - isValidAccessToken");
            response.setStatus(200);
            String auth_token="Access-Token:"+ accessToken+":Refresh-Token:" + refreshToken;
            Cookie auth_cookie=new Cookie("AuthToken",auth_token);
            auth_cookie.setMaxAge(5);
            response.addCookie(auth_cookie);
        }
        // access token은 유효하지 않고, refresh token은 유효한 경우
        else if(!jwtTokenProvider.isValidAccessToken(accessToken)&&jwtTokenProvider.isValidRefreshToken(refreshToken)){
            logger.info(">JwtTokenInterceptor - invalid AccessToken && valid RefreshToken");
            TokenResponse tokenResponse=oAuthService.issueAccessToken(accessToken, refreshToken);
            if(tokenResponse==null){
                response.setStatus(401);
            }
            else{
                logger.info("New accessToken: {}",tokenResponse.getACCESS_TOKEN());
                logger.info("New refreshToken: {}",tokenResponse.getREFRESH_TOKEN());
                response.setStatus(200);
                String auth_token="Access-Token:"+ tokenResponse.getACCESS_TOKEN()+":Refresh-Token:" + tokenResponse.getREFRESH_TOKEN();
                Cookie auth_cookie=new Cookie("AuthToken",auth_token);
                auth_cookie.setMaxAge(5);
                response.addCookie(auth_cookie);
            }
        }
        // refresh token도 유효하지 않은 경우
        else{
            response.setStatus(401);
        }
    }
}