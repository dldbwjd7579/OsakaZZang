package edu.android.osakazzang;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static edu.android.osakazzang.Osaka.OsakaScheduleEntry.*;


/**
 * Created by SUT on 2017-10-11.
 */

public class OsakaDbHepler extends SQLiteOpenHelper{
    public static final String TAG = "edu.android";
    public static final String DB_NAME = "contact.db"; // 데이터베이스 이름
    public static final int DB_VERSION = 1; // 데이터베이스 버전

    private static final String SQL_CREAT_TABLE = // SQL문장 생성 & 명령어
            " create table " + TABLE_NAME
            + "("
            +_ID + " integer primary key autoincrement, "
            + COL_Osaka_SIGHTNAME + " text not null, "
            + COL_Osaka_SIGHTADDRESS + " text not null, "
            + COL_Osaka_SIGHTPHONE + " text not null, "
            + COL_Osaka_HOMEAPGE + " text not null, "
            + COL_Osaka_SIGHTOPEN + " integer, "
            + COL_Osaka_SIGHTCLOSE + " integer, "
            + COL_Osaka_SIGHTLAT + " real not null, "
            + COL_Osaka_SIGHTLNG + " real not null, "
            + COL_Osaka_SIGHTDATE + " integer DEFAULT (datetime('now','localtime'))"
            + ")";

    private static final String SQL_DROP_TABLE = // Table을 삭제한다는 명령어
            "drop table if exits " + TABLE_NAME;


    public OsakaDbHepler(Context context) { // 메인 액티비티 주소면
        super(context, DB_NAME, null, DB_VERSION); // null: 커서 팩토리 사용하지 않음
    }

    // 데이터베이스 없어서 호출되는 메소드
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "Helper::onCreate() 호출");
        // SQLiteDatabase는 메소드를 사용하기위한 클래스이므로 그냥 호출만하면된다
        // 테이블이 생성되지않는 경우에 테이블 생성
        try {
            sqLiteDatabase.execSQL(SQL_CREAT_TABLE);
            Log.i(TAG, "테이블 생성 성공");

        }catch (Exception e){
            Log.i(TAG, "테이블 생성 실패" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.i(TAG, "Helper::onUpgrade()호출 " );

    }
    @Override
    public void onOpen(SQLiteDatabase db) { //DB존재여부를 검사
        Log.i(TAG, "Helper::onOpen() 호출");
        super.onOpen(db);
    }
    // mainActvity 주소값을 전달해서

    public long insert (Osaka o) {
        // EditText의 상품 이름, 가격,수량을 읽음
        Log.i(TAG, "Helper::insert() 호출");
        // 직접가져올수 없으므로 insert하기위해
        // SQLiteDatabase 객체를 얻어옴
        SQLiteDatabase db = getWritableDatabase();
        Log.i(TAG, "Get DB : " + db.toString()); //오픈된 데이터베이스를 여기로 넘김

        // 테이블에 insert할 데이터를 contentValues 객체에 저장
        ContentValues values = new ContentValues(); // ContentValues: insert, update 등 기능
        values.put(COL_Osaka_SIGHTNAME, o.getSIGHTNAME()); // 모델클래스에서 정의한 컬럼을 가져옴
        values.put(COL_Osaka_SIGHTADDRESS, o.getSIGHTADDRESS());
        values.put(COL_Osaka_SIGHTPHONE, o.getSIGHTPHONE());
        values.put(COL_Osaka_HOMEAPGE, o.getHOMEPAGE());
        values.put(COL_Osaka_SIGHTOPEN, o.getSIGHTOPEN());
        values.put(COL_Osaka_SIGHTCLOSE, o.getSIGHTCLOSE());
        values.put(COL_Osaka_SIGHTLAT, o.getSIGHTLAT());
        values.put(COL_Osaka_SIGHTLNG, o.getSIGHTLNG());

        long result = db.insert(TABLE_NAME, null, values);
        Log.i(TAG, "insert result = " + result);

        db.close();

        return result;

    }

    public List<Osaka> select(){
        List<Osaka> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();

        for(int i = 0; i < cursor.getCount(); i++){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            String phone = cursor.getString(3);
            String home = cursor.getString(4);
            int open = cursor.getInt(5);
            int close = cursor.getInt(6);
            double lat =cursor.getDouble(7);
            double lng = cursor.getDouble(8);
            long date = cursor.getLong(9);

            Osaka osaka = new Osaka(id, name, address, phone, home, open, close, lat, lng, date);
            list.add(osaka);

            cursor.moveToNext();
        }

        cursor.close();

        return list;
    }

    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(TABLE_NAME, null, null);
    }

}
