package be.dispatcher.graphhopper.external_router;

import be.dispatcher.graphhopper.external_router.routeinfojson.RouteInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RouteService {

	@GET("route")
	Call<RouteInfo> listRepos(@Query("vehicle") String speedProfile, @Query("point") String startPoint, @Query("point") String endPoint,@Query("points_encoded") boolean pointsEncoded, @Query("details") String details);
}
