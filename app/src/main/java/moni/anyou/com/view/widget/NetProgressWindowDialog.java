package moni.anyou.com.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import moni.anyou.com.view.R;


public class NetProgressWindowDialog extends WindowDialog {

    public NetProgressWindowDialog(Context context) {
        super(context);
    }
    View view = inflater.inflate(R.layout.dialog_progress, null);
    {
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (window != null) window.dismiss();
            }
        });
    }

    @SuppressLint("NewApi")
    @Override
    public PopupWindow getWindow(int i, Object tag, PopupWindow window) {

        switch (i) {
            case 0:
                if (window == null) {
                    window = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
                }
                if (tag != null) {
                    TextView textview = (TextView) view.findViewById(R.id.text1);
                    textview.setText(tag.toString());
                }
                try {
                    window.showAtLocation(view, Gravity.CENTER,
                            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    window.update();
                } catch (Exception ex) {

                }
                break;
            case -1:

                if (window != null) {
                    try {
                        window.dismiss();
                    } catch (Exception ex) {

                    }
                }

                break;
        }
        return window;
    }


    public boolean isShowing() {
        int temp = view.getVisibility();
        boolean tempStatus = false;
        switch (temp) {
            case 0:
                tempStatus = false;
                break;
            default:
                tempStatus = true;
                break;
        }
        return tempStatus;
    }

}
