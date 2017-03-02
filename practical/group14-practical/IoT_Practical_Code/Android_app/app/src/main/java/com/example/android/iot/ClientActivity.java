package com.example.android.iot;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class ClientActivity extends AppCompatActivity {

    String mUrl="http://192.168.1.132:8080/api/clients";
    //String postUrl="http://192.168.56.1:8080/api/clients/s168877/3/0/15/write/europefnafn";
    List<LeshanClient> leshanclients;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //QueryUtils.PostData(postUrl);
        //QueryUtils.makeHttpRequest(createUrl(postUrl));
        leshanclients = QueryUtils.fetchLeshanClientData(mUrl);


        // Create a list of words
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<Button> wordviewList = new ArrayList<>();
        //for(int i=0;i<leshanclients.size();i++)
            //LeshanClient l=new leshanclients.get(i);
        for(LeshanClient l:leshanclients) {
            words.add(l.getClientName());
           /* List<DeviceIOT>devices=l.getDevices();
            for(DeviceIOT d:devices)
            {
                List<DeviceResource>Resources=d.getResources();

                for(DeviceResource R:Resources)
                {
                    words.add(R.getId()+"---"+R.getValue());
                }

            }*/
        }

        LinearLayout rootview=(LinearLayout)findViewById(R.id.activity_client);


        for(i=0;i<words.size();i++) {
            Button wordview=new Button(this);
            wordview.setText(words.get(i));
            wordviewList.add(wordview);
            rootview.addView(wordview);



            wordview.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {

                Intent numbersIntent = new Intent(ClientActivity.this, ControlActivity.class);
                //numbersIntent.putExtra("ClientObj",leshanclients.get(0));
                startActivity(numbersIntent);
            }


        });
    }

        }
}
