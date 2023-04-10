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
public class PointVO {
	private int pointNo;
	private String uid;
	private int ordNo;
	private int point;
	private String pointDate;
	private String expiration;
	
	
}
