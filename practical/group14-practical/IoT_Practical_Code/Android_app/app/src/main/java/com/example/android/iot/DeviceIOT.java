package com.example.android.iot;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.value;

/**
 * Created by s168877 on 1/10/2017.
 */

public class DeviceIOT {

    private List<DeviceResource>Resources=new ArrayList<>();



    public void addResource(String id,String value)
    {

        Resources.add(new DeviceResource(id,value));
    }

    public List<DeviceResource> getResources()
    {

        return Resources;
    }


}


