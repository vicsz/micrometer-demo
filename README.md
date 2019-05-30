# Micrometer Demo 

## Solution - Metrics Controller: 

```java 

    @RequestMapping("/count")
    public void count(){

        Metrics.counter("sample.counter").increment(Math.random());

    }

    @PostConstruct
    public void gauge(){

        Metrics.gauge("sample.gauge", Runtime.getRuntime(), Runtime::freeMemory);

    }


    @RequestMapping("/timer")
    public void timer(){

       Metrics.timer("sample.timer").record(this::randomDelay);

    }

    @RequestMapping("/summary")
    public void distributionSummary() {

       Metrics.summary("sample.summary").record(getRandomPurchaseAmount());

    }

    @Scheduled(fixedRate = 10000)
    public void performScheduledPurchases() {

        Metrics.summary("sample.summary.tagged", "product_name", getRandomPurchaseName()).record(getRandomPurchaseAmount());

    }


```

## Humio Integration Notes 

Setup a Free Humio Cloud account at : https://humio.com

Add the Humio Micrometer registry to your build dependencies. 

```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-humio</artifactId>
    <version>1.1.4</version>
</dependency>
```

In your sandbox repo settings, create and get an Ingest Token. 

Update your application properties with the Humio ingest token:

```properties
    management.metrics.export.humio.api-token=YOUR_TOKEN
```

<img src="img/humio.png" width="800">

Sample Humio Queries: 

Timechart example:

__name=purchase | timechart(function=avg(sum))__

Timechart example split by product_name (label / tag):

__name=purchase | timechart(product_name, function=avg(sum))__