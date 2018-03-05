package view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.wangping.audioviewdemo.R;

/**
 * Created by wangping on 2018/3/1.
 */

/**
 * 音频播放图
 */

public class MyAudioView extends View {

    private int mRectCount;
    //每个小矩形的宽度
    private int mRectWidth;
    private float percent;
    //每个小矩形的高度
    private int mRectHeigt;
    //View的宽度
    private int mViewWidth;
    private int mDelayTine;
    private int mTopColor,mBottomColor;
    private int mOffset;

    //每个小矩形当前的高度
    private float []mCurentHeight;

    private Paint mPaint;
    private LinearGradient linearGradient;



    /**
     * 代码中直接new调用
     * @param context
     */
    public MyAudioView(Context context) {
        this(context,null);
        Log.i("infor","test>>"+"MyAudioView1");
    }

    /**
     * 在xml使用自定义view并且没有自定义属性时使用
     * @param context
     * @param attrs
     */

    public MyAudioView(Context context, @Nullable AttributeSet attrs) {

        this(context, attrs,0);
        Log.i("infor","test>>"+"MyAudioView2");
    }

    /**
     * 在xml中使用自定义属性时调用
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MyAudioView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        Log.i("infor","test>>"+"MyAudioView3");
        mPaint=new Paint();
        //初始化画笔设置抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.MyAudioView);
         mRectCount=ta.getInt(R.styleable.MyAudioView_rectCount,10);
         mOffset=(int)(ta.getFloat(R.styleable.MyAudioView_rectOffset,3));
         mDelayTine=ta.getInt(R.styleable.MyAudioView_delayTime,300);
         mTopColor=ta.getColor(R.styleable.MyAudioView_topColor, Color.YELLOW);
         mBottomColor=ta.getColor(R.styleable.MyAudioView_bottomColor,Color.BLUE);
         //释放资源
         ta.recycle();

    }

  /*
  重写onMeasure方法是为了让控件在wrap_content时候有一个和合适的宽度
   */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("infor","test>>"+"onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        if(widthMode==MeasureSpec.AT_MOST){
            widthSize=200;
        }if(heightMode==MeasureSpec.AT_MOST){
            heightSize=200;
        }
        setMeasuredDimension(widthSize,heightSize);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("infor","test>>"+"onDraw");
        super.onDraw(canvas);
        if(mCurentHeight!=null){
            //使用指定了的每个矩形的高度进行绘制
            for(int i=0;i<mRectCount;i++){
                int random=(int)(Math.random()*50);
//                int random=0;
//                canvas.drawRect((float)(mViewWidth*0.4/2+mRectWidth*i+mOffset),mCurentHeight[i]+random,(float)(mViewWidth*0.4/2+mRectWidth*(i+1)),mRectHeigt,mPaint);
                canvas.drawRect((float)(mViewWidth*0.4/2+mRectWidth*i+mOffset),mCurentHeight[i]+random,(float)(mViewWidth*0.4/2+mRectWidth*(i+1)),mRectHeigt,mPaint);
            }
        }else{
            //没有指定则使用随机数的高度
            for(int i=0;i<mRectCount;i++){
                int currentHeight=0;
                canvas.drawRect((float) (mViewWidth * 0.4 / 2 + mRectWidth * i + mOffset), currentHeight,
                        (float) (mViewWidth * 0.4 / 2 + mRectWidth * (i + 1)), mRectHeigt, mPaint);

            }
        }
//        //让view延迟mDelayTime毫秒再重新绘制
        postInvalidateDelayed(mDelayTine);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("infor","test>>"+"onSizeChanged");
        mViewWidth=getMeasuredWidth();
        mRectHeigt=getMeasuredHeight();
        Log.i("infor","mRectHeigt:"+mRectHeigt);
        mRectWidth=(int)(mViewWidth*percent/mRectCount);
        linearGradient=new LinearGradient(0,0,mRectWidth,mRectHeigt,mTopColor,mBottomColor, Shader.TileMode.CLAMP);
       mPaint.setShader(linearGradient);
    }

    public  void setCurrentHeight(float []mCurentHeight){
        this.mCurentHeight=mCurentHeight;
    }

    public void setRectWidthPrecent(float percent){
        if(percent==0||percent>=1)
            this.percent=0.6f;
        else{
            this.percent=percent;
        }



    }
}
