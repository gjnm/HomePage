
package page.home.com.gjnm.homepage.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import page.home.com.gjnm.homepage.app.MyApplication;
import page.home.com.gjnm.homepage.recommend.CardListAdapter;
import page.home.com.gjnm.homepage.utils.DXAnimatorHelper;
import page.home.com.gjnm.homepage.utils.GlobalConfigMgr;
import page.home.com.gjnm.homepage.R;
import page.home.com.gjnm.homepage.utils.EntranceType;
import page.home.com.gjnm.homepage.utils.UiUtils;

public class UniteSlideLayout extends RelativeLayout {

    private static final String TAG = "ResultCardLayout";
    private static final int PROTECT_DISTANCE = 2;
    private static final long UP_ANIM_TIME = 150;
    private static final float SCALE_TO_SIZE = 1f;
    private static final long VIEW_MOVE_ANIM_TIME = 300;

    //use in dp
    private static final int HEADER_TITLE_TRANS_DISTANCE = 7;
    private Context mContext;

    private MyRecyclerview mCardListView;
    private View mListContent;
    private RelativeLayout.LayoutParams mListContentLayoutParams;

    private View mArrowTips;
    private View mBottomTips;
    private EntranceType mEntranceType;

    private int mViewBgTransDistance;
    private ImageView mHeaderIcon;
    private View mHeaderContent;
    private int mTitleTransDistance;

    // 保护距离，在偏移在该保护距离内算作点击
    private int mProtectDistance;

    private boolean mIsScrollToTop;
    private VelocityTracker mVelocityTracker;  // 滑动速度跟踪类VelocityTracker
    private int mInterceptLastMotionY;
    private boolean mIsShrink;
    private int mOnTouchLastMotionY;

    private int mTopDistance = 0;
    private int mBottomDistance;
    private float mScaleSize = SCALE_TO_SIZE;

    private float mIntervalDistance;
    private FrameLayout mTopContent;
    private boolean mIsFirstTouch = true;
    private boolean isCanSlide; // 页面是否可以滑动
    private int mListPaddingTop;
    private boolean isNeedReportScroll;
    private int mListViewPos;
    private int mRatePos = -1;//评分卡片的在集合中的位置
    private boolean mRateCardHadStartAnim = false;//rate卡片已经展示过动画了

    public UniteSlideLayout(Context context) {
        super(context);
        init(context);
    }

    public UniteSlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UniteSlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UniteSlideLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        inflate(context, R.layout.result_card_view, this);
        mTitleTransDistance = UiUtils.dipToPx(context, HEADER_TITLE_TRANS_DISTANCE);
        mProtectDistance = UiUtils.dipToPx(context, PROTECT_DISTANCE);
        mBottomDistance = GlobalConfigMgr.getInstance(MyApplication.getInstance()).getTopHeight();
        mIntervalDistance = mBottomDistance - mTopDistance;
        mViewBgTransDistance = UiUtils.getScreenHeightPixels(context);
        isCanSlide = true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
    }

    private void initViews() {

        mTopContent = (FrameLayout) findViewById(R.id.title_content_container);

        mCardListView = (MyRecyclerview) findViewById(R.id.card_list_view);
        mCardListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCardListView.setParentViewGroup(this);

        mListContent = findViewById(R.id.list_content);
        mListContentLayoutParams = (RelativeLayout.LayoutParams) mListContent.getLayoutParams();

        if (mBottomDistance != 0) {
            mListContentLayoutParams.topMargin = mBottomDistance;
            mListContent.setLayoutParams(mListContentLayoutParams);
        }

        mArrowTips = findViewById(R.id.slide_arrow);
        mBottomTips = findViewById(R.id.slide_arrow_tv);
        mBottomTips.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        startTipsSlideAnim();
    }

    public void addTitleView(View view) {
        mTopContent.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ViewTreeObserver vto = mTopContent.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            public void onGlobalLayout() {
                if (mTopContent == null) {
                    return;
                }
                int height = mTopContent.getMeasuredHeight();
                if (height > 0) {
                    mBottomDistance = height;
                    mIntervalDistance = mBottomDistance - mTopDistance;
                    GlobalConfigMgr.getInstance(MyApplication.getInstance()).setTopHeight(height);
                    mListContentLayoutParams.topMargin = height;
                    mListContent.setLayoutParams(mListContentLayoutParams);
                    mTopContent.getViewTreeObserver()
                            .removeGlobalOnLayoutListener(this);
                }
            }
        });

    }

    public void setCardAdapterWithAnim(CardListAdapter adapter) {
        mCardListView.setAdapter(adapter);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInterceptLastMotionY = y;
                if (!isCanSlide) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = y - mInterceptLastMotionY;
                if ((Math.abs(dy) >= mProtectDistance)
                        && ((mIsShrink && mCardListView.getScrollToTop() && dy > 0) || (!mIsShrink))) {
                    mOnTouchLastMotionY = y;
                    mCardListView.setOpListActionUp(false);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!mCardListView.getOpListActionUp()) {
                    mCardListView.setOpListActionUp(true);
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int y = (int) event.getY();
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        // 把触摸事件 MotionEvent 对象传递给VelocityTracker,然后分析MotionEvent 对象在单位时间类发生的位移来计算速度
        // 你可以使用getXVelocity() 或getXVelocity()获得横向和竖向的速率到速率时,但是使用它们之前请先调用computeCurrentVelocity(int)来初始化速率的单位
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mOnTouchLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsFirstTouch) {
                    mOnTouchLastMotionY = y;
                    mIsFirstTouch = false;
                }

                int dy = y - mOnTouchLastMotionY;
                if (mCardListView.getVisibility() == View.VISIBLE) {
                    doListAnimByMove(mListContent, mListContentLayoutParams, dy);
                    if (mScaleSize == 0) {
                        event.setAction(MotionEvent.ACTION_DOWN);
                        dispatchTouchEvent(event);
                    }
                }
                mOnTouchLastMotionY = y;
                break;
            case MotionEvent.ACTION_UP:
                if (!mCardListView.getOpListActionUp()) {
                    mCardListView.setOpListActionUp(true);
                }
                mIsFirstTouch = true;
                handleUpAnim();
                releaseVelocityTracker();
                break;

            case MotionEvent.ACTION_CANCEL:
                releaseVelocityTracker();
                break;

        }

        return super.onTouchEvent(event);
    }

    private void releaseVelocityTracker() {
        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }

    }

    /**
     * 根据当时手指移动的距离对title做动画，将view往上移动
     *
     * @param view 对应的向上移动view
     * @param lp   LayoutParams
     * @param dy   滑动的距离
     */
    private void doListAnimByMove(View view, RelativeLayout.LayoutParams lp, int dy) {
        if (dy > 0 && mListContentLayoutParams.topMargin == mBottomDistance) {
            return;
        }

        if (dy != 0) {

            int topMargin = mListContentLayoutParams.topMargin;
            topMargin += dy;
            if (topMargin <= mTopDistance) {
                topMargin = mTopDistance;
            }
            if (topMargin >= mBottomDistance) {
                topMargin = mBottomDistance;
            }
            lp.topMargin = topMargin;
            doScaleByTopMargin(topMargin);
            view.setLayoutParams(lp);
        }
    }

    /**
     * 根据listview 距离顶部的距离计算动画比例
     */
    private void doScaleByTopMargin(int topMargin) {
        resetScaleSize(topMargin);
        resetIsShrink();
        if (0 <= mScaleSize && mScaleSize <= SCALE_TO_SIZE) {
            ViewHelper.setPivotX(mTopContent, mTopContent.getWidth() / 2);
            ViewHelper.setPivotY(mTopContent, 0);
            ViewHelper.setScaleX(mTopContent, mScaleSize);
            ViewHelper.setScaleY(mTopContent, mScaleSize);
            ViewHelper.setAlpha(mTopContent, mScaleSize);
        }
    }

    private void resetIsShrink() {
        if (mScaleSize == 0) {
            mIsShrink = true;
        } else if (mScaleSize <= SCALE_TO_SIZE) {
            mIsShrink = false;
        }
    }

    private float resetScaleSize(int topMargin) {
        mScaleSize = (topMargin - mTopDistance) / (mIntervalDistance / SCALE_TO_SIZE);
        if (mScaleSize <= 0) {
            mScaleSize = 0;
        } else if (mScaleSize >= SCALE_TO_SIZE) {
            mScaleSize = SCALE_TO_SIZE;
        }
        return mScaleSize;
    }

    public void handleUpAnim() {
        int topMargin = mListContentLayoutParams.topMargin;
        float scaleSize = resetScaleSize(topMargin);
        resetIsShrink();
        if (scaleSize == 0 || scaleSize == SCALE_TO_SIZE) {
            return;
        }
        if (scaleSize < SCALE_TO_SIZE / 2) {
            scaleAnimator(scaleSize, 0, 0, UP_ANIM_TIME, 0);
        } else {
            scaleAnimator(scaleSize, SCALE_TO_SIZE, SCALE_TO_SIZE, UP_ANIM_TIME, 0);
        }
    }

    /**
     * 首页滑动效果
     *
     * @param startSize   :开始大圆压缩比例
     * @param endSize     :最终压缩比例
     * @param scaleSize   :是否压缩到终点
     * @param aninTime    :动画时长
     * @param delayTime   :延迟动画时间
     */
    public void scaleAnimator(float startSize, float endSize, float scaleSize, long aninTime, long delayTime) {
        ValueAnimator listAnimator = null;
        ObjectAnimator trashXAnimator;
        ObjectAnimator trashYAnimator;
        ObjectAnimator alphaAnimator;

        isCanSlide = false;

        AnimatorSet animatorSet;
        if (mTopContent == null) {
            return;
        }
        ViewHelper.setPivotX(mTopContent, mTopContent.getWidth() / 2);
        ViewHelper.setPivotY(mTopContent, 0);
        mScaleSize = scaleSize;
        resetIsShrink();
        if (mIsShrink) {
            listAnimator = ValueAnimator.ofFloat(mListContentLayoutParams.topMargin, mTopDistance);

        } else {
            listAnimator = ValueAnimator.ofFloat(mListContentLayoutParams.topMargin, mBottomDistance);
        }

        listAnimator.setDuration(aninTime);

        trashXAnimator = ObjectAnimator.ofFloat(mTopContent, "scaleX", startSize, endSize);
        trashXAnimator.setDuration(aninTime);
        trashYAnimator = ObjectAnimator.ofFloat(mTopContent, "scaleY", startSize, endSize);
        trashYAnimator.setDuration(aninTime);
        alphaAnimator = ObjectAnimator.ofFloat(mTopContent, "alpha", startSize, endSize);
        alphaAnimator.setDuration(aninTime);

        listAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                float animatedValue = (Float) animator.getAnimatedValue();
                mListContentLayoutParams.topMargin = (int) animatedValue;
                mListContent.setLayoutParams(mListContentLayoutParams);
            }
        });
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(listAnimator, trashXAnimator, trashYAnimator, alphaAnimator);
        animatorSet.setStartDelay(delayTime);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                isCanSlide = false;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isCanSlide = true;
            }
        });
        animatorSet.start();
    }

    private AnimatorSet mListAnimSet;

    private void startTipsSlideAnim() {
        mListAnimSet = DXAnimatorHelper.startListViewAnim(mContext, mBottomTips);
    }

    private void stopTipsSlideAnim() {
        if (mListAnimSet != null) {
            mListAnimSet.cancel();
            mListAnimSet = null;
        }
    }

}
