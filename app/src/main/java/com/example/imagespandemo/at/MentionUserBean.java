package com.example.imagespandemo.at;

/**
 * 提到某人数据
 */
public class MentionUserBean {

    private int start;
    private int end;
    private long uid;

    public MentionUserBean() {

    }

    public MentionUserBean(int start, int end, long uid) {
        this.start = start;
        this.end = end;
        this.uid = uid;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
