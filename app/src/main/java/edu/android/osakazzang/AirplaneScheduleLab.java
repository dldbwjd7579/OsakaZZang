package edu.android.osakazzang;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by STU on 2017-09-25.
 */

public class AirplaneScheduleLab {
    private static AirplaneScheduleLab instance;
    private List<AirplaneSchedule> list;

    private AirplaneScheduleLab () {
        list = new ArrayList<>();
        makeDummyData();
    }

    private void makeDummyData() {
        for (int i = 0; i < 5; i++) {
            // 날짜정보를 시간정보로 변환해주는 메소드 getTime를 사용해서
            // 생성자 불러서 list 에 저장
            Calendar cal1 = new GregorianCalendar(2017, 8, 20, 10 + i, 00);
            Date departure = cal1.getTime();

            Calendar cal2 = new GregorianCalendar(2017, 8, 20, 12 + i, 00);
            Date arrival = cal2.getTime();

            AirplaneSchedule schedule = new AirplaneSchedule("ICN", "Narita", "KE100", departure, arrival);
            list.add(schedule);

        }

        for (int i = 0; i < 5; i++) {
            Calendar cal1 = new GregorianCalendar(2017, 8, 22, 10 + i, 00);
            Date departure = cal1.getTime();
            Calendar cal2 = new GregorianCalendar(2017, 8, 22, 12 + i, 00);
            Date arrival = cal2.getTime();
            AirplaneSchedule schedule = new AirplaneSchedule("ICN", "Nartita", "KE100", departure , arrival);
            list.add(schedule);
        }


        for (int i = 0; i < 5; i++) {
            Calendar cal1 = new GregorianCalendar(2017, 8, 22, 10 + i, 00);
            Date departure = cal1.getTime();
            Calendar cal2 = new GregorianCalendar(2017, 8, 22, 10 + i, 00);
            Date arrival = cal2.getTime();
            AirplaneSchedule schedule = new AirplaneSchedule("ICN", "Narita", "KE100", departure, arrival);
            list.add(schedule);
        }

    }

    public static AirplaneScheduleLab getInstance(){
        if(instance == null){
          instance = new AirplaneScheduleLab();

        }

        return instance;

    }

    public List<AirplaneSchedule> getList(){

        return list;
    }

    public List<AirplaneSchedule> getListByDepartDate(Date departDate){
        // 출발날짜와 출발시간을 뽑아내기 위해서 설정
       // Arraylist 생성자를 호출
        List<AirplaneSchedule> result = new ArrayList<>();

        for(AirplaneSchedule schedule : list) {
            // getInstance 라는 static 메소드를 호출하여 출발날짜정보를 가져옴
            Calendar scheduleDepartTime = Calendar.getInstance();
            // AirPlaneSchedule 에 있는 출발시간정보를 가져와서 세팅
            scheduleDepartTime.setTime(schedule.getDepartTime());

            // getInstance 라는 static 메소드를 호출하여 도착날짜정보를 가져옴
            Calendar searchDepartTime = Calendar.getInstance();
            // AirPlaneSchedule 에 있는 도착시간정보를 가져와서 세팅
            searchDepartTime.setTime(departDate);
            // setTime 이란 Calender 타입을 Date (시간) 타입으로 변환하기 위해 사용

            // 내가 선택한 년도 월일을 비교하기 위해 if 문 사용
            if (scheduleDepartTime.get(Calendar.YEAR)
                    == searchDepartTime.get(Calendar.YEAR)
                    && scheduleDepartTime.get(Calendar.MONTH)
                    == searchDepartTime.get(Calendar.MONTH)
                    && scheduleDepartTime.get(Calendar.DAY_OF_MONTH)
                    == searchDepartTime.get(Calendar.DAY_OF_MONTH)) {

                result.add(schedule);

            }

        }

        return result;



    }


}
