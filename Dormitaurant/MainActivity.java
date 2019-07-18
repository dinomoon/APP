package com.first.dormitaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.first.dormitaurant.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");

    private String getTime() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate) + " "+dayOfWeek();
    }

    private String dayOfWeek() {
        Calendar cal = Calendar.getInstance();
        String strweek = null;

        int nWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (nWeek == 1) {
            strweek = "일요일";
        } else if (nWeek == 2) {
            strweek = "월요일";
        } else if (nWeek == 3) {
            strweek = "화요일";
        } else if (nWeek == 4) {
            strweek = "수요일";
        } else if (nWeek == 5) {
            strweek = "목요일";
        } else if (nWeek == 6) {
            strweek = "금요일";
        } else if (nWeek == 7) {
            strweek = "토요일";
        }
        return strweek;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView breakfast = (TextView) findViewById(R.id.breakfast);
        final TextView lunch = (TextView) findViewById(R.id.lunch);
        final TextView dinner = (TextView) findViewById(R.id.dinner);
        final TextView date = (TextView) findViewById(R.id.date);
        final StringBuilder builder = new StringBuilder();


        date.setText(getTime());

        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList k = new ArrayList();
                try{
                    Document doc = Jsoup.connect("http://dorm.knu.ac.kr/scdorm/_new_ver/").get();
                    //String title = doc.title();
                    Elements links = doc.select("td").select(".txt_right");
                    //ArrayList where = new ArrayList();

                    for(Element link: links) {
                        k.add(link.text());
                    }

                } catch (IOException e) {
                    e.fillInStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        breakfast.setText(k.get(0).toString());
                        lunch.setText(k.get(1).toString());
                        dinner.setText(k.get(2).toString());
                    }
                });
            }
        }).start();
    }

}

