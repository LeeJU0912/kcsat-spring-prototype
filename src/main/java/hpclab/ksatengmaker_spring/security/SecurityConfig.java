package hpclab.ksatengmaker_spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_USER");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .headers((headerConfig) -> headerConfig
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(authorize -> authorize
                        // 페이지 권한 설정
                        .requestMatchers("/member/**").hasRole("ADMIN")
                        .requestMatchers("/board/post/**").hasRole("USER")
                        .requestMatchers("/question/**").hasRole("USER")
                        .requestMatchers("/myBook/**").hasRole("USER")
                        .anyRequest().permitAll()
                )
                .formLogin((authorize) -> authorize
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error=true")
                        .usernameParameter("email")
                        .passwordParameter("password")
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .exceptionHandling((exception) -> exception
                        .accessDeniedPage("/denied")
                );

//        http.getSharedObject(AuthenticationManagerBuilder.class)
//                .inMemoryAuthentication()
//                .withUser("HPCLab1@csatmaker.site")
//                .password(passwordEncoder().encode("HPCLab1"))
//                .roles("ADMIN")
//                .and()
//                .withUser("HPCLab2@csatmaker.site")
//                .password(passwordEncoder().encode("HPCLab2"))
//                .roles("ADMIN")
//                .and()
//                .withUser("HPCLab3@csatmaker.site")
//                .password(passwordEncoder().encode("HPCLab3"))
//                .roles("ADMIN")
//                .and()
//                .withUser("HPCLab4@csatmaker.site")
//                .password(passwordEncoder().encode("HPCLab4"))
//                .roles("ADMIN")
//                .and()
//                .withUser("HPCLab5@csatmaker.site")
//                .password(passwordEncoder().encode("HPCLab5"))
//                .roles("ADMIN");

        return http.build();
    }
}
