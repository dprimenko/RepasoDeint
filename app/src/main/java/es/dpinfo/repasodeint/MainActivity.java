package es.dpinfo.repasodeint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import es.dpinfo.repasodeint.fragments.ListFragment;
import es.dpinfo.repasodeint.interfaces.IListPresenter;

/**
 * Created by dprimenko on 5/03/17.
 */
public class MainActivity extends AppCompatActivity {

    private ListFragment listFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = new Bundle();
        bundle.putInt("list", IListPresenter.PRODUCT);

        if (getIntent().getBundleExtra("bundle") != null) {
            bundle.putString("selection", getIntent().getBundleExtra("bundle").getString("selection"));
        }

        listFragment = ListFragment.newInstance(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, listFragment).commit();
    }
}
