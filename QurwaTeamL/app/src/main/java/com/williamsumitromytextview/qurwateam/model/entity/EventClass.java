package com.williamsumitromytextview.qurwateam.model.entity;

/**
 * Created by USER on 15/05/2017.
 */

public class EventClass {
    private Integer id;
    private String JudulEvent;
    private String IsiEvent;
    private String GambarEvent;
    private String ALAMATEVENT;
    private String WAKTUEVENT;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJudulEvent() {
        return JudulEvent;
    }

    public void setJudulEvent(String judulEvent) {
        JudulEvent = judulEvent;
    }

    public String getIsiEvent() {
        return IsiEvent;
    }

    public void setIsiEvent(String isiEvent) {
        IsiEvent = isiEvent;
    }

    public String getGambarEvent() {
        return GambarEvent;
    }

    public void setGambarEvent(String gambarEvent) {
        GambarEvent = gambarEvent;
    }

    public String getALAMATEVENT() {
        return ALAMATEVENT;
    }

    public void setALAMATEVENT(String ALAMATEVENT) {
        this.ALAMATEVENT = ALAMATEVENT;
    }

    public String getWAKTUEVENT() {
        return WAKTUEVENT;
    }

    public void setWAKTUEVENT(String WAKTUEVENT) {
        this.WAKTUEVENT = WAKTUEVENT;
    }

}
