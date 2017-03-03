package moni.anyou.com.view.view.photo.local;


import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import moni.anyou.com.view.tool.contacts.LocalConstant;

/**
 * Created by Administrator on 2016/12/19.
 */
public class ConfigHelper {
    private static ConfigHelper mConfigHelper;
    private GalleryConfig galleryConfig = null;
    private ConfigHelper() {
    }
    public static ConfigHelper getInstance() {
        if (null == mConfigHelper) {
            mConfigHelper = new ConfigHelper();
        }
        return mConfigHelper;
    }

    public GalleryConfig getLocalConfig(IHandlerCallBack iHandlerCallBack,int width,int height)
    {
        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new LocalImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .crop(true, width, height, width, height)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath(LocalConstant.Local_Photo_Path)          // 图片存放路径
                .build();
        return galleryConfig;
    }

    public GalleryConfig getLocalConfig(IHandlerCallBack iHandlerCallBack)
    {
        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new LocalImageLoader())     // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)      // 监听接口（必填）
                .multiSelect(false)                      // 是否多选   默认：false
                .crop(true)                              // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                      // 是否现实相机按钮  默认：false
                .filePath(LocalConstant.Local_Photo_Path)// 图片存放路径
                .build();
        return galleryConfig;
    }

    public GalleryConfig getPhotoConfig(IHandlerCallBack iHandlerCallBack)
    {
        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new LocalImageLoader())
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .crop(true)
                .filePath(LocalConstant.Local_Photo_Path)          // 图片存放路径   （选填）
                .isOpenCamera(true)                  // 直接开启相机的标识位
                .build();
        return galleryConfig;
    }
}
