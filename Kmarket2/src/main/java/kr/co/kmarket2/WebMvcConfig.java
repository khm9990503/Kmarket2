package kr.co.kmarket2;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
	
	private final StorageProperties storageProperties;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/**
         * 파일 업로드의 경우, 이와 같이 리소스 핸들러를 넣는 것이 최선 같다.
         */
		registry.addResourceHandler(storageProperties.getWebLocation() + "/**" )
        .addResourceLocations("file:" + Paths.get("thumb").toAbsolutePath() + "/");
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
}
