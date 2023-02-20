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
public class ArticleVO {
	private int no;
	private int parent; 
	private int comment;
	private String cate;
	private String cate2;
	private String title;
	private String group;
	private String content;
	private int file;
	private int hit;
	private String uid;
	private String regip;
	private String rdate;
	
	private String nick;
	
	private int fno;
	private String oriName;
	private int download;
	
	private String fname;
	
	private String c1Name;
	private String c2Name;
	
}
