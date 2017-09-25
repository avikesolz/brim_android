package com.brim.Pojo;

import org.json.JSONObject;

/**
 * Created by su on 4/9/17.
 */

public class OfferSetGet {

    String type;
    JSONObject content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSONObject getContent() {
        return content;
    }

    public void setContent(JSONObject content) {
        this.content = content;
    }
}
