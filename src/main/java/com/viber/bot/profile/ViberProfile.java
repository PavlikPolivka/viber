package com.viber.bot.profile;

import com.ppolivka.viber.model.Subscriber;

public class ViberProfile extends UserProfile {
    public ViberProfile(Subscriber subscriber) {
        super(subscriber.getViberId(), subscriber.getName(), "", "", "");
    }
}
