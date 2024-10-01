package org.nidhi.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ApplicationSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
           http.authorizeRequests( authorizeRequests -> authorizeRequests
                   .requestMatchers("/public/**").permitAll()
                   .requestMatchers("/").authenticated()
                   .requestMatchers("/login").permitAll()
                   .anyRequest().permitAll()
                  )
                   .formLogin( formLogin -> formLogin
                           .defaultSuccessUrl("/", true)
                           .permitAll()
                   )
                   .logout( logout -> logout
                           .logoutUrl("/logout")
                           .logoutSuccessUrl("/?logout")
                           .invalidateHttpSession(true)
                           .clearAuthentication(true)
                           .deleteCookies("JSESSIONID")
                           .permitAll()
                   )
                   .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoder bean
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        List<UserDetails> users = new ArrayList<>();
//        UserDetails user = User.builder()
//                .username("nidhi")
//                .password(passwordEncoder().encode("123456"))
//                .roles("USER")
//                .build();
//        users.add(user);
//        return new InMemoryUserDetailsManager(users);
//    }
}
