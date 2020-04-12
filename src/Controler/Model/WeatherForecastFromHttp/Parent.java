package Controler.Model.WeatherForecastFromHttp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Parent{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("location_type")
    @Expose
    private String locationType;
    @SerializedName("woeid")
    @Expose
    private Integer woeid;
    @SerializedName("latt_long")
    @Expose
    private String lattLong;


}
