package com.example.socialapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class LoginData  extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LoginData.db";
    private static final String TABLE_PERSON = "person";
    private static final String COLUMN_PERSON_ID = "user_id";
    private static final String COLUMN_PERSON_NAME = "user_name";
    private static final String COLUMN_PERSON_EMAIL = "user_email";
    private static final String COLUMN_PERSON_PASSWORD = "user_password";
    private static final String COLUMN_PERSON_AGE = "user_age";
    private static final String COLUMN_PERSON_PHONE = "user_phone";
    private static final String COLUMN_PERSON_IMAGE = "user_image";
    private static final String DB_TABLE = "table_image";
    private static final String KEY_ID = "image_userid";
    private static final String KEY_IMAGE = "image_data";

    private String CREATE_PERSON_TABLE = "CREATE TABLE " + TABLE_PERSON + "("
            + COLUMN_PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PERSON_NAME + " TEXT,"
            + COLUMN_PERSON_EMAIL + " TEXT," + COLUMN_PERSON_PASSWORD + " TEXT," + COLUMN_PERSON_AGE + " TEXT," + COLUMN_PERSON_PHONE + " TEXT,"+ COLUMN_PERSON_IMAGE + " BLOB" + ")";
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + DB_TABLE + "("+
            KEY_ID + " INTEGER PRIMARY KEY," + KEY_IMAGE + " BLOB);";

    private String DROP_PERSON_TABLE = "DROP TABLE IF EXISTS " + TABLE_PERSON;
    private String DROP_TABLE_IMAGE = "DROP TABLE IF EXISTS " + DB_TABLE;
    public LoginData(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_PERSON_TABLE);
        db.execSQL(CREATE_TABLE_IMAGE);
    }

    @Override
    public  void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_PERSON_TABLE);
        db.execSQL(DROP_TABLE_IMAGE);
        onCreate(db);
    }
    public void addEntry(int id, byte[] image) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ID,    id);
        cv.put(KEY_IMAGE,   image);
        database.insert( DB_TABLE, null, cv );
    }
    public byte[] searchImage(int id){
        byte[] img = null;
        System.out.println("Entrou aqui1");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DB_TABLE + " where " + KEY_ID + "='" + id + "'" , null);
        if(cursor==null){
            return null;
        }
        if(cursor.moveToFirst()){
            System.out.println("Entrou aqui");
            if(cursor.getCount()>0) img = cursor.getBlob(cursor.getColumnIndex("image_data"));
        }
        return img;
    }
    public boolean existsImage(int id){
        System.out.println("Entrou aqui1");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DB_TABLE + " where " + KEY_ID + "='" + id + "'" , null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }
    public void addPerson(Person person){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PERSON_NAME, person.getName());
        values.put(COLUMN_PERSON_EMAIL, person.getEmail());
        values.put(COLUMN_PERSON_PASSWORD, person.getPassword());
        values.put(COLUMN_PERSON_AGE, person.getAge());
        values.put(COLUMN_PERSON_PHONE, person.getPhone());
        db.insert(TABLE_PERSON, null, values);
        db.close();
    }

    public boolean checkPerson(String email){
        String[] columns = {
                COLUMN_PERSON_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_PERSON_EMAIL + " = ?";
        String[] selectionArgs = { email };
        Cursor cursor = db.query(TABLE_PERSON,columns,selection,selectionArgs,null,null,null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    public boolean checkPerson(String email, String password){

        String[] columns = {
                COLUMN_PERSON_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_PERSON_EMAIL + " = ?" + " AND " + COLUMN_PERSON_PASSWORD + " =?";
        String[] selectionArgs = { email, password };
        Cursor cursor = db.query(TABLE_PERSON,columns,selection,selectionArgs,null,null,null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }
    public Person getPerson(String email, String password){
        Person p1 = new Person();
        String[] columns = {
                COLUMN_PERSON_ID,COLUMN_PERSON_NAME,COLUMN_PERSON_AGE,COLUMN_PERSON_PHONE
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_PERSON_EMAIL + " = ?" + " AND " + COLUMN_PERSON_PASSWORD + " =?";
        String[] selectionArgs = { email, password };
        Cursor cursor = db.query(TABLE_PERSON,columns,selection,selectionArgs,null,null,null);
        cursor.moveToFirst();
        p1.setId(cursor.getInt(cursor.getColumnIndex("user_id")));
        p1.setName(cursor.getString(cursor.getColumnIndex("user_name")));
        p1.setAge(cursor.getString(cursor.getColumnIndex("user_age")));
        p1.setPhone(cursor.getString(cursor.getColumnIndex("user_phone")));
        p1.setPassword(password);
        p1.setEmail(email);
        cursor.close();
        db.close();
        return p1;
    }
    public void updateDetails(Person details,int flag){
        Person p1 = new Person();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PERSON_NAME, details.getName());
        values.put(COLUMN_PERSON_PHONE, details.getPhone());
        values.put(COLUMN_PERSON_EMAIL, details.getEmail());
        values.put(COLUMN_PERSON_PASSWORD, details.getPassword());
        values.put(COLUMN_PERSON_AGE, details.getAge());
        if(flag==1){
            db.update(TABLE_PERSON, values, COLUMN_PERSON_ID + "='" + SaveInstant.getInstance().getId() + "'", null);
        }else{
        }
        db.close();
    }
    // Table Names


    // Table create statement


}
