package com.example.jsondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String jsonWeather, jsonMain, nameLocated, timezone;
    ImageView imWeather;
    List<Weather> listWeather;
    TextView tvLocated, tvTime, tvWeather, tvTemp;
    String json= "{\"coord\": { \"lon\": 139,\"lat\": 35}, \"weather\": [ { \"id\": 800, \"main\": \"Clear\", \"description\": \"clear sky\", \"icon\": \"01n\" } ]," +
            " \"base\": \"stations\", \"main\": { \"temp\": 289.92, \"pressure\": 1009, \"humidity\": 92, \"temp_min\": 288.71, \"temp_max\": 290.93 }, \"wind\": { \"speed\": 0.47, \"deg\": 107.538 }, \"clouds\": { \"all\": 2 }, \"dt\": 1560350192," +
            " \"sys\": { \"type\": 3, \"id\": 2019346, \"message\": 0.0065, \"country\": \"JP\", \"sunrise\": 1560281377, \"sunset\": 1560333478 }, \"timezone\": 32400, \"id\": 1851632, \"name\": \"Shuzenji\", \"cod\": 200 }";
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLocated= findViewById(R.id.tvLocated);
        tvTime= findViewById(R.id.tvTime);
        tvWeather= findViewById(R.id.tvWeather);
        tvTemp= findViewById(R.id.tvTemp);
        listWeather= new ArrayList<>();

        try {
            JSONObject object= new JSONObject(json);
            jsonWeather= object.getString("weather");
            jsonMain= object.getString("main");
            nameLocated= object.getString("name");
            timezone= object.getString("timezone");

            tvLocated.setText(nameLocated);
            int time= Integer.valueOf(timezone);

            tvTime.setText(TimeZone(time));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject obMain= new JSONObject(jsonMain);
            String temp= obMain.getString("temp");

            float tempC= Float.valueOf(temp) - 273.15f;
            tempC= Math.round(tempC*10)/10;
            tvTemp.setText(String.valueOf(tempC)+" C");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray= new JSONArray(jsonWeather);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject object= jsonArray.getJSONObject(i);
                String id= object.getString("id");
                String main= object.getString("main");
                String strDes= object.getString("description");
                String icon= object.getString("icon");
                listWeather.add(new Weather(id, main, strDes, icon));
            }
            tvWeather.setText(listWeather.get(0).getDes());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private String TimeZone(int time){
        int hour= time/3600;
        int sec= time%3600;
        int minu= sec/60;
        String minute, session;
        if(minu<10)
            minute= "0"+String.valueOf(minu);
        else
            minute= String.valueOf(minu);

        if(hour<13)
            session= "AM";
        else
            session="PM";

        return "Time: "+hour+":"+minute+" "+session;
    }
}
