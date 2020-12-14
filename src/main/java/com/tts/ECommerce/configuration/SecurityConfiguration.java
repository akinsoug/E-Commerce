package com.tts.ECommerce.configuration;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.tts.ECommerce.service.UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private DataSource dataSource;
	
	@Value("${spring.queries.users-query}")
	  private String usersQuery;
	  @Value("${spring.queries.roles-query}")
	  private String rolesQuery;

	@Override
	  protected void configure(AuthenticationManagerBuilder auth)
	      throws Exception {
	    auth.
	        jdbcAuthentication()
	        .usersByUsernameQuery(usersQuery)
	        .authoritiesByUsernameQuery(rolesQuery)
	        .dataSource(dataSource)
	        .passwordEncoder(bCryptPasswordEncoder);
	  }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			authorizeRequests()
			
			.antMatchers("/console/**").permitAll()
            .antMatchers("/products").permitAll()
            .antMatchers("/signin").permitAll()
            .antMatchers("/signup").permitAll()
            .antMatchers("/").permitAll()
			
            
            .antMatchers().hasAuthority("USER").anyRequest()
            .authenticated().and().csrf().disable().formLogin()
            .loginPage("/signin").failureUrl("/signin?error=true")
            .defaultSuccessUrl("/products")
            .usernameParameter("username")
            .passwordParameter("password")
            .and().logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/signin").and().exceptionHandling();
		
		http.headers().frameOptions().disable();
            
            
//			.antMatchers("/cart").authenticated()
//			.and().formLogin().loginPage("/signin")
//			.loginProcessingUrl("/login")
//			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
//			.logoutSuccessUrl("/");
	}
	
	@Override
	  public void configure(WebSecurity web) throws Exception {
	    web
	        .ignoring()
	        .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	  }
	
	
}

