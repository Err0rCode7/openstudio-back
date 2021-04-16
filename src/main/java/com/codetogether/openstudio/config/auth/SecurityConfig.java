package com.codetogether.openstudio.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception  {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**",
                        "/h2-console/**", "/page-3/**").permitAll()
                .antMatchers("/api/v1/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                .antMatchers("/page-1/**", "/page-2/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                .antMatchers("/admin/**").hasRole(Role.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/oauth2/authorization/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}