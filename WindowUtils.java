package com.example.mywindowstudy;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WindowUtils {
    private static WindowManager windowManager;
    private static WindowManager.LayoutParams layoutParams;
    private static LinearLayout mFloatLayout;
    private static TextView mFloatView;

    public static void addWindow(final Context context) {
        windowManager = (WindowManager) context.getApplicationContext().getSystemService(context.WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                0, 0,
                PixelFormat.TRANSPARENT
        );
        //flag设置window属性
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//      layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //type设置window层级
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        layoutParams.x = 0;
        layoutParams.y = 300;

        //获取浮动窗口所在布局
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
        mFloatView = mFloatLayout.findViewById(R.id.floatView_id);

        windowManager.addView(mFloatLayout, layoutParams);

        //绑定触摸移动监听
        mFloatLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int rawX = (int) event.getRawX();
                int rawY = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE: {
                        layoutParams.x = rawX - mFloatLayout.getWidth() / 2;
                        layoutParams.y = rawY - mFloatLayout.getHeight() / 2 - 60;
                        windowManager.updateViewLayout(mFloatLayout, layoutParams);
                        break;
                    }
                    default:
                        break;
                }
                return false;
            }
        });
    }

    public static void deleteWindow() {
        if (windowManager != null && mFloatLayout != null) {
            windowManager.removeView(mFloatLayout);
        }
    }

    public static void updateWindow() {
        if (windowManager != null && mFloatLayout != null) {
            mFloatView.setText("updateWindow!");
            windowManager.updateViewLayout(mFloatLayout, layoutParams);
        }
    }
}
