package com.lcb.linebot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.linecorp.bot.model.event.MessageEvent;
import lombok.Data;
import lombok.ToString;

import java.awt.*;
import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineMessage {
    private String destination;
    private List<MessageEvent> events;

    public List<MessageEvent> getEvents() {
        return events;
    }

    public void setEvents(List<MessageEvent> events) {
        this.events = events;
    }}
