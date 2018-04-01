package be.dispatcher.graphhopper.reverse.geocode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HospitalService {

		@GET("reverse.php")
		Call<ReverseGeocode> listHospitals(@Query("key") String key, @Query("lat") Double lat, @Query("lon") Double lon, @Query("format") String format);
}


