package hu.ebanjo.ledshop.dbs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("whopper")
				.password("{noop}shopper").roles("USER");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.httpBasic()
            .and()
            .antMatcher("/**").authorizeRequests().anyRequest().hasRole("USER")
				.and().formLogin().loginPage("/login.jsp")
				.failureUrl("/login.jsp?error=1").loginProcessingUrl("/login")
				.permitAll().and().logout()
				.logoutSuccessUrl("/listEmployees.html");
	}
}