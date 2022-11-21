package surfy.comfy.auth.oauth;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import surfy.comfy.config.BaseException;
import surfy.comfy.config.BaseResponse;
import surfy.comfy.data.token.TokenResponse;
import surfy.comfy.entity.Token;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
public class OAuthController {
    private final OAuthService oAuthService;
    Logger logger= LoggerFactory.getLogger(OAuthController.class);
    private final HttpServletResponse response;

    @GetMapping("/login/{socialLoginType}") //GOOGLE이 들어올 것이다.
    //BaseResponse<String>
    public String socialLoginRedirect(@PathVariable(name="socialLoginType") String SocialLoginPath) throws IOException {
        logger.info("OAuthController");
        SocialLoginType socialLoginType= SocialLoginType.valueOf(SocialLoginPath.toUpperCase());
        logger.info("[socialLoginRedirect] - {}",socialLoginType);
        String redirectUrl=oAuthService.request(socialLoginType);
        //response.sendRedirect(redirectUrl);
        return redirectUrl;
        //return new BaseResponse<>(redirectUrl);
    }

//    @GetMapping(value = "/auth/{socialLoginType}/callback")
//    public BaseResponse<TokenResponse> callback (
//            @PathVariable(name = "socialLoginType") String socialLoginPath,
//            @RequestParam(name = "code") String code)throws IOException, BaseException {
//        System.out.println(">> 소셜 로그인 API 서버로부터 받은 code :"+ code);
//        SocialLoginType socialLoginType= SocialLoginType.valueOf(socialLoginPath.toUpperCase());
//        logger.info("socialLoginType:{}",socialLoginType);
//        TokenResponse tokenResponse=oAuthService.oAuthLogin(socialLoginType,code);
//
//        return new BaseResponse<>(tokenResponse);
//    }


    // 구글 로그인 - 프론트에서 accessToken을 받음.
    @GetMapping(value="/login/google/{accesstoken}")
    public BaseResponse<TokenResponse> googleLogin(@PathVariable(name="accesstoken") String accessToken) throws IOException, NoSuchAlgorithmException {
//    public BaseResponse<TokenResponse> login(@PathVariable(name = "socialLoginType") String socialLoginPath, @RequestParam(name="accessToken") String accessToken) throws IOException {
        //logger.info("[login] socialLoginType: {}",socialLoginPath);
        logger.info("[login] accessToken: {}",accessToken);
        SocialLoginType socialLoginType= SocialLoginType.valueOf("google".toUpperCase());
        TokenResponse tokenResponse=oAuthService.oAuthLogin(socialLoginType,accessToken);
        logger.info("tokenResponse: {}",tokenResponse);

        return new BaseResponse<>(tokenResponse);
    }

//    @GetMapping("/login/kakao/{code}")
//    public BaseResponse<TokenResponse> kakaoLogin(@PathVariable(name="code")String code) throws IOException {
//        logger.info("[KAKAO LOGIN]");
//        SocialLoginType socialLoginType= SocialLoginType.valueOf("kakao".toUpperCase());
//        TokenResponse tokenResponse=oAuthService.oAuthLogin(socialLoginType,code);
//
//        return new BaseResponse<>(tokenResponse);
//    }

    @DeleteMapping("/logout/{memberId}")
    public BaseResponse<String> logout(@PathVariable(name="memberId")Long memberId){
        logger.info("[LOGOUT]");
        String response=oAuthService.logout(memberId);

        return new BaseResponse<>(response);
    }

}
