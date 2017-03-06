package es.dpinfo.repasodeint.db;

import android.provider.BaseColumns;

/**
 * Created by dprimenko on 5/03/17.
 */
public class DatabaseContract {

    public static class CategoryEntry implements BaseColumns {

        public static final String TABLE_NAME = "categoria";
        public static final String COL_NAME = "nombre";

        public static final String SQL_CREATE_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s TEXT NOT NULL)",
                TABLE_NAME,
                BaseColumns._ID,
                COL_NAME);

        public static final String SQL_DROP_TABLE = String.format("DROP TABLE IF EXISTS %s", CategoryEntry.TABLE_NAME);

    }

    public static class ProductEntry implements BaseColumns {

        public static final String TABLE_NAME = "producto";
        public static final String COL_NAME = "nombre";
        public static final String COL_DATE_EXPIRY = "fecha_caducidad";
        public static final String COL_IDCATEGORY = "idCategoria";
        public static final String REFERENCES_IDCATEGORY = String.format("REFERENCES %s (%s) ON UPDATE CASCADE ON DELETE RESTRICT", TABLE_NAME, CategoryEntry._ID);

        public static final String SQL_CREATE_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s TEXT NOT NULL," +
                "%s TEXT," +
                "%s INTEGER NOT NULL %s)",
                TABLE_NAME,
                BaseColumns._ID,
                COL_NAME,
                COL_DATE_EXPIRY,
                COL_IDCATEGORY,
                REFERENCES_IDCATEGORY);

        public static final String SQL_DROP_TABLE = String.format("DROP TABLE IF EXISTS %s", ProductEntry.TABLE_NAME);
    }
}
