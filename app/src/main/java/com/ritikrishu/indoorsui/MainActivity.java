package com.ritikrishu.indoorsui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.customlbs.library.IndoorsException;
import com.customlbs.library.IndoorsFactory;
import com.customlbs.library.IndoorsLocationListener;
import com.customlbs.library.callbacks.LoadingBuildingStatus;
import com.customlbs.library.model.Building;
import com.customlbs.library.model.Zone;
import com.customlbs.library.util.IndoorsCoordinateUtil;
import com.customlbs.shared.Coordinate;
import com.customlbs.surface.library.IndoorsSurfaceFactory;
import com.customlbs.surface.library.IndoorsSurfaceFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IndoorsLocationListener {

    Building building;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getRequiredPermsAndStartIndoors();
    }


    private void getRequiredPermsAndStartIndoors(){
        if(
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED
                ){
            startIndoors();
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE
            }, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        startIndoors();
    }

    private void startIndoors(){
        IndoorsFactory.Builder indoorsBuilder = new IndoorsFactory.Builder();

        IndoorsSurfaceFactory.Builder surfaceBuilder = new IndoorsSurfaceFactory.Builder();

        indoorsBuilder.setContext(this);


        indoorsBuilder.setApiKey("insert api key from WeWork Promenade v2");

        indoorsBuilder.setBuildingId((long) 1119976587);

        indoorsBuilder.setUserInteractionListener(this);

        surfaceBuilder.setIndoorsBuilder(indoorsBuilder);

        IndoorsSurfaceFragment indoorsFragment = surfaceBuilder.build();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(android.R.id.content, indoorsFragment, "indoors");
        transaction.commit();
    }

    @Override
    public void loadingBuilding(LoadingBuildingStatus loadingBuildingStatus) {

    }

    @Override
    public void buildingLoaded(Building building) {
        this.building = building;
    }

    @Override
    public void leftBuilding(Building building) {

    }

    @Override
    public void buildingReleased(Building building) {

    }

    @Override
    public void positionUpdated(Coordinate coordinate, int i) {
        Location loc = IndoorsCoordinateUtil.toGeoLocation(coordinate, building);
        if(loc != null){
            Toast.makeText(this, loc.getLatitude() + "<- lat lng ->" + loc.getLongitude(), Toast.LENGTH_LONG).show();
        }
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
    public void onError(IndoorsException e) {

    }
}
