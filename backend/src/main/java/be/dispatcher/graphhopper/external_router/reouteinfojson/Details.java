package be.dispatcher.graphhopper.external_router.reouteinfojson;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {

	@SerializedName("time")
	@Expose
	private List<List<Double>> times = null;

	public List<List<Double>> getTimes() {
		return times;
	}

	public void setTimes(List<List<Double>> times) {
		this.times = times;
	}

}
