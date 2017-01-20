package com.ppolivka.viber.configuration;

import com.viber.bot.ViberSignatureValidator;
import com.viber.bot.api.ViberBot;
import com.viber.bot.profile.BotProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nullable;

@Configuration
public class BotConfiguration {

    @Value("${viber.auth-token}")
    private String authToken;

    @Value("${viber.name}")
    private String name;

    @Nullable
    @Value("${viber.avatar:@null}")
    private String avatar;

    @Bean
    ViberBot viberBot() {
        return new ViberBot(new BotProfile(name, avatar), authToken);
    }

    @Bean
    ViberSignatureValidator signatureValidator() {
        return new ViberSignatureValidator(authToken);
    }

}
