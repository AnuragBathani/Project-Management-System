package com.anurag.service;

import com.anurag.model.PlanType;
import com.anurag.model.Subscription;
import com.anurag.model.User;
import com.anurag.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Override
    public Subscription createSubsription(User user) {

        Subscription subscription=new Subscription();

        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);

        return subscriptionRepository.save(subscription);
    }


    @Override
    public Subscription getUserSubscription(Long userId) throws Exception {
        Subscription subscription= subscriptionRepository.findByUserId(userId);
        if(!isValid(subscription)){
            subscription.setPlanType(PlanType.FREE);
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
            subscription.setSubscriptionStartDate(LocalDate.now());

        }
        return subscription;
    }

    @Override
    public Subscription updateSubscription(Long userId, PlanType currentplan) {

        Subscription subscription=subscriptionRepository.findByUserId(userId);
        subscription.setPlanType(currentplan);
        subscription.setSubscriptionStartDate(LocalDate.now());

        if(currentplan.equals(PlanType.MONTHLY)){
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
        } else if (currentplan.equals(PlanType.ANNUALLY)) {
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        }

        return subscriptionRepository.save(subscription);
    }



    @Override
    public boolean isValid(Subscription subscription) {

        if(subscription.getPlanType().equals(PlanType.FREE)){
            return true;
        }

        LocalDate enddate=subscription.getSubscriptionEndDate();

        LocalDate currdate=LocalDate.now();


        return enddate.isAfter(currdate) || enddate.isEqual(currdate);
    }
}
