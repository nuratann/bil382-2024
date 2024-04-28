package kg.buyers.productservice;

import lombok.Builder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return httpSecurity
                .authorizeHttpRequests(customizer -> customizer
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/products/").hasRole("SELLER")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/products/").hasRole("SELLER")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/products/").hasRole("SELLER")
                        .requestMatchers(HttpMethod.POST,"/api/v1/reviews/").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/reviews/").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST,"/api/v1/comments/").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/comments/").hasRole("CUSTOMER")
                        .anyRequest().authenticated())
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        var converter = new JwtAuthenticationConverter();
        var jwtGrantedAuthoritiesConverter =  new JwtGrantedAuthoritiesConverter();
        converter.setPrincipalClaimName("preferred_username");
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
            var roles = jwt.getClaimAsStringList("spring_security_roles");
            return Stream.concat(authorities.stream(),
                        roles.stream()
                                .filter(role -> role.startsWith("ROLE_"))
                                .map(SimpleGrantedAuthority::new)
                                .map(GrantedAuthority.class::cast))
                    .toList();
        });
        return converter;
    }
}
