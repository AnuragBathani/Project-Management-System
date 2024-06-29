package com.anurag.service;

import com.anurag.model.PlanType;
import com.anurag.model.Subscription;
import com.anurag.model.User;

public interface SubscriptionService {


    Subscription createSubsription(User user);

    Subscription getUserSubscription(Long userId) throws Exception;

    Subscription updateSubscription(Long userId, PlanType currentplan) ;

    boolean isValid(Subscription subscription);

}
