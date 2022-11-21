package surfy.comfy.auth;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import surfy.comfy.auth.oauth.OAuthService;
import surfy.comfy.data.token.TokenResponse;
import surfy.comfy.entity.Member;
import surfy.comfy.entity.Token;
import surfy.comfy.exception.token.InvalidRefreshToken;
import surfy.comfy.repository.MemberRepository;
import surfy.comfy.repository.TokenRepository;
import surfy.comfy.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final OAuthService oAuthService;
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;
    Logger logger= LoggerFactory.getLogger(JwtTokenInterceptor.class);

    // preHandle -> 컨트롤러의 메서드에 매핑된 특정 URI가 호출됐을 때 실행되는 메서드, 컨트롤러를 접근하기 직전에 실행되는 메서드.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        logger.info("==============================================");
        logger.info("preHandle method: {}",request.getMethod());
        logger.info("JwtToken 호출");
        String accessToken = request.getHeader("accesstoken");
        logger.info("AccessToken: {}",accessToken);
        String refreshToken = request.getHeader("refreshtoken");
        logger.info("RefreshToken: {}",refreshToken);
        logger.info("Request URL: {}",request.getRequestURL());

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }
        if(accessToken.equals("null") && refreshToken.equals("null")){
            logger.info("tokens are null");
            throw new InvalidRefreshToken();

        }
        else if (!accessToken.equals("null") && jwtTokenProvider.isValidAccessToken(accessToken)) { // 유효한 accessToken
            logger.info(">JwtTokenInterceptor - isValidAccessToken");
            return true;
        }
        // access token은 유효하지 않고, refresh token은 유효한 경우
        else if(!jwtTokenProvider.isValidAccessToken(accessToken)&&jwtTokenProvider.isValidRefreshToken(refreshToken)){
            logger.info(">JwtTokenInterceptor - invalid AccessToken && valid RefreshToken");
            TokenResponse tokenResponse=oAuthService.issueAccessToken(request);
            logger.info("[interceptor]");
            logger.info("accessToken: {}",tokenResponse.getACCESS_TOKEN());
            logger.info("refreshToken: {}",tokenResponse.getREFRESH_TOKEN());
//            response.addHeader("ACCESS_TOKEN",tokenResponse.getACCESS_TOKEN());
//            response.addHeader("REFRESH_TOKEN",String.valueOf(tokenResponse.getREFRESH_TOKEN()));
//            response.addHeader("msg", "Reissue access token.");
            return true;
        }
        // refresh token도 유효하지 않은 경우
        else{
//            Token token=tokenRepository.findByRefreshTokenIdxEncrypted(refreshToken).get();
//            logger.info("try to delete token");
//            tokenRepository.delete(token);
            throw new InvalidRefreshToken();
        }

//        response.setStatus(401);
//        response.setHeader("ACCESS_TOKEN", accessToken);
//        response.setHeader("REFRESH_TOKEN", refreshToken);
//        response.setHeader("msg", "Check the tokens.");
//        logger.info("response: {}",response);
        //return true;
        //return false;
    }
}
