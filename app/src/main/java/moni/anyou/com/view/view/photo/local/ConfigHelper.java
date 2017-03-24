package moni.anyou.com.view.view.photo.local;


import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import moni.anyou.com.view.tool.contacts.LocalConstant;

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
                .crop(false)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(false, 1, 1, 500, 500)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(false)                     // 是否现实相机按钮  默认：false
                .filePath(LocalConstant.Local_Photo_Path) // 图片存放路径
                .build();
//        galleryConfig = new GalleryConfig.Builder()
//                .imageLoader(new LocalImageLoader())    // ImageLoader 加载框架（必填）
//                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
//                .crop(true, width, height, width, height)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
//                .isShowCamera(true)// 是否现实相机按钮  默认：false
//                .provider("com.yancy.gallerypickdemo.fileprovider")
//                .filePath(LocalConstant.Local_Photo_Path)          // 图片存放路径
//                .build();
        return galleryConfig;
    }

    public GalleryConfig getLocalConfig(IHandlerCallBack iHandlerCallBack,int size)
    {
        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new LocalImageLoader())     // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)      // 监听接口（必填）
                .multiSelect(true)                      // 是否多选   默认：false
                .crop(true)
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
                .crop(true)
                .provider("com.anyou.picselect.fileprovider")
                .filePath(LocalConstant.Local_Photo_Path)          // 图片存放路径   （选填）
                .isOpenCamera(true)                  // 直接开启相机的标识位
                .build();
        return galleryConfig;
    }

}
