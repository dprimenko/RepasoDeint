package es.dpinfo.repasodeint.presenter;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import es.dpinfo.repasodeint.ShopApplication;
import es.dpinfo.repasodeint.fragments.ListFragment;
import es.dpinfo.repasodeint.interfaces.IListPresenter;
import es.dpinfo.repasodeint.provider.ProviderContract;

/**
 * Created by dprimenko on 5/03/17.
 */
public class ListPresenterImpl implements IListPresenter.Presenter, LoaderManager.LoaderCallbacks<Cursor>{

    private IListPresenter.View view;
    private int id;

    public ListPresenterImpl(IListPresenter.View view, int id) {
        this.view = view;
        this.id = id;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Loader<Cursor> loader = null;

        switch (id) {
            case IListPresenter.PRODUCT:
                loader = new CursorLoader(
                        ShopApplication.getContext(),
                        ProviderContract.Product.CONTENT_URI,
                        ProviderContract.Product.PROJECTION,
                        args.getString("selection"),
                        args.getStringArray("selectionArgs"),
                        args.getString("sortOrder"));
                break;
            case IListPresenter.CATEGORY:
                loader = new CursorLoader(
                        ShopApplication.getContext(),
                        ProviderContract.Category.CONTENT_URI,
                        ProviderContract.Category.PROJECTION,
                        args.getString("selection"),
                        args.getStringArray("selectionArgs"),
                        args.getString("sortOrder"));
                break;
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case IListPresenter.PRODUCT:
                data.setNotificationUri(view.getContentResolver(), ProviderContract.Product.CONTENT_URI);
                break;
            case IListPresenter.CATEGORY:
                data.setNotificationUri(view.getContentResolver(), ProviderContract.Category.CONTENT_URI);
                break;
        }

        view.setCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        view.setCursor(null);
    }

    @Override
    public void getAllFields(Bundle args) {
        ((ListFragment)view).getLoaderManager().initLoader(id, args, this);
    }
}
