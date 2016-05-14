package se.futurememories.fixmycity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import se.futurememories.fixmycity.model.MyMarker;
import se.futurememories.fixmycity.report.MenuFragment;
import se.futurememories.fixmycity.report.MenuView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MapsActivity extends Activity implements OnMapReadyCallback, android.location.LocationListener, MenuFragment.OnFragmentInteractionListener {

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.bottom_sheet)
    FrameLayout bottomsheet;
    @BindView(R.id.fab_btn)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.menu)
    MenuView menuView;

    private Scene originalScene;
    private Scene sceneA;

    private static final int MY_DANGEROUS_PERMISSIONS_REQ_CODE = 123;
    private GoogleMap mMap;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);
        originalScene = Scene.getSceneForLayout(coordinatorLayout, R.layout.activity_maps, this);
        sceneA = Scene.getSceneForLayout(coordinatorLayout, R.layout.maps_scene_a, this);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_DANGEROUS_PERMISSIONS_REQ_CODE);
        }else {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
//            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            mapView.getMapAsync(this);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_DANGEROUS_PERMISSIONS_REQ_CODE) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressWarnings("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        List<MyMarker> myMarkers = addMarkers();
        Location location = getLastKnownLocation();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        // Add a marker in Sydney and move the camera
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);
        LatLng me = new LatLng(latitude, longitude);
//        mMap.addMarker(new MarkerOptions().position(me).title("Hi this is me"));
        CameraPosition myPos = CameraPosition.fromLatLngZoom(me, 14f);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(myPos);

        mMap.moveCamera(cameraUpdate);

        for (MyMarker myMarker : myMarkers) {
            mMap.addMarker(new MarkerOptions().position(myMarker.getPosition()).icon(BitmapDescriptorFactory.fromResource(myMarker.getImageType())));
        }
    }

    private ArrayList<MyMarker> addMarkers() {
        ArrayList<MyMarker> markers = new ArrayList<>();
        markers.add(new MyMarker("Förslag", "BadPlats", "Här skulle jag vilja bada", "http://91.189.44.144/akuro/userFiles/projekt/5_1.jpg", new LatLng(57.70833, 11.96913)));
        markers.add(new MyMarker("Renhållning", "Klotter", "HMe gustaf, mother of all gods", "http://91.189.44.144/akuro/userFiles/projekt/5_1.jpg", new LatLng(57.71333, 11.96778)));
        markers.add(new MyMarker("Fråga", "", "Vem fan kom det här namnet", "nope.jpeg", new LatLng(57.70734, 11.96785)));
        return markers;
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng myPos = new LatLng(location.getLatitude(), location.getLongitude());
//        mMap.addMarker(new MarkerOptions().position(myPos).title("Marker on me"));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @SuppressWarnings("MissingPermission")
    private Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @OnClick(R.id.fab_btn)
    public void fabClicked(){
//        Intent intent = new Intent(this, MenuActivity.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.zoom_out);
//        TransitionSet set = new TransitionSet();
//        set.addTransition(new Fade()).setDuration(500).addTarget(floatingActionButton).addTarget(bottomsheet).addTransition(new Slide()).setDuration(1000).addTarget(menuFragment);
//        Transition transition = new AutoTransition();
//        transition.setDuration(500);
//        TransitionManager.go(sceneA, set);
//        TransitionManager.go(sceneA);
//        getFragmentManager().beginTransaction().add(R.id.map, new MapFragment()).commit();
//        getFragmentManager().beginTransaction().add(R.id.menu, MenuFragment.newInstance()).commit();
        goToScene(sceneA);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void goToScene(Scene scene) {
        ChangeBounds changeBounds = new ChangeBounds();
        Fade fadeOut = new Fade(Fade.OUT);
        Slide slide = new Slide();
        fadeOut.addTarget(bottomsheet).addTarget(floatingActionButton);
        slide.addTarget(menuView);
        fadeOut.addTarget(floatingActionButton).addTarget(bottomsheet);
        TransitionSet transition = new TransitionSet();
        transition.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        transition
                .addTransition(fadeOut)
                .addTransition(slide);
        transition.setDuration(1000);
        TransitionManager.go(scene, transition);
    }
}
