package samueldev.projects.monitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import samueldev.projects.core.property.JwtConfiguration;
import samueldev.projects.security.config.SecurityTokenConfig;

@EnableWebSecurity
public class SecurityMonitoringConfig extends SecurityTokenConfig {
    private final UserDetailsService userDetailsService;

    public SecurityMonitoringConfig(JwtConfiguration jwtConfiguration, UserDetailsService userDetailsService) {
        super(jwtConfiguration);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/instances/499c8d58d310/**").hasRole("ADMIN")
                .antMatchers("/instances/4d9f3acb4167/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .and()
                .httpBasic();
        super.configure(http);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
