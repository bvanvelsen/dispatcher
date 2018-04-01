package be.dispatcher.graphhopper.reverse.geocode;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import be.dispatcher.graphhopper.LatLon;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class RetrofitReverseGeocoderCaller {

	private static final String FORMAT = "json";
	private static final int ZOOM = 18;

	private LoadingCache<LatLon, ReverseGeocode> cache = CacheBuilder.newBuilder()
			.expireAfterWrite(7, TimeUnit.DAYS)
			.build(
					new CacheLoader<LatLon, ReverseGeocode>() {
						public ReverseGeocode load(LatLon key) {
							try {
								TimeUnit.MILLISECONDS.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							OkHttpClient.Builder httpClient = new OkHttpClient
									.Builder().addInterceptor(new HttpLoggingInterceptor()
									.setLevel(HttpLoggingInterceptor.Level.BASIC));
							Retrofit retrofit = new Retrofit.Builder()
									.baseUrl("https://eu1.locationiq.org/v1/")
									.addConverterFactory(GsonConverterFactory.create())
									.client(httpClient.build())
									.build();
							ReverseGeocoderService service = retrofit.create(ReverseGeocoderService.class);

							Call<ReverseGeocode> json = service.listRepos("91b9ecc565b3cb",key.getLat(), key.getLon(), FORMAT, ZOOM);
							try {
								return json.execute().body();
							} catch (IOException e) {
								e.printStackTrace();
								return null;
							}
						}
					});

	public ReverseGeocode doCall(LatLon latLon) {
		try {
			return cache.get(latLon);
		} catch (ExecutionException e) {
			return null;
		}
	}
}
