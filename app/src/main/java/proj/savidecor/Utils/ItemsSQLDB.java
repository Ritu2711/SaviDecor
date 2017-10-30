package proj.savidecor.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemsSQLDB extends SQLiteOpenHelper {

    private static ItemsSQLDB mDB;

    private static final String DATABASE_NAME = "neon.db";
    public static final String MainCategory = "cAT";
    public static final String MainID = "cID";
    public static final String ParentID = "cAT";
    public static final String MainCategoryS = "cATs";
    public static final String MainIDS = "cIDs";
    private static final int DATABASE_VERSION = 1;

    public static synchronized ItemsSQLDB getInstance(Context context){
        if (mDB==null){
            mDB= new ItemsSQLDB(context.getApplicationContext());
        }
        return mDB;
    }

    private ItemsSQLDB(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table category (id integer primary key,cAT text,cID text)");
        sqLiteDatabase.execSQL("create table subcategory (id integer primary key,cAT text,cIDs text,cATs text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists category");
        sqLiteDatabase.execSQL("drop table if exists subcategory");
        onCreate(sqLiteDatabase);
    }

    public void insertall(String ccat, String iid){
        SQLiteDatabase sqdb=getWritableDatabase();
        sqdb.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(MainCategory, ccat);
            values.put(MainID, iid);
            sqdb.insert("category", null, values);
            sqdb.setTransactionSuccessful();
        }catch (Exception ee){ee.printStackTrace();}
        finally {
            sqdb.endTransaction();
        }
    }
    public void inse(String sectionID, String iids, String ccats){
        SQLiteDatabase sqdb=getWritableDatabase();
        sqdb.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(ParentID, sectionID);
            values.put(MainIDS, iids);
            values.put(MainCategoryS, ccats);
            sqdb.insert("subcategory", null, values);
            sqdb.setTransactionSuccessful();
        }catch (Exception ee){ee.printStackTrace();}
        finally {
            sqdb.endTransaction();
        }
    }
    public void delete() {
        SQLiteDatabase sqdb=getWritableDatabase();
        sqdb.beginTransaction();
        try {
            sqdb.delete("category", null, null);
            sqdb.setTransactionSuccessful();
        }catch (Exception ee){ee.printStackTrace();}
        finally {
            sqdb.endTransaction();
        }
    }

    public Cursor fetchall(){
        SQLiteDatabase sqdb=getReadableDatabase();

        return sqdb.query("category",new String[] {MainCategory,MainID},null,null,null,null,null);
    }

    public Cursor fetchS(int pagen){
        SQLiteDatabase sqdb=getReadableDatabase();
      return sqdb.rawQuery("select * from subcategory where cAT='"+pagen+"'",null);
    }

    public void deleteS(String pagen) {
        SQLiteDatabase sqdb=getWritableDatabase();
        sqdb.beginTransaction();
        try {
            sqdb.delete("subcategory",ParentID+"=?",new String[]{pagen});
            sqdb.setTransactionSuccessful();
        }catch (Exception ee){ee.printStackTrace();}
        finally {
            sqdb.endTransaction();
        }
    }
}
