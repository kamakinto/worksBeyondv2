package worksBeyond;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.*;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsService userDetailsService;
	


	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		//auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
		auth
		
			.userDetailsService(userDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
//			.inMemoryAuthentication()
//				.withUser("user").password("password").roles("USER").and()
//				.withUser("admin").password("password").roles("USER", "ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
				.antMatchers( "/","/faq","/saveUser","/homepage", "/requestServices","/registerUser", "/welcomeHome", "/whoWeServe").permitAll()
				.antMatchers("/admin/**", "/uploadDocument").hasRole("ADMIN")
				.antMatchers("/static/**", "/templates/**").permitAll()
				.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
				.anyRequest().authenticated()
				
			.and()
				.formLogin()
				.defaultSuccessUrl("/", true) //Change to User details page once it is stood up
				
				.loginPage("/login")
				.failureUrl("/login?error")
				.permitAll()
			.and()
				.logout()
				.logoutUrl("/logout")
				.deleteCookies("remember-me")
				.logoutSuccessUrl("/")
				.permitAll()
			.and()
				.rememberMe()
			.and()
				.csrf().disable();
				
	
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web 
			.ignoring()
				.antMatchers("/resources/**", "/css/**/","/js/**","/images/**","/**/favicon.ico");
		
	}
}
