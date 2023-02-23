package kr.co.kmarket2.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.co.kmarket2.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L; // 객체 번호 부여

	
	private MemberEntity user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 계정이 갖는 권한 목록 리턴 ex)grade
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getLevel()));
		return authorities;
	}

	@Override
	public String getPassword() {
		// 계정의 password 들고오기
		return user.getPass();
	}

	@Override
	public String getUsername() {
		// 계정이 갖는 ID
		return user.getUid();
	}

	@Override
	public boolean isAccountNonExpired() {
		// 계정이 만료 여부 ( true : 만료 X, false : 만료 O)
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정이 잠김 여부 ( true : 잠김 X, false : 잠김 O)
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 계정 비밀번호 만료 여부 ( true : 만료 X , false : 만료 O )
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 계정 활성화 여부 : ( true : 활성화 , false : 비활성 )
		return true;
	}

}
