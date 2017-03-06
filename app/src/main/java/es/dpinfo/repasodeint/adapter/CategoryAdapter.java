package es.dpinfo.repasodeint.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.CursorAdapter;

import es.dpinfo.repasodeint.ShopApplication;
import es.dpinfo.repasodeint.interfaces.IListPresenter;

/**
 * Created by dprimenko on 5/03/17.
 */
public class CategoryAdapter extends CursorAdapter {

    public CategoryAdapter() {
        super(ShopApplication.getContext(), null, IListPresenter.CATEGORY);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
