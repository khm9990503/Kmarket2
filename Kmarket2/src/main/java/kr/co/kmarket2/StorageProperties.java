package kr.co.kmarket2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("storage")
public class StorageProperties {
	String location;
	String WebLocation;
}
