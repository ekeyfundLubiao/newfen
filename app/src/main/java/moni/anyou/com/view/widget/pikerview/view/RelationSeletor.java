package moni.anyou.com.view.widget.pikerview.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import moni.anyou.com.view.R;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.widget.pikerview.TimeSelector;
import moni.anyou.com.view.widget.pikerview.Utils.DateUtil;
import moni.anyou.com.view.widget.pikerview.Utils.ScreenUtil;

/**
 * Created by Administrator on 2016/12/21.
 */

public class RelationSeletor {
    public interface ResultHandler {
        void handle(String time);
    }
    private ResultHandler handler;
    private Context context;

    private Dialog seletorDialog;
    private PickerView relation_pv;
    private ArrayList<String> StringRelations;
    private String startRelations;
    private boolean spanRelations;
    private final long ANIMATORDELAY = 200L;
    private final long CHANGEDELAY = 90L;
    private String workStart_str;
    private String workEnd_str;
    private Button tv_cancle;
    private Button tv_select;

    public RelationSeletor(Context context, ResultHandler resultHandler, String startDate, String endDate) {
        this.context = context;
        this.handler = resultHandler;
        initDialog();
        initView();
    }

    public RelationSeletor(Context context, ResultHandler resultHandler, String startDate, String endDate, String workStartTime, String workEndTime) {
        this(context, resultHandler, startDate, endDate);
        this.workStart_str = workStartTime;
        this.workEnd_str = workEndTime;
    }
    private void initDialog() {
        if (seletorDialog == null) {
            seletorDialog = new Dialog(context, R.style.time_dialog);
            seletorDialog.setCancelable(false);
            seletorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            seletorDialog.setContentView(R.layout.dialog_relation_selector);
            Window window = seletorDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams lp = window.getAttributes();
            int width = ScreenUtil.getInstance(context).getScreenWidth();
            lp.width = width;
            window.setAttributes(lp);
        }
    }

    private void initView() {
        relation_pv = (PickerView) seletorDialog.findViewById(R.id.relation_pv);
        tv_cancle = (Button) seletorDialog.findViewById(R.id.btn_cancle);
        tv_select = (Button) seletorDialog.findViewById(R.id.btn_select);


        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletorDialog.dismiss();
            }
        });
        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.handle(DateUtil.format(selectedCalender.getTime(), FORMAT_HM));
                seletorDialog.dismiss();
            }
        });

    }

    public void show() {
        if (StringRelations!=null) {
            Toast.makeText(context, "初始数据不能为空", Toast.LENGTH_LONG).show();
            return;
        }

      //  if (!excuteWorkTime()) return;
        initParameter();
        initTimer();
        addListener();
        seletorDialog.show();


    }


    int relationSize=-1;
    private void initParameter() {
//        int RalationSize=-1;
//        if (StringRelations!=null) {
//            relationSize=StringRelations.size();
//        }
//
//        startRelations = StringRelations.get(relationSize/2);
    }

    private void initTimer() {
        initArrayList();
        for (int i=0;i<relationSize;i++) {
            StringRelations.add(StringRelations.get(i));
        }
        loadComponent();
    }
    private void initArrayList() {
        if (StringRelations == null) StringRelations = new ArrayList<>();
        StringRelations.clear();
        StringRelations.add("爸爸");
        StringRelations.add("妈妈");
        StringRelations.add("爷爷");
        StringRelations.add("奶奶");
        StringRelations.add("叔叔");


    }

    private void loadComponent() {
        relation_pv.setData(StringRelations);
        relation_pv.setSelected(0);
        excuteScroll();
    }
    private void excuteScroll() {
        relation_pv.setCanScroll(relationSize > 1);

    }

    private void addListener() {
        relation_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                ToastTools.showShort(context,text);
            }
        });}

    public void setIsLoop(boolean isLoop) {
        this.relation_pv.setIsLoop(isLoop);

    }
}

