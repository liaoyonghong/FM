package com.versionsystem.basic.security;

import com.versionsystem.common.CrosFilter;
import com.versionsystem.common.CustomUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.HeaderWriterFilter;

import javax.annotation.Resource;

/**
 * 代替spring-security.xml
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Resource(name = "procedurePasswordEncoder")
	private PasswordEncoder procedurePasswordEncoder;

	public WebSecurityConfig() {
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/assets/**", "/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
//			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login").permitAll()
			.and().logout().invalidateHttpSession(true).logoutSuccessUrl("/logout/success").logoutUrl("/logout").permitAll()
			.and().exceptionHandling().accessDeniedPage("/defied").authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
			/**
			 * 与<custom-filter>的关系可参考
			 * 	https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html5/
			 * 在 Table 2. Standard Filter Aliases and Ordering
			 */
			.and().addFilterAt(new CustomUsernamePasswordAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new CrosFilter(), HeaderWriterFilter.class)
			.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(procedurePasswordEncoder);
	}

}
