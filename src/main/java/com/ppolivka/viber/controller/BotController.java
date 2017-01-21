package com.ppolivka.viber.controller;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;
import com.ppolivka.viber.service.SubscriberService;
import com.viber.bot.Request;
import com.viber.bot.ViberSignatureValidator;
import com.viber.bot.api.ViberBot;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class BotController {

    private final Logger logger = Logger.getLogger(BotController.class);

    private final ViberBot bot;
    private final ViberSignatureValidator signatureValidator;
    private final SubscriberService subscriberService;

    @Autowired
    public BotController(ViberBot bot, ViberSignatureValidator signatureValidator, SubscriberService subscriberService) {
        this.bot = bot;
        this.signatureValidator = signatureValidator;
        this.subscriberService = subscriberService;
    }

    @RequestMapping(value = "/message")
    public void sendMessage(@RequestParam("text") String text) {
        subscriberService.notifySubscribers(text);
    }

    @RequestMapping(value = "/bot", produces = "application/json", method = POST)
    public String incoming(@RequestBody String json, @RequestHeader("X-Viber-Content-Signature") String serverSideSignature) throws Exception {
        Preconditions.checkState(signatureValidator.isSignatureValid(serverSideSignature, json), "invalid signature");
        @Nullable InputStream response = bot.incoming(Request.fromJsonString(json)).get();
        return response != null ? CharStreams.toString(new InputStreamReader(response, Charsets.UTF_8)) : null;
    }

}
