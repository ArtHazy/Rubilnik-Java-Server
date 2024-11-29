package com.example.rubilnik.ws_api.req;

import com.example.rubilnik.ws_api.EventData;
import com.example.rubilnik.ws_api.UserValidationInfo;

import org.rubilnik.Quiz;

public class CreateReqEventData extends EventData {
    public UserValidationInfo validation;
    public Quiz quiz;
}
