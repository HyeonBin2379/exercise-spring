package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import controller.ApiExceptionAdvice;
import controller.CommonExceptionHandler;
import interceptor.AuthCheckInterceptor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/view/", ".jsp");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/main").setViewName("main");
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setBasenames("message.label");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authCheckInterceptor())
                .addPathPatterns("/edit/**")
                .excludePathPatterns("/edit/help/**");
    }

    @Bean
    public AuthCheckInterceptor authCheckInterceptor() {
        return new AuthCheckInterceptor();
    }

    @Bean
    public CommonExceptionHandler commonExceptionHandler() {
        return new CommonExceptionHandler();
    }

    @Bean
    public ApiExceptionAdvice apiExceptionAdvice() {
        return new ApiExceptionAdvice();
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder
                .json()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(formatter))
                .simpleDateFormat("yyyyMMdd HHmmss")
                .build();
        converters.add(0,
                new MappingJackson2HttpMessageConverter(objectMapper));
    }
}
