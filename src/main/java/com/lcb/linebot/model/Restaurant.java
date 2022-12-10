package com.lcb.linebot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Restaurant {

    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("cover_image")
    private String coverImage;
    @JsonProperty("address")
    private String address;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty("business_hours")
    private String businessHours;
    @JsonProperty("traffic_guideline")
    private String trafficGuideline;
    @JsonProperty("parking_information")
    private String parkingInformation;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("fb_url")
    private String fb_url;
    @JsonProperty("web_url")
    private String web_url;
    @JsonProperty("note")
    private String note;
}
