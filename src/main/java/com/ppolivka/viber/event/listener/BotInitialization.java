package com.ppolivka.viber.event.listener;

import com.ppolivka.viber.controller.BotController;
import com.ppolivka.viber.service.SubscriberService;
import com.viber.bot.api.ViberBot;
import com.viber.bot.message.TextMessage;
import com.viber.bot.profile.UserProfile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BotInitialization implements ApplicationListener<ApplicationReadyEvent> {

    private final Logger logger = Logger.getLogger(BotController.class);

    private final ViberBot bot;
    private final SubscriberService subscriberService;

    @Autowired
    public BotInitialization(ViberBot bot, SubscriberService subscriberService) {
        this.bot = bot;
        this.subscriberService = subscriberService;
    }

    @Value("${viber.webhook}")
    private String webhook;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        try {
            bot.setWebhook(webhook).get();
        } catch (Exception e) {
            logger.error("Bot webhook cannot be set.");
        }

        bot.onMessageReceived((event, message, response) -> response.send(message));
        bot.onSubscribe((event, response) -> {
            UserProfile userProfile = event.getUser();
            subscriberService.addSubscriber(userProfile);
            response.send(text("Hi " + userProfile.getName()));
        });
        bot.onUnsubscribe(event -> subscriberService.removeSubscriber(event.getUserId()));
    }

    private TextMessage text(String text) {
        return new TextMessage(text);
    }

}
