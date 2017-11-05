package page.home.com.gjnm.homepage.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.WindowManager;

import page.home.com.gjnm.homepage.app.MyApplication;
import page.home.com.gjnm.homepage.R;


public class UiUtils {

    private static int mStartAlpha, mStartRed, mStartGreen, mStartBlue;
    private static int mIntervalAlpha, mIntervalRed, mIntervalGreen, mIntervalBlue;
    private static int mStartColor, mToColor;
    private static float sDensity = 0.0F;
    private static int sDensityDpi = 0;
    private static int sScreenWidth = 0;
    private static int sScreenHeight = 0;

    static public int getScreenWidthPixels(Context context) {
        if (sScreenWidth == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            sScreenWidth = dm.widthPixels;
        }
        return sScreenWidth;
    }

    public static int getScreenHeightPixels(Context context) {
        if (sScreenHeight == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            sScreenHeight = dm.heightPixels;
        }
        return sScreenHeight;
    }

    public static Point getScreenSizePixels(Context context) {
        if (sScreenWidth == 0 || sScreenHeight == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            sScreenWidth = dm.widthPixels;
            sScreenHeight = dm.heightPixels;
        }
        return new Point(sScreenWidth, sScreenHeight);
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        } else {
            return getResizedBitmap(drawable, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Convert drawable to bitmap
     * 
     * @return null paramDrawable can not get width or height.
     */
    public static Bitmap getResizedBitmap(Drawable paramDrawable, int width, int height) {
        if (paramDrawable == null) {
            return null;
        }

        if (width <= 0) {
            width = paramDrawable.getIntrinsicWidth();
        }

        if (height <= 0) {
            height = paramDrawable.getIntrinsicHeight();
        }
        if (width <= 0 || height <= 0) {
            return null;
        }
        paramDrawable.setBounds(0, 0, width, height);
        Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
        Bitmap localBitmap = Bitmap.createBitmap(width, height, localConfig);
        Canvas localCanvas = new Canvas(localBitmap);
        paramDrawable.draw(localCanvas);
        return localBitmap;
    }

    public static int dipToPx(int dip) {
        return (int) (dip * getScreenDensity() + 0.5f);
    }

    static public int dipToPx(Context context, int dip) {
        return (int) (dip * getScreenDensity(context) + 0.5f);
    }

    public static float getScreenDensity() {
        if (sDensity != 0F) {
            return sDensity;
        } else {
            return getScreenDensity(MyApplication.getInstance());
        }
    }

    static public float getScreenDensity(Context context) {
        if (sDensity == 0F) {
            try {
                DisplayMetrics dm = new DisplayMetrics();
                ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                        .getMetrics(dm);
                sDensity = dm.density;
            } catch (Exception e) {
                sDensity = 1.0F;
            }
        }
        return sDensity;
    }

    public static int getScreenDensityDpi() {
        if (sDensityDpi == 0) {
            try {
                DisplayMetrics dm = new DisplayMetrics();
                ((WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE))
                        .getDefaultDisplay().getMetrics(dm);
                sDensityDpi = dm.densityDpi;
            } catch (Exception e) {
                sDensityDpi = DisplayMetrics.DENSITY_DEFAULT;
            }
        }
        return sDensityDpi;
    }

    public static Bitmap scaleBitmapByWidth(Resources res, int newWidth, boolean isWarning) {
        if (newWidth <= 0)
            return null;
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.widget_battery_high_100,
                null);
        if (bitmap == null) {
            return null;
        }
        int height = bitmap.getHeight();
        return getBatteryBitmap(res, newWidth, height, isWarning);
    }

    public static Bitmap getBatteryBitmap(Resources res, int width, int height, boolean isWarning) {
        int color;
        if (isWarning) {
            color = res.getColor(R.color.dialog_widget_warning_color);
        } else {
            color = res.getColor(R.color.one_key_switch_on);
        }
        if (width <= 0 || height <= 0) {
            return null;
        }
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRect(rectF, paint);
        canvas.drawBitmap(b, rect, rect, paint);
        return b;

    }

    public static Spanned getBlueColoredString(Context ctx, int textResId) {
        String ret = "";
        if (textResId != 0) {
            ret = ctx.getString(R.string.color_format_string,
                    ctx.getString(textResId));
        }
        return Html.fromHtml(ret);
    }

    public static void setDialogMaxHeight(final View targetView,
            final Context mct) {
        ViewTreeObserver observer = targetView.getViewTreeObserver();
        observer.addOnPreDrawListener(new OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int contentHeight;
                contentHeight = targetView.getMeasuredHeight();
                if (contentHeight > dipToPx(mct,
                        Constant.DIALOG_MAX_HEIGHT_DIP)) {
                    ViewGroup.LayoutParams params = targetView.getLayoutParams();
                    params.height = dipToPx(mct,
                            Constant.DIALOG_MAX_HEIGHT_DIP);
                    targetView.setLayoutParams(params);
                }
                return true;
            }
        });
    }

    public static int transformColor(int startColor, int toColor, float fraction) {
        if (startColor != mStartColor || toColor != mToColor) {
            mStartColor = startColor;
            mToColor = toColor;

            mStartAlpha = Color.alpha(startColor);
            mStartRed = Color.red(startColor);
            mStartGreen = Color.green(startColor);
            mStartBlue = Color.blue(startColor);

            mIntervalAlpha = Color.alpha(toColor) - mStartAlpha;
            mIntervalRed = Color.red(toColor) - mStartRed;
            mIntervalGreen = Color.green(toColor) - mStartGreen;
            mIntervalBlue = Color.blue(toColor) - mStartBlue;
        }

        int currentAlpha = (int) (mIntervalAlpha * fraction + mStartAlpha);
        int currentRed = (int) (mIntervalRed * fraction + mStartRed);
        int currentGreen = (int) (mIntervalGreen * fraction + mStartGreen);
        int currentBlue = (int) (mIntervalBlue * fraction + mStartBlue);
        return Color.argb(currentAlpha, currentRed, currentGreen, currentBlue);
    }

    public static float dipToPxFloat(int dip) {
        return (float)dip * getScreenDensity() + 0.5F;
    }

    public static int spToPx(float sp) {
        return (int)(sp * getScreenDensity() + 0.5F);
    }

    /**
     * @param bitmap
     * @param anglePixels
     * @return 获得左上和右上圆角的图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int anglePixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);
        final int color = Color.WHITE;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight() * 2);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, anglePixels, anglePixels, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rectF, paint);
        return output;
    }
}
