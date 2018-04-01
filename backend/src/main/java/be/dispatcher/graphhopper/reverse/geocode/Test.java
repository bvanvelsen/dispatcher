package be.dispatcher.graphhopper.reverse.geocode;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Test {

	public static void main(String[] args) {
		OkHttpClient.Builder httpClient = new OkHttpClient
				.Builder().addInterceptor(new HttpLoggingInterceptor()
				.setLevel(HttpLoggingInterceptor.Level.BODY));
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://eu1.locationiq.org/v1/")
				.addConverterFactory(GsonConverterFactory.create())
				.client(httpClient.build())
				.build();
		ReverseGeocoderService service = retrofit.create(ReverseGeocoderService.class);

		Call<ReverseGeocode> json = service.listRepos("91b9ecc565b3cb",50.937506, 5.216248, "json", 18);
		try {
			System.out.println(json.execute().body());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
