package com.example.android.iot;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.LightingColorFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.example.android.iot.QueryUtils.fetchLeshanClientData;

public class ControlActivity extends AppCompatActivity {
    private String mUrl="http://192.168.1.132:8080/api/clients";
    private String LightColorUrl;
    private String LowLightUrl;
    private String writelUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_control);
        TextView t=(TextView)findViewById(R.id.textView);

        Intent intent=getIntent();

       LeshanClient l= QueryUtils.fetchLeshanClientData(mUrl).get(0);

        String ClientName=l.getClientName();
         LightColorUrl=mUrl+"/"+ClientName+"/10250/0/5/write/";
        LowLightUrl=mUrl+"/"+ClientName+"/10250/0/6/write/";

        List<String> ObjUrl=l.getObjectURL();
        int k=-1;
        for(int i=0;i<ObjUrl.size();i++)
        {
            String url=ObjUrl.get(i);
            if(url.contains("10250"))
                k=i;
        }
        String LightColor="White";
        String LowLight="false";
        DeviceIOT d=l.getDevices().get(k);
        List<DeviceResource>r =d.getResources();
        for(int i=0;i<r.size();i++)
        {

            if(r.get(i).getId().equals("5"))
                LightColor=r.get(i).getValue();

            if(r.get(i).getId().equals("6"))
                LowLight=r.get(i).getValue();


        }


            if(LightColor.equals("(0,0,0)")) {
                RadioButton radio = (RadioButton) findViewById(R.id.radio_off);
                radio.setChecked(true);
                t.setText("CurrentColor: OFF");

            }

        if(LightColor.equals("(255,0,0)")) {
            RadioButton radio = (RadioButton) findViewById(R.id.radio_red);
            radio.setChecked(true);
            t.setText("CurrentColor: RED");

        }

        if(LightColor.equals("(255,255,255)")) {
            RadioButton radio = (RadioButton) findViewById(R.id.radio_white);
            radio.setChecked(true);
            t.setText("CurrentColor: WHITE");

        }


        if(LightColor.equals("(250,200,100)")) {
            RadioButton radio = (RadioButton) findViewById(R.id.radio_dim);
            radio.setChecked(true);
            t.setText("CurrentColor: DIM");
           }

    }
public void OnCheckBoxClick(View view)
{

    boolean checked = ((CheckBox) view).isChecked();

    if(checked)
    {
        writelUrl=LowLightUrl+"true";
        QueryUtils.PostData(writelUrl);
    }
    else
    {writelUrl=LowLightUrl+"false";
        QueryUtils.PostData(writelUrl);

    }

}
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?

        TextView t=(TextView)findViewById(R.id.textView);


        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_off:
                if (checked)
                    t.setText("Updated Color: Off");
                writelUrl=LightColorUrl+"(0,0,0)";
                QueryUtils.PostData(writelUrl);
                break;

            case R.id.radio_red:
                if (checked)
                    t.setText("Updated Color: Red");
                writelUrl=LightColorUrl+"(255,0,0))";
                QueryUtils.PostData(writelUrl);
                    break;
            case R.id.radio_white:
                if (checked)
                    t.setText("Updated Color: White");
                writelUrl=LightColorUrl+"(255,255,255)";
                QueryUtils.PostData(writelUrl);
                    break;
            case R.id.radio_dim:
                if (checked)
                    t.setText("Updated Color: Dim");
                writelUrl=LightColorUrl+"(250,200,100)";
                QueryUtils.PostData(writelUrl);

                break;
        }
    }


}
