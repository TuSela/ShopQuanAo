package com.Nhom19.shopQuanAo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.Nhom19.shopQuanAo.repository")
@EntityScan("com.Nhom19.shopQuanAo.entity")
public class ShopQuanAoApplication {

	public static void main(String[] args)
    {
		SpringApplication.run(ShopQuanAoApplication.class, args);

    }

}
