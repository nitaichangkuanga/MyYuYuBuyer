package com.wushuikeji.www.yuyubuyer.bean;

/**
 * @author Jack_chentao
 * @time 2016/9/27 0027 上午 10:34.
 * @des ${TODO}
 */
public class SystemInfoBean {
    //图片的url
    public String imgUrl;
    //好友名
    public String friendName;
    //日期
    public String date;
    //时间
    public String time;
    //内容
    public String content;

    @Override
    public String toString() {
        return "SystemInfoBean{" +
                "imgUrl='" + imgUrl + '\'' +
                ", friendName='" + friendName + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
