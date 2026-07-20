package gestione.elettrica.gestione_energia_elettrica.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder getBCrypt(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){

        // DISABILITO IL FORM DI DEFAULT

        httpSecurity.formLogin(formLogin -> formLogin.disable() );

        //DISABILITO LE SESSIONI

        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //DISABILITIAMO ANCHE DA ATTACCHI CSRF

        httpSecurity.csrf(csrf -> csrf.disable());

        //ELIMINO I CONTROLLI AUTOMATICI DI SPRING SECURITY - quindi poi non interverrà sulle richieste

        httpSecurity.authorizeHttpRequests(req -> req.requestMatchers("/auth/**").permitAll());

        //IMPLEMENTO I MIEI CONTROLLI BASATI SUI TOKEN

        return httpSecurity.build();

    }
}
