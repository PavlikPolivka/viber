package com.ppolivka.viber.service.impl;

import com.ppolivka.viber.model.Subscriber;
import com.ppolivka.viber.repository.SubscriberRepository;
import com.ppolivka.viber.service.SubscriberService;
import com.viber.bot.profile.UserProfile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriberServiceImpl implements SubscriberService {

    private final Logger logger = Logger.getLogger(SubscriberService.class);


    private final SubscriberRepository subscriberRepository;

    @Autowired
    public SubscriberServiceImpl(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public void addSubscriber(UserProfile userProfile) {
        logger.info("Adding subscriber");
        Subscriber subscriber = new Subscriber();
        subscriber.setName(userProfile.getName());
        subscriber.setViberId(userProfile.getId());
        subscriberRepository.save(subscriber);
    }

    @Override
    public void removeSubscriber(String userViberId) {
        logger.info("Removing subscriber");
        Subscriber subscriber = subscriberRepository.findByViberId(userViberId);
        subscriberRepository.delete(subscriber);
    }
}
