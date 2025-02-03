package com.traveleasy.pathbase.Views.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.traveleasy.pathbase.R;
import com.traveleasy.pathbase.Views.Activities.CityFinder;
import com.traveleasy.pathbase.Views.weatherData;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WeatherFragment extends Fragment {
    final String APP_ID = "992b93fb0046f09c41ad92a5ba15b950";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";
    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;
    final int CITY_REQUEST_CODE = 102;
    private static final int TIME_INTERVAL = 2000;
    private long lastBackPressedTime = 0;

    String Location_Provider = LocationManager.GPS_PROVIDER;

    TextView NameofCity, weatherState, Temperature;
    RelativeLayout mcityFinder;
    ImageView mweatherIcon;
    LocationManager mLocationManager;
    LocationListener mLocationListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        weatherState = view.findViewById(R.id.weatherCondition);
        Temperature = view.findViewById(R.id.temperature);
        mweatherIcon = view.findViewById(R.id.weatherIcon);
        NameofCity = view.findViewById(R.id.cityName);
        mcityFinder = view.findViewById(R.id.cityFinder);

        String fullText = "Fetching___\nKeep Patience, stay on the page!!";

        SpannableString spannableString = new SpannableString(fullText);

        int textSizeInPx = (int) (16 * getResources().getDisplayMetrics().density);

        spannableString.setSpan(
                new AbsoluteSizeSpan(textSizeInPx),
                fullText.indexOf("Keep Patience, stay on the page!!"),
                fullText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        spannableString.setSpan(
                new ForegroundColorSpan(Color.GRAY),
                fullText.indexOf("Keep Patience, stay on the page!!"),
                fullText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        NameofCity.setText(spannableString);

        mcityFinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), CityFinder.class);
                startActivityForResult(intent, CITY_REQUEST_CODE);
            }
        });

        handleBackPress();

        return view;
    }

    private void handleBackPress() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (System.currentTimeMillis() - lastBackPressedTime < TIME_INTERVAL) {
                    requireActivity().finish();
                } else {
                    Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
                    lastBackPressedTime = System.currentTimeMillis();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getWeatherForCurrentLocation();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CITY_REQUEST_CODE && resultCode == requireActivity().RESULT_OK && data != null) {
            String city = data.getStringExtra("City");
            if (city != null) {
                getWeatherForNewCity(city);
            }
        }
    }

    private void getWeatherForNewCity(String city) {
        RequestParams params = new RequestParams();
        params.put("q", city);
        params.put("appid", APP_ID);
        params.put("units", "metric");
        letsdoSomeNetworking(params);
    }

    private void getWeatherForCurrentLocation() {
        mLocationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());

                RequestParams params = new RequestParams();
                params.put("lat", Latitude);
                params.put("lon", Longitude);
                params.put("appid", APP_ID);
                params.put("units", "metric");
                letsdoSomeNetworking(params);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(requireContext(), "Please enable location services", Toast.LENGTH_SHORT).show();
            }
        };

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mLocationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getWeatherForCurrentLocation();
        } else {
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void letsdoSomeNetworking(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // Check if the response contains a valid "cod" field
                    if (response.has("cod") && response.getInt("cod") == 200) {
                        // Parse the weather data and update the UI
                        weatherData weatherD = weatherData.fromJson(response);
                        updateUI(weatherD);
                    } else {
                        // Display error if "cod" is not 200
                        String message = response.has("message") ? response.getString("message") : "Invalid response";
                        Toast.makeText(requireContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(requireContext(), "Failed to parse the weather data.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // Handle failure
                Toast.makeText(requireContext(), "Unable to fetch weather data. Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void updateUI(weatherData weather) {
        Temperature.setText(weather.getmTemperature());
        NameofCity.setText(weather.getMcity());
        weatherState.setText(weather.getmWeatherType());
        String iconName = weather.getMicon();
        int resourceID = requireContext().getResources().getIdentifier(iconName, "drawable", requireContext().getPackageName());
        mweatherIcon.setImageResource(resourceID != 0 ? resourceID : R.drawable.finding);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }
}
