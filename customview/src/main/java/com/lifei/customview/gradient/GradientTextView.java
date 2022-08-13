package com.lifei.customview.gradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import org.jetbrains.annotations.NotNull;

public class GradientTextView extends AppCompatTextView {
    private Paint mPaint;
    public GradientTextView(@NonNull @NotNull Context context) {
        super(context);
        init();
    }

    public GradientTextView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradientTextView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        mPaint=new Paint();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        
    }
}
