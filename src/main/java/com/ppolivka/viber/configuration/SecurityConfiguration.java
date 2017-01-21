package com.ppolivka.viber.configuration;

import com.ppolivka.viber.model.Account;
import com.ppolivka.viber.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> {
            Account account = accountRepository.findByName(username);
            if (account != null) {
                return new User(
                        account.getName(),
                        account.getPassword(),
                        true,
                        true,
                        true,
                        true,
                        AuthorityUtils.createAuthorityList("USER")
                );
            } else {
                throw new UsernameNotFoundException("could not find the user '" + username + "'");
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
