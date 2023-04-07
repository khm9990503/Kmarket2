package kr.co.kmarket2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// 인가 (접근 권한) 설정
		http.authorizeRequests().antMatchers("/").permitAll(); // ↓ ROLE_2 X
		http.authorizeRequests().antMatchers("/admin/*").hasAnyRole("5","6","7"); 
		http.authorizeRequests().antMatchers("/my/*").hasAnyRole("1", "2", "3", "4", "5", "6", "7"); 
		http.authorizeRequests().antMatchers("/product/cart").hasAnyRole("1", "2", "3", "4", "5", "6", "7"); 
		//http.authorizeRequests().antMatchers("/board/write").hasAnyRole("3","4","5"); 
		//http.authorizeRequests().antMatchers("/board/view").hasAnyRole("3","4","5"); 
		//http.authorizeRequests().antMatchers("/board/modify").hasAnyRole("3","4","5"); 
		
		
		// 사이트 위조 방지 설정 , 배포 시 주석하기
		http.csrf().disable();
		
		// 로그인 설정
		http.formLogin()
			.loginPage("/member/login")
			.defaultSuccessUrl("/index")
			.failureUrl("/member/login?success=100")
			.usernameParameter("uid")
			.passwordParameter("pass");
			
		// 로그아웃 설정
		http.logout()
					.invalidateHttpSession(true)
					.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
					.logoutSuccessUrl("/member/login?success=200");
		
		
	}
	
	@Autowired
	private SecurityUserService userService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		// Security 사용자에 대한 권한 설정
		auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("manager").password("{noop}1234").roles("MANAGER");
		auth.inMemoryAuthentication().withUser("member").password("{noop}1234").roles("MEMBER");
		*/
		// 로그인인증 처리 서비스, 암호화 방식 설정 BCrypt = SHA2 + 'salt'
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
