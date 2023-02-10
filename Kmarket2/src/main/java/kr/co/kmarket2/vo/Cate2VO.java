package kr.co.kmarket2.vo;
/*
 * cate1	TINYINT(4)		
 * cate2	TINYINT(4)		
 * c2Name	VARCHAR(20)		
 */

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
public class Cate2VO {
	private int cate1;
	private int cate2;
	private String c2Name;
	
	private String artiCate;
	private String artiCate2;
	
}
