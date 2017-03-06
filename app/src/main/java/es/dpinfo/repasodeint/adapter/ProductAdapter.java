package es.dpinfo.repasodeint.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.CursorAdapter;
import android.widget.TextView;

import es.dpinfo.repasodeint.R;
import es.dpinfo.repasodeint.ShopApplication;
import es.dpinfo.repasodeint.db.DatabaseContract;
import es.dpinfo.repasodeint.interfaces.IListPresenter;
import es.dpinfo.repasodeint.models.Product;
import es.dpinfo.repasodeint.provider.ProviderContract;

/**
 * Created by dprimenko on 5/03/17.
 */
public class ProductAdapter extends CursorAdapter {

    private ProductHolder holder;

    public ProductAdapter() {
        super(ShopApplication.getContext(), null, IListPresenter.PRODUCT);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_product, parent, false);
        holder = new ProductHolder();
        holder.txvName = (TextView) view.findViewById(R.id.txv_name_product_item);
        holder.txvDateExpiry = (TextView) view.findViewById(R.id.txv_date_expiry_product_item);

        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        holder = (ProductHolder) view.getTag();

        holder.txvName.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.ProductEntry.COL_NAME)));

        String dateExpiry = cursor.getString(cursor.getColumnIndex(DatabaseContract.ProductEntry.COL_DATE_EXPIRY));

        if (dateExpiry != null) {
            holder.txvDateExpiry.setText("Fecha de caducidad: " + dateExpiry);
        }
    }

    @Override
    public Object getItem(int position) {

        getCursor().moveToPosition(position);

        Product product = new Product();

        product.setmId(getCursor().getInt(getCursor().getColumnIndex(DatabaseContract.ProductEntry._ID)));
        product.setmName(getCursor().getString(getCursor().getColumnIndex(DatabaseContract.ProductEntry.COL_NAME)));
        product.setmName(getCursor().getString(getCursor().getColumnIndex(DatabaseContract.ProductEntry.COL_DATE_EXPIRY)));
        product.setmIdCategory(getCursor().getInt(getCursor().getColumnIndex(DatabaseContract.ProductEntry.COL_IDCATEGORY)));

        return product;
    }

    class ProductHolder {
        TextView txvName;
        TextView txvDateExpiry;
    }
}
