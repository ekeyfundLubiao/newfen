package moni.anyou.com.view.view.photo.local;

import android.app.Activity;
import android.content.Context;


;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.yancy.gallerypick.inter.ImageLoader;
import com.yancy.gallerypick.widget.GalleryImageView;

import moni.anyou.com.view.R;
import moni.anyou.com.view.tool.ImageTools;

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
