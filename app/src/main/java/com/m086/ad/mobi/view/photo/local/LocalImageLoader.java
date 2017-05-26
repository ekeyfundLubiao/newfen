package com.m086.ad.mobi.view.photo.local;

import android.app.Activity;
import android.content.Context;


;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.yancy.gallerypick.inter.ImageLoader;
import com.yancy.gallerypick.widget.GalleryImageView;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.tool.ImageTools;

/**
 * Created by Administrator on 2016/12/16.
 */
public class LocalImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity,
                             Context context,
                             String path, GalleryImageView galleryImageView,
                             int width,
                             int height) {
        ImageSize imageSize = new ImageSize(width, height);
        ImageTools.getInstance(activity).mImageLoader.displayImage(
                "file://" + path,
                new ImageViewAware(galleryImageView),
                ImageTools.getInstance(activity).getLocalOptions(R.mipmap.ic_launcher),
                imageSize,
                null,
                null
        );
    }
    @Override
    public void clearMemoryCache() {

    }
}
