package view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by wangping on 2018/3/2.
 * 可以拖动的VIEW
 */

public class DragView extends View {
  Scroller scroller;
    private int lastX;
    private int lastY;
    private int x;
    private int  y;

    public DragView(Context context) {
        super(context, null);
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        scroller=new Scroller(context);
    }

//    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }


//    @Override
//    protected void onDraw(Canvas canvas) {
//////        Paint paint = new Paint();
//////        paint.setColor(Color.RED);
////        canvas.drawCircle(currentX, currentY, 50, paint);
////        super.onDraw(canvas);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX=(int )event.getX();
                lastY=(int)event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                x=(int)event.getX()-lastX;
                y=(int)event.getY()-lastY;
//                scroller.startScroll();

//                ((View)getParent()).scrollBy(-x,-y);
                layout(getLeft()+x,getTop()+y,getRight()+x,getBottom()+y);
//                layout(x,y,getRight(),getBottom());
//                LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) getLayoutParams();
//                layoutParams.leftMargin=getLeft()+x;
//                layoutParams.topMargin=getTop()+y;
//                setLayoutParams(layoutParams);
                break;
                //手指离开屏幕触发
            case MotionEvent.ACTION_UP:
                Log.i("infor","up>>>");
                View viewGroup=((View)getParent());
                scroller.startScroll(viewGroup.getScrollX(),viewGroup.getScrollY(),
                        (int)(event.getX()), (int)(event.getY()),5000);
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断Scroller是否执行完毕
        if(scroller.computeScrollOffset()){
            ((View)getParent()).scrollTo(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }
    }
}





