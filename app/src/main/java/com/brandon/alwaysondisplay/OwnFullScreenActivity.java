package com.brandon.alwaysondisplay;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class OwnFullScreenActivity extends Activity {

    boolean iShow = false;
    View view;
    View view1;
    View view2;
    WindowInsetsControllerCompat controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_own_full_screen);
        view = findViewById(R.id.content_container);
        view1 = findViewById(R.id.top_holder);
        view2 = findViewById(R.id.bottom_choice_control);
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
}