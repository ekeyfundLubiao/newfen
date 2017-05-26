package com.m086.ad.mobi.tool;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.m086.ad.mobi.R;


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


    public static void forCommentText(String targetStr, TextView view, int form, Context context) {
        int strSize=targetStr.length();
        SpannableStringBuilder builder = new SpannableStringBuilder(targetStr);
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan fristSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.color_comment));
        ForegroundColorSpan secondSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.black));
        builder.setSpan(fristSpan,0, form, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(builder);

    }
}
