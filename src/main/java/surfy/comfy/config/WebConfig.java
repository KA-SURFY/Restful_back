package surfy.comfy.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import surfy.comfy.auth.JwtTokenInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtTokenInterceptor jwtTokenInterceptor;



    public void addInterceptors(InterceptorRegistry registry) {
        // TODO: jwt interceptor addPathPatterns 수정 필요
        registry.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/myPage/**")
                .addPathPatterns("/surveys/**")
                .addPathPatterns("/result/**")
                ;


    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        // TODO: allowedOrigins-> 우리 서버 ip로 수정 필요 ex)http://192.126.32.2:3000
//        registry.addMapping("/**")
//                .allowedOrigins("*");

        registry.addMapping("/**")
                .allowedOrigins("http://www.commfy.shop")
                .exposedHeaders("ACCESS_TOKEN")
                .exposedHeaders("REFRESH_TOKEN")
                .exposedHeaders("msg") // 서버에서 반환할 헤더
                .allowCredentials(true)
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.HEAD.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name());

//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("POST", "OPTIONS")
//                .allowedMethods("DELETE","OPTIONS")
//                .allowedMethods("GET","OPTIONS")
//                .allowedHeaders("Content-Type", "ACCESS_TOKEN")
//                .allowedHeaders("Content-Type","REFRESH_TOKEN");

    }
}