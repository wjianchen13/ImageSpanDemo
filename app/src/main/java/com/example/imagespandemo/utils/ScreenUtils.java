package com.example.imagespandemo.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.DialogFragment;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class ScreenUtils {

    /**
     * 居中对齐
     */
    public static final int KIWII_ALIGN_CENTER = 1;

    public static final int INTER_IMG_CHAT_NORMAL_HEIGHT = 17; //图标高度
    
    /**
     * 返回屏幕密度
     */
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 返回屏幕密度
     */
    public static int getDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 返回屏幕高(px)
     */
    public static int getScreenHeight(Context context) {
        if (context == null)
            return 0;

        Resources resources = context.getResources();
        if (resources == null)
            return 0;

        DisplayMetrics metrics = resources.getDisplayMetrics();
        if (metrics == null)
            return 0;

        return metrics.heightPixels;
    }

    /**
     * 返回屏幕宽(px)
     */
    public static int getScreenWidth(Context context) {
        if (context == null)
            return 0;

        Resources resources = context.getResources();
        if (resources == null)
            return 0;

        DisplayMetrics metrics = resources.getDisplayMetrics();
        if (metrics == null)
            return 0;

        return metrics.widthPixels;
    }

    /**
     * 返回屏幕的全部高度
     *
     * @param
     * @return
     */
    public static int getAllScreenHeight(Activity activity) {
        int height = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
            height = dm.heightPixels;  // 屏幕高
        } else {
            Display display = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics dm = new DisplayMetrics();
            @SuppressWarnings("rawtypes")
            Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked")
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, dm);
            } catch (Exception e) {
                e.printStackTrace();
            }
            height = dm.heightPixels;
        }
        return height;
    }

    /**
     * 返回屏幕的全部高度
     *
     * @param
     * @return
     */
    public static int getAllScreenHeight(Context context) {
        int height = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getRealMetrics(dm);
            height = dm.heightPixels;  // 屏幕高
        } else {
            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            DisplayMetrics dm = new DisplayMetrics();
            @SuppressWarnings("rawtypes")
            Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked")
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, dm);
            } catch (Exception e) {
                e.printStackTrace();
            }
            height = dm.heightPixels;
        }
        return height;
    }

    private static DisplayMetrics displayMetrics = new DisplayMetrics();

    /**
     * 返回屏幕宽(px)
     */
    public static int getScreenWidth(Activity activity) {
        if (activity != null) {
            WindowManager windowManager = activity.getWindowManager();
            if (windowManager != null) {
                Display display = windowManager.getDefaultDisplay();
                if (display != null) {
                    display.getMetrics(displayMetrics);
                }
            }
        }
        if (displayMetrics != null)
            return displayMetrics.widthPixels;
        return 0;
    }

    /**
     * 返回屏幕高(px)
     */
    public static int getScreenHeight(Activity activity) {
        try {
            if (activity != null) {
                WindowManager windowManager = activity.getWindowManager();
                if (windowManager != null) {
                    Display display = windowManager.getDefaultDisplay();
                    if (display != null) {
                        display.getMetrics(displayMetrics);
                    }
                }
            }
            if (displayMetrics != null)
                return displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 通过反射，获取包含虚拟键的整体屏幕高度
     *
     * @return
     */
    public static int getHasVirtualKey(Activity activity) {
        int dpi = 0;
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        return (int) (dpValue * getDensity(context) + 0.5f);
    }

    /**
     * @param dpValue
     * @param density
     * @return
     */
    public static int dip2px(float dpValue, float density) {
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        return (int) (pxValue / getDensity(context) + 0.5f);
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        if(context instanceof Activity)
            return getStatusBarHeight((Activity) context);
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    public static int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        return resources.getDimensionPixelSize(resourceId);
    }

    // 隐藏底部悬浮导航栏
    public static void hideNavgationBar(final Activity activity) {
        try {
            hideNavgationBar(activity.getWindow().getDecorView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏底部悬浮导航栏
     * @param isImmersion 是否支持沉浸式
     * @return
     */
    public static void hideNavgationBar(final Activity activity, boolean isImmersion) {
        try {
            hideNavgationBar(activity.getWindow().getDecorView(), isImmersion);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void hideNavgationBar(Window window){
        if(window != null){
            WindowManager.LayoutParams params = window.getAttributes();
            if(params != null && params.systemUiVisibility != getHideNavgationUiFlag()) {
                params.systemUiVisibility = getHideNavgationUiFlag();
                window.setAttributes(params);
            }
        }
    }

    // 隐藏底部悬浮导航栏
    public static void hideNavgationBar(DialogFragment dialogFragment) {
        if(dialogFragment == null
                || dialogFragment.getDialog() == null
                || dialogFragment.getDialog().getWindow() == null)
            return;
        hideNavgationBar(dialogFragment.getDialog().getWindow().getDecorView());
    }

    // 隐藏底部悬浮导航栏
    public static void hideNavgationBar(Dialog dialog) {
        if(dialog == null
                || dialog.getWindow() == null)
            return;
        hideNavgationBar(dialog.getWindow());
    }

    // 隐藏底部悬浮导航栏
    public static void hideNavgationBar(final View decorView) {
        if (decorView != null
                && decorView.getSystemUiVisibility() != getHideNavgationUiFlag()) {

//            final WeakReference<View> wrView = new WeakReference<>(decorView);
//            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
//                @Override
//                public void onSystemUiVisibilityChange(int visibility) {
//                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
//
//                        if(listener != null)
//                            listener.onSystemUiVisibilityChange(visibility);
//
//                        View dv = wrView.get();
//                        if(dv != null) {
//                            dv.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    View d = wrView.get();
//                                    if(d != null) {
//                                        d.setOnSystemUiVisibilityChangeListener(null);
//                                        hideNavgationBar(d);
//                                    }
//                                }
//                            }, 50);
//                        }
//                    }
//                }
//            });

            decorView.setSystemUiVisibility(getHideNavgationUiFlag());
        }
    }

    /**
     * 隐藏底部悬浮导航栏
     * @param isImmersion 是否支持沉浸式
     * @return
     */
    public static void hideNavgationBar(final View decorView, boolean isImmersion) {
        if (decorView != null
                && decorView.getSystemUiVisibility() != getHideNavgationUiFlag(isImmersion)) {
            decorView.setSystemUiVisibility(getHideNavgationUiFlag(isImmersion));
        }
    }

    public static void hideNavgationBar(WindowManager.LayoutParams params) {
        if(params != null){
            params.systemUiVisibility = getHideNavgationUiFlag();
        }
    }

    public static int getHideNavgationUiFlag(){

        return View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                // Activity全屏显示，但是状态栏不会被覆盖掉，而是正常显示，只是Activity顶端布   局会被覆盖住
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

//        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

//        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                | View.SYSTEM_UI_FLAG_IMMERSIVE
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    }

    /**
     * 获取状态栏导航栏标志
     * @param isImmersion 是否支持沉浸式
     * @return
     */
    public static int getHideNavgationUiFlag(boolean isImmersion){
        int flag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        return isImmersion ? flag | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN : flag;
    }

    public static boolean isHideNavgationUIFlag(Activity activity){
        if (activity!=null){
            return activity.getWindow().getDecorView().getSystemUiVisibility() == getHideNavgationUiFlag();
        }
        return false;
    }

    /**
     * 设置导航栏是否可见
     * @param window
     * @param isVisible
     */
    public static void setNavBarVisibility(final Window window, boolean isVisible) {
        try {
            if (isVisible) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                View decorView = window.getDecorView();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    int visibility = decorView.getSystemUiVisibility();
                    decorView.setSystemUiVisibility(visibility & ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置导航栏沉浸式
     * @param window
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setNavBarImmersive(final Window window) {
        View decorView = window.getDecorView();
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 判断导航栏是否可见
     *
     * @param activity activity
     * @return {@code true}: 可见<br>{@code false}: 不可见
     */
    public static boolean isNavBarVisible(final Activity activity) {
        boolean isNoLimits = (activity.getWindow().getAttributes().flags
                & WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) != 0;
        if (isNoLimits) return false;
        View decorView = activity.getWindow().getDecorView();
        int visibility = decorView.getSystemUiVisibility();
        return (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
    }

    /**
     * 获取屏幕的宽高，存储在返回数据中，0：宽 1：高
     * @param
     * @return
     */
    public static int[] getScreenParams(Activity activity) {
        int[] params = new int[2];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
            params[0] = dm.widthPixels;
            params[1] = dm.heightPixels;
        } else {
            DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
            params[0] = metrics.widthPixels;
            params[1] = metrics.heightPixels;
        }
        return params;
    }

}

