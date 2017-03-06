package es.dpinfo.repasodeint.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import es.dpinfo.repasodeint.db.DatabaseContract;
import es.dpinfo.repasodeint.db.DatabaseHelper;

/**
 * Created by dprimenko on 5/03/17.
 */
public class ShopProvider extends ContentProvider {

    private static final int CATEGORY = 1;
    private static final int CATEGORY_ID = 2;
    private static final int PRODUCT = 3;
    private static final int PRODUCT_ID = 4;

    private static final UriMatcher uriMatcher;
    private SQLiteDatabase db;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ProviderContract.AUTHORITY, ProviderContract.Category.CONTENT_PATH, CATEGORY);
        uriMatcher.addURI(ProviderContract.AUTHORITY, ProviderContract.Category.CONTENT_PATH + "/#", CATEGORY_ID);
        uriMatcher.addURI(ProviderContract.AUTHORITY, ProviderContract.Product.CONTENT_PATH, PRODUCT);
        uriMatcher.addURI(ProviderContract.AUTHORITY, ProviderContract.Product.CONTENT_PATH + "/#", PRODUCT_ID);
    }



    @Override
    public boolean onCreate() {
        db = DatabaseHelper.getInstance().openDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor = null;

        switch (uriMatcher.match(uri)) {
            case PRODUCT:
                cursor = db.query(DatabaseContract.ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PRODUCT_ID:
                selection = "_id = ?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                cursor = db.query(DatabaseContract.ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CATEGORY:
                cursor = db.query(DatabaseContract.CategoryEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CATEGORY_ID:
                selection = "_id = ?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                cursor = db.query(DatabaseContract.CategoryEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case UriMatcher.NO_MATCH:
                throw new IllegalArgumentException("URI invalid");
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int resultInsert = -1;
        Uri result = uri;

        switch (uriMatcher.match(uri)) {
            case PRODUCT:
                resultInsert = (int)db.insert(DatabaseContract.ProductEntry.TABLE_NAME, null, values);
                break;
            case CATEGORY:
                resultInsert = (int)db.insert(DatabaseContract.CategoryEntry.TABLE_NAME, null, values);
                break;
        }

        result = ContentUris.withAppendedId(uri, resultInsert);

        if (resultInsert != -1) {
            getContext().getContentResolver().notifyChange(result,null);
        } else {
            throw new SQLiteException("CRUD: Insert error");
        }

        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int resultDelete = -1;
        Uri result = uri;

        switch (uriMatcher.match(uri)) {
            case PRODUCT:
                resultDelete = db.delete(DatabaseContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CATEGORY:
                resultDelete = db.delete(DatabaseContract.CategoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
        }

        result = ContentUris.withAppendedId(uri, resultDelete);

        if (resultDelete != -1) {
            getContext().getContentResolver().notifyChange(result,null);
        } else {
            throw new SQLiteException("CRUD: Delete error");
        }

        return resultDelete;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int resultUpdate = -1;
        Uri result = uri;

        switch (uriMatcher.match(uri)) {
            case PRODUCT:
                resultUpdate = db.update(DatabaseContract.ProductEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CATEGORY:
                resultUpdate = db.update(DatabaseContract.CategoryEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
        }

        result = ContentUris.withAppendedId(uri, resultUpdate);

        if (resultUpdate != -1) {
            getContext().getContentResolver().notifyChange(result,null);
        } else {
            throw new SQLiteException("CRUD: Update error");
        }

        return resultUpdate;
    }
}
