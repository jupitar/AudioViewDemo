package view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by wangping on 2018/3/5.
 */

public class DragViewGroup extends FrameLayout {
    private ViewDragHelper  viewDragHelper;

    private View mMenuView;
    private View mMainView;
    private int mWidth;


    public DragViewGroup(Context context){

        super(context);
        Log.i("infor","test>>DragViewGroup1");
        initViews();
    }

    public DragViewGroup(Context context, AttributeSet attributes){

        super(context,attributes);
        Log.i("infor","test>>DragViewGroup2");
        initViews();
    }


    public DragViewGroup(Context context,AttributeSet attributeSet,int defaultStyle){
        super(context,attributeSet,defaultStyle);
        Log.i("infor","test>>DragViewGroup3");
        initViews();

    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
        Log.i("infor","test>>onFinishInflate");
        mMainView=getChildAt(1);
        mMenuView=getChildAt(0);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("infor","test>>onSizeChanged");
        mWidth=mMenuView.getMeasuredWidth();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
       boolean flag=viewDragHelper.shouldInterceptTouchEvent(ev);
       Log.i("infor","test>>onInterceptTouchEvent:"+flag);
        return flag;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("infor","test>>onTouchEvent:");
        //将触摸事件交给viewDragHelper处理，必不可少
         viewDragHelper.processTouchEvent(event);
         return true;
    }

    private void initViews() {
        viewDragHelper=ViewDragHelper.create(this,callback);
    }

    private ViewDragHelper.Callback callback=new ViewDragHelper.Callback() {

        //何时开始触摸事件
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //如果当前触摸的view是mMainView时开始检测
            return mMainView==child;
        }

        //处理水平滑动

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return  left;
        }

        //处理垂直滑动
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        //拖动结束后开始调用

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            if(mMainView.getLeft()<200){
                //关闭菜单，相当于Scroller的startScroller方法
                viewDragHelper.smoothSlideViewTo(mMainView,0,0);
                ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);

            }else{
                //打开菜单
                viewDragHelper.smoothSlideViewTo(mMainView,600,0);
                 ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
            }
        }

    };


    @Override
    public void computeScroll() {
        if(viewDragHelper.continueSettling(true)){
              ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
