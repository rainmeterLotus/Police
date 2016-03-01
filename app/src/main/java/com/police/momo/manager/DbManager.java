package com.police.momo.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.police.momo.R;
import com.police.momo.bean.Question;
import com.police.momo.bean.QuestionParent;
import com.police.momo.util.MLog;
import com.police.momo.util.SharedPreferencesUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者： momo on 2015/10/6.
 * 邮箱：wangzhonglimomo@163.com
 */
public class DbManager {
    private final int BUFFER_SIZE = 1024;
    public static final String DB_NAME = "police.db";
    private String DBFile = "";
    private SQLiteDatabase database;
    private Context context;
    public static final String TAG = "DbManager";
    private AtomicInteger openCount;

    public static DbManager sInstance;
    public static int db_version = 5;  //更新数据库使用
    public static String db_version_key = "db_version";  //更新数据库使用

    public DbManager(Context context) {
        this.context = context;
        DBFile = new File(context.getFilesDir(), DB_NAME).getAbsolutePath();
        openCount = new AtomicInteger();
    }

    public static DbManager sharedInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DbManager(context.getApplicationContext());
        }
        return sInstance;
    }

    public SQLiteDatabase openDatabase() {
        openCount.getAndIncrement();
        if (database == null) {
            openDatabase(DBFile);
        }
        return database;
    }

    public void closeDatabase() {
        if (openCount.decrementAndGet() == 0) {
            if (database != null) {
                database.close();
            }
            database = null;
        }
    }

    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    /**
     * @param dbfile
     * @return
     */
    private SQLiteDatabase openDatabase(String dbfile) {
        try {
            File file = new File(dbfile);
            int local_db_verson = SharedPreferencesUtils.getSpParam(context, db_version_key, 0);
            if (local_db_verson < db_version) {
                MLog.e("CityDBManager", "更新了数据库" + db_version);
                SharedPreferencesUtils.setSpParam(context, db_version_key, db_version);
                InputStream is = context.getResources().openRawResource(R.raw.police_db);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                    fos.flush();
                }
                fos.close();
                is.close();
            }

            if (database != null) {
                database.close();
            }
            database = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
            return database;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SQLiteDatabase.openOrCreateDatabase("", null);
    }

    /**
     * 获取所有的问题列表
     *
     * @return
     */
    public List<Question> getQuestions() {
        String sql = "select * from question";
        return rawQuery(sql);
    }

    /**
     * 根据type_cat获取所有的问题列表
     *
     * @return
     */
    public List<Question> getQuestionsByTypeCat(String type_cat) {
        String sql = "select * from question where type_cat = " + type_cat;
        return rawQuery(sql);
    }

    /**
     * 根据parent_type_cat获取所有子类型
     *
     * @return
     */
    public List<QuestionParent> getTypeCatByParentTypeCat(String parentType_cat) {
        String sql = "select type, type_cat from question where parent_type_cat =  "
                + parentType_cat + " group by type_cat";
        List<QuestionParent> lists = new ArrayList<QuestionParent>();
        try {
            Cursor cursor = getDatabase().rawQuery(sql, null);
            while (cursor.moveToNext()) {
                QuestionParent parent = new QuestionParent();
                parent.setName(cursor.getString(cursor.getColumnIndex("type")));
                parent.setCatId(cursor.getString(cursor.getColumnIndex("type_cat")));
                lists.add(parent);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lists;
    }

    /**
     * 获取所有的父类type
     *
     * @return
     */
    public List<QuestionParent> getAllParentTypeCat() {
        String sql = "select parent_name,parent_type_cat from question group by parent_type_cat";
        MLog.i(TAG, sql);
        List<QuestionParent> lists = new ArrayList<QuestionParent>();
        try {
            Cursor cursor = getDatabase().rawQuery(sql, null);
            while (cursor.moveToNext()) {
                QuestionParent parent = new QuestionParent();
                parent.setName(cursor.getString(cursor.getColumnIndex("parent_name")));
                parent.setCatId(cursor.getString(cursor.getColumnIndex("parent_type_cat")));
                lists.add(parent);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lists;
    }

    /**
     * 获取所有的父类type,除了基本信息
     *
     * @return
     */
    public List<QuestionParent> getAllParentTypeCatEcxeptBasic() {
        String sql = "select parent_name,parent_type_cat from question  where parent_type_cat != " +
                "0 group by  parent_type_cat";
        MLog.i(TAG, sql);
        List<QuestionParent> lists = new ArrayList<QuestionParent>();
        try {
            Cursor cursor = getDatabase().rawQuery(sql, null);
            while (cursor.moveToNext()) {
                QuestionParent parent = new QuestionParent();
                parent.setName(cursor.getString(cursor.getColumnIndex("parent_name")));
                parent.setCatId(cursor.getString(cursor.getColumnIndex("parent_type_cat")));
                lists.add(parent);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lists;
    }

    /**
     * 根据 type 名字 获取 父类 parent_type_cat
     *
     * @return
     */
    public String getParentTypeCatByType(String type) {
        String sql = "select type_cat from question where type =  '" + type + "'  group by " +
                "type_cat";
        MLog.i(TAG, sql);
        String type_cat = "";
        try {
            Cursor cursor = getDatabase().rawQuery(sql, null);
            while (cursor.moveToNext()) {
                type_cat = cursor.getString(cursor.getColumnIndex("type_cat"));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type_cat;
    }

    /**
     * 根据SQL获取数据
     */
    private List<Question> rawQuery(String sql) {
        MLog.i(TAG, sql);
        List<Question> lists = new ArrayList<Question>();
        try {
            Cursor cursor = getDatabase().rawQuery(sql, null);
            while (cursor.moveToNext()) {
                Question question = new Question();
                question.setType(cursor.getString(cursor.getColumnIndex("type")));
                question.setNum(cursor.getInt(cursor.getColumnIndex("num")));
                question.setContent(cursor.getString(cursor.getColumnIndex("content")));
                question.setAnswer(cursor.getString(cursor.getColumnIndex("answer")));
                question.setUpdateTime(cursor.getString(cursor.getColumnIndex("update_time")));
                question.setType_cat(cursor.getInt(cursor.getColumnIndex("type_cat")));
                question.setDefaultAnswer(cursor.getString(cursor.getColumnIndex
                        ("answer_default")));
                question.setParentName(cursor.getString(cursor.getColumnIndex("parent_name")));
                question.setParentTypeId(cursor.getString(cursor.getColumnIndex
                        ("parent_type_cat")));
                lists.add(question);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lists;
    }

}
