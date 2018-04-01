package be.dispatcher.graphhopper.external_router.reouteinfojson;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Instruction {

@SerializedName("distance")
@Expose
private Double distance;
@SerializedName("heading")
@Expose
private Double heading;
@SerializedName("sign")
@Expose
private Integer sign;
@SerializedName("interval")
@Expose
private List<Integer> interval = null;
@SerializedName("text")
@Expose
private String text;
@SerializedName("time")
@Expose
private Integer time;
@SerializedName("street_name")
@Expose
private String streetName;
@SerializedName("last_heading")
@Expose
private Double lastHeading;

public Double getDistance() {
return distance;
}

public void setDistance(Double distance) {
this.distance = distance;
}

public Double getHeading() {
return heading;
}

public void setHeading(Double heading) {
this.heading = heading;
}

public Integer getSign() {
return sign;
}

public void setSign(Integer sign) {
this.sign = sign;
}

public List<Integer> getInterval() {
return interval;
}

public void setInterval(List<Integer> interval) {
this.interval = interval;
}

public String getText() {
return text;
}

public void setText(String text) {
this.text = text;
}

public Integer getTime() {
return time;
}

public void setTime(Integer time) {
this.time = time;
}

public String getStreetName() {
return streetName;
}

public void setStreetName(String streetName) {
this.streetName = streetName;
}

public Double getLastHeading() {
return lastHeading;
}

public void setLastHeading(Double lastHeading) {
this.lastHeading = lastHeading;
}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("distance", distance)
				.append("heading", heading)
				.append("sign", sign)
				.append("interval", interval)
				.append("text", text)
				.append("time", time)
				.append("streetName", streetName)
				.append("lastHeading", lastHeading)
				.toString();
	}
}