package com.example.pesha.config;

import com.example.pesha.exception.MyAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    MyAccessDeniedHandler myAcessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/loginpage")
                .successForwardUrl("/")
                .failureForwardUrl("/fail");


        http.authorizeHttpRequests()
                .antMatchers("/loginpage", "/logoutsuccess", "/user/**", "/product/**", "/register", "/chcekout/**").permitAll()
                .antMatchers("/user/admin/all", "/order/admin/all").hasAnyAuthority("admin")
                .anyRequest().authenticated()
                .and()
                .csrf().disable();


        http.logout()
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/logoutsuccess")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

        http.exceptionHandling().accessDeniedHandler(myAcessDeniedHandler);

        http.rememberMe()
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(60 * 60 * 24 * 7);
    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
