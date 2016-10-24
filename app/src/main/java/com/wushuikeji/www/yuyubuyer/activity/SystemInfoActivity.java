package com.wushuikeji.www.yuyubuyer.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
import com.wushuikeji.www.yuyubuyer.utils.ToNextActivityUtils;
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

public class SystemInfoActivity extends AppCompatActivity {

    @InjectView(R.id.rl_systemInfo_back)
    RelativeLayout rl_systemInfo_back;

    //空视图
    @InjectView(R.id.tv_systemInfo_empty)
    TextView tv_systemInfo_empty;

    //进度条
    @InjectView(R.id.pb_systemLoading_progressBar)
    CircularProgress pb_systemLoading_progressBar;

    @InjectView(R.id.systemInfo_listView)
    PullToRefreshSwipeMenuListView systemInfo_listView;

    private View mFooterView;
    private List<SystemInfoBean> systemInfoList = new ArrayList<SystemInfoBean>();

    private boolean isBottom;//判断listView是否底部

    private FriendInfoListViewAdapter mSystemInfoListViewAdapter;
    private SwipeMenuCreator mCreator;

    private static final int LOADING = 0;
    private static final int SUCCESS = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADING:
                    pb_systemLoading_progressBar.setVisibility(View.VISIBLE);
                    tv_systemInfo_empty.setVisibility(View.GONE);
                    systemInfo_listView.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    //有数据
                    if (systemInfoList.size() != 0) {
                        pb_systemLoading_progressBar.setVisibility(View.GONE);
                        tv_systemInfo_empty.setVisibility(View.GONE);
                        systemInfo_listView.setVisibility(View.VISIBLE);
                        //更新数据
                        if (mSystemInfoListViewAdapter != null) {
                            mSystemInfoListViewAdapter.notifyDataSetChanged();
                        }
                    } else {
                        pb_systemLoading_progressBar.setVisibility(View.GONE);
                        tv_systemInfo_empty.setVisibility(View.VISIBLE);
                        systemInfo_listView.setVisibility(View.GONE);
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
        setContentView(R.layout.activity_system_info);
        initView();
        initData();
        initEvent();

    }

    private void initView() {
        ButterKnife.inject(this);

        //去除系统自带的颜色渐变
        systemInfo_listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //将尾部布局蹦出来
        //        if(mFooterView == null) {
        //            mFooterView = View.inflate(UIUtils.getContext(), R.layout.item_listview_mainshop_loadmore, null);
        //        }
        //        //必须在setAdapter之前
        //        //加之前，先判断数据源有几个，不够一屏幕显示，不加入
        //        ListViewUtils.isAddListViewSystemInfoFooterView(systemInfoList,systemInfo_listView,mFooterView);
        mSystemInfoListViewAdapter = new FriendInfoListViewAdapter(systemInfoList);
        systemInfo_listView.setAdapter(mSystemInfoListViewAdapter);

        systemInfo_listView.setPullRefreshEnable(true);
        systemInfo_listView.setPullLoadEnable(true);

        //添加侧滑图片和文字的操作
        SwipeMenuCreator mCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(UIUtils.getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                // set item width
                deleteItem.setBackground(R.color.colorMainTitle);
                // set item width
                deleteItem.setWidth(ConvertUtils.dp2px(60, UIUtils.getContext()));
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(14);
                deleteItem.setTitleColor(Color.WHITE);
                // set a icon
                deleteItem.setIcon(R.mipmap.delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        systemInfo_listView.setMenuCreator(mCreator);
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
        rl_systemInfo_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //空视图的点击事件
        tv_systemInfo_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //listView的点击事件
        systemInfo_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToNextActivityUtils.toNextAndNoFinishActivity(SystemInfoActivity.this,SystemInfoDetailsActivity.class);
            }
        });

        //侧滑点击删除
        systemInfo_listView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        systemInfoList.remove(position);
                        mSystemInfoListViewAdapter.notifyDataSetChanged();
                        ToastUtils.showToastInUIThread(UIUtils.getContext(), "删除了" + position);
                        break;
                }
            }
        });
        //刷新与加载更多的监听
        systemInfo_listView.setXListViewListener(new IXListViewListener() {
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
        loadAndRefreshComple(systemInfo_listView);
        mSystemInfoListViewAdapter.notifyDataSetChanged();
    }

    /**
     * 模拟数据
     */
    private void moniData() {
        Random random = null;
        for (int i = 0; i < 10; i++) {
            SystemInfoBean mSystemInfoBean = new SystemInfoBean();
            random = new Random();
            mSystemInfoBean.imgUrl = String.valueOf(R.mipmap.system);
            mSystemInfoBean.friendName = "系统消息";
            mSystemInfoBean.content = "很高兴认识你，欢迎加入鱼鱼";
            mSystemInfoBean.date = "2016-06-" + random.nextInt(20);
            mSystemInfoBean.time = random.nextInt(20) + ":00:00";
            systemInfoList.add(mSystemInfoBean);
        }
    }

    /**
     * 真正刷新数据的方法
     */
    private void updateData() {
        //tv_systeminfo_empty.setVisibility(View.GONE);
        //清空数据源
        systemInfoList.clear();
        //添加数据到数据源
        moniData();
        //ListViewUtils.isAddListViewSystemInfoFooterView(systemInfoList,systemInfo_listView,mFooterView);
        //刷新完成
        loadAndRefreshComple(systemInfo_listView);
        mSystemInfoListViewAdapter.notifyDataSetChanged();
    }
}
