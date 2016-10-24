package com.wushuikeji.www.yuyubuyer.base;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.wushuikeji.www.yuyubuyer.service.LocationService;
import com.wushuikeji.www.yuyubuyer.utils.PermissionsCheckerUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class BaseApplication extends Application {

	private LinkedList<Activity> activityList = new LinkedList<Activity>();

	private static Context	mContext;
	private static Thread	mMainThread;
	private static long		mMainTreadId;
	private static Looper	mMainLooper;
	private static Handler	mHandler;
	//百度定位
	public LocationService locationService;
	public Vibrator mVibrator;
	//权限
	public PermissionsCheckerUtils mPermissionsCheckerUtils;

	public static BaseApplication baseApplication;
	public static BaseApplication getInstance() {

		return baseApplication;
	}

	public static Handler getHandler() {
		return mHandler;
	}

	public static Context getContext() {
		return mContext;
	}

	public static Thread getMainThread() {
		return mMainThread;
	}

	public static long getMainTreadId() {
		return mMainTreadId;
	}

	public static Looper getMainThreadLooper() {
		return mMainLooper;
	}

	@Override
	public void onCreate() {// 程序的入口
		super.onCreate();
		//配置ImageLoader
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2)// 设置线程的优先级
				.denyCacheImageMultipleSizesInMemory()// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
				.discCacheFileNameGenerator(new Md5FileNameGenerator())// 设置缓存文件的名字
				.discCacheFileCount(50)// 缓存文件的最大个数
				.tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
				.build();
		//缓存目录自定义discCacheFileNameGenerator(new UnlimitedDiscCache(cacheDir))

		// Initialize ImageLoader with configuration
		ImageLoader.getInstance().init(config);
		//默认
//		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

		//配置OkHttpUtils参数
		HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
		CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.connectTimeout(8000L, TimeUnit.MILLISECONDS)
				.readTimeout(8000L, TimeUnit.MILLISECONDS)
				.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
				//其他配置
				.build();
		OkHttpUtils.initClient(okHttpClient);

		// 初始化化一些.常用属性.然后放到盒子里面来
		// 上下文
		mContext = getApplicationContext();

		// 主线程
		mMainThread = Thread.currentThread();

		// 主线程Id
		mMainTreadId = android.os.Process.myTid();

		// tid thread
		// uid user
		// pid process
		// 主线程Looper对象
		mMainLooper = getMainLooper();

		// 定义一个handler
		mHandler = new Handler();

		//捕获程序中未try-catch的异常
//		CrashHandler crashHandler = CrashHandler.getInstance();
//		crashHandler.init(getApplicationContext());

		//便于统一管理Activity
		this.baseApplication = this;
		//百度定位的初始化
		/***
		 * 初始化定位sdk，建议在Application中创建
		 */
		locationService = new LocationService(getApplicationContext());
		mPermissionsCheckerUtils = new PermissionsCheckerUtils(getApplicationContext());
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
	}

	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 遍历所有Activity并finish
	public void closeActivity() {
		try {
			for (Activity activity : activityList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
