package com.example.rubilnik.ws_api.req;

import com.example.rubilnik.ws_api.UserPublicData;

import java.util.ArrayList;

public class LeftReqEventData extends JoinReqEventData {
    public UserPublicData user;
    public ArrayList<UserPublicData> roommates;
}
