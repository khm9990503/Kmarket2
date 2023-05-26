package kr.co.kmarket2.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BannerVO {
	private int no;
	private String bName;
	private String bSize;
	private String bColor;
	private String bLink;
	private String bLocate;
	private String beginDate;
	private String endDate;
	private String beginTime;
	private String endTime;
	private String bFile;
	private MultipartFile bFile_;
}
