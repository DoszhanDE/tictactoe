package de.doszhan.tictactoe.config;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable() // the app doesn't use cookies for auth, therefore safe to disable csrf
      .addFilterAfter(new JWTAuthFilter(jwtSecret), UsernamePasswordAuthenticationFilter.class)
      .authorizeRequests()
      .antMatchers("/user/**").permitAll()
      .anyRequest().authenticated();
  }
}