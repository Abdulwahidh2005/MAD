package com.example.exp10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "StudentDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE students(" +
                "roll_no INTEGER PRIMARY KEY," +
                "name TEXT," +
                "dept TEXT," +
                "mobile TEXT," +
                "email TEXT," +
                "college TEXT," +
                "year TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS students");
        onCreate(db);
    }

    // INSERT
    public boolean insertStudent(String roll, String name, String dept,
                                 String mobile, String email,
                                 String college, String year) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("roll_no", roll);
        cv.put("name", name);
        cv.put("dept", dept);
        cv.put("mobile", mobile);
        cv.put("email", email);
        cv.put("college", college);
        cv.put("year", year);

        long result = db.insert("students", null, cv);
        return result != -1;
    }

    // READ
    public String getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM students", null);

        if (c.getCount() == 0) return "No Records";

        StringBuilder sb = new StringBuilder();
        while (c.moveToNext()) {
            sb.append("Roll: ").append(c.getInt(0)).append("\n");
            sb.append("Name: ").append(c.getString(1)).append("\n");
            sb.append("Dept: ").append(c.getString(2)).append("\n");
            sb.append("Mobile: ").append(c.getString(3)).append("\n");
            sb.append("Email: ").append(c.getString(4)).append("\n");
            sb.append("College: ").append(c.getString(5)).append("\n");
            sb.append("Year: ").append(c.getString(6)).append("\n\n");
        }
        return sb.toString();
    }

    // UPDATE
    public boolean updateStudent(String roll, String name, String dept,
                                 String mobile, String email,
                                 String college, String year) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", name);
        cv.put("dept", dept);
        cv.put("mobile", mobile);
        cv.put("email", email);
        cv.put("college", college);
        cv.put("year", year);

        int result = db.update("students", cv,
                "roll_no=?", new String[]{roll});

        return result > 0;
    }

    // DELETE
    public boolean deleteStudent(String roll) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("students",
                "roll_no=?", new String[]{roll});
        return result > 0;
    }
}
