package com.m086.ad.mobi.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

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

import com.m086.ad.mobi.tool.contacts.LocalConstant;

/**
 * Created by Administrator on 2016/12/9.
 */
public class ImageTools {
    // 内存缓存大小
    private static final long Memory_Cache_Size = Runtime.getRuntime()
            .maxMemory() / 8;
    // 本地缓存大小
    private static final long Disc_Cache_Size = 50 * 1024 * 1024;
    // 本地缓存最多文件数
    private static final int Max_Cache_No = 150;
    // 线程池内加载的数量
    private static final int Max_Thread_Size = 8;
    // 连接超时时间
    private static final int Connect_Timeout = 5 * 1000;
    // 读取超时时间
    private static final int Read_Timeout = 30 * 1000;
    private Context mContext;
    private static ImageTools mImageTools = null;

    private ImageLoaderConfiguration mConfig;
    public ImageLoader mImageLoader = null;

    public ImageTools(Context context) {
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
                                .getOwnCacheDirectory(mContext, LocalConstant.Relative_Path)))
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

    public static DisplayImageOptions getLocalOptions(Drawable drawable) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(drawable)
                .showImageForEmptyUri(drawable)
                .showImageOnFail(drawable)
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .delayBeforeLoading(0)
                .resetViewBeforeLoading(true)
                .build();
        return options;
    }

    public static DisplayImageOptions getLocalOptions(int drawable) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(drawable)
                .showImageForEmptyUri(drawable)
                .showImageOnFail(drawable)
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .delayBeforeLoading(0)
                .resetViewBeforeLoading(true)
                .build();
        return options;
    }

    public static ImageTools getInstance(Context context) {
        if (null == mImageTools) {
            mImageTools = new ImageTools(context);
        }
        return mImageTools;
    }


    public static Bitmap drawableToBitamp(Drawable drawable) {
        Bitmap bitmap;
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        System.out.println("Drawable转Bitmap");
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
