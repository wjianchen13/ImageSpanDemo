package com.example.imagespandemo.at;

import android.text.Editable;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * @某个人相关操作
 */
public class RoomMentionManager {

    public static final String TAG = RoomMentionManager.class.getSimpleName();
    private static final String LTR = "\u200E";

    private List<MentionUserBean> mentionList;

    private String content;
    private String atUser;

    private String rContent;
    private String rAtUser;

    public RoomMentionManager() {
        mentionList = new ArrayList<>();
    }

    public void add(MentionUserBean bean) {
        if(bean != null) {
            if(mentionList == null) {
                mentionList = new ArrayList<>();
            }
            mentionList.add(bean);
        }
    }

    public void clear() {
        if(mentionList != null)
            mentionList.clear();
    }

    public boolean isAtTa(Editable editable) {
        if(editable != null) {
            AtUserSpan[] dataSpans = editable.getSpans(0, editable.length(), AtUserSpan.class);
            return dataSpans != null && dataSpans.length > 0;
        }
        return false;
    }

    public String getContent() {
        return content;
    }

    public String getAtUser() {
        return atUser;
    }

    /**
     * 初始化发送信息
     * 把content的特殊字符去掉
     * 修改atUser各个span的start 和 end
     */
    public void initSendMsg(Editable editable) {
        if(editable != null) {
            AtUserSpan[] dataSpans = editable.getSpans(0, editable.length(), AtUserSpan.class);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("完整字符串：").append(editable).append("\n")
                    .append("字符串大小：").append(editable.length()).append("\n")
                    .append("特殊字符串：\n");
            int distance = 0; // 删除特殊字符"\u200E" 需要跳过的距离
            if(mentionList == null) {
                mentionList = new ArrayList<>();
            }
            mentionList.clear();
            for (int i = 0; i < dataSpans.length; i ++) {
                AtUserSpan dataSpan = dataSpans[i];
                if(dataSpan != null) {
                    stringBuilder.append(dataSpan.toString()).append("\n");
                    int start = editable.getSpanStart(dataSpan) - (2 * i);
                    int end = editable.getSpanEnd(dataSpan) - (2 * i + 2);
                    long uid = dataSpan.getUid();
                    AtActivity.log("uid: " + uid + "  start: " + start + "  end: " + end + "  name: " + dataSpan.getName());
                    MentionUserBean bean = new MentionUserBean(start, end, uid);
                    mentionList.add((bean));
                }
            }
            atUser = CommonGsonUtils.toJson(mentionList);
            String content1 = editable.toString().trim();
            AtActivity.log("content1: " + content1);
            content = content1.replace("\u200E", "");
            AtActivity.log("content: " + content);
        }
    }

    public String getrContent() {
        return rContent;
    }

    public String getrAtUser() {
        return rAtUser;
    }

    /**
     * 处理接收到的@消息
     * 1 content在@username前后添加特殊字符
     * 2 修改atUser start和end的值
     */
    public String initReceiveMsg(String content, String atUser) {
        if(!TextUtils.isEmpty(content) && !TextUtils.isEmpty(atUser)) {
            List<MentionUserBean> users = CommonGsonUtils.fromJson(atUser, new TypeToken<ArrayList<MentionUserBean>>() {});
            if(users != null && users.size() > 0) {
                StringBuilder sb = new StringBuilder(content);
                for(int i = 0; i < users.size(); i ++) {
                    MentionUserBean user = users.get(i);
                    if(user != null) {
                        int start = user.getStart() + 2 * i;
                        int insertStart = user.getStart() + 2 * i; // 插入index
                        if(insertStart >= 0 && insertStart <= sb.length()) {
                            sb.insert(insertStart, LTR);
                        }
                        int end = user.getEnd() + 2 * i + 2;
                        int insertEnd = user.getEnd() + 1 + 2 * i;
                        if(sb.length() >= insertEnd && insertEnd >= 0) { // 插入范围限制，可能会出异常
                            sb.insert(insertEnd, LTR);
                        }
                        user.setStart(start);
                        user.setEnd(end);
                    }
                }
                rContent = sb.toString();
                rAtUser = CommonGsonUtils.toJson(users);
            }
        }
        return "";
    }

    /**
     * 把特殊字符都去掉
     * @param editable
     * @return
     */
    public String getContent(Editable editable) {
        if(editable != null) {
            String content1 = editable.toString().trim();
            String content = content1.replace("\u200e", "");
            content = content.replace("\u200f", "");
            return content;
        }
        return "";
    }

//    /**
//     * 添加特殊字符
//     * @return
//     */
//    public String setContent(String content, String atUser) {
//        if(content != null) {
//            String content1 = editable.toString().trim();
//            String content = content1.replace("\u200e", "");
//            content = content.replace("\u200f", "");
//            return content;
//        }
//        return "";
//    }

    /**
     * 去掉首尾特殊字符之后，start 和end的值应该有所改变
     * @param editable
     * @return
     */
    public String getMentionJson(Editable editable) {
        if (mentionList == null) {
            mentionList = new ArrayList<>();
        }
        if(editable != null) {
//            editable.replace(0, editable.length(), )
            AtUserSpan[] dataSpans = editable.getSpans(0, editable.length(), AtUserSpan.class);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("完整字符串：").append(editable).append("\n")
                    .append("字符串大小：").append(editable.length()).append("\n")
                    .append("特殊字符串：\n");
            for (AtUserSpan dataSpan : dataSpans) {
                stringBuilder.append(dataSpan.toString()).append("\n");
                int start = editable.getSpanStart(dataSpan);
                int end = editable.getSpanEnd(dataSpan);
                long uid = dataSpan.getUid();
                MentionUserBean bean = new MentionUserBean(start, end, uid);
                mentionList.add((bean));
            }
//            LogUtils.i(TAG, "getData: " + stringBuilder);
//            AppUtil.showToast(stringBuilder);
        }

        return CommonGsonUtils.toJson(mentionList);
    }



}
