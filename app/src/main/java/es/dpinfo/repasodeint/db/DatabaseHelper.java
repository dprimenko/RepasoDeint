package es.dpinfo.repasodeint.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

import es.dpinfo.repasodeint.ShopApplication;

/**
 * Created by dprimenko on 5/03/17.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    protected static final int DATABASE_VERSION = 5;
    protected static final String DATABASE_NAME = "products.db";
    private AtomicInteger mOpenDatabase;
    private SQLiteDatabase mDatabase;

    private static volatile DatabaseHelper databaseHelper;

    public synchronized static DatabaseHelper getInstance() {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper();
        }

        return databaseHelper;
    }

    private DatabaseHelper() {
        super(ShopApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
        mOpenDatabase = new AtomicInteger();
    }

    public SQLiteDatabase openDatabase() {

        if (mOpenDatabase.incrementAndGet() == 1) {
            mDatabase = getWritableDatabase();
        }
        return mDatabase;
    }

    public void closeDatabase() {
        if (mOpenDatabase.decrementAndGet() == 0) {
            mDatabase.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.beginTransaction();
            db.execSQL(DatabaseContract.CategoryEntry.SQL_CREATE_TABLE);
            db.execSQL(DatabaseContract.ProductEntry.SQL_CREATE_TABLE);
            db.setTransactionSuccessful();
        } catch(SQLiteException e) {
            Log.e(DATABASE_NAME + " on create", e.getMessage());
        } finally {
            db.endTransaction();
            insertDemoData(db);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.beginTransaction();
            db.execSQL(DatabaseContract.CategoryEntry.SQL_DROP_TABLE);
            db.execSQL(DatabaseContract.ProductEntry.SQL_DROP_TABLE);
            db.setTransactionSuccessful();
        } catch(SQLiteException e) {
            Log.e(DATABASE_NAME + " on update", e.getMessage());
        } finally {
            db.endTransaction();
            onCreate(db);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA set foreign_keys=ON");
            }
        }
    }

    public void insertDemoData(SQLiteDatabase db) {

        try {
            db.beginTransaction();
            db.execSQL(String.format("INSERT INTO %s (%s) VALUES(\"normal\")", DatabaseContract.CategoryEntry.TABLE_NAME, DatabaseContract.CategoryEntry.COL_NAME));
            db.execSQL(String.format("INSERT INTO %s (%s) VALUES(\"alimento\")", DatabaseContract.CategoryEntry.TABLE_NAME, DatabaseContract.CategoryEntry.COL_NAME));

            db.execSQL(String.format("INSERT INTO %s (%s, %s, %s) VALUES(\"patata\", \"2017-03-04\", 2)",
                    DatabaseContract.ProductEntry.TABLE_NAME,
                    DatabaseContract.ProductEntry.COL_NAME,
                    DatabaseContract.ProductEntry.COL_DATE_EXPIRY,
                    DatabaseContract.ProductEntry.COL_IDCATEGORY));
            db.execSQL(String.format("INSERT INTO %s (%s, %s, %s) VALUES(\"lapiz\", null, 1)",
                    DatabaseContract.ProductEntry.TABLE_NAME,
                    DatabaseContract.ProductEntry.COL_NAME,
                    DatabaseContract.ProductEntry.COL_DATE_EXPIRY,
                    DatabaseContract.ProductEntry.COL_IDCATEGORY));
            db.execSQL(String.format("INSERT INTO %s (%s, %s, %s) VALUES(\"patatas fritas\", \"2017-03-02\", 2)",
                    DatabaseContract.ProductEntry.TABLE_NAME,
                    DatabaseContract.ProductEntry.COL_NAME,
                    DatabaseContract.ProductEntry.COL_DATE_EXPIRY,
                    DatabaseContract.ProductEntry.COL_IDCATEGORY));

            db.setTransactionSuccessful();
        } catch(SQLiteException e) {
            Log.e(DATABASE_NAME + " on demo", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }
}
