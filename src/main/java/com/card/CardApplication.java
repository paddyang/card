package com.card;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("com.card.mapper")
@EnableCaching
public class CardApplication {
//打war包
//public class CardApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CardApplication.class, args);
	}

	/**
	 * 打war包
	 * @param builder
	 * @return
	 */
	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}*/

}
