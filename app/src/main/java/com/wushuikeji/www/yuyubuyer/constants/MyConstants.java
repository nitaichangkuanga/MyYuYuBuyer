package com.wushuikeji.www.yuyubuyer.constants;

public interface MyConstants
{
	String SPNAME = "cachevalue";//sp的文件名
	String ISGUIDE = "isguide";//是否进入过向导界面
	String FRAGMENTINDEX = "fragmentIndex";//保存fragmentIndex
	int REQUESTCODE = 0;//请求码
	String USERNAME = "userName";//个人里的用户昵称
	String BUYERID = "BUYERID";//个人里的用户YUYUID
	String ISLOGINSTATUS = "isLoginStatus";//登录的状态
	String LOGINSPNAME = "loginSpName";//单独保存登录的sp名字，好用户退出登录删除
	String LATITUDE = "latitude";//纬度
	String LONGITUDE = "longitude";//经度
	String SHOPSPNAME = "shopSpName";//保存商铺的sp名字，好用户重新选择商圈时删除
	String SHOPFRAGMENTJSON = "shopFragmentJSON";//shopFragment的数据缓存
	String BUYERSPNAME = "buyerSpName";//保存商铺的sp名字，好用户重新选择商圈时删除
	String BUYERFRAGMENTJSON = "buyerFragmentJSON";//buyerFragment的数据缓存
	String FOOTVIEWNAME = "暂无数据";//用于删除footView
	String LOGIN = "请先登录";//提示登录
	String BUYERDETAILSATTENTIONSPNAME = "buyerDetailsAttentionSpName";//保存buyer详情里的是否关注的sp名字(清理缓存时不删除)
	String ISATTENTION = "isAttention";//是否关注过
}
