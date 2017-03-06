package es.dpinfo.repasodeint.interfaces;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.content.ContentResolver;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;

/**
 * Created by dprimenko on 5/03/17.
 */
public interface IListPresenter {

    int CATEGORY = 1;
    int PRODUCT = 3;

    interface View {
        CursorAdapter getAdapter();
        void showMessageError(String error);
        ContentResolver getContentResolver();
        void setCursor(Cursor cursor);
    }

    interface Presenter {
        void getAllFields(Bundle args
        );
    }
}
