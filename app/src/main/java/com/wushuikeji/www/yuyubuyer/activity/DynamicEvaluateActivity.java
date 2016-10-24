package com.wushuikeji.www.yuyubuyer.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wushuikeji.www.yuyubuyer.R;
import com.wushuikeji.www.yuyubuyer.adapter.FriendInfoListViewAdapter;
import com.wushuikeji.www.yuyubuyer.bean.SystemInfoBean;
import com.wushuikeji.www.yuyubuyer.mylibrary.PullToRefreshSwipeMenuListView;
import com.wushuikeji.www.yuyubuyer.mylibrary.pulltorefresh.interfaces.IXListViewListener;
import com.wushuikeji.www.yuyubuyer.mylibrary.swipemenu.bean.SwipeMenu;
import com.wushuikeji.www.yuyubuyer.mylibrary.swipemenu.bean.SwipeMenuItem;
import com.wushuikeji.www.yuyubuyer.mylibrary.swipemenu.interfaces.OnMenuItemClickListener;
import com.wushuikeji.www.yuyubuyer.mylibrary.swipemenu.interfaces.SwipeMenuCreator;
import com.wushuikeji.www.yuyubuyer.mylibrary.util.RefreshTime;
import com.wushuikeji.www.yuyubuyer.utils.ConvertUtils;
import com.wushuikeji.www.yuyubuyer.utils.ToastUtils;
import com.wushuikeji.www.yuyubuyer.utils.UIUtils;
import com.wushuikeji.www.yuyubuyer.view.CircularProgress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DynamicEvaluateActivity extends AppCompatActivity {

    @InjectView(R.id.rl_dynamicEvaluate_back)
    RelativeLayout rl_dynamicEvaluate_back;

    //空视图
    @InjectView(R.id.tv_dynamicEvaluate_empty)
    TextView tv_dynamicEvaluate_empty;

    //进度条
    @InjectView(R.id.pb_evaluateLoading_progressBar)
    CircularProgress pb_evaluateLoading_progressBar;

    @InjectView(R.id.dynamicEvaluate_listView)
    PullToRefreshSwipeMenuListView dynamicEvaluate_listView;

    private View mFooterView;
    private List<SystemInfoBean> dynamicEvaluateList = new ArrayList<SystemInfoBean>();

    private boolean isBottom;//判断listView是否底部

    private FriendInfoListViewAdapter mDynamicEvaluateListViewAdapter;
    private SwipeMenuCreator mCreator;

    private static final int LOADING = 0;
    private static final int SUCCESS = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADING:
                    pb_evaluateLoading_progressBar.setVisibility(View.VISIBLE);
                    tv_dynamicEvaluate_empty.setVisibility(View.GONE);
                    dynamicEvaluate_listView.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    //有数据
                    if(dynamicEvaluateList.size() != 0) {
                        pb_evaluateLoading_progressBar.setVisibility(View.GONE);
                        tv_dynamicEvaluate_empty.setVisibility(View.GONE);
                        dynamicEvaluate_listView.setVisibility(View.VISIBLE);
                        //更新数据
                        if(mDynamicEvaluateListViewAdapter != null) {
                            mDynamicEvaluateListViewAdapter.notifyDataSetChanged();
                        }
                    }else {
                        pb_evaluateLoading_progressBar.setVisibility(View.GONE);
                        tv_dynamicEvaluate_empty.setVisibility(View.VISIBLE);
                        dynamicEvaluate_listView.setVisibility(View.GONE);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_evaluate);
        initView();
        initData();
        initEvent();
    }
    private void initView() {
        ButterKnife.inject(this);

        //去除系统自带的颜色渐变
        dynamicEvaluate_listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //将尾部布局蹦出来
//        if(mFooterView == null) {
//            mFooterView = View.inflate(UIUtils.getContext(), R.layout.item_listview_mainshop_loadmore, null);
//        }
//
//        //必须在setAdapter之前
//        //加之前，先判断数据源有几个，不够一屏幕显示，不加入
//        ListViewUtils.isAddListViewSystemInfoFooterView(dynamicEvaluateList,dynamicEvaluate_listView,mFooterView);
        mDynamicEvaluateListViewAdapter = new FriendInfoListViewAdapter(dynamicEvaluateList);
        dynamicEvaluate_listView.setAdapter(mDynamicEvaluateListViewAdapter);

        dynamicEvaluate_listView.setPullRefreshEnable(true);
        dynamicEvaluate_listView.setPullLoadEnable(true);

        //添加侧滑图片和文字的操作
        SwipeMenuCreator mCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(UIUtils.getContext());
                // set item background
                deleteItem.setBackground(R.color.colorMainTitle);
                // set item width
                deleteItem.setWidth(ConvertUtils.dp2px(60,UIUtils.getContext()));
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(14);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);

                // set a icon
                deleteItem.setIcon(R.mipmap.delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        dynamicEvaluate_listView.setMenuCreator(mCreator);
    }

    private void initData() {
        //开启线程加载数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                //休眠一秒
                mHandler.obtainMessage(LOADING).sendToTarget();
                SystemClock.sleep(2000);
                //加载数据
                //模拟初始化数据
                moniData();
                //放送handler
                mHandler.obtainMessage(SUCCESS).sendToTarget();
            }
        }).start();


    }

    private void initEvent() {
        //返回键
        rl_dynamicEvaluate_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //空视图的点击事件
        tv_dynamicEvaluate_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //listView的滑动事件
//        dynamicEvaluate_listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                // 数据已经到底部，而且用户是松手状态，这个时候需要加载新的数据
//                if (isBottom && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                    loadNewData();
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                // 判读是否需要加载新的数据
//                isBottom = (firstVisibleItem + visibleItemCount) == totalItemCount;
//            }
//        });
        //侧滑点击删除
        dynamicEvaluate_listView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        dynamicEvaluateList.remove(position);
                        mDynamicEvaluateListViewAdapter.notifyDataSetChanged();
                        ToastUtils.showToastInUIThread(UIUtils.getContext(),"删除了" +position);
                        break;
                }
            }
        });
        //刷新与加载更多的监听
        dynamicEvaluate_listView.setXListViewListener(new IXListViewListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
                        RefreshTime.setRefreshTime(getApplicationContext(), df.format(new Date()));
                        updateData();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNewData();
                    }
                }, 2000);
            }
        });
    }

    private void loadAndRefreshComple(PullToRefreshSwipeMenuListView mListView) {
        mListView.setRefreshTime(RefreshTime.getRefreshTime(getApplicationContext()));
        mListView.stopRefresh();
        mListView.stopLoadMore();
    }

    /**
     * 下拉加载更多的数据显示
     */
    private void loadNewData() {
        SystemClock.sleep(1000);
        moniData();
        loadAndRefreshComple(dynamicEvaluate_listView);
        mDynamicEvaluateListViewAdapter.notifyDataSetChanged();
    }

    /**
     * 模拟数据
     */
    private void moniData() {
        Random random = null;
        for (int i = 0; i < 10; i++) {
            SystemInfoBean mFriendInfoBean = new SystemInfoBean();
            random = new Random();
            mFriendInfoBean.imgUrl = String.valueOf(R.mipmap.logo);
            mFriendInfoBean.friendName = "半夏微凉";
            mFriendInfoBean.content = "写的太棒了，正是我需要，太棒了!";
            mFriendInfoBean.date = "2016-06-"+random.nextInt(20);
            mFriendInfoBean.time = random.nextInt(20)+":00:00";
            dynamicEvaluateList.add(mFriendInfoBean);
        }
    }

    /**
     * 真正刷新数据的方法
     */
    private void updateData() {
        //tv_systeminfo_empty.setVisibility(View.GONE);
        //清空数据源
        dynamicEvaluateList.clear();
        //添加数据到数据源
        moniData();
        loadAndRefreshComple(dynamicEvaluate_listView);
        //ListViewUtils.isAddListViewSystemInfoFooterView(dynamicEvaluateList,dynamicEvaluate_listView,mFooterView);
        //刷新完成
        mDynamicEvaluateListViewAdapter.notifyDataSetChanged();
    }
}
