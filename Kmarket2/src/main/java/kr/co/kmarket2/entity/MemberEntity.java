package kr.co.kmarket2.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="km_member")
public class MemberEntity {
	@Id
	private String uid;
	private String pass;
	private String name;
	private int gender;
	private String hp;
	private String email;
	private int type;
	private String point;
	private int level;
	private String zip;
	private String addr1;
	private String addr2;
	private String company;
	private String ceo;
	private String bizRegNum;
	private String comRegNum;
	private String tel;
	private String manager;
	private String managerHp;
	private String fax;
	private String regip;
	private String wdate;
	private String rdate;
	private int locationTerms;
}
