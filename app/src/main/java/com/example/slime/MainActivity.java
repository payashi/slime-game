package com.example.slime;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.MediaController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    int num=0,best;
    boolean tOver=false;
    ImageView slime;
    TextView tName,tCount,tTime,tBest;
    Drawable d1,d2;
    Globals globals;
    private Timer mainTimer;					//タイマー用
    private MainTimerTask mainTimerTask;		//タイマタスククラス
    private int count = 10;						//カウント
    private Handler mHandler = new Handler();   //UI Threadへのpost用ハンドラ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slime = (ImageView)findViewById(R.id.slime);
        tName = (TextView)findViewById(R.id.tName);
        tCount = (TextView)findViewById(R.id.tCount);
        tTime = (TextView)findViewById(R.id.tTime);
        tBest=(TextView)findViewById(R.id.tBest);
        Resources res = getResources();
        d1=res.getDrawable(R.drawable.s1_1);
        d2=res.getDrawable(R.drawable.s1_2);
        globals = (Globals) this.getApplication();
        try{
            InputStream in = openFileInput("a.txt");
            BufferedReader reader =
            new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String s;
            s=reader.readLine();
            best=Integer.parseInt(s);
            reader.close();
            in.reset();
        }catch(IOException e) {
            e.printStackTrace();
        }
        //タイマーインスタンス生成
        this.mainTimer = new Timer();
        //タスククラスインスタンス生成
        this.mainTimerTask = new MainTimerTask();
        //タイマースケジュール設定＆開始
        this.mainTimer.schedule(mainTimerTask, 1000, 1000);
        tBest.setText("Best:"+Integer.toString(best));
    }
    public void count(){
        if(num==30) {
            tName.setText("Stab Slime");
            d1 = getResources().getDrawable(R.drawable.s4_1);
            d2 = getResources().getDrawable(R.drawable.s4_2);
            ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(100);
        }else if(num==20){
            tName.setText("Tempest Slime");
            d1=getResources().getDrawable(R.drawable.s3_1);
            d2=getResources().getDrawable(R.drawable.s3_2);
            ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(100);
        }else if(num==10) {
            tName.setText("Sun Slime");
            d1 = getResources().getDrawable(R.drawable.s2_1);
            d2 = getResources().getDrawable(R.drawable.s2_2);
            ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(100);
        }
        return;
    }
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();				 	//タッチしたＸ座標
        float y = event.getY(); 			 	//タッチしたＹ座標
        int actionID = event.getAction();		//タッチイベントのアクション種別
        if(tOver||((x-360)*(x-360)+(y-800)*(y-800)>=210*210))return super.onTouchEvent(event);
        switch (actionID) {
            case MotionEvent.ACTION_DOWN:
                num++;
                count();
                tCount.setText(num + "");
                slime.setImageDrawable(d2);
                break;
            case MotionEvent.ACTION_UP:
                slime.setImageDrawable(d1);
                break;
            default :
                break;
        }
        return super.onTouchEvent(event);
    }
    public class MainTimerTask extends TimerTask {
        @Override
        public void run() {
            //ここに定周期で実行したい処理を記述します
            mHandler.post( new Runnable() {
                public void run() {
                    //実行間隔分を加算処理
                    count -= 1;
                    if(count<=0){
                        tTime.setText("Time Up!");
                        tOver=true;
                        if(slime.getDrawable()==d2)slime.setImageDrawable(d1);
                        return;
                    }
                    //画面にカウントを表示
                    tTime.setText("Time:"+String.valueOf(count));
                }
            });
        }
    }
    @Override
    protected void onDestroy(){
        if(best<num)best=num;
        try{
            OutputStream out = openFileOutput("a.txt",MODE_PRIVATE);
            PrintWriter writer =
                    new PrintWriter(new OutputStreamWriter(out,"UTF-8"));
            writer.append(Integer.toString(best)+"\n");
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
