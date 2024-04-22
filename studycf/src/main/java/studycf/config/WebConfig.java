package studycf.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import studycf.interceptor.LoginInterceptor;

@Component
public class WebConfig implements WebMvcConfigurer{

	private LoginInterceptor loginInterceptor;
	
	public WebConfig(LoginInterceptor loginInterceptor) {
		this.loginInterceptor = loginInterceptor;
	}
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/order/addOrder");
				

		WebMvcConfigurer.super.addInterceptors(registry);
		
	}
	
	
	
}
