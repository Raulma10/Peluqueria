package com.tfg.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
         http
                    .csrf(csrf->csrf.disable())
                    .authorizeHttpRequests(authRequest -> authRequest
                            .requestMatchers("/usuarios/login").permitAll()
                            .requestMatchers("/css/**","/js/**","/images/**","/estilos.css").permitAll()
                            .requestMatchers("/usuarios/crearusuario").permitAll()
                            .anyRequest().authenticated())
                    .formLogin(form->form
                        .loginPage("/usuarios/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/usuarios/principal",true)
                        .permitAll()
                    )
                    .logout(logout->logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/usuarios/login")
                        .permitAll()
                    )

                    .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
                    return http.build();   
    }

}
