package com.myproject.jobapp.config;

import com.myproject.jobapp.service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    private CustomUserDetailService customUserDetailService;
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public WebSecurityConfig(CustomUserDetailService customUserDetailService,CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customUserDetailService = customUserDetailService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    // Doesn't require pw
    private final String[] publicUrl = {"/",
            "/global-search/**",
            "/register",
            "/register/**",
            "/webjars/**",
            "/resources/**",
            "/assets/**",
            "/css/**",
            "/summernote/**",
            "/js/**",
            "/*.css",
            "/*.js",
            "/*.js.map",
            "/fonts**", "/favicon.ico", "/resources/**", "/error"};

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http.authorizeHttpRequests(authorizeRequests ->{
            authorizeRequests.requestMatchers(publicUrl).permitAll();
            //Other requests need log in
            authorizeRequests.anyRequest().authenticated();
        });

        http.formLogin(formLogin ->
                    formLogin.loginPage("/login").permitAll().successHandler(customAuthenticationSuccessHandler))
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/");})
                .cors(Customizer.withDefaults())
                .csrf(csrf->csrf.disable());

        return http.build();
    }

    //This method tells spring how to find user and its pw
    @Bean
    protected AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        //set whether to encode
        authProvider.setPasswordEncoder(passwordEncoder());
        //set how to acquire users (using custom service class)
        authProvider.setUserDetailsService(customUserDetailService);
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
