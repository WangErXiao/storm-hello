package com.yao.mq.model;

import com.yao.mq.utils.EventType;
import com.yao.mq.utils.SourceType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yao on 15/5/6.
 */
public class EventModel implements Serializable{
    private String id;
    private EventType eventType;
    private Date date;
    private SourceType sourceType;

    public EventModel() {

    }

    public EventModel(String id, EventType eventType, Date date, SourceType sourceType) {
        this.id = id;
        this.eventType = eventType;
        this.date = date;
        this.sourceType = sourceType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }
}
