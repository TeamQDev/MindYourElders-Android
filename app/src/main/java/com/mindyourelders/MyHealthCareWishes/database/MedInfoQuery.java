package com.mindyourelders.MyHealthCareWishes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourelders.MyHealthCareWishes.model.MedInfo;

/**
 * Created by welcome on 9/24/2017.
 */

public class MedInfoQuery {

    Context context;
    static DBHelper dbHelper;

    public static final String TABLE_NAME = "MedInfo";

    public static final String COL_ID= "Id";
    public static final String COL_USERID= "UserId";
    public static final String COL_NOTE ="ConditionNote";
    public static final String COL_MOUTH_NOTE ="MouthNote";
    public static final String COL_EYE_GLASSES="Glasses";
    public static final String COL_EYE_LENSE="Lense";
    public static final String COL_TEETH_FALSE= "UpperMouth";
    public static final String COL_TEETH_IMPLANTS="LowerMouth";
    public static final String COL_TEETH_MOUTH="DryMouth";
    public static final String COL_HEARING_AIDES ="Aides";
    public static final String COL_BLOODTYPE="BloodType";
    public static final String COL_ORGANDONOR="OrganDonor";
    public static final String COL_SPEECH="Speech";
    public static final String COL_BLIND ="Blind";
    public static final String COL_AIDE_NOTE="Aide_Note";
    public static final String COL_VISION_NOTE="VisionNote";
    public static final String COL_DIET_NOTE="DietNote";

    public MedInfoQuery(Context context, DBHelper dbHelper) {
        this.context=context;
        this.dbHelper=dbHelper;
    }

    public static String createMedInfoTable() {
        String createTableQuery="create table  If Not Exists "+TABLE_NAME+"("+COL_ID+" INTEGER PRIMARY KEY, "+COL_USERID+" INTEGER, "+
              COL_EYE_GLASSES+" VARCHAR(20),"+COL_EYE_LENSE+" VARCHAR(20),"+
                COL_TEETH_FALSE+" VARCHAR(20),"+COL_TEETH_IMPLANTS+" VARCHAR(20),"+COL_HEARING_AIDES+" VARCHAR(20),"+
                COL_BLOODTYPE+" VARCHAR(20),"+COL_ORGANDONOR+" VARCHAR(20),"+COL_NOTE+" VARCHAR(20),"+
                COL_SPEECH+" VARCHAR(20),"+COL_BLIND+" VARCHAR(20),"+COL_AIDE_NOTE+" VARCHAR(20),"+
               COL_VISION_NOTE+" VARCHAR(20),"+COL_DIET_NOTE+" VARCHAR(20),"+
                COL_MOUTH_NOTE+" VARCHAR(20),"+COL_TEETH_MOUTH+" VARCHAR(20)"+
                ");";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery="DROP TABLE IF EXISTS "+TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean insertMedInfoData(int userid,String blood, String glass, String lense, String falses, String implants, String aid, String donor, String note, String mouth, String mouthnote, String visionnote, String aidenote, String dietnote, String blind, String speech) {
        boolean flag=false;
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_USERID,userid);
        cv.put(COL_NOTE,note);
        cv.put(COL_EYE_GLASSES,glass);
        cv.put(COL_EYE_LENSE,lense);
        cv.put(COL_TEETH_FALSE,falses);
        cv.put(COL_TEETH_IMPLANTS,implants);
        cv.put(COL_HEARING_AIDES,aid);
        cv.put(COL_BLOODTYPE,blood);
        cv.put(COL_ORGANDONOR,donor);
        cv.put(COL_MOUTH_NOTE,mouthnote);
        cv.put(COL_TEETH_MOUTH,mouth);
        cv.put(COL_SPEECH,speech);
        cv.put(COL_BLIND,blind);
        cv.put(COL_AIDE_NOTE,aidenote);
        cv.put(COL_VISION_NOTE,visionnote);
        cv.put(COL_DIET_NOTE,dietnote);

       Cursor c = MedInfoQuery.fetchOneRecordCursor(userid);
        if (c.moveToFirst()) {
            int rowid=db.update(TABLE_NAME,cv,COL_USERID+"="+userid,null);
            if (rowid==0)
            {
                flag=false;
            }
            else
            {
                flag=true;
            }

        }
        else{
            long rowid=db.insert(TABLE_NAME,null,cv);

            if (rowid==-1)
            {
                flag=false;
            }
            else
            {
                flag=true;
            }

        }




        return flag;
    }

    private static Cursor fetchOneRecordCursor(int userid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);
        return c;
    }

    public static MedInfo fetchOneRecord(int userid) {
        MedInfo medInfo=new MedInfo();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);

        if (c.moveToFirst()) {
            do {
                medInfo.setId(c.getInt(c.getColumnIndex(COL_ID)));
                medInfo.setUserId(c.getInt(c.getColumnIndex(COL_USERID)));
                medInfo.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                medInfo.setGlass(c.getString(c.getColumnIndex(COL_EYE_GLASSES)));
                medInfo.setLense(c.getString(c.getColumnIndex(COL_EYE_LENSE)));
                medInfo.setFalses(c.getString(c.getColumnIndex(COL_TEETH_FALSE)));
                medInfo.setImplants(c.getString(c.getColumnIndex(COL_TEETH_IMPLANTS)));
                medInfo.setAid(c.getString(c.getColumnIndex(COL_HEARING_AIDES)));
                medInfo.setBloodType(c.getString(c.getColumnIndex(COL_BLOODTYPE)));
                medInfo.setDonor(c.getString(c.getColumnIndex(COL_ORGANDONOR)));
                medInfo.setMouth(c.getString(c.getColumnIndex(COL_TEETH_MOUTH)));
                medInfo.setMouthnote(c.getString(c.getColumnIndex(COL_MOUTH_NOTE)));
                medInfo.setSpeech(c.getString(c.getColumnIndex(COL_SPEECH)));
                medInfo.setBlind(c.getString(c.getColumnIndex(COL_BLIND)));
                medInfo.setAideNote(c.getString(c.getColumnIndex(COL_AIDE_NOTE)));
                medInfo.setVisionNote(c.getString(c.getColumnIndex(COL_VISION_NOTE)));
                medInfo.setDietNote(c.getString(c.getColumnIndex(COL_DIET_NOTE)));

            } while (c.moveToNext());
        }
        
        return medInfo;
    }
}
