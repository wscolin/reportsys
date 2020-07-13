package com.zxd;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.DelegatingFilterProxy;

@SpringBootApplication
@MapperScan("com.zxd.report.mapper") //MapperScan 扫描MyBatis的Mapper接口
public class Application extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
	public FilterRegistrationBean filterRegistrationBean(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		DelegatingFilterProxy proxt = new DelegatingFilterProxy();
		proxt.setTargetFilterLifecycle(true);
		proxt.setTargetBeanName("shiroFilter");
		filterRegistrationBean.setFilter(proxt);
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

}
