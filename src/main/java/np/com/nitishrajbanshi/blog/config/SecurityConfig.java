package np.com.nitishrajbanshi.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import np.com.nitishrajbanshi.blog.services.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailService userDetailService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http
        .csrf(customizer->customizer.disable())
        .authorizeHttpRequests(request->request.anyRequest().authenticated())
        .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .httpBasic();
        return http.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(BCryptPasswordEncoder encoder){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(this.userDetailService);
        return provider;
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

}
