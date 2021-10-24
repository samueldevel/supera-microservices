package samueldev.projects.monitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import samueldev.projects.monitor.user.MonitorUserImpl;

@EnableWebSecurity
public class SecurityMonitoringConfig extends WebSecurityConfigurerAdapter {
    private final MonitorUserImpl monitorUser;

    public SecurityMonitoringConfig(MonitorUserImpl monitorUser) {
        this.monitorUser = monitorUser;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/instances/499c8d58d310/**").hasRole("ADMIN")
                .antMatchers("/instances/4d9f3acb4167/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(monitorUser).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
