package com.lcb.linebot.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Events {
    private String type;
    private String message;
    private String webhookEventId;
    private String deliveryContext;
    private String timestamp;
    private String source;
    private String replyToken;
    private String mode;

}
