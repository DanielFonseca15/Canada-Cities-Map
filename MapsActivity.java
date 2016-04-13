package com.danielfreire.canadacitiesmap;

import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;


import java.io.IOException;
import java.util.List;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class MapsActivity extends FragmentActivity {


    final Context context = this;


    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().show();
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();



        //  AdView mAdView = (AdView)findViewById(R.id.adView);
    //    AdRequest adRequest = new AdRequest.Builder().build();
  //      mAdView.loadAd(adRequest);
//addTestDevice("0B4015D363BDA1BEAFFBBA336C775FFD")

    }

  //  public void openDialog() {
//
//
  //      final Dialog dialog = new Dialog(context);
    //    dialog.setContentView(R.layout.dialog_demo);
      //  dialog.setTitle(R.string.dialog_title);
      //  final Button dialogButton = (Button) dialog.findViewById(R.id.dialog_search);
       // dialogButton.setOnClickListener(new View.OnClickListener() {
       //     @Override
        //    public void onClick(View v) {
         //       dialog.dismiss();
//
    //          }
    //    });
      //  dialog.show();
//
  //  }


    public void openaboutDialog() {


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflate = this.getLayoutInflater();

        alert.setView(inflate.inflate(R.layout.about_demo, null));
        alert.setTitle("About us");
        //alert.setMessage("");
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
                // .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                // @Override
                // public void onClick(DialogInterface dialog, int which) {
                //   dialog.cancel();

                //}
                // })
                .show();


    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    //procurar por cidades ou lugares
    public void onSearch(View view) throws IOException, Exception {
        hideSoftKeyboard(view);
        EditText location_tf = (EditText) findViewById(R.id.tf_location);
        String location = location_tf.getText().toString();
        List<Address> addressList = null;
        Toast.makeText(this, location, Toast.LENGTH_LONG).show();


        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);


            try {
                addressList = geocoder.getFromLocationName(location, 1);
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

            } catch (IllegalStateException e1) {
                Toast.makeText(this, "Invalid Location", Toast.LENGTH_LONG).show();
                e1.printStackTrace();
                return;
            } catch (IOException e2) {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
                //Log.e("Geocoder IllegalArgumentException exception: ", eam.getMessage());
                e2.printStackTrace();
                return;

            } catch (Exception e) {
                Toast.makeText(this, "No Results Found", Toast.LENGTH_LONG).show();
                // Log.e("Geocoder IOException exception: ", e.getMessage());
                e.printStackTrace();
                return;
            }

        }
    }
    //public void onSearch1(View view) throws IOException, ClientProtocolException {
    //  hideSoftKeyboard(view);
    // EditText location_tf1 = (EditText) findViewById(R.id.tf_location1);
    // String location1 = location_tf1.getText().toString();
    // List<Address> addressList = null;
    // Toast.makeText(this, location1, Toast.LENGTH_LONG).show();

    // if (location1 != null || !location1.equals("")) {
    //    Geocoder geocoder = new Geocoder(this);
    //  try {
    //    addressList = geocoder.getFromLocationName(location1, 1);
    // }catch (IllegalArgumentException eam){
    //   eam.printStackTrace();
    // }
    // catch (IOException e) {

    //   e.printStackTrace();
    //}
    //}
    //Address address = addressList.get(0);
    //LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
    //mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
    //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    //mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    //}


    private void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    //mudar para satelite ou normal

    public void changeType(View view) {
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else if (mMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        } else
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void vancouver(View view) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(49.246292, -123.116226)).title("Marker"));
        LatLng Vancouver = new LatLng(49.246292, -123.116226);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Vancouver));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);



        //  Uri gmmIntentUri = Uri.parse("geo:0,0?q=restaurants");
      //  Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
      //  mapIntent.setPackage("com.google.android.apps.maps");
      //  startActivity(mapIntent);



    }

    public void ottawa(View view) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(45.425533, -75.692482)).title("Marker"));
        LatLng Ottawa = new LatLng(45.425533, -75.692482);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Ottawa));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);
    }

    public void toronto(View view) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(43.7182412, -79.378058)).title("Marker"));
        LatLng Toronto = new LatLng(43.7182412, -79.378058);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Toronto));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);
    }

    public void winnipeg(View view) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(49.853822, -97.1522251)).title("Marker"));
        LatLng Winnipeg = new LatLng(49.853822, -97.1522251);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Winnipeg));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);
    }

    public void victoria(View view) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(48.426708, -123.3584162)).title("Marker"));
        LatLng Victoria = new LatLng(48.426708, -123.3584162);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Victoria));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);
    }

    public void quebec(View view) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(46.8580074, -71.3460728)).title("Marker"));
        LatLng Quebec = new LatLng(46.8580074, -71.3460728);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Quebec));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);
    }

    public void montreal(View view) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(45.5601257, -73.7120504)).title("Marker"));
        LatLng Montreal = new LatLng(45.5601257, -73.7120504);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Montreal));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);
    }

    public void calgary(View view) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(51.013117, -114.0741556)).title("Marker"));
        LatLng Calgary = new LatLng(51.013117, -114.0741556);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Calgary));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);
    }

    public void niagara(View view) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(43.0539325, -79.0880345)).title("Marker"));
        LatLng Niagara = new LatLng(43.0539325, -79.0880345);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Niagara));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);
    }

    public void regina(View view) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(50.4585942, -104.6354091)).title("Marker"));
        LatLng Regina = new LatLng(50.4585942, -104.6354091);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Regina));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);
    }

    public void edmonton(View view) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(53.5558774, -113.4939486)).title("Marker"));
        LatLng Edmonton = new LatLng(53.5558774, -113.4939486);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Edmonton));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);
    }

    public void saskatoon(View view) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(52.1396545, -106.646727)).title("Marker"));
        LatLng Saskatoon = new LatLng(52.1396545, -106.646727);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Saskatoon));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);
    }

    public void saintjohn(View view) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(45.2598013, -66.0390314)).title("Marker"));
        LatLng Saintjohn = new LatLng(45.2598013, -66.0390314);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Saintjohn));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);
    }

    public void halifax(View view) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(44.6492308, -63.6219743)).title("Marker"));
        LatLng Halifax = new LatLng(44.6492308, -63.6219743);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Halifax));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);
    }

    public void whitehorse(View view) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(60.6997545, -135.0845781)).title("Marker"));
        LatLng Whitehorse = new LatLng(60.6997545, -135.0845781);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Whitehorse));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);
    }

    public void yellowknife(View view) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(62.474876, -114.408995)).title("Marker"));
        LatLng Yellowknife = new LatLng(62.474876, -114.408995);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Yellowknife));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);
    }

    public void thunderbay(View view) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(48.4025585, -89.2719957)).title("Marker"));
        LatLng Thunderbay = new LatLng(48.4025585, -89.2719957);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Thunderbay));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);
    }

    public void windsor(View view) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(42.2926986, -83.002882)).title("Marker"));
        LatLng Windsor = new LatLng(42.2926986, -83.002882);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Windsor));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setMyLocationEnabled(true);
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    public void setUpMap() {
        //Latlng canada
        mMap.addMarker(new MarkerOptions().position(new LatLng(56, -96)).title("Canada").snippet("Welcome to Canada!").draggable(true));
        LatLng canada = new LatLng(56, -96);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(canada));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(5));
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);



        // Latlng Toronto
        // mMap.addMarker(new MarkerOptions().position(new LatLng(43.761539, -79.411079)).title("Marker"));
        // LatLng latLng = new LatLng(43.761539, -79.411079);
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        // mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

    }

    private ShareActionProvider mShareActionProvider;

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu_ais_; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.menu_ais_, menu);//  return true;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_maps, menu);


        mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.action_share)
                .getActionProvider();
        mShareActionProvider.setShareIntent(doShare());
        // SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        // SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
        //       .getActionView();
        //searchView.setSearchableInfo(searchManager
        //.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    public Intent doShare() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.danielfreire.canadacitiesmap");
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            //case R.id.action_search:
              //  openDialog();
                //return true;
            case R.id.action_about:
                openaboutDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
