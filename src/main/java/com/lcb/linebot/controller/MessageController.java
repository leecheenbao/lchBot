package com.lcb.linebot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcb.linebot.model.LineMessage;
import com.linecorp.bot.client.LineBlobClient;
import com.linecorp.bot.client.LineClientConstants;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.response.GetMessageEventResponse;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.sun.org.apache.xml.internal.serialize.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/message")
@Slf4j
@LineMessageHandler
public class MessageController {
    @PostMapping("info")
    public ResponseEntity<Map<String, Object>> getDataInfo(@RequestBody LineMessage lineMessage) {
        Map<String,Object> respResult = new LinkedHashMap<>();
        System.out.println(lineMessage);
        String test = lineMessage.getEvents().toArray().toString();
        return ResponseEntity.ok(respResult);

    }


}
