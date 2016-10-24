package com.wushuikeji.www.yuyubuyer.utils;

import android.view.View;
import android.widget.ListView;

import com.wushuikeji.www.yuyubuyer.bean.AllEvaluateBean;
import com.wushuikeji.www.yuyubuyer.bean.BuyerBean;
import com.wushuikeji.www.yuyubuyer.bean.OrderBean;
import com.wushuikeji.www.yuyubuyer.bean.ShopBean;
import com.wushuikeji.www.yuyubuyer.bean.ShopDetailsBean;
import com.wushuikeji.www.yuyubuyer.bean.ShopEvaluateBean;
import com.wushuikeji.www.yuyubuyer.bean.SystemInfoBean;

import java.util.List;

/**
 * @author Jack_chentao
 * @time 2016/9/30 0030 上午 10:08.
 * @des ${TODO}
 */
public class ListViewUtils {
    /**
     * 是否添加尾部布局
     */
    public static void isAddListViewShopFooterView(List<ShopBean> mList, ListView mListView, View footerView) {
        if(mList.size()>7 && mListView.getFooterViewsCount()==0) {
            mListView.addFooterView(footerView,null,false);
        }
    }
    public static void isAddListViewBuyerFooterView(List<BuyerBean> mList, ListView mListView, View footerView) {
        if(mList.size()>7 && mListView.getFooterViewsCount()==0) {
            mListView.addFooterView(footerView,null,false);
        }
    }
    public static void isAddListViewOrderrFooterView(List<OrderBean> mList, ListView mListView, View footerView) {
        if(mList.size()>7 && mListView.getFooterViewsCount()==0) {
            mListView.addFooterView(footerView,null,false);
        }
    }
    public static void isAddListViewSystemInfoFooterView(List<SystemInfoBean> mList, ListView mListView, View footerView) {
        if(mList.size()>7 && mListView.getFooterViewsCount()==0) {
            mListView.addFooterView(footerView,null,false);
        }
    }

    public static void isAddListViewShopDetailsFooterView(List<ShopDetailsBean> mList, ListView mListView, View footerView) {
        if(mList.size()>3 && mListView.getFooterViewsCount()==0) {
            mListView.addFooterView(footerView,null,false);
        }
    }

    public static void isAddListViewShopEvaluateFooterView(List<ShopEvaluateBean> mList, ListView mListView, View footerView) {
        if(mList.size()>7 && mListView.getFooterViewsCount()==0) {
            mListView.addFooterView(footerView,null,false);
        }
    }

    public static void isAddListViewAllEvaluateFooterView(List<AllEvaluateBean> mList, ListView mListView, View footerView) {
        if(mList.size()>7 && mListView.getFooterViewsCount()==0) {
            mListView.addFooterView(footerView,null,false);
        }
    }
}
