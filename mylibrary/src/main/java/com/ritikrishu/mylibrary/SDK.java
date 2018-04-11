package com.ritikrishu.mylibrary;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.customlbs.library.Indoors;
import com.customlbs.library.IndoorsException;
import com.customlbs.library.IndoorsFactory;
import com.customlbs.library.IndoorsLocationListener;
import com.customlbs.library.LocalizationParameters;
import com.customlbs.library.callbacks.IndoorsServiceCallback;
import com.customlbs.library.callbacks.LoadingBuildingStatus;
import com.customlbs.library.model.Building;
import com.customlbs.library.model.Zone;
import com.customlbs.shared.Coordinate;

import java.util.List;

public class SDK extends IntentService implements IndoorsServiceCallback, IndoorsLocationListener {

    public static final String SHOULD_START = "should_start_indoors";

    public SDK() {
        super("SDK sample");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {



        Intent notificationIntent = new Intent(this, SDK.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification =
                new Notification.Builder(this, "ch_id")
                        .setContentTitle("title")
                        .setContentText("body")
                        .setSmallIcon(R.drawable.ic_android_black_24dp)
                        .setContentIntent(pendingIntent)
                        .setTicker("Ticker")
                        .build();

        startForeground(123, notification);

        boolean shouldStart = intent.getBooleanExtra(SHOULD_START, false);
        if(shouldStart){
            start();
        }
        else{
            stop();
        }
    }


    private void start(){
        IndoorsFactory.createInstance(getApplicationContext(), "insert api key from WeWork Promenade v2", this, false);
    }

    private void stop(){
        Indoors indoors = IndoorsFactory.getInstance();
        if (indoors != null) {
            indoors.removeLocationListener(this);

            IndoorsFactory.releaseInstance(this);
        }
    }

    @Override
    public void loadingBuilding(LoadingBuildingStatus loadingBuildingStatus) {

    }

    @Override
    public void buildingLoaded(Building building) {

    }

    @Override
    public void leftBuilding(Building building) {

    }

    @Override
    public void buildingReleased(Building building) {

    }

    @Override
    public void positionUpdated(Coordinate coordinate, int i) {

    }

    @Override
    public void orientationUpdated(float v) {

    }

    @Override
    public void changedFloor(int i, String s) {

    }

    @Override
    public void enteredZones(List<Zone> list) {

    }

    @Override
    public void buildingLoadingCanceled() {

    }

    @Override
    public void connected() {
        Indoors indoors = IndoorsFactory.getInstance();

        indoors.registerLocationListener(this);

        indoors.setLocatedCloudBuilding(1119976587, new LocalizationParameters(), true);
    }

    @Override
    public void onError(IndoorsException e) {

    }
}
