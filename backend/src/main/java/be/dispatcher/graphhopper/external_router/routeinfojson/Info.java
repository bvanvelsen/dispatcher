package be.dispatcher.graphhopper.external_router.routeinfojson;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

@SerializedName("took")
@Expose
private Integer took;
@SerializedName("copyrights")
@Expose
private List<String> copyrights = null;

public Integer getTook() {
return took;
}

public void setTook(Integer took) {
this.took = took;
}

public List<String> getCopyrights() {
return copyrights;
}

public void setCopyrights(List<String> copyrights) {
this.copyrights = copyrights;
}

}