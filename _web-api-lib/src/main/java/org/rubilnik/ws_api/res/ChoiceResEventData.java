package com.example.rubilnik.ws_api.res;

import com.example.rubilnik.ws_api.EventData;
import com.example.rubilnik.ws_api.UserPublicData;

public class ChoiceResEventData extends EventData {
    public UserPublicData user;
    public int questionInd;
    public int[] choicesInd;
}
