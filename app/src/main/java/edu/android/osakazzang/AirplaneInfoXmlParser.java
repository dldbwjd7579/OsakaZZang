package edu.android.osakazzang;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by STU on 2017-09-26.
 */

public class AirplaneInfoXmlParser {


        private static final String XML_TAG_ITEM = "item"; //데이터 시작
        private static final String XML_TAG_AIRLINE = "airlineKorean"; //항공사 이름
        private static final String XML_TAG_AIRPLANE = "internationalNum"; // 항공편 이름
        private static final String XML_TAG_AIRPORT = "airport"; //기준 공항
        private static final String XML_TAG_CITY = "city"; //운항 구간
        private static final String XML_TAG_INOUT = "internationalIoType"; //입국인지 출국인지 : IN / OUT
        private static final String XML_TAG_TIME = "internationalTime"; //계획 시간

        //전체 정보 저장할 리스트
        public List<AirplaneInfo> list = new ArrayList<AirplaneInfo>();
        //비행기편 한개 저장하는 객체
        private AirplaneInfo airplane;
        private String text;

        public List<AirplaneInfo> parse (String xml)
                throws XmlPullParserException, IOException {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));


            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                Log.i("logTag", "event: " + event);

                // xml 파일에서 태그 이름을 읽음
                String tagName = parser.getName();
                Log.i("logTag", "tag name : " + tagName);

                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        Log.i("logTag", "start_tag");
                        if (tagName.equals(XML_TAG_ITEM)) {
                            airplane = new AirplaneInfo();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        Log.i("logTag", "text : " + text);

                        break;
                    case XmlPullParser.END_TAG:
                        Log.i("logTag", "tag name : " + tagName);

                        if (tagName.equals(XML_TAG_AIRLINE)) {
                            airplane.setAirLine(text);
                        } else if (tagName.equals(XML_TAG_AIRPLANE)) {
                            airplane.setAirPlane(text);
                        } else if (tagName.equals(XML_TAG_AIRPORT)) {
                            airplane.setAirPort(text);
                        } else if (tagName.equals(XML_TAG_CITY)) {
                            airplane.setCity(text);
                        } else if (tagName.equals(XML_TAG_INOUT)) {
                            airplane.setInOut(text);
                        } else if (tagName.equals(XML_TAG_TIME)){
                            airplane.setTime(Integer.parseInt(text));
                        } else if(tagName.equals(XML_TAG_ITEM)){
                            list.add(airplane);
                        }
                        break;

                } // end switch
                event = parser.next();
                Log.i("logTag", "next: " + event);

            } // end while

            return list;
        }


}
