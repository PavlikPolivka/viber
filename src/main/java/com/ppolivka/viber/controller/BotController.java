package com.ppolivka.viber.controller;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;
import com.viber.bot.Request;
import com.viber.bot.ViberSignatureValidator;
import com.viber.bot.api.ViberBot;
import com.viber.bot.message.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.google.common.util.concurrent.Futures.immediateFuture;
import static java.util.Optional.of;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class BotController implements ApplicationListener<ApplicationReadyEvent> {

    private final ViberBot bot;

    private final ViberSignatureValidator signatureValidator;

    @Value("${viber.webhook}")
    private String webhook;

    @Autowired
    public BotController(ViberBot bot, ViberSignatureValidator signatureValidator) {
        this.bot = bot;
        this.signatureValidator = signatureValidator;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent appReadyEvent) {
        try {
            bot.setWebhook(webhook).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        bot.onMessageReceived((event, message, response) -> response.send(message));
        bot.onConversationStarted(event ->
                immediateFuture(of(new TextMessage("Hi " + event.getUser().getName())))
        );
    }

    @RequestMapping(value = "/bot", produces = "application/json", method = POST)
    public String incoming(@RequestBody String json, @RequestHeader("X-Viber-Content-Signature") String serverSideSignature) throws Exception {
        Preconditions.checkState(signatureValidator.isSignatureValid(serverSideSignature, json), "invalid signature");
        @Nullable InputStream response = bot.incoming(Request.fromJsonString(json)).get();
        return response != null ? CharStreams.toString(new InputStreamReader(response, Charsets.UTF_8)) : null;
    }

}
