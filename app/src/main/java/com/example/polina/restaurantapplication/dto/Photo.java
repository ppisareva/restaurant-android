package com.example.polina.restaurantapplication.dto;

import java.io.Serializable;

/**
 * Created by polina on 03.02.16.
 */
public class Photo implements Serializable {

          private String prefix;
           private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    private String id;
          private long createdAt;
          private int width;
           private int  height;
          private String visibility;
}
