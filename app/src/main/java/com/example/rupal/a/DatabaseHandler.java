package com.example.rupal.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.rupal.a.utils.UserScore;

/**
 * Created by Rupal on 9/24/2017.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "qa";

    // Quote table name
    private static final String TABLE_QUOTE = "quote";
    // Table Quote Columns names
    private static final String QUOTE_NAME = "quote_desc";
    private static final String QUOTE_ID = "quote_id";

    // Category table name
    private static final String TABLE_CATEGORY = "category";
    // Table Category Columns names
    private static final String CATEGORY_NAME = "category_name";

    // Table Question of All Category
    private static final String QUESTION_ID = "question_id";
    private static final String QUESTION = "question";
    private static final String OPTION1 = "option1";
    private static final String OPTION2 = "option2";
    private static final String OPTION3 = "option3";
    private static final String OPTION4 = "option4";
    private static final String ANSWER = "answer";

    // Category table name
    private static final String TABLE_JOB = "job";
    // Table Category Columns names
    private static final String JOB_ID = "job_id";
    private static final String JOB_NAME = "job_name";
    private static final String PLACE = "place";
    private static final String SALARY = "salary";
    private static final String QUALIFICATION = "qualification";
    private static final String LAST_DATE = "last_date";

    // Category table name
    private static final String TABLE_SCORE = "level_score";
    // Table Category Columns names
    private static final String SCORE_ID = "score_id";
    private static final String LEVEL_PASSED = "level_passed";
    private static final String LEVEL_NUMBER = "level_number";
    private static final String SCORE = "score";
    private static final String LOAD_INDEX = "next_load";
    private static final String CATEGORY = "category";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Quote Table
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_QUOTE + "("
                + QUOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + QUOTE_NAME + " TEXT"
                +")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        System.out.println("DD______________  Create Table");

        // Category Table
        CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
                + CATEGORY_NAME + " TEXT"
                +")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        // Job Table
        CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_JOB + "("
                + JOB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + JOB_NAME + " TEXT"
                + PLACE + " TEXT"
                + SALARY + " TEXT"
                + QUALIFICATION + " TEXT"
                + LAST_DATE + " TEXT"
                +")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        // Score Table
        CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SCORE + "("
                + SCORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SCORE + " TEXT,"
                + CATEGORY + " TEXT,"
                + LEVEL_NUMBER + " TEXT"
                +")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        System.out.println("Table Created");
    }

    // User Score

    // Getting Contacts Count
    public int getLevelCount(String cat) {

        int count = 0;
        try {

            String countQuery = "SELECT  * FROM " + TABLE_SCORE + " WHERE "+CATEGORY  +"="+ cat;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            count = cursor.getCount();


            cursor.close();
            // return count

        } catch(Exception e) {

        }
        return count;
    }

    public void addLevelScore(UserScore score) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SCORE, score.getScore());
        values.put(CATEGORY, score.getCategory());
        values.put(LEVEL_NUMBER, score.getLevel_number());
        db.insert(TABLE_SCORE, null, values);
        db.close(); // Closing database connection
    }

    public void updateLevelScore(UserScore score) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SCORE, score.getScore());
        contentValues.put(CATEGORY, score.getCategory());
        contentValues.put(LEVEL_NUMBER, score.getLevel_number());

//        int id = db.update(TABLE_SCORE,
//                contentValues,
//                CATEGORY + " = ? AND " + LEVEL_NUMBER + " = ?",
//                new String[]{score.getCategory(), "'" + score.getLevel_number() +"'"});

        String s = "category = '"+score.getCategory()+"' and level_number= "+score.getLevel_number();
        int id=db.update(TABLE_SCORE,contentValues,s,null);

        db.close(); // Closing database connection
    }

    public ArrayList<UserScore> getLevelScore(String cat) {

        String countQuery = "SELECT * FROM level_score WHERE category = " + "'"+cat+"'"  ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        String s[] = cursor.getColumnNames();

        ArrayList<UserScore> listUserScore = new ArrayList<UserScore>();

        if (cursor.moveToFirst()) {
            do {
                UserScore userScore = new UserScore();
                userScore.setScore(cursor.getInt(1));
                userScore.setCategory(cursor.getString(2));
                userScore.setLevel_number(cursor.getInt(3));
                listUserScore.add(userScore);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return  listUserScore;
    }

    public boolean getLevelScoreAlreadyExist(String cat, int level_number) {

        String countQuery = "SELECT * FROM level_score WHERE " + CATEGORY + " = "+ "'"+cat+"' AND "+ LEVEL_NUMBER + " = '" +level_number +"'" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if(cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);

        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addQuotes(Quote quote) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QUOTE_NAME, quote.getQuoteDesc()); // Contact Name
        // Inserting Row

        values.put(QUOTE_ID, quote.getQuoteId());
        db.insert(TABLE_QUOTE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public String getQuote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_QUOTE, new String[] { QUOTE_ID,
                        QUOTE_NAME}, QUOTE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String s = cursor.getString(1);
        cursor.close();

        return s;
    }


    // Getting contacts Count
    public int getQuotesCount() {

        String countQuery = "SELECT  * FROM " + TABLE_QUOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

//    // Getting All Contacts
//    public List<Quote> getAllContacts() {
//
//        List<Quote> contactList = new ArrayList<Quote>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_QUOTE;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//
//                System.out.println("A::: "+cursor.getString(0) + "B::: " + cursor.getString(1));
////                Quote contact = new Quote();
////                contact.setID(Integer.parseInt(cursor.getString(0)));
////                contact.setName(cursor.getString(1));
////                contact.setPhoneNumber(cursor.getString(2));
////                // Adding contact to list
////                contactList.add(contact);
//            } while (cursor.moveToNext());
//        }
//
//        // return contact list
//        return contactList;
//    }

    public void resetQuotesData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_QUOTE, null, null);
    }

    // Adding new contact
    public void addCategory(String categoryName) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, categoryName); // Contact Name
        // Inserting Row
        db.insert(TABLE_CATEGORY, null, values);
        db.close(); // Closing database connection

    }

    // Getting Contacts Count
    public int getCategoryCount() {

        String countQuery = "SELECT  * FROM " + TABLE_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    // Getting All Category
    public ArrayList<String> getCategory() {

//        List<Category> categoryList = new ArrayList<Category>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

        ArrayList<String> categoryList = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Category category = new Category();
                category.setCategoryName(cursor.getString(0));

                // Adding contact to list
                categoryList.add(cursor.getString(0));

                System.out.println("Retriving Cat" + cursor.getString(0));

            } while (cursor.moveToNext());
        }

        // return contact list
        return categoryList;
    }

    public void resetCategoryData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, null, null);
    }

    // Getting Contacts Count
    public int getQuestionCount(String tableName) {

        String countQuery = "SELECT  * FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public void resetJobData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, null, null);
    }

    // Adding new contact
    public void addJob(Job job) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(JOB_ID, job.getJob_id()); // JOB ID
        values.put(JOB_NAME, job.getJob_name()); // JOB NAME
        values.put(PLACE, job.getPlace()); // PLACE
        values.put(SALARY, job.getSalary()); // SALARY
        values.put(QUESTION, job.getQualification()); // QUALIFICATION

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        //Date today = job.getLast_date();
//        String last_date = df.format(job.getLast_date());
        values.put(LAST_DATE, job.getLast_date()); // LAST DATE

        db.insert(TABLE_JOB, null, values);
        db.close(); // Closing database connection
    }


        public List<Job> getJob() {

        //List<Quote> contactList = new ArrayList<Quote>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_JOB;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                System.out.println("id "+cursor.getInt(0) + "\nB::: " + cursor.getString(1)
                        + "\nname " + cursor.getString(2)
                        + "\nplace " + cursor.getString(3)
                        + "\nquali " + cursor.getString(4));
//                Quote contact = new Quote();
//                contact.setID(Integer.parseInt(cursor.getString(0)));
//                contact.setName(cursor.getString(1));
//                contact.setPhoneNumber(cursor.getString(2));
//                // Adding contact to list
//                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return null;
    }
}