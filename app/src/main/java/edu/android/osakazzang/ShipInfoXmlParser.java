package edu.android.osakazzang;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by STU on 2017-09-27.
 */

public class ShipInfoXmlParser {
    // xml 파일에서 관심있는 태그 이름들
    private static final String XML_TAG_PLYCAT = "plyCat"; // 선박 종류
    private static final String XML_TAG_CAT = "cat"; // 입 출항 구분
    private static final String XML_TAG_DEPARTDT = "departDt"; // 운항 날짜
    private static final String XML_TAG_SHIPID = "shipId"; // 배 아이디
    private static final String XML_TAG_SHIPNMKOR = "shipNmKor"; // 선박 명
    private static final String XML_TAG_DPTTM = "dptTm"; // 출발 시간
    private static final String XML_TAG_ARVNMKOR = "arvNmKor"; // 도착지 명
    private static final String XML_TAG_ARVDT = "arvDt"; // 도착 일자
    private static final String XML_TAG_ARVTM = "arvTm"; // 도착 시간
    private static final String XML_TAG_ITEM = "item";

    private List<ShipInfo> list = new ArrayList<ShipInfo>();
    private ShipInfo shipInfo;
    private String text;

    public List<ShipInfo> parser(String xml) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(xml));

        int event = parser.getEventType();
        while(event != XmlPullParser.END_DOCUMENT){
            String tagName = parser.getName();

            switch (event){
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if (tagName.equals(XML_TAG_ITEM)) {
                        shipInfo = new ShipInfo();
                    }
                    break;
                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;
                case XmlPullParser.END_TAG:
                    if(tagName.equals(XML_TAG_CAT)){
                        shipInfo.setCat(text);
                    } else if(tagName.equals(XML_TAG_DEPARTDT)){
                        shipInfo.setDepartDt(text);
                    } else if(tagName.equals(XML_TAG_SHIPID)){
                        shipInfo.setShipId(text);
                    } else if(tagName.equals(XML_TAG_SHIPNMKOR)){
                        shipInfo.setShipNmKor(text);
                    } else if(tagName.equals(XML_TAG_DPTTM)){
                        shipInfo.setDptTm(text);
                    } else if(tagName.equals(XML_TAG_ARVNMKOR)){
                        shipInfo.setArvnmKor(text);
                    } else if(tagName.equals(XML_TAG_ARVDT)){
                        shipInfo.setArvDt(text);
                    } else if(tagName.equals(XML_TAG_ARVTM)){
                        shipInfo.setArvTm(text);
                    } else if(tagName.equals(XML_TAG_ITEM)) {
                        list.add(shipInfo);

                    }
                    break;
            } // end switch
            event = parser.next();
        } // end while

        return list;

    } // end parser

} // end ShipInfoXmlParser
