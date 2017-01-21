package com.ppolivka.viber.repository;

import com.ppolivka.viber.model.Subscriber;
import org.springframework.data.repository.CrudRepository;


public interface SubscriberRepository extends CrudRepository<Subscriber, Integer> {

    Subscriber findByViberId(String viberId);

}
