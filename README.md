# Micrometer Demo 

## Solution - Metrics Controller: 

```java 

   @RequestMapping("/transferFile")
   public void transferFile(){

       Metrics.counter("files.transferred").increment(new Random().nextInt(10));

   }

   @PostConstruct
   public void waterLevel(){

       Metrics.gauge("water.level", this, MetricsController::getWaterLevel);

   }


   @RequestMapping("/randomDelay")
   public void timer(){

       Metrics.timer("random.delay.time").record(this::randomDelay);

   }

   @RequestMapping("/purchase")
   public void purchase() {

       Metrics.summary("purchase").record(getRandomPurchaseAmount());

   }

   @Scheduled(fixedRate = 2000)
   public void performScheduledPurchases() {

       Metrics.summary("purchase.details", "product_name", getRandomPurchaseName()).record(getRandomPurchaseAmount());

   }

   @RequestMapping("/exception")
   public void exception(){
       throw new RuntimeException("Something bad happened!!");
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

__name=purchase_amount | timechart(product_name, function=avg(sum))__


Count purchases: 

name=purchase | sum(field=sum)

## Creating a Slack Incoming WebHook URL

For Humio Alerting functionality

One can be created by logging into your Slack account at www.slack.com, browsing the *App Directory* for *Incoming WebHooks* and adding your own configuration. 

(App Directory Link is available on home page footer of Slack under Resources).

<img src="img/appdirectory.png" width="750">

<img src="img/webhook.png" width="750">

<img src="img/webhookurl.png" width="750">

You should then be able to send Slack messages to yourself by *posting* to that URL.

Example: (note INSERT_YOUR_WEB_HOOK_URL -- update this with your generated "Webhook URL")

```sh
curl -s -d "payload={\"text\":\"Test Message\"}" INSERT_YOUR_WEB_HOOK_URL_HERE
```