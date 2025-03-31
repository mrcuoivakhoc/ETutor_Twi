package com.example.Comp1640.Config.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http
                .cors(customizer -> customizer.configurationSource(request -> {
                    org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
                    config.setAllowCredentials(true);
                    config.addAllowedOrigin("http://localhost:4200"); // ✅ Chỉ cho phép Angular truy cập
                    config.addAllowedHeader("*"); // ✅ Chấp nhận tất cả headers
                    config.addAllowedMethod("*"); // ✅ Chấp nhận tất cả methods (GET, POST, ...)
                    return config;
                }))
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers("/api/classroom/**").permitAll()
//                        .requestMatchers("/api/classroom/**").hasRole("ADMIN")
                        .requestMatchers("/api/major/**").permitAll()
//                        .requestMatchers("/api/major/**").hasRole("ADMIN")
                        .requestMatchers("/api/student/**").permitAll()
//                        .requestMatchers("/api/student/**").hasRole("ADMIN")
                        .requestMatchers("/api/tutor/**").permitAll()
//                        .requestMatchers("/api/tutor/**").hasRole("ADMIN")
                        .requestMatchers("/api/user/**").permitAll()
//                        .requestMatchers("/api/user/**").hasRole("ADMIN")
                        .requestMatchers("/api/blog/**").permitAll()

                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }


//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:4200") // ✅ Cho phép Angular
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowedHeaders("*")
//                        .allowCredentials(true);
//            }
//        };
//    }
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }


}
