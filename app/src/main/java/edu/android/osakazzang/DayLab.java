package edu.android.osakazzang;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by SUT on 2017-09-22.
 */

    public class DayLab {

        private static final int[] RES_IDS = {
                R.drawable.n1,
                R.drawable.n2,
                R.drawable.n3,
                R.drawable.n4,
                R.drawable.n5,
                R.drawable.n1,
                R.drawable.n2,
                R.drawable.n3,
                R.drawable.n4,
                R.drawable.n5,
                R.drawable.n2,
                R.drawable.n3,
                R.drawable.n4,
        };

        private static DayLab instance;
        private List<Day> days;

        private DayLab() {
            days = new ArrayList<>();
            DummyData();
        } // end DayLab

        private void DummyData() {
            for (int i = 0; i < 10; i++) { // i: 도착날짜 - 출발날짜
                Day d = new Day(8, 20 + i, RES_IDS[i % RES_IDS.length]);
                days.add(d);
            }
            for (int i = 0; i < 10; i++) { // i: 도착날짜 - 출발날짜
                Day d = new Day(9, 1 + i, RES_IDS[i % RES_IDS.length]);
                days.add(d);
            }

        }// end Dummy Data()

        public  static DayLab getInstance() {
            if (instance == null){
                instance = new DayLab();
            }
            return  instance;
        }

        public List<Day> getDays() {
            return days;

        }

        public List<Day> make(Date startDate, Date endDate) {
            List<Day> list = new ArrayList<>();

            long start = startDate.getTime();
            long end = endDate.getTime();

            long between = end - start;
            int days = (int) (between / (24 * 60 * 60 * 1000) + 1);
            Log.i("tag", "days: " + days);

            if (between >= 0) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);
                long nextDay = start;
                for (int i = 0; i < days; i++) {
                    // FIXME: ArrayIndexOutOfBoundsException 가능성 있음
                    Day day = new Day(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), RES_IDS[i]);
                    list.add(day);

                    nextDay += 24 * 60 * 60 * 1000;
                    cal.setTime(new Date(nextDay));
                }
            }

            return list;
        }

        public List<Day> getListBetweenDays(int startMonth, int startDay, int endMonth, int endDay) {
            List<Day> list = new ArrayList<>();
            for (Day day : days) {
                if (startDay <= day.getMonth() && day.getMonth() <= endMonth
                        && startDay <= day.getDay() && day.getDay() <= endDay) {
                    list.add(day);
                }
            }

            return list;
        }
}
