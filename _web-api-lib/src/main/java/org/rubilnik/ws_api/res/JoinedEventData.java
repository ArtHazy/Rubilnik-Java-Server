package com.example.rubilnik.ws_api.res;

import com.example.rubilnik.ws_api.UserPublicData;

import java.util.ArrayList;

public class JoinedEventData extends JoinEventData {
    public UserPublicData user;
    public ArrayList<UserPublicData> roommates;
}
