package com.teamdevAcademy.academy.config;
import com.teamdevAcademy.academy.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import static com.teamdevAcademy.academy.entities.Permission.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
   // @Autowired
    //private JwtAuthenticationFilter jwtAuthenticationFilter;
   @Autowired
    private AuthenticationProvider authenticationProvider;
 /*   @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("slimenerania1999@gmail.com")
                .password(passwordEncoder().encode("Admin123"))
                .roles(ADMIN.name()) // Utilisation du rôle personnalisé
                .build();

        UserDetails user = User.withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles(USER.name()) // Utilisation du rôle personnalisé
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }


 */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable()

                .authorizeRequests()
                .antMatchers(
                        "/api/v1/auth/**",
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html")
                .permitAll()


                .antMatchers( "/User/signIn").permitAll()
                .antMatchers( "/User/Signup").permitAll()
                .antMatchers( "/User/password-reset").permitAll()
                .antMatchers( HttpMethod.GET,"/User/**").permitAll()
                .antMatchers(HttpMethod.GET,"/categories/**").permitAll()
                .antMatchers(HttpMethod.GET,"/chapitre/**").permitAll()
                .antMatchers(HttpMethod.GET,"/cours/**").permitAll()
                .antMatchers(HttpMethod.GET,"/Matieres/**").permitAll()
                .antMatchers(HttpMethod.GET,"/images/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/User/**").permitAll()
                //.antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.PUT, "/User/**").hasAnyAuthority(ADMIN_UPDATE.name(), USER_UPDATE.name())
               // .antMatchers(HttpMethod.DELETE,"/User/**").hasAuthority(ADMIN_DELETE.name())
                .antMatchers(HttpMethod.DELETE,"/categories/**").hasAuthority(ADMIN_DELETE.name())
                .antMatchers(HttpMethod.DELETE,"/chapitre/**").hasAuthority(ADMIN_DELETE.name())
                .antMatchers(HttpMethod.DELETE,"/cours/**").hasAuthority(ADMIN_DELETE.name())
                .antMatchers(HttpMethod.DELETE,"/Matieres/**").hasAuthority(ADMIN_DELETE.name())
                .antMatchers(HttpMethod.PUT,"/chapitre/**").hasAuthority(ADMIN_UPDATE.name())
                .antMatchers(HttpMethod.PUT,"/cours/**").hasAuthority(ADMIN_UPDATE.name())
                .antMatchers(HttpMethod.PUT,"/Matieres/**").hasAuthority(ADMIN_UPDATE.name())
                .antMatchers(HttpMethod.POST,"/chapitre/**").hasAuthority(ADMIN_CREATE.name())
                .antMatchers(HttpMethod.POST,"/categories/**").hasAuthority(ADMIN_CREATE.name())
                .antMatchers(HttpMethod.POST,"/cours/**").hasAuthority(ADMIN_CREATE.name())
                .antMatchers(HttpMethod.POST,"/Matieres/**").hasAuthority(ADMIN_CREATE.name())
                .anyRequest().authenticated()


                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .and();


        return http.build();
    }




    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}

