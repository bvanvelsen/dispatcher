package be.dispatcher.graphhopper.external_router.routeinfojson;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Points {

@SerializedName("coordinates")
@Expose
private List<List<Double>> coordinates = null;
@SerializedName("type")
@Expose
private String type;

public List<List<Double>> getCoordinates() {
return coordinates;
}

public void setCoordinates(List<List<Double>> coordinates) {
this.coordinates = coordinates;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

}