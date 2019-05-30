package com.example.micrometerdemo;

import io.micrometer.core.instrument.Metrics;
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
@EnableScheduling
public class MetricsController {


    @RequestMapping("/transferFile")
    public void transferFile(){

        System.out.println("Transferred " + new Random().nextInt(10) + " files");

        //TODO Count number of times this was called
    }

    @PostConstruct
    public void waterLevel(){

        System.out.println("Water level: " + getWaterLevel());

        //TODO Sample Water Level

    }


    @RequestMapping("/randomDelay")
    public void timer(){

        randomDelay();

        //TODO Time the Method
    }

    @RequestMapping("/purchase")
    public void purchase() {

        System.out.println("Purchase of : " + getRandomPurchaseName());

        //TODO Track a purchase amounts

    }

    @Scheduled(fixedRate = 2000)
    public void performScheduledPurchases() {

        System.out.println("Purchased " + getRandomPurchaseName() + " for " + getRandomPurchaseAmount());

        //TODO Track detailed purchase amounts (with tag)
    }

    @RequestMapping("/exception")
    public void exception(){

        //TODO Add alerting on 500 errors
    }



    //////////////
    //////////////
    //////////////


    private double getRandomPurchaseAmount(){

        //Random Purchase Amount with an minute sinusoidal pattern
        double waveValue = (Math.cos(((double) LocalTime.now().getSecond() )/ 60 * (2 * Math.PI)) + 1 ) / 2;
        double totalValue = waveValue * 35 + (Math.random() * 50);

        return Math.round(totalValue);

    }

    private String getRandomPurchaseName() {

        List<String> purchaseNames = Arrays.asList("car", "boat", "house", "goat", "dog");

        return purchaseNames.get((int)(Math.random() * purchaseNames.size()));
    }

    private double getWaterLevel(){

        //Emulate variable water level with free memory stats
        return Runtime.getRuntime().freeMemory();
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

