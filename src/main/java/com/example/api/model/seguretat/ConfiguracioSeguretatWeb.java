package com.example.api.model.seguretat;

import com.example.api.model.serveis.ElMeuUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfiguracioSeguretatWeb extends WebSecurityConfigurerAdapter {

    private final ElMeuAuthenticationEntryPoint elmeuEntryPoint;
    private final ElMeuUserDetailsService elmeuUserDetailsService;
    private final PasswordEncoderConfig xifrat;

//Per fer proves al principi, per poder fer post i put d'usuaris sense seguretat
//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().anyRequest();
//    }



//codi per fer una prova autenticant en memòria "inMemoryAuthentication()"
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .passwordEncoder(xifrat)
//                .withUser("Montse")
//                .password(xifrat.encode("secret"))
//                .roles("ADMIN"); // és necessari posar tots els camps, fins el rol (authorities)
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(elmeuUserDetailsService).passwordEncoder(xifrat);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors() //amb aquesta línia evitem la configuració custom del cors en un fitxer a part
                .and()
                .httpBasic()
                .authenticationEntryPoint(elmeuEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/me/**").hasRole("ADMIN") //per fer proves del forbidden
                .antMatchers(HttpMethod.GET, "/users/**", "/rana/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/users/**", "/rana/**").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/rana/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/rana/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/rana/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated();
        // .and()
        // .csrf().disable();
    }

}
