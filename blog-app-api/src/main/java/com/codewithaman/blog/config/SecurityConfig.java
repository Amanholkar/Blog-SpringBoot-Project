package com.codewithaman.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.codewithaman.blog.security.CustomUserDetailService;
import com.codewithaman.blog.security.JWTauthenticationEntryPoint;
import com.codewithaman.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   
    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private JWTauthenticationEntryPoint jwTauthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter aJwtAuthenticationFilter;


    public static final String[] PUBLIC_URLS = {

        "/api/auth/**",
        "/v3/api-docs",
        "/v2/api-docs",
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/webjars/**"


    };
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        
       http.
       csrf().disable()   // cross site request forzari
       .authorizeHttpRequests()
       .antMatchers(PUBLIC_URLS).permitAll()
       .antMatchers(HttpMethod.GET).permitAll()
       .anyRequest()
       .authenticated() 
       .and()
       .exceptionHandling().authenticationEntryPoint(this.jwTauthenticationEntryPoint)
       .and()
       .sessionManagement()
       .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
       

       http.addFilterBefore(this.aJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
        auth.userDetailsService(this.userDetailService).passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
   
        return super.authenticationManagerBean();
    }
}
