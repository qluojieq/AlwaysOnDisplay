package com.brandon.alwaysondisplay;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class OwnFullScreenActivity extends Activity {

    boolean iShow = false;
    View view;
    TextView view1;
    TextView battery;
    View view2;
    BatteryView batteryView;
    WindowInsetsControllerCompat controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_own_full_screen);
        view = findViewById(R.id.content_container);
        view1 = findViewById(R.id.top_holder);
        battery =  findViewById(R.id.show_battery_count);
        setGradient(view1);
        view2 = findViewById(R.id.bottom_choice_control);
        batteryView = findViewById(R.id.battery_view);
        view2.setOnClickListener(v -> {});
        controller = ViewCompat.getWindowInsetsController(view);
        controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE);
        controller.setAppearanceLightNavigationBars(true);
        controller.setAppearanceLightStatusBars(false);
        view.setOnClickListener(v -> {
            if(iShow){
                hideStatusBar();
            }else {
                showStatusBar();
            }
        });
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        getWindow().setAttributes(lp);

        IntentFilter intentFilter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        //注册接收器以获取电量信息
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //获取当前电量，如未获取具体数值，则默认为0
                int batteryLevel=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
                //获取最大电量，如未获取到具体数值，则默认为100
                int batteryScale=intent.getIntExtra(BatteryManager.EXTRA_SCALE,100);
                //显示电量
                battery.setText("电量"+(batteryLevel*100/batteryScale)+"%");
                batteryView.setPower(batteryLevel);
            }
        }, intentFilter);

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        showStatusBar();
    }

    //全屏并且隐藏状态栏
    private void hideStatusBar() {
        iShow = false;
//        WindowManager.LayoutParams attrs = getWindow().getAttributes();
//        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
//        getWindow().setAttributes(attrs);
        controller.hide(WindowInsetsCompat.Type.statusBars());
        controller.hide(WindowInsetsCompat.Type.navigationBars());
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
    }
    private void showStatusBar() {
        iShow = true;
//        WindowManager.LayoutParams attrs = getWindow().getAttributes();
//        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
//        getWindow().setAttributes(attrs);
        controller.show(WindowInsetsCompat.Type.statusBars());
        controller.show(WindowInsetsCompat.Type.navigationBars());
        view1.setVisibility(View.VISIBLE);
        view2.setVisibility(View.VISIBLE);
    }

    private void setGradient(TextView textView) {
        float endX = textView.getPaint().getTextSize() * textView.getText().length();
        int[] colors = new int[]{ Color.RED,
                Color.GREEN,
                Color.BLUE};
        //颜色的数组
        float []position = new float[]{0f, 0.3f, 1.0f}; //颜色渐变位置的数组
        LinearGradient linearGradient =
                new LinearGradient(0f, 0f, endX, 0f, colors, position, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(linearGradient);
        textView.invalidate();
    }
}