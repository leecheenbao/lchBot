package com.lcb.linebot.enums;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public enum ReplyTypeEnum {
    KLG_LIST("LIST","基隆清單"),
    KLG_WEATHER("WEATHER", "基隆天氣"),
    KLG_FOOD("FOOD","基隆美食"),
    KLG_VIEW("VIEW","基隆景點"),
    NO_DATA("NO_DATA","找不到關鍵字");


    String type;

    String desc;

    ReplyTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDesc(String type) {
        for (ReplyTypeEnum ele : ReplyTypeEnum.values()) {
            if (type.equals(ele.getType())) {
                return ele.getDesc();
            }
        }
        return null;
    }

    public static ReplyTypeEnum getEnum(String type) {
        for (ReplyTypeEnum ele : ReplyTypeEnum.values()) {
            if (type.equals(ele.getType())) {
                return ele;
            }
        }
        return null;
    }

    public static List<Map<String, Object>> getList() {
        List<Map<String, Object>> list = Lists.newArrayList();
        for (ReplyTypeEnum ele : ReplyTypeEnum.values()) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("type", ele.getType());
            map.put("desc", ele.getDesc());
            list.add(map);
        }
        return list;
    }
}
