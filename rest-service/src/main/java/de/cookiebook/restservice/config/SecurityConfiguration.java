package de.cookiebook.restservice.config;

import de.cookiebook.restservice.JWT.JWTAuthenticationFilter;
import de.cookiebook.restservice.JWT.JWTAuthorizationFilter;
import de.cookiebook.restservice.service.AuthenticationUserDetailService;
import lombok.val;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtRequestFilter jwtRequestFilter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationUserDetailService authenticationUserDetailService;

    public SecurityConfiguration(BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationUserDetailService authenticationUserDetailService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationUserDetailService = authenticationUserDetailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, AuthenticationConfigConstants.SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.POST, "/users/register").permitAll()
//                .antMatchers(HttpMethod.POST, "/help").permitAll() // Frontend
                .antMatchers(HttpMethod.GET, "/userlist").permitAll()
//                .antMatchers(HttpMethod.GET, "/information_privacy").permitAll() // Frontend
//                .antMatchers(HttpMethod.GET, "/contacts").permitAll() // Frontend
//                .antMatchers(HttpMethod.GET, "/team").permitAll() // Frontend
//                .antMatchers(HttpMethod.GET, "/guide").permitAll() // Frontend
//                .antMatchers(HttpMethod.GET, "/faq").permitAll() // Frontend
//                .antMatchers(HttpMethod.GET, "/error").permitAll() // Frontend
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationUserDetailService).passwordEncoder(bCryptPasswordEncoder);
    }
}
