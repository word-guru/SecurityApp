package com.example.securityapplication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.security.Permission;

import static com.example.securityapplication.security.Roles.*;
import static com.example.securityapplication.security.UserPermission.*;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index.html", "/css/*", "/js/*").permitAll()
                .antMatchers("/students/**").hasRole(STUDENT.name())

               // .antMatchers(HttpMethod.GET,"/admin/student/**").hasAnyRole(ADMIN.name(), ADMIN_TRAINEE.name())
               // .antMatchers(HttpMethod.POST,"/admin/student/**").hasAuthority(COURSE_WRITE.getPermission())
               // .antMatchers(HttpMethod.DELETE,"/admin/student/**").hasAuthority(COURSE_WRITE.getPermission())
               // .antMatchers(HttpMethod.PUT,"/admin/student/**").hasAuthority(COURSE_WRITE.getPermission())

                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails johnSmith = User.builder()
                .username("john")
                .password(passwordEncoder.encode("password"))
                .authorities(STUDENT.getGrantedAuthority())
               // .roles(STUDENT.name())
                .build();

        UserDetails jamesBond = User.builder()
                .username("james")
                .password(passwordEncoder.encode("123456789"))
                .authorities(ADMIN.getGrantedAuthority())
               // .roles(ADMIN.name())
                .build();

        UserDetails frankSinatra = User.builder()
                .username("frank")
                .password(passwordEncoder.encode("qwerty"))
                .authorities(ADMIN_TRAINEE.getGrantedAuthority())
                //.roles(ADMIN_TRAINEE.name())
                .build();
        return new InMemoryUserDetailsManager(
                johnSmith,
                jamesBond,
                frankSinatra
        );
    }
}
