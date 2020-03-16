package dev.security;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration Spring Security.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    private JWTAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

    private JWTAuthorizationFilter jwtAuthorizationFilter;

    public WebSecurityConfig(JWTAuthenticationSuccessHandler jwtAuthenticationSuccessHandler, JWTAuthorizationFilter jwtAuthorizationFilter) {
        this.jwtAuthenticationSuccessHandler = jwtAuthenticationSuccessHandler;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }



    // Algorithme de hashage du mot de passe
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuration de l'identité.
     *
     * @param ds
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService(DataSource ds) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
        manager.setDataSource(ds);
		manager.setUsersByUsernameQuery("select username, password, 'true' from userexa where username=?");
		manager.setAuthoritiesByUsernameQuery("select username, roles from userexa where username=?");
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().exceptionHandling()
				.authenticationEntryPoint(
						(request, response, authException) -> response.setStatus(HttpServletResponse.SC_FORBIDDEN))
				.and().authorizeRequests().anyRequest().authenticated().and().formLogin()
				.successHandler(jwtAuthenticationSuccessHandler)
				.failureHandler(
						(request, response, exception) -> response.setStatus(HttpServletResponse.SC_BAD_REQUEST))
				.permitAll().and().addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
				.logout().logoutSuccessHandler((req, resp, auth) -> resp.setStatus(HttpServletResponse.SC_OK))
				.deleteCookies(TOKEN_COOKIE);
    }
}
