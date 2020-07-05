package com.zxd;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zxd.report.mapper") //MapperScan 扫描MyBatis的Mapper接口
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
