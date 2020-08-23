package com.example.slime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
public class TitleActivity extends Activity {
    //Globals globals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        //globals = (Globals) this.getApplication();
    }
    public boolean onTouchEvent(MotionEvent event) {
        int actionID = event.getAction();
        switch (actionID) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                if(140<=x&&x<=580&&850<=y&&y<=925){
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                }
                if(115<=x&&x<=610&&950<=y&&y<=1030){
                    Intent intent = new Intent(this,Options.class);
                    startActivity(intent);
                }
                if(85<=x&&x<=640&&1050<=y&&y<=1135){
                    Intent intent = new Intent(this,Ranking.class);
                    startActivity(intent);
                }
                break;
            default :
                break;
        }
        return super.onTouchEvent(event);
    }
}
