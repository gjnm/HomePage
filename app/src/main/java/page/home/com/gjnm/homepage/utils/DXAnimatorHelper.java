package page.home.com.gjnm.homepage.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import page.home.com.gjnm.homepage.R;
import page.home.com.gjnm.homepage.app.MyApplication;

/**
 * Created by gaojian12 on 17/11/4.
 */

public class DXAnimatorHelper {
    private static final int ARROW_SHOW_MAX_NUMBER = 2;
    private static final long TIPS_ARROW_ANIM_ONE_TIME = 1000;

    public static AnimatorSet startListViewAnim(final Context context, final View mSlideArrow) {
        int count;
        Interpolator interpolator;
        count = 5;
        interpolator = new LinearInterpolator();
        ValueAnimator translateY = ObjectAnimator.ofFloat(mSlideArrow, "translationY", 0,
                -UiUtils.dip2px(context, 7));
        translateY.setDuration(TIPS_ARROW_ANIM_ONE_TIME);
        translateY.setInterpolator(interpolator);
        translateY.setRepeatCount(count);
        translateY.setRepeatMode(ValueAnimator.REVERSE);
        translateY.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                Animation fadeInAnimation = AnimationUtils.loadAnimation(
                        MyApplication.getInstance(), R.anim.access_finish_slide_arrow_fade_out);
                mSlideArrow.startAnimation(fadeInAnimation);
                mSlideArrow.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mSlideArrow.setVisibility(View.VISIBLE);
                Animation fadeInAnimation = AnimationUtils.loadAnimation(
                        MyApplication.getInstance(), R.anim.access_finish_slide_arrow_fade_in);
                mSlideArrow.startAnimation(fadeInAnimation);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(translateY);
        animatorSet.setStartDelay(1000l);
        animatorSet.start();
        return animatorSet;
    }
}
