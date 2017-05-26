package com.m086.ad.mobi.widget.utils.imageload;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class ImageLoadUtil {
	// 本地缓存相对路径
	public static final String Relative_Path = "data/anyou/http";
	// 内存缓存大小
	private static final long Memory_Cache_Size = Runtime.getRuntime()
			.maxMemory() / 8;
	// 本地缓存大小
	private static final long Disc_Cache_Size = 30 * 1024 * 1024;
	// 本地缓存最多文件数
	private static final int Max_Cache_No = 100;
	// 线程池内加载的数量
	private static final int Max_Thread_Size = 5;
	// 连接超时时间
	private static final int Connect_Timeout = 5 * 1000;
	// 读取超时时间
	private static final int Read_Timeout = 30 * 1000;
	private Context mContext;
	private static ImageLoadUtil mImageLoadUtil = null;

	private ImageLoaderConfiguration mConfig;
	public ImageLoader mImageLoader = null;
	
	private ImageLoadUtil(Context context) {
		mContext = context;
		mConfig = new ImageLoaderConfiguration.Builder(context)
				.threadPoolSize(Max_Thread_Size)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCache(
						new UsingFreqLimitedMemoryCache((int) Memory_Cache_Size))
				.memoryCacheSize((int) Memory_Cache_Size)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheSize((int) Disc_Cache_Size)
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.diskCacheFileCount(Max_Cache_No)
				.diskCache(
						new UnlimitedDiskCache(StorageUtils
								.getOwnCacheDirectory(mContext, Relative_Path)))
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(
						new BaseImageDownloader(mContext, Connect_Timeout,
								Read_Timeout)).writeDebugLogs().build();
		mImageLoader = ImageLoader.getInstance();
		mImageLoader.init(mConfig);
	}
	
	public static DisplayImageOptions getOptions(int loadingRes, int errRes,
			int failRes) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(loadingRes)
				.showImageForEmptyUri(errRes)
				.showImageOnFail(failRes)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.considerExifParams(true)
				.delayBeforeLoading(0)
				.resetViewBeforeLoading(true)
				.displayer(new RoundedBitmapDisplayer(0))
				.displayer(new FadeInBitmapDisplayer(0))
				.build();
		return options;
	}
	
	public static DisplayImageOptions getOptions(int loadingRes, int errRes,
			int failRes, boolean inMemory, boolean OnDisk, int lazyTime,
			int roundSize, int fadeTime) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		       // 设置图片在下载期间显示的图片
				.showImageOnLoading(loadingRes)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageForEmptyUri(errRes)
				// 设置图片加载/解码过程中错误时候显示的图片
				.showImageOnFail(failRes)
				// 设置下载的图片是否缓存在内存中
				.cacheInMemory(inMemory)
				// 设置下载的图片是否缓存在SD卡中
				.cacheOnDisk(OnDisk)
				// 保留Exif信息
				.considerExifParams(true)
				// 设置图片以如何的编码方式显示
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				// 设置图片的解码类型
				.bitmapConfig(Bitmap.Config.RGB_565)
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				.considerExifParams(true)
				// 设置图片下载前的延迟
				.delayBeforeLoading(lazyTime)// int
				// delayInMillis为你设置的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// .preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				.displayer(new RoundedBitmapDisplayer(roundSize))// 是否设置为圆角，弧度为多少
				.displayer(new FadeInBitmapDisplayer(fadeTime))// 淡入
				.build();
		return options;
	}

	public static ImageLoadUtil getInstance(Context context) {
		if (null == mImageLoadUtil) {
			mImageLoadUtil = new ImageLoadUtil(context);
		}
		return mImageLoadUtil;
	}
}
