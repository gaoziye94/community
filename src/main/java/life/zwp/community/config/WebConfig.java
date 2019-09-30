package life.zwp.community.config;

import life.zwp.community.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public SessionInterceptor getTokenInterceptor(){
        return new SessionInterceptor();
    }
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getTokenInterceptor())
                .excludePathPatterns("/static/**") //不拦截静态资源
                .excludePathPatterns("/login")
                .excludePathPatterns("/logout")
                .addPathPatterns("/**");
    }
}
