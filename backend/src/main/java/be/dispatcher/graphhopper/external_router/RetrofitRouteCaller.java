package be.dispatcher.graphhopper.external_router;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import be.dispatcher.graphhopper.external_router.routeinfojson.RouteInfo;
import be.dispatcher.graphhopper.external_router.routeinfojson.RouteInfoEnriched;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class RetrofitRouteCaller {

	private static final String FORMAT = "json";
	//	http://localhost:8989/route?point=50.925963%2C5.342097&point=50.871159%2C5.27068&points_encoded=false

	private LoadingCache<RouteInput, RouteInfo> cache = CacheBuilder.newBuilder()
			.expireAfterWrite(7, TimeUnit.DAYS)
			.build(
					new CacheLoader<RouteInput, RouteInfo>() {
						public RouteInfo load(RouteInput key) {
							OkHttpClient.Builder httpClient = new OkHttpClient
									.Builder().addInterceptor(new HttpLoggingInterceptor()
									.setLevel(HttpLoggingInterceptor.Level.BASIC));
							Retrofit retrofit = new Retrofit.Builder()
									.baseUrl("http://localhost:8989/")
									.addConverterFactory(GsonConverterFactory.create())
									.client(httpClient.build())
									.build();
							RouteService service = retrofit.create(RouteService.class);

							Call<RouteInfo> json = service.listRepos(key.getSpeedprofile(), key.getStartLocation().toPoint(), key.getDestinationLocation().toPoint(), false, "time");
							try {
								return json.execute().body();
							} catch (IOException e) {
								e.printStackTrace();
								return null;
							}
						}
					});

	public RouteInfoEnriched doCall(RouteInput routeInput) {
		try {
			RouteInfo routeInfo = cache.get(routeInput);
			RouteInfoEnriched routeInfoEnriched = new RouteInfoEnriched(routeInfo);
			routeInfoEnriched.enrichRouteInfo();
			return routeInfoEnriched;
		} catch (ExecutionException e) {
			return null;
		}
	}

	public LoadingCache<RouteInput, RouteInfo> getCache() {
		return cache;
	}
}
