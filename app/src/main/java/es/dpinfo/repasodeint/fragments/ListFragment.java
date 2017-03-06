package es.dpinfo.repasodeint.fragments;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.support.v4.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

import es.dpinfo.repasodeint.R;
import es.dpinfo.repasodeint.adapter.CategoryAdapter;
import es.dpinfo.repasodeint.adapter.ProductAdapter;
import es.dpinfo.repasodeint.interfaces.IListPresenter;
import es.dpinfo.repasodeint.presenter.ListPresenterImpl;

/**
 * Created by dprimenko on 5/03/17.
 */
public class ListFragment extends Fragment implements IListPresenter.View {

    private ListView lwList;
    private CursorAdapter adapter;
    private ListPresenterImpl presenter;
    private RelativeLayout llList;

    public static ListFragment newInstance(Bundle bundle) {
        ListFragment listFragment = new ListFragment();
        listFragment.setArguments(bundle);
        return listFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ListPresenterImpl(this, getArguments().getInt("list"));

        switch (getArguments().getInt("list")) {
            case IListPresenter.PRODUCT:
                adapter = new ProductAdapter();
                break;
            case IListPresenter.CATEGORY:
                adapter = new CategoryAdapter();
                break;
        }

        Bundle bundle = getArguments();

        presenter.getAllFields(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lwList = (ListView) view.findViewById(R.id.lw_list);
        llList = (RelativeLayout) view.findViewById(R.id.llList);

        lwList.setAdapter(adapter);
    }

    @Override
    public android.support.v4.widget.CursorAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void showMessageError(String error) {
        Snackbar.make(llList, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public ContentResolver getContentResolver() {
       return getActivity().getContentResolver();
    }

    @Override
    public void setCursor(Cursor cursor) {
        if (cursor != null) {
            adapter.swapCursor(cursor);
        }
    }
}
