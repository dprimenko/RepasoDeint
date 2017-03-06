package es.dpinfo.repasodeint.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import es.dpinfo.repasodeint.MainActivity;
import es.dpinfo.repasodeint.R;

import es.dpinfo.repasodeint.MainActivity;

public class WarningReceiver extends BroadcastReceiver {

    public static final String WARNING_ACTION = "com.amador.fridge.WARNING";
    public static final String RECOVERY_DATE = "date";
    public static final String RECOVERY_COUNT = "count";
    public static final String RECOVERY_HOME_ACTIVITY_ORDER = "order";


    public WarningReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        int count = 0;
        count = intent.getIntExtra(RECOVERY_COUNT, -1);
        NotificationCompat.Builder noty = new NotificationCompat.Builder(context);
        noty.setAutoCancel(true);
        noty.setSmallIcon(android.R.drawable.ic_dialog_info);
        noty.setContentText("Hay " + String.valueOf(count) + " alimentos caducados");
        Intent i = new Intent(context, MainActivity.class);

        Bundle bundle = new Bundle();

        String today = intent.getStringExtra(RECOVERY_DATE);
        bundle.putString("selection", "fecha_caducidad <= " + today);
        bundle.putStringArray("selectionArgs", new String[] {intent.getStringExtra(RECOVERY_DATE)});
        i.putExtra("bundle", bundle);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i, PendingIntent.FLAG_ONE_SHOT);
        noty.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, noty.build());
    }
}
