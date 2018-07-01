package be.dispatcher.graphhopper.reverse.geocode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReverseGeocoderService {

		@GET("reverse.php")
		Call<ReverseGeocode> listRepos(@Query("key") String key, @Query("lat") Double lat,@Query("lon") Double lon, @Query("format") String format, @Query("zoom") int zoom);
}


