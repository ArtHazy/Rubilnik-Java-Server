package com.example.rubilnik.ws_api.res;

import com.example.rubilnik.ws_api.UserPublicData;

import java.util.ArrayList;

public class LeftEventData extends JoinEventData {
    public com.example.rubilnik.ws_api.UserPublicData user;
    public ArrayList<UserPublicData> roommates;
}
