package com.rahpa.yasamani.rahpamap.Utils;

import com.rahpa.yasamani.rahpamap.Entities.Direction;
import com.rahpa.yasamani.rahpamap.Entities.GoogleGeocode.Address;
import com.rahpa.yasamani.rahpamap.Entities.Route;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Saeed on 4/4/2018.
 */

public interface WebServiceRequests {

    @GET("directions/json")
    Observable<Direction> getDirection(@Query("origin") String origin, @Query("destination") String destination, @Query("apikey") String key);

    @GET("geocode/json")
    Observable<Address> getAddress(@Query("latlng") String latlng, @Query("apikey") String key);

}
