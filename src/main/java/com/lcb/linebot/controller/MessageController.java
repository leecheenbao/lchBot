package com.lcb.linebot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcb.linebot.enums.ReplyTypeEnum;
import com.lcb.linebot.model.Restaurant;
import com.lcb.linebot.model.Restaurants;
import com.lcb.linebot.util.GetAPI;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.lcb.linebot.enums.ReplyTypeEnum.*;

@Slf4j
@LineMessageHandler
public class MessageController {

    @Autowired
    private LineMessagingClient lineMessagingClient;
    @Value("${keelung.restaurants.url}")
    private String foodAPI;
    @Value("{keelung.accommodations.url}")
    private String accommodationsAPI;
    @Value("{keelung.attractions.url")
    private String attractionsAPI;

    @EventMapping
    public void handleTextMessage(MessageEvent<TextMessageContent> event) throws JsonProcessingException {
        TextMessageContent message = event.getMessage();
        handleTextContent(event.getReplyToken(), event, message );
    }

    @EventMapping
    public void handleStickerMessage(MessageEvent<StickerMessageContent> event) {
        StickerMessageContent message = event.getMessage();
        reply(event.getReplyToken(), new StickerMessage(
                message.getPackageId(), message.getStickerId()
        ));
    }

    private void handleTextContent(String replyToken, Event event, TextMessageContent content) throws JsonProcessingException {
        String text = content.getText().toUpperCase();
        GetAPI getAPI = new GetAPI();
        StringBuffer stringBuffer = new StringBuffer();
        String res = "";
        // 首先，建立 ObjectMapper 物件
        ObjectMapper mapper = new ObjectMapper();

        switch (text) {
            case "COFFEE":{
                String foodData = getAPI.httpRequest(foodAPI);
                Restaurants restaurants = mapper.readValue(foodData, Restaurants.class);
                for (Restaurant restaurant :restaurants.getRestaurants()){
                    if (restaurant.getTitle().contains("咖啡")){
                        stringBuffer.append(getRestaurantInfo(restaurant));
                    }
                }
            }
            case "FOOD":{
                String foodData = getAPI.httpRequest(foodAPI);
                Restaurants restaurants = mapper.readValue(foodData, Restaurants.class);
                for (Restaurant restaurant : restaurants.getRestaurants()){
                    stringBuffer.append(getRestaurantInfo(restaurant));
                }
            }

            case "WEATHER":{

            }

            res = stringBuffer.toString();
            if (res.length() > 4000){
                res =  stringBuffer.substring(0, 4000 - 2) + "...";
            }

            this.reply(replyToken, Arrays.asList(
                new TextMessage(res)
            ));
        }
    }

    private void handleStickerContent(String replyToken, StickerMessageContent content) {
        reply(replyToken, new StickerMessage(
                content.getPackageId(), content.getStickerId()
        ));
    }


    private void replyText(@NonNull  String replyToken, @NonNull String message) {
        if(replyToken.isEmpty()) {
            throw new IllegalArgumentException("replyToken is not empty");
        }

        if(message.length() > 1000) {
            message = message.substring(0, 1000 - 2) + "...";
        }
        this.reply(replyToken, new TextMessage(message));
    }

    private void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, Collections.singletonList(message));
    }

    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        try {
            BotApiResponse response = lineMessagingClient.replyMessage(
                    new ReplyMessage(replyToken, messages)
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private StringBuffer getRestaurantInfo(Restaurant restaurant){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer = restaurant.getTitle() != null ?
                stringBuffer.append( restaurant.getTitle() + "\n") : stringBuffer;
        stringBuffer = restaurant.getPhone() != null ?
                stringBuffer.append("電話：" + restaurant.getPhone() + "\n") : stringBuffer;
        stringBuffer = restaurant.getAddress() != null ?
                stringBuffer.append("地址：" + restaurant.getAddress() + "\n") : stringBuffer;
        stringBuffer = restaurant.getNote() != null ?
                stringBuffer.append("tips：" + restaurant.getNote() + "\n") : stringBuffer;
        stringBuffer = restaurant.getWeb_url() != null ?
                stringBuffer.append("連結：" + restaurant.getWeb_url() + "\n") : stringBuffer;
        stringBuffer = restaurant.getFb_url() != null ?
                stringBuffer.append("FB連結：" + restaurant.getFb_url() + "\n") : stringBuffer;
        stringBuffer = restaurant.getParkingInformation() != null ?
                stringBuffer.append("停車位：" + restaurant.getParkingInformation() + "\n") : stringBuffer;

        if (!(".").equals(restaurant.getTrafficGuideline())){
            stringBuffer = restaurant.getTrafficGuideline() != null ?
                    stringBuffer.append("交通方式：\n" + restaurant.getTrafficGuideline() + "\n") : stringBuffer;
        }
        //結尾多一個分行
        stringBuffer.append("\n");

        return stringBuffer;
    }

}
