package moni.anyou.com.view.tool;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import moni.anyou.com.view.R;


/**
 * Created by Administrator on 2016/12/5.
 */

public class TextTool {
    public static void forDiffText(String targetStr, TextView view, int form, Context context) {
        int strSize=targetStr.length();
        SpannableStringBuilder builder = new SpannableStringBuilder(targetStr);
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan redSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.appcolor));
        builder.setSpan(redSpan,form, strSize-2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(builder);

    }
}
