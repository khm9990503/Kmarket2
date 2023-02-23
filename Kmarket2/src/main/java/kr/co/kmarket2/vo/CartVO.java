package kr.co.kmarket2.vo;

import java.util.List;

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
public class CartVO {
	private int cartNo;
	private String uid;
	private int prodNo;
	private int count;
	private int price;
	private int discount;
	private int point;
	private int delivery;
	private int total;
	private String rdate;
	
	// 추가 필드
	private String prodName;
	private String thumb1;
	private String descript;
	private int prodCate1;
	private int prodCate2;
	
	// view에서 ajax로 넘길 때 필요한 정보
	private int disPrice;
	
}
