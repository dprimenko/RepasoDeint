package es.dpinfo.repasodeint.services;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.dpinfo.repasodeint.db.DatabaseContract;
import es.dpinfo.repasodeint.provider.ProviderContract;
import es.dpinfo.repasodeint.receiver.WarningReceiver;

/**
 * Created by dprimenko on 6/03/17.
 */
public class ExpiryService extends IntentService {

    public ExpiryService() {
        super("Any Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("Servicer", "Running");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String today = "\"" + formatter.format(new Date()) + "\"";

                Cursor cursor = getContentResolver().query(ProviderContract.Product.CONTENT_URI, ProviderContract.Product.PROJECTION, null, null, null);

                int count = 0;

                if (cursor.moveToFirst()) {
                    do {
                        try {
                            String curDate = cursor.getString(cursor.getColumnIndex(DatabaseContract.ProductEntry.COL_DATE_EXPIRY));

                            if (curDate != null) {
                                Date date = formatter.parse(curDate);

                                if (new Date().after(date)) {
                                    count++;
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } while(cursor.moveToNext());
                }

                if (count > 0) {
                    Intent intent1 = new Intent();
                    intent1.putExtra(WarningReceiver.RECOVERY_COUNT, count);
                    intent1.putExtra(WarningReceiver.RECOVERY_DATE, today);
                    intent1.setAction(WarningReceiver.WARNING_ACTION);
                    sendBroadcast(intent1);
                }
                handler.postDelayed(this,10000);
            }
        }, 10000);

        return START_STICKY;
    }
}
