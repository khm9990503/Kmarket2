package kr.co.kmarket2.vo;
/*
 * prodNo		INT(11)
 * prodCate1	TINYINT(4)
 * prodCate2	TINYINT(4)
 * prodName		VARCHAR(100)
 * descript		VARCHAR(100)
 * company		VARCHAR(100)
 * seller		VARCHAR(20)
 * price		INT(11)
 * discount		TINYINT(4)
 * point		INT(11)
 * stock		INT(11)
 * sold			INT(11)
 * delivery		INT(11)
 * hit			INT(11)
 * score		TINYINT(4)
 * review		INT(4)
 * thumb1		VARCHAR(255)
 * thumb2		VARCHAR(255)
 * thumb3		VARCHAR(255)
 * detail		VARCHAR(255)
 * status		VARCHAR(20)
 * duty			VARCHAR(20)
 * receipt		VARCHAR(255)
 * bizType		VARCHAR(20)
 * origin		VARCHAR(20)
 * ip			VARCHAR(20)
 * rdate		DATETIME
 */

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductVO {
	private	int prodNo;
	private int prodCate1;
	private int prodCate2;
	private String prodName;
	private String descript;
	private String company;
	private String seller;
	private int price;
	private int discount;
	private int point;
	private int stock;
	private int sold;
	private int delivery;
	private int hit;
	private int score;
	private int review;
	private String thumb1;
	private String thumb2;
	private String thumb3;
	private String detail;
	private MultipartFile thumb1_;
	private MultipartFile thumb2_;
	private MultipartFile thumb3_;
	private MultipartFile detail_;
	private String status;
	private String duty;
	private String receipt;
	private String bizType;
	private String origin;
	private String ip;
	private String rdate;
	private int disPrice;
	
}
