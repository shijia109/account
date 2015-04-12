package com.example.AccountBook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private Button button_keepAccount;
    private MySQLiteOpenHelper myHealper;
    private Calendar calendar;
    private int curr_year;
    private int curr_month;
    private int curr_day;

    private TextView textView_income_thisMonth;
    private TextView textView_outlay_thisMonth;
    private TextView textView_remain_thisMonth;

    private TextView textView_income_today;
    private TextView textView_outlay_today;

    private TextView textView_income_thisWeek;
    private TextView textView_outLay_thisWeek;

    private TextView textView_income_thisYear;
    private TextView textView_outlay_thisYear;

    private TextView textView_month_now;

    private   int DAY_OF_WEEK;

    private float sum_income_today,sum_outlay_today;
    private float sum_income_thisMonth,sum_outlay_thisMonth;
    private float sum_income_thisYear,sum_outlay_thisYear;
    private float sum_income_thisWeek,sum_outlay_thisWeek;



    private List<Map<String,Object>> list_income,list_outlay;

    private LinearLayout layout_today;
    private  LinearLayout layout_thisWeek;
    private  LinearLayout layout_thisMonth;
    private  LinearLayout layout_thisYear;



    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.main);

        button_keepAccount= (Button) findViewById(R.id.button_keep_account);
        myHealper=new MySQLiteOpenHelper(this);
        //获得当前日期
        calendar=Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        curr_year=calendar.get(Calendar.YEAR);
        curr_month=calendar.get((Calendar.MONTH));
        curr_day=calendar.get(Calendar.DAY_OF_MONTH);
        DAY_OF_WEEK=calendar.get(Calendar.DAY_OF_WEEK);

        //控件的初始化
        textView_income_thisMonth= (TextView) findViewById(R.id.textView_main_income_this_month);
        textView_outlay_thisMonth= (TextView) findViewById(R.id.textView_main_outlay_this_month);
        textView_remain_thisMonth= (TextView) findViewById(R.id.textView_main_remaining_this_month);

        textView_income_today= (TextView) findViewById(R.id.textView_main_income_today);
        textView_outlay_today= (TextView) findViewById(R.id.textView_main_outlay_today);

        textView_income_thisYear= (TextView) findViewById(R.id.textView_main_income_this_year);
        textView_outlay_thisYear= (TextView) findViewById(R.id.textView_main_outlay_this_year);

        textView_income_thisWeek= (TextView) findViewById(R.id.textView_main_income_this_week);
        textView_outLay_thisWeek= (TextView) findViewById(R.id.textView_main_outlay_this_week);

        textView_month_now= (TextView) findViewById(R.id.textView_main_month_now);

        //下边3个导航条及上面一个
        layout_today= (LinearLayout) findViewById(R.id.layout_main_today);
        layout_thisWeek= (LinearLayout) findViewById(R.id.layout_main_this_week);
        layout_thisYear= (LinearLayout) findViewById(R.id.layout_main__this_year);
        layout_thisMonth= (LinearLayout) findViewById(R.id.layout_main__this_month);

        //////////点击监听
        //----------------------记账按钮
        button_keepAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });

        //-------今天
        layout_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("date_type","today");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        layout_thisWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("date_type","thisWeek");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        layout_thisMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("date_type","thisMonth");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        layout_thisYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("date_type","thisYear");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDBdata();
        textView_month_now.setText((curr_month+1)+"月");

    }


    private  void getDBdata(){
        list_income=myHealper.selectList("select * from tb_income",null);
        list_outlay=myHealper.selectList("select * from tb_outlay",null);

        if(list_income.size()>0){
            //  Toast.makeText(this,"income不空"+list_income,Toast.LENGTH_SHORT).show();


            //   Log.i("","date="+db_year+db_month+db_day);
            for (int i = 0; i < list_income.size(); i++) {
                String getDate=list_income.get(i).get("date").toString();
                //   Log.i("","getDate="+getDate);
                String[] dates=getDate.split("-");
                String db_year  = dates[0];
                String db_month = dates[1];
                String db_day   = dates[2];

                if (Integer.parseInt(db_year)==curr_year){
                    sum_income_thisYear+=Float.valueOf(list_income.get(i).get("money").toString());
                    if(Integer.parseInt(db_month)==curr_month+1){
                        sum_income_thisMonth+=Float.valueOf(list_income.get(i).get("money").toString());
                        if(Integer.parseInt(db_day)==curr_day){
                            sum_income_today+=Float.valueOf(list_income.get(i).get("money").toString());
                        }

                        if(Integer.parseInt(db_day)>=DAY_OF_WEEK && Integer.parseInt(db_day)<DAY_OF_WEEK+7){
                            sum_income_thisWeek+=Float.valueOf(list_income.get(i).get("money").toString());
                        }

                    }

                }

            }

            textView_income_today.setText(String.valueOf(sum_income_today));
            textView_income_thisYear.setText(String.valueOf(sum_income_thisYear));
            textView_income_thisMonth.setText(String.valueOf(sum_income_thisMonth));
            textView_income_thisWeek.setText(String.valueOf(sum_income_thisWeek));

            //支出
            for (int i = 0; i < list_outlay.size(); i++) {
                String getDate=list_outlay.get(i).get("date").toString();
                //   Log.i("","getDate="+getDate);
                String[] dates=getDate.split("-");
                String db_year  = dates[0];
                String db_month = dates[1];
                String db_day   = dates[2];

                if (Integer.parseInt(db_year)==curr_year){
                    sum_outlay_thisYear+=Float.valueOf(list_outlay.get(i).get("money").toString());
                    if(Integer.parseInt(db_month)==curr_month+1){
                        sum_outlay_thisMonth+=Float.valueOf(list_outlay.get(i).get("money").toString());
                        if(Integer.parseInt(db_day)==curr_day){
                            sum_outlay_today+=Float.valueOf(list_outlay.get(i).get("money").toString());
                        }

                        if(Integer.parseInt(db_day)>=DAY_OF_WEEK && Integer.parseInt(db_day)<DAY_OF_WEEK+7){
                            sum_outlay_thisWeek+=Float.valueOf(list_outlay.get(i).get("money").toString());
                        }
                    }
                }
            }

            textView_outlay_today.setText(String.valueOf(sum_outlay_today));
            textView_outlay_thisYear.setText(String.valueOf(sum_outlay_thisYear));
            textView_outlay_thisMonth.setText(String.valueOf(sum_outlay_thisMonth));
            textView_outLay_thisWeek.setText(String.valueOf(sum_outlay_thisWeek));
            float remain=sum_income_thisMonth-sum_outlay_thisMonth;
            textView_remain_thisMonth.setText(String.valueOf(remain));
            if(remain>0){
                textView_remain_thisMonth.setTextColor(Color.rgb(238,17,17));
            }else{
                textView_remain_thisMonth.setTextColor(Color.rgb(17,228,17));
            }






        }else{
            Log.i("","空list_income="+list_income);
        }

        if(list_outlay.size()>0){
            //   Toast.makeText(this,"outlay不空"+list_outlay,Toast.LENGTH_SHORT).show();
            Log.i("","list_outlay="+list_outlay);
        }else{
            Log.i("","空list_outlay="+list_outlay);
        }

    }

}
