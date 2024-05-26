package com.example.wheretogo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;



import androidx.annotation.Nullable;

public class DB_MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "BoolLibrary.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME ="my_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "site_name";
    private static final String COLUMN_CITY = "site_city";
    private static final String COLUMN_ADDRESS = "site_address";

    private static final String COLUMN_PID = "site_pid";
    private static final String COLUMN_INTRO = "site_intro";

    DB_MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context; // losing this line cost me a great deal of time to fix it;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // much attention needed to take care of the format
        String query =  "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_CITY + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_PID + " TEXT, " +
                COLUMN_INTRO + " TEXT);";
        db.execSQL(query);
        //TODO: COMPLETE DEFAULT SETTING;


        {
            // default_1
            String name_defalut = "南京大学汉口路校门";
            String city_default = "南京";
            String address_default = "南京大学(鼓楼校区)-汉口路南门";
            String PID_default = "09002500121709071035569301I";
            String intro_default = "来南京旅游，不能错过百年历史南京大学！ 南大（鼓楼校区 ）坐落于南京市中心 俨然有一种大隐隐于市的神秘感校区内古朴的建筑端庄厚重开和爬山虎、树木 相映成趣，岁月沉淀却不显沧桑 在校园里citywalk一下午感受独属于南京书香气息";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }
        {
            // Default 2:
            String name_defalut = "梧桐大道";
            String city_default = "南京";
            String address_default = "陵园路";
            String PID_default = "09002500121709060812110887H";
            String intro_default = "树是梧桐树，城是南京城 “总要去一趟南京吧，看看这满城的梧桐\".很多人问，不知道南京哪条路是梧桐大道？ 其实，并没有一条路叫梧桐大道 那些道路两侧遍植梧桐，都被我们称作“梧桐大道” 春日南京最美的梧桐大道，莫过于这些地方";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }
        {
            // Default 3:
            String name_defalut = "夫子庙";
            String city_default = "南京";
            String address_default = "夫子庙";
            String PID_default = "0900250012221013094721969AX";
            String intro_default = "南京，这座拥有着六朝古都历史的城市，每 一个角落都蕴含着浓厚的历史文化底蕴。而位于南 京城南的夫子庙，更是这座城市的文化灵魂。";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }
        {
            // Default 4:
            String name_defalut = "玄武湖";
            String city_default = "南京";
            String address_default = "玄武湖";
            String PID_default = "09002500122002220828517715K";
            String intro_default = "玄武湖是江南三大名湖之一，历史上的古玄武湖周边是当时南京最适合人类居住的空间，存有北 月阳营、锁金村、安怀村等古文化遗址。玄武湖也 是六朝时期的皇家园林湖泊和操练水军的演兵场、 月代时期保存黄册的国家档案馆所在地，被誉为 “金陵明珠”。";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }
        {
            // Default 5:
            String name_defalut = "紫峰大厦";
            String city_default = "南京";
            String address_default = "紫峰大厦";
            String PID_default = "0900250012221008122348381AD";
            String intro_default = "南京紫峰大厦（Zifeng Tower），正式名称为南京国 际金融中心，是位于中国江苏省南京市的一座摩天 大楼。它曾是中国第六高楼以及江苏第一高楼，建 成时也是世界上排名靠前的超高建筑之一。紫峰大 厦集办公、商业、文化娱乐于一体，是南京城市的 地标性建筑，并成为游客了解现代南京城市发展的 窗口。";
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            db.insert(TABLE_NAME, null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addBook(String name, String city, String address, String PID, String intro){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME,name);
        // THIS IS INVERSE
        cv.put(COLUMN_ADDRESS,city);
        cv.put(COLUMN_CITY,address);
        cv.put(COLUMN_PID,PID);
        cv.put(COLUMN_INTRO,intro);

        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "失败了...", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "添加成功! :D", Toast.LENGTH_SHORT).show();
        }
    }

    // called in mainActivity;
    Cursor readAllData(){

        String query = "SELECT * FROM "+ TABLE_NAME; // once again LETHAL mistake
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);// this line is wrong!
        }
        return cursor;
    }

    void updateData(String row_id, String name, String city, String address, String PID, String intro){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_CITY,city);
        cv.put(COLUMN_ADDRESS,address);
        cv.put(COLUMN_PID,PID);
        cv.put(COLUMN_INTRO,intro);

        long result = db.update(TABLE_NAME,cv,"_id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context,"更新失败...QAQ",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"更新成功!",Toast.LENGTH_SHORT).show();
        }
    }
    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getReadableDatabase();
        long result = db.delete(TABLE_NAME,"_id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context,"删除失败...T.T",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"删除成功!",Toast.LENGTH_SHORT).show();
        }
    }


    // cooperate with mainActivity storeSelectedDataInArray (Test usage)
    Cursor readSelectedData(String siteName){

        String query = "SELECT * FROM "+ TABLE_NAME +" WHERE site_name GLOB \'*" + siteName +"*\'"; // once again LETHAL mistake
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);// this line is wrong!
        }
        return cursor;
    }

    Cursor getPotentialSiteName(String siteName){
        String query = "SELECT * FROM "+ TABLE_NAME +" WHERE site_name GLOB \'*" + siteName +"*\'"; // once again LETHAL mistake
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);// this line is wrong!
        }
        return cursor;
    }

    String getSelectedPid(String siteName){

        Cursor cursor = readSelectedData(siteName);
        String rtn = null;

        if(cursor.getCount()==0){
            Toast.makeText(context,"未查询到...",Toast.LENGTH_SHORT).show();
            return rtn;
        }else if(cursor.getCount() == 1){
            while(cursor.moveToNext()){
                rtn = cursor.getString(4);
            }
            return rtn;
        }else{
            Toast.makeText(context,"查询到多个...",Toast.LENGTH_SHORT).show();
            return rtn;
        }
    }

    String getSelectedIntro(String siteName){
        Cursor cursor = readSelectedData(siteName);
        String rtn = null;
        if(cursor.getCount()==0){
            Toast.makeText(context,"未查询到...",Toast.LENGTH_SHORT).show();
            return rtn;
        }else if(cursor.getCount() == 1){
            while(cursor.moveToNext()){
                rtn = cursor.getString(5);
            }
            return rtn;
        }else{
            Toast.makeText(context,"查询到多个...",Toast.LENGTH_SHORT).show();
            return rtn;
        }
    }
    // plz ignore this one... this one is for text usage;
    void getSelectedDataTest(){
        {
            //Default test for getSelectedPid() and getSelectedIntro();
            String name_defalut = "紫峰小厦";
            String city_default = "南京";
            String address_default = "紫峰大厦";

            String PID_default = getSelectedPid("紫峰大厦");
            String intro_default = getSelectedIntro("紫峰大厦");

            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name_defalut);
            cv.put(COLUMN_CITY, city_default);
            cv.put(COLUMN_ADDRESS, address_default);
            cv.put(COLUMN_PID, PID_default);
            cv.put(COLUMN_INTRO, intro_default);
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLE_NAME, null, cv);
        }
    }



}
/*




 */
