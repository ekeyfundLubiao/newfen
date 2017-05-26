package com.m086.ad.mobi.view.photo.local;


import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import com.m086.ad.mobi.tool.contacts.LocalConstant;

/**
 * Created by Administrator on 2016/12/19.
 */
public class ConfigHelper {    private static ConfigHelper mConfigHelper;
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
                .provider("com.anyou.picselect.fileprovider")   // provider(必填)// 记录已选的图片
                .multiSelect(true)                      // 是否多选   默认：false
                .multiSelect(true, 9)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(9)                             // 配置多选时 的多选数量。    默认：9
                .isShowCamera(false)
                   // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .isShowCamera(false)                     // 是否现实相机按钮  默认：false
                .filePath(LocalConstant.Local_Photo_Path) // 图片存放路径
                .build();
        return galleryConfig;
    }

    public GalleryConfig getLocalConfig(IHandlerCallBack iHandlerCallBack,int size)
    {
        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new LocalImageLoader())     // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)      // 监听接口（必填）
                .multiSelect(true)                      // 是否多选   默认：false
                .crop(true, 1, 1, 300, 500)
                .multiSelect(true, size)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(size)// 配置裁剪功能的参数，   默认裁剪比例 1:1
                .provider("com.anyou.picselect.fileprovider")
                .isShowCamera(false)                      // 是否现实相机按钮  默认：false
                .filePath(LocalConstant.Local_Photo_Path)// 图片存放路径
                .build();
        return galleryConfig;
    }

    public GalleryConfig getPhotoConfig(IHandlerCallBack iHandlerCallBack)
    {
        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new LocalImageLoader())
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .crop(true, 1, 1, 300, 500)
                .provider("com.anyou.picselect.fileprovider")
                .filePath(LocalConstant.Local_Photo_Path)          // 图片存放路径   （选填）
                .isOpenCamera(true)                  // 直接开启相机的标识位
                .build();
        return galleryConfig;
    }


    public GalleryConfig getPhotoConfig(IHandlerCallBack iHandlerCallBack,boolean isCrop)
    {
        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new LocalImageLoader())
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .crop(isCrop, 1, 1, 300, 500)
                .provider("com.anyou.picselect.fileprovider")
                .filePath(LocalConstant.Local_Photo_Path)          // 图片存放路径   （选填）
                .isOpenCamera(true)                  // 直接开启相机的标识位
                .build();
        return galleryConfig;
    }

}
