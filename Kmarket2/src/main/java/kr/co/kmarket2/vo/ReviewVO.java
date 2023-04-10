package kr.co.kmarket2.vo;

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
public class ReviewVO {
	private int revNo;
	private int prodNo;
	private String content;
	private String uid;
	private int rating;
	private String regip;
	private	String rdate;
	private String prodName;
	
	
	// 댓글 ajax 불러오기
	private int groupStart;
	private int groupEnd;
	private int lastPageNum;
	private int currentPage;
	
	// 상품카테고리
	private int prodCate1;
	private int prodCate2;
}
