package com.example.android.iot;

/**
 * Created by s168877 on 1/10/2017.
 */

public class DeviceResource {

    private String mId;
    private String mValue;


    public  DeviceResource(String id, String Value) {
        mId = id;
        mValue = Value;

    }

    public void setId(String Id)
    {
        mId=Id;

    }

    public void setValue(String Value)
    {
        mValue=Value;

    }

    public String getId()
    {
        return mId;
    }

    public String getValue()
    {
        return mValue;
    }



}