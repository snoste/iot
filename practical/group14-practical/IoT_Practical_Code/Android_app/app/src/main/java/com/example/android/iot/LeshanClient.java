package com.example.android.iot;

import android.bluetooth.BluetoothClass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LeshanClient implements Serializable {


    private int  ndevices;


    private String ClientName;

    private String serverUrl="http://192.168.56.1:8080/api/clients";

    private List<String>ObjectURL=new ArrayList<>();

    private List<DeviceIOT>Devices=new ArrayList<>();

    //public DeviceIot device;

    public LeshanClient( String Clientname) {
        ClientName = Clientname;

    }

    public void setUrl(String Url)
    {

        serverUrl=Url;
    }

    public List<String> getObjectURL()
    {
        return ObjectURL;
    }



    public boolean addUrl( String url ) {
        ObjectURL.add( url );
        return true;
    }

    public boolean UpdateDeviceData()
    {
        int i=ObjectURL.size();

        for (int j=0;j<i;j++) {

            Devices.add(QueryUtils.fetchDeviceData(serverUrl,ClientName,ObjectURL.get(j)));
        }
          return  true;
    }

    public String getClientName()
    {
        return ClientName;

    }

    public List<DeviceIOT> getDevices()
    {
        return Devices;
    }





}
