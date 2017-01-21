package com.ppolivka.viber.service;

import com.viber.bot.profile.UserProfile;

public interface SubscriberService {

    void addSubscriber(UserProfile userProfile);
    void removeSubscriber(String userBiberId);

}
