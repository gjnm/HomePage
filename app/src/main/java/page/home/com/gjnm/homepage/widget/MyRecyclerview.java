package page.home.com.gjnm.homepage.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import page.home.com.gjnm.homepage.utils.UiUtils;


/**
 * Created by gaojian12 on 17/10/18.
 */

public class MyRecyclerview extends RecyclerView {
    private static final int PROTECT_DISTANCE = 2;

    private boolean mIsScrollToTop = false;
    private int mInterceptLastMotionY;
    private int mListPaddingTop;
    private int mProtectDistance;
    private ViewGroup parent;
    private boolean opListActionUp; // 是否接受up事件

    public MyRecyclerview(Context context) {
        super(context);
        init();
    }

    public MyRecyclerview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRecyclerview(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mListPaddingTop = getPaddingTop();
        mProtectDistance = UiUtils.dipToPx(PROTECT_DISTANCE);
        opListActionUp = true;
        this.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int firstVisibleItem, int visibleItemCount) {
                if (firstVisibleItem == 0) {
                    View firstView = getChildAt(0);
                    if (firstView == null) {
                        mIsScrollToTop = false;
                        return;
                    }
                    int top = firstView.getTop();
                    mIsScrollToTop = top > mListPaddingTop - mProtectDistance;
                } else {
                    mIsScrollToTop = false;
                }
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInterceptLastMotionY = y;
                if (!opListActionUp) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = y - mInterceptLastMotionY;
                if (dy > mProtectDistance) {
                    if (mIsScrollToTop) {
                        float offsetX = 0;
                        float offsetY = 0;
                        for (View v = this; v != null && v != parent; v = (View) v.getParent()) {
                            offsetX += v.getLeft() - v.getScrollX();
                            offsetY += v.getTop() - v.getScrollY();
                        }
                        final MotionEvent eventParent = MotionEvent.obtainNoHistory(event);
                        eventParent.offsetLocation(offsetX, offsetY);


                        eventParent.setAction(MotionEvent.ACTION_DOWN);
                        if (parent != null) {
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    parent.dispatchTouchEvent(eventParent);
                                }
                            });
                            return false;
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if (!opListActionUp) {
                    opListActionUp = true;
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    public boolean getScrollToTop() {
        return mIsScrollToTop;
    }

    public void setParentViewGroup(ViewGroup parent) {
        this.parent = parent;
    }

    public void setOpListActionUp(boolean state) {
        opListActionUp = state;
    }

    public boolean getOpListActionUp() {
        return opListActionUp;
    }
}
