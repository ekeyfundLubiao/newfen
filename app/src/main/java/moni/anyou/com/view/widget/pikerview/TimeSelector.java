package moni.anyou.com.view.widget.pikerview;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
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
import moni.anyou.com.view.widget.pikerview.Utils.DateUtil;
import moni.anyou.com.view.widget.pikerview.Utils.ScreenUtil;
import moni.anyou.com.view.widget.pikerview.Utils.TextUtil;
import moni.anyou.com.view.widget.pikerview.view.PickerView;


public class TimeSelector {

    public interface ResultHandler {
        void handle(String time);
    }

    public enum SCROLLTYPE {

        HOUR(1),
        MINUTE(2);

        private SCROLLTYPE(int value) {
            this.value = value;
        }

        public int value;

    }

    public enum MODE {

        YMD(1),
        YMDHM(2),
        HM(3);


        private MODE(int value) {
            this.value = value;
        }

        public int value;

    }


    private int scrollUnits = SCROLLTYPE.HOUR.value + SCROLLTYPE.MINUTE.value;
    private ResultHandler handler;
    private Context context;

    private final String FORMAT_STR = "yyyy-MM-dd";
    private final String FORMAT_HM = "HH:mm";
    private Dialog seletorDialog;
    private PickerView year_pv;
    private PickerView month_pv;
    private PickerView day_pv;

    private final int MAXMINUTE = 59;
    private int MAXHOUR = 23;
    private final int MINMINUTE = 0;
    private int MINHOUR = 0;
    private final int MAXMONTH = 12;

    private ArrayList<String> year, month, day, hour, minute;
    private int startYear, startMonth, startDay, endYear, endMonth, endDay;
    private boolean spanYear, spanMon, spanDay;
    private Calendar selectedCalender = Calendar.getInstance();
    private final long ANIMATORDELAY = 200L;
    private final long CHANGEDELAY = 90L;
    private String workStart_str;
    private String workEnd_str;
    private Calendar startCalendar;
    private Calendar endCalendar;
    private Calendar currentCalender;
    private Button tv_cancle;
    private Button tv_select;


    public TimeSelector(Context context, ResultHandler resultHandler, String startDate, String endDate) {
        this.context = context;
        this.handler = resultHandler;
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        startCalendar.setTime(DateUtil.parse(startDate, FORMAT_STR));
        endCalendar.setTime(DateUtil.parse(endDate, FORMAT_STR));
        currentCalender = Calendar.getInstance();
        initDialog();
        initView();
        setIsLoop(true);
    }


    public TimeSelector(Context context, ResultHandler resultHandler, String startDate, String endDate, String workStartTime, String workEndTime) {
        this(context, resultHandler, startDate, endDate);
        this.workStart_str = workStartTime;
        this.workEnd_str = workEndTime;
        setIsLoop(true);
    }

    public void show() {
        if (startCalendar.getTime().getTime() >= endCalendar.getTime().getTime()) {
            Toast.makeText(context, "start>end", Toast.LENGTH_LONG).show();
            return;
        }

        if (!excuteWorkTime()) return;
        initParameter();
        initTimer();
        addListener();
        seletorDialog.show();


    }

    private void initDialog() {
        if (seletorDialog == null) {
            seletorDialog = new Dialog(context, R.style.time_dialog);
            seletorDialog.setCancelable(false);
            seletorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            seletorDialog.setContentView(R.layout.dialog_selector);
            Window window = seletorDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams lp = window.getAttributes();
            int width = ScreenUtil.getInstance(context).getScreenWidth();
            lp.width = width;
            window.setAttributes(lp);
        }
    }

    private void initView() {
        year_pv = (PickerView) seletorDialog.findViewById(R.id.year_pv);
        month_pv = (PickerView) seletorDialog.findViewById(R.id.month_pv);
        day_pv = (PickerView) seletorDialog.findViewById(R.id.day_pv);
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
                handler.handle(DateUtil.format(selectedCalender.getTime(), FORMAT_STR));
                seletorDialog.dismiss();
            }
        });

    }

    private void initParameter() {
        startYear = startCalendar.get(Calendar.YEAR);
        startMonth = startCalendar.get(Calendar.MONTH) + 1;
        startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        endYear = endCalendar.get(Calendar.YEAR);
        endMonth = endCalendar.get(Calendar.MONTH) + 1;
        endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
        spanYear = startYear != endYear;
        spanMon = (!spanYear) && (startMonth != endMonth);
        spanDay = (!spanMon) && (startDay != endDay);
        selectedCalender.setTime(startCalendar.getTime());

    }

    private void initTimer() {
        initArrayList();

        if (spanYear) {
            for (int i = startYear; i <= endYear; i++) {
                year.add(String.valueOf(i) + "年");
            }
            for (int i = startMonth; i <= MAXMONTH; i++) {
                month.add(fomatTimeUnit(i) + "月");
            }
            for (int i = startDay; i <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(fomatTimeUnit(i) + "日");
            }


        } else if (spanMon) {
            year.add(String.valueOf(startYear));
            for (int i = startMonth; i <= endMonth; i++) {
                month.add(fomatTimeUnit(i) + "月");
            }
            for (int i = startDay; i <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(fomatTimeUnit(i) + "日");
            }

        } else if (spanDay) {
            year.add(String.valueOf(startYear));
            month.add(fomatTimeUnit(startMonth));
            for (int i = startDay; i <= endDay; i++) {
                day.add(fomatTimeUnit(i) + "日");
            }
        }

        loadComponent();

    }

    private boolean excuteWorkTime() {
        boolean res = true;
        if (!TextUtil.isEmpty(workStart_str) && !TextUtil.isEmpty(workEnd_str)) {
            String[] start = workStart_str.split(":");
            String[] end = workEnd_str.split(":");
            Calendar workStartCalendar = Calendar.getInstance();
            Calendar workEndCalendar = Calendar.getInstance();
            workStartCalendar.setTime(startCalendar.getTime());
            workEndCalendar.setTime(endCalendar.getTime());
            Calendar startTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();
            Calendar startWorkTime = Calendar.getInstance();
            Calendar endWorkTime = Calendar.getInstance();

            startTime.set(Calendar.HOUR_OF_DAY, startCalendar.get(Calendar.HOUR_OF_DAY));
            endTime.set(Calendar.HOUR_OF_DAY, endCalendar.get(Calendar.HOUR_OF_DAY));
            startWorkTime.set(Calendar.HOUR_OF_DAY, workStartCalendar.get(Calendar.HOUR_OF_DAY));
            startWorkTime.set(Calendar.MINUTE, workStartCalendar.get(Calendar.MINUTE));
            endWorkTime.set(Calendar.HOUR_OF_DAY, workEndCalendar.get(Calendar.HOUR_OF_DAY));
            endWorkTime.set(Calendar.MINUTE, workEndCalendar.get(Calendar.MINUTE));


            if (startTime.getTime().getTime() == endTime.getTime().getTime() || (startWorkTime.getTime().getTime() < startTime.getTime().getTime() && endWorkTime.getTime().getTime() < startTime.getTime().getTime())) {
                Toast.makeText(context, "Wrong parames!", Toast.LENGTH_LONG).show();
                return false;
            }
            startCalendar.setTime(startCalendar.getTime().getTime() < workStartCalendar.getTime().getTime() ? workStartCalendar.getTime() : startCalendar.getTime());
            endCalendar.setTime(endCalendar.getTime().getTime() > workEndCalendar.getTime().getTime() ? workEndCalendar.getTime() : endCalendar.getTime());
            MINHOUR = workStartCalendar.get(Calendar.HOUR_OF_DAY);
            MAXHOUR = workEndCalendar.get(Calendar.HOUR_OF_DAY);

        }
        return res;


    }

    private String fomatTimeUnit(int unit) {
        return unit < 10 ? "0" + String.valueOf(unit) : String.valueOf(unit);
    }

    private void initArrayList() {
        if (year == null) year = new ArrayList<>();
        if (month == null) month = new ArrayList<>();
        if (day == null) day = new ArrayList<>();
        if (hour == null) hour = new ArrayList<>();
        if (minute == null) minute = new ArrayList<>();
        year.clear();
        month.clear();
        day.clear();
        hour.clear();
        minute.clear();
    }


    private void addListener() {
        year_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                text = text.replace("年", "").replace("月", "").replace("日", "");
                selectedCalender.set(Calendar.YEAR, Integer.parseInt(text));
                monthChange();


            }
        });
        month_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                text = text.replace("年", "").replace("月", "").replace("日", "");
                selectedCalender.set(Calendar.DAY_OF_MONTH, 1);
                selectedCalender.set(Calendar.MONTH, Integer.parseInt(text) - 1);
                dayChange();


            }
        });
        day_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                text = text.replace("年", "").replace("月", "").replace("日", "");
                selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(text));
            }
        });


    }

    int currentYear;
    int currentMonth;
    int currentDay;
    DateIndex dateindex;

    private void loadComponent() {
        currentYear = currentCalender.get(Calendar.YEAR);
        currentMonth = currentCalender.get(Calendar.MONTH);
        currentDay = currentCalender.get(Calendar.DATE);
        year_pv.setData(year);
        month_pv.setData(month);
        day_pv.setData(day);

        DateIndex dateindex = getindex(year, month, day);
        year_pv.setSelected(dateindex.yearI);
        month_pv.setSelected(dateindex.monthI);
        day_pv.setSelected(dateindex.dayI);
        excuteScroll();

    }

    private void excuteScroll() {
        year_pv.setCanScroll(year.size() > 1);
        month_pv.setCanScroll(month.size() > 1);
        day_pv.setCanScroll(day.size() > 1);
    }

    private void monthChange() {

        month.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        if (selectedYear == startYear) {
            for (int i = startMonth; i <= MAXMONTH; i++) {
                month.add(fomatTimeUnit(i) + "月");
            }
        } else if (selectedYear == endYear) {
            for (int i = 1; i <= endMonth; i++) {
                month.add(fomatTimeUnit(i) + "月");
            }
        } else {
            for (int i = 1; i <= MAXMONTH; i++) {
                month.add(fomatTimeUnit(i) + "月");
            }
        }
        selectedCalender.set(Calendar.MONTH, Integer.parseInt(month.get(0).replace("月", "")) - 1);
        month_pv.setData(month);
        month_pv.setSelected(0);
        excuteAnimator(ANIMATORDELAY, month_pv);

        month_pv.postDelayed(new Runnable() {
            @Override
            public void run() {
                dayChange();
            }
        }, CHANGEDELAY);

    }

    private void dayChange() {

        day.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
        if (selectedYear == startYear && selectedMonth == startMonth) {
            for (int i = startDay; i <= selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(fomatTimeUnit(i) + "日");
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth) {
            for (int i = 1; i <= endDay; i++) {
                day.add(fomatTimeUnit(i) + "日");
            }
        } else {
            for (int i = 1; i <= selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(fomatTimeUnit(i) + "日");
            }
        }
        selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day.get(0).replace("日", "")));
        day_pv.setData(day);
        day_pv.setSelected(0);
        excuteAnimator(ANIMATORDELAY, day_pv);
    }


    private void excuteAnimator(long ANIMATORDELAY, View view) {
//        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
//                0f, 1f);
//        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
//                1.3f, 1f);
//        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
//                1.3f, 1f);
//        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(ANIMATORDELAY).start();
    }


    public void setNextBtTip(String str) {
        tv_select.setText(str);
    }


    public int disScrollUnit(SCROLLTYPE... scrolltypes) {
        if (scrolltypes == null || scrolltypes.length == 0)
            scrollUnits = SCROLLTYPE.HOUR.value + SCROLLTYPE.MINUTE.value;
        for (SCROLLTYPE scrolltype : scrolltypes) {
            scrollUnits ^= scrolltype.value;
        }
        return scrollUnits;
    }


    public void setIsLoop(boolean isLoop) {
        this.year_pv.setIsLoop(isLoop);
        this.month_pv.setIsLoop(isLoop);
        this.day_pv.setIsLoop(isLoop);
    }

    class DateIndex {
        int yearI;
        int monthI;
        int dayI;

        public DateIndex(int yearI, int monthI, int dayI) {
            this.yearI = yearI;
            this.monthI = monthI;
            this.dayI = dayI;
        }
    }

    private static DateIndex staticVaule = null;

    private DateIndex getindex(ArrayList<String> Year, ArrayList Month, ArrayList Day) {
        if (staticVaule != null) {
            return staticVaule;
        } else {
            int yearSize = Year.size();
            int monthSize = Month.size();
            int daySize = Day.size();
            int yearI = 0;
            int monthI = 0;
            int dayI = 0;
            String strcurrentYear = "" + currentYear;
            String strcurrentMonth = currentMonth + 1 + "";
            String strcurrentDay = "" + currentDay;
            for (int i = 0; i < yearSize; i++) {

                if (Year.get(i).equals(strcurrentYear.trim())) {
                    yearI = i;
                    break;
                }
            }
            for (int i = 0; i < monthSize; i++) {
                Log.d("TAG", "month: " + month.get(i));
                if (month.get(i).equals(strcurrentMonth.trim())) {
                    monthI = i;
                    break;
                }
            }
            for (int i = 0; i < daySize; i++) {
                Log.d("TAG", "day: " + day.get(i));
                if (day.get(i).equals(strcurrentDay.trim())) {
                    dayI = i;
                    break;
                }
            }
            staticVaule = new DateIndex(yearI, monthI, dayI);
            return staticVaule;
        }
    }
}
