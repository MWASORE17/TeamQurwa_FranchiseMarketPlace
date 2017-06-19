package com.williamsumitromytextview.myapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WilliamSumitro on 18/06/2017.
 */

public class DataListEvent {
    private static final String[] JudulEvent =
            {"Franchise Expo 2017",
                    "Meetup and Join The Franchise",
                    "How to conquer your franchise business",
                    "How to take over your Parent's Company",
                    "Franchisor debat"};

    private static final String[] IsiEvent =
            {"Franchise Expo 2017 - Let's Work Together\nTicket dikenakan baiaya Rp. 1.200.000,- \nBook now",
                    "Lets go to meet and Join pur Franchise....\n" +
                            "\"+\"Only Rp. 1.300.000,- .\\n\"+\"Book now ",
                    "How to conquer your franchise business ?  - Let's Work Together.\n" +
                            "\"+\"Only Rp. 1.200.000,- .\\n\"+\"Book now",
                    "How to take over your Parent's Company ?  - Let's Come And Know it.\n" +
                            "\"+\"Only Rp. 1.200.000,- .\\n\"+\"Book now ",
                    "Welcome to Franchisor debat - Let's Build Your Future with us\"+\"\\n\"+\"Only Rp. 1.400.000,- \"+\"\\n\"+\"Book now "};

    private static final int[] GambarEvent = {R.drawable.event1, R.drawable.event2, R.drawable.event3, R.drawable.event4, R.drawable.event5};
    private static final String[] ALAMATEVENT = {"Jl. Thamrin No. 34 A", "Jl. Thamrin No. 24 A", "Jl. Asia No. 234 A", "Jl. Teladan No. 34 A", "Jl. Thamrin No. 34 A"};
    private static final String[] WAKTUEVENT = {"Senin, 9 April 2017, 20:00", "Senin, 13 April 2017, 20:00", "Rabu, 29 Maret 2018, 09:00", "Minggu, 22 April 2017, 20:00", "Senin, 9 April 2017, 20:00"};

    public static List<ListEvent> getListEvent() {
        List<ListEvent> event = new ArrayList<>();
        for (int i = 0; i < JudulEvent.length; i++) {
            ListEvent listEvent = new ListEvent();
            listEvent.setGAMBAREVENT(GambarEvent[i]);
            listEvent.setJUDULEVENT(JudulEvent[i]);
            listEvent.setALAMATEVENT(ALAMATEVENT[i]);
            listEvent.setWAKTUEVENT(WAKTUEVENT[i]);
            listEvent.setISIEVENT(IsiEvent[i]);
            event.add(listEvent);
        }
        return event;
    }
    public static int getindex(String name){
        int index = -1;
        for(int i = 0; i<JudulEvent.length;i++){
            if(name.equals(JudulEvent[i].toString()+"P1k#c!dffR$!SSAQ23")){
                index = i;
                break;
            }
            else {
                index = -1;
            }
        }
        return index;
    }
    public static String getJudulEvent(int index){
        return JudulEvent[index].toString();
    }
    public static int getGambarEvent(int index){
        return GambarEvent[index];
    }
}
