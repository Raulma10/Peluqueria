package com.tfg.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

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
                            .requestMatchers("/usuarios/cerrarsesion").authenticated()
                            .requestMatchers("/usuarios/borrarusuario").hasRole("ADMIN")
                            .requestMatchers("/citas/gestionarcita").hasRole("CLIENTE")
                            .requestMatchers("/citas/reservarcita").hasRole("CLIENTE")
                            .requestMatchers("/citas/miscitas").hasRole("CLIENTE")
                            .requestMatchers("/citas/borrarcita").hasRole("ADMIN")
                            .requestMatchers("/servicios/gestionarservicios").hasRole("ADMIN")
                            .requestMatchers("/servicios/crearservicio").hasRole("ADMIN")
                            .requestMatchers("/servicios/actualizarservicio").hasRole("ADMIN")
                            .requestMatchers("/servicios/borrarservicio").hasRole("ADMIN")
                            .requestMatchers("/servicios/verservicios").permitAll()
                            .anyRequest().authenticated())
                    .formLogin(form->form
                        .loginPage("/usuarios/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/usuarios/principal",true)
                        .failureUrl("/usuarios/login?error")

                    )
                    .logout(logout->logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/usuarios/login?logout")
                        .permitAll()
                    )

                    .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
                    return http.build();   
    }

}
