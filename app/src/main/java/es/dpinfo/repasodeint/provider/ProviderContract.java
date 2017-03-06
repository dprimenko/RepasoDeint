package es.dpinfo.repasodeint.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dprimenko on 5/03/17.
 */
public class ProviderContract {

    public static final String AUTHORITY = "es.dpinfo.repasodeint";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public static class Category implements BaseColumns {

        public static final String CONTENT_PATH = "categoria";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String NAME = "nombre";

        public static final String[] PROJECTION = new String[] {BaseColumns._ID,NAME};
    }

    public static class Product implements BaseColumns {

        public static final String CONTENT_PATH = "producto";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String NAME = "nombre";
        public static final String DATE_EXPIRY = "fecha_caducidad";
        public static final String IDCATEGORY = "idCategoria";

        public static final String[] PROJECTION = new String[] {BaseColumns._ID,NAME, DATE_EXPIRY, IDCATEGORY};
    }
}
