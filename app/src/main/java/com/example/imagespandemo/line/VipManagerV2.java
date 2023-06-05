package com.example.imagespandemo.line;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.imagespandemo.R;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * VIP 相关资源管理 V2版本
 * svga文件通过网络获取，替换之前通过本地文件获取的方式
 */
public class VipManagerV2 {

    /**
     * 压缩文件目录名称
     */
    private static final String NAME_ZIP = "vip_resources";

    /**
     * 财富等级名称
     */
    private static final String NAME_VIP_WEALTH = "vip_wealth_";

    /**
     * 明星等级名称
     */
    private static final String NAME_VIP_STAR = "vip_star_";

    /**
     * Svip名称
     */
    private static final String NAME_VIP_SVIP = "vip_svip_";

    /**
     * 头像框名称
     */
    private static final String NAME_VIP_AVATAR = "vip_avatar_";

    /**
     * 头像框名称
     */
    private static final String NAME_VIP_ENTRY = "vip_entry_";

    /**
     * 资料卡名称
     */
    private static final String NAME_VIP_INFO = "vip_info_";

    /**
     * 聊天气泡名称
     */
    private static final String NAME_VIP_CHAT = "vip_chat_";

    /**
     * 语音波名称
     */
    private static final String NAME_VIP_WAVE = "vip_wave_";

    /**
     * 财富等级目录
     */
    private static final String DIRECTORY_VIP_WEALTH = "wealth";

    /**
     * 明星等级目录
     */
    private static final String DIRECTORY_VIP_STAR = "star";

    /**
     * SVIP徽章目录
     */
    private static final String DIRECTORY_VIP_SVIP = "svip";

    /**
     * 头像框目录
     */
    private static final String DIRECTORY_VIP_AVATAR = "avatar";

    /**
     * 入场飘屏目录
     */
    private static final String DIRECTORY_VIP_ENTRY = "entry";

    /**
     * 资料卡目录
     */
    private static final String DIRECTORY_VIP_INFO = "info";

    /**
     * 聊天气泡目录
     */
    private static final String DIRECTORY_VIP_CHAT = "chat";

    /**
     * 语音波目录
     */
    private static final String DIRECTORY_VIP_WAVE = "wave";

    private final int[] startColor;
    private final int[] midColor;
    private final int[] endColor;

    private String mPrivateRootPath;

    private static VipManagerV2 INSTANCE = null;

    private VipManagerV2() {

        startColor = new int[]{R.color.ccbcbcb,
                R.color.c01a067,
                R.color.c2176f5,
                R.color.cff4090,
                R.color.cfe652c,
                R.color.c952bf4,
                R.color.cda910a};
        midColor = new int[]{R.color.ccbcbcb,
                R.color.caaff76,
                R.color.c87bcff,
                R.color.cffa370,
                R.color.cff7b9b,
                R.color.ce935e2,
                R.color.cf8d539};
        endColor = new int[]{R.color.ccbcbcb,
                R.color.c01a067,
                R.color.c2176f5,
                R.color.cff4090,
                R.color.cfe652c,
                R.color.c952bf4,
                R.color.cda910a};
    }

    public static VipManagerV2 getInstance() {
        if (INSTANCE == null) {
            synchronized (VipManagerV2.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VipManagerV2();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 显示vip名字，渐变，需要点击
     *
     * @param nobleId vip id
     * @return
     */
    public SpannableString getVipNameClick(Context context, String start, String name, int nobleId, final View.OnClickListener mOnClickListener, final Object clickTag, @ColorRes int defaultColor) {
        return SpannableUtils.getGradientClickSpanText(context, start,
                name,
                getColor(startColor, nobleId, defaultColor),
                getColor(midColor, nobleId, defaultColor),
                getColor(endColor, nobleId, defaultColor),
                nobleId > 0,
                defaultColor,
                mOnClickListener,
                clickTag);
    }

    /**
     * 获取对应VIP等级名称的开始颜色
     */
    private int getColor(int[] colors, int nobleId, @ColorRes int defaultColor) {
        if(nobleId == 0 && defaultColor != 0)
            return defaultColor;
        if (nobleId >= 0 && nobleId <= 6 && colors != null && colors.length >= 7) {
            return colors[nobleId];
        }
        return R.color.ccbcbcb;
    }








}
