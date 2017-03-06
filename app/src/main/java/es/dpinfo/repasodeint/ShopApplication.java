package es.dpinfo.repasodeint;

import android.app.Application;
import android.content.Intent;
import android.view.animation.AnticipateOvershootInterpolator;

import es.dpinfo.repasodeint.services.ExpiryService;

/**
 * Created by dprimenko on 5/03/17.
 */
public class ShopApplication extends Application {

    private static ShopApplication singleton;

    public ShopApplication() {
        singleton = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(singleton, ExpiryService.class));
    }

    public static ShopApplication getContext() {
        return singleton;
    }

}
