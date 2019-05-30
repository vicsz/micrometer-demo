package com.example.micrometerdemo;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/metrics")
@EnableScheduling
public class MetricsController {


    @RequestMapping("/count")
    public void count(){

        //TODO Count number of times this was called

        System.out.println(Math.random());

    }

    @PostConstruct
    public void gauge(){

        //TODO Sample FreeMemory Available

        System.out.println(Runtime.getRuntime().freeMemory());

    }


    @RequestMapping("/timer")
    public void timer(){

        //TODO Time the Method

        randomDelay();

    }

    @RequestMapping("/summary")
    public void distributionSummary() {

        //TODO Track a purchase amounts

        System.out.println(getRandomPurchaseAmount());

    }

    @Scheduled(fixedRate = 10000)
    public void performScheduledPurchases() {

        //TODO Track purchase amounts , broken down by purchase names

        System.out.println(getRandomPurchaseName() + " " + getRandomPurchaseAmount());

        
    }

    ////

    //Random Purchase Amount with an hourly pattern
    private double getRandomPurchaseAmount(){

        double waveValue = (Math.cos(((double) LocalTime.now().getMinute() )/ 60 * (2 * Math.PI)) + 1 ) / 2;

        double totalValue = waveValue * 35 + (Math.random() * 50);

        return Math.round(totalValue);

    }

    private String getRandomPurchaseName() {

        List<String> purchaseNames = Arrays.asList("car", "boat", "house", "goat", "dog");

        return purchaseNames.get((int)(Math.random() * purchaseNames.size()));
    }


    private void randomDelay(){

        int min_millisecond_delay = 50;
        int max_millisecond_delay = 250;

        int millisecond_delay = new Random().nextInt(max_millisecond_delay-min_millisecond_delay) + min_millisecond_delay;

        try {
            TimeUnit.MILLISECONDS.sleep(millisecond_delay);
        } catch (InterruptedException ignored) {}
    }


}

