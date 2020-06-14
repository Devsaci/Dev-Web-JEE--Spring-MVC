package org.devsaci.springmvc.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource datasource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = passwordEncoder();
		System.out.println("**********************************");
		System.out.println((passwordEncoder.encode("1234")));
		System.out.println("**********************************");
		
		
		auth.jdbcAuthentication()
		.dataSource(datasource)
		.usersByUsernameQuery
		("select username as principal,password as credentiels,active from users where username=?")
		.authoritiesByUsernameQuery
		("select username as principal, role as role from users_roles where username=?")
		.passwordEncoder(passwordEncoder)
		.rolePrefix("ROLE_");
	

		/*
		 * auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder.
		 * encode("1234")).roles("USER");
		 * auth.inMemoryAuthentication().withUser("user2").password(passwordEncoder.
		 * encode("1234")).roles("USER");
		 * auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.
		 * encode("1234")).roles("USER", "ADMIN");
		 */
		
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.formLogin().loginPage("/login");
		// http.httpBasic(); //different de formLogin()
		http.authorizeRequests().antMatchers("/admin/**", "/delete**/**", "/save**/**", "/form**/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers("/patient**/**").hasRole("USER");
		//http.authorizeRequests().antMatchers("/patient**/**").hasRole("ADMIN");
		// http.csrf();
		http.authorizeRequests().antMatchers("/user/**","/login/**","/webjars/**").permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		http.exceptionHandling().accessDeniedPage("/notAuthorized");

	}

}
