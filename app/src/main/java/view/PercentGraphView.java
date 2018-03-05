package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.example.wangping.audioviewdemo.R;

/**
 * Created by wangping on 2018/3/2.
 */

/**
 * 百分比图（比例图）
 */

public class PercentGraphView extends View {
    //实心圆的画笔
    private Paint mCirlcePaint;
    //外圆弧的画笔
    private Paint mArcPaint;
    //文字的画笔
    private Paint mTextPaint;
    private int mCircleColor;
    private int mArcColor;
    private int mTextColor;
    private float mTextSize;

    private String mTextContent="HelloWorld";


    private int mCircleX;
    private int mCircleY;
    private float mRadius;


    private int mViewWidth;
    private int mViewHeight;

    private RectF mArcRectF;
    //开始的角度
    private float mstartAngle;
    //扫过的角度
    private float mSweepAngle;
    private float mArcPaintStrokeWidth;

    public PercentGraphView(Context context) {
        this(context,null);
    }

    public PercentGraphView(Context context, @Nullable AttributeSet attrs) {
        this(context, null,0);
    }

    public PercentGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        TypedArray  ta = context.obtainStyledAttributes(attrs, R.styleable.PercentGraphView);
        mCircleColor = ta.getColor(R.styleable.PercentGraphView_innerCircleColor, Color.parseColor
                ("#abcdef"));
        mArcColor=ta.getColor(R.styleable.PercentGraphView_outCircleStrokeWidth,Color.parseColor("#fedcba"));
        mArcPaintStrokeWidth=ta.getDimension(R.styleable.PercentGraphView_outCircleStrokeWidth,50);
        mstartAngle=ta.getFloat(R.styleable.PercentGraphView_startAngle,0);
        mSweepAngle=ta.getFloat(R.styleable.PercentGraphView_swapAngle,270);
        mTextColor=ta.getColor(R.styleable.PercentGraphView_textColor,Color.parseColor("#1afa29"));
        mTextSize=ta.getDimension(R.styleable.PercentGraphView_textSize,15);
       mTextContent=ta.getString(R.styleable.PercentGraphView_text);
//         mTextContent=(String) (ta.getText(R.styleable.PercentGraphView_textContentTest));
        ta.recycle();
        mCirlcePaint=new Paint();
        mCirlcePaint.setColor(mCircleColor);
        mCirlcePaint.setStyle(Paint.Style.FILL);
        mCirlcePaint.setAntiAlias(true);


        mArcPaint = new Paint();
        mArcPaint.setColor(mArcColor);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        if(widthMode==MeasureSpec.AT_MOST){
            widthSize=300;

        }
        if(heightMode==MeasureSpec.AT_MOST){
            heightSize=300;

        }
        setMeasuredDimension(widthSize, heightSize);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画出外部的圆
        canvas.drawCircle(mCircleX,mCircleY,mRadius,mCirlcePaint);
        //绘制外层环
        canvas.drawArc(mArcRectF,mstartAngle,mSweepAngle,false,mArcPaint);
        if(!TextUtils.isEmpty(mTextContent)){
            //绘制最中间的文字
            canvas.drawText(mTextContent,0,mTextContent.length(),mCircleX,mCircleY,mTextPaint);

        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mViewWidth==0){
            mViewWidth=getMeasuredWidth();
        }
        if(mViewHeight==0){
            mViewHeight=getMeasuredHeight();
        }

        if(mViewWidth>0&&mViewHeight>0){
            //获得圆心坐标
            mCircleX=getPaddingLeft()+mViewWidth/2;
            mCircleY=getPaddingTop()+mViewHeight/2;
            //半径为宽高的较小值除去内边距的0.4
            if(mViewHeight>=mViewWidth){
                mRadius=(float)((mViewWidth/2-getPaddingLeft())*0.4);
            }else{
                mRadius=(float)((mViewHeight/2-getPaddingTop())*0.4);
            }

        }
        mArcRectF=new RectF((float)(mViewWidth*0.1),(float) (mViewHeight * 0.1),(float) (mViewWidth * 0.9), (float) (mViewHeight * 0.9));
        mArcPaint.setStrokeWidth(mArcPaintStrokeWidth);
    }
}
