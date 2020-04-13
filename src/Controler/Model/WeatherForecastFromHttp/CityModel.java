package Controler.Model.WeatherForecastFromHttp;

import com.google.gson.annotations.SerializedName;


public class CityModel{
    @SerializedName("title")
    private String title;
    @SerializedName("location_type")
    private String locationType;
    @SerializedName("woeid")
    private Integer woeid;
    @SerializedName("latt_long")
    private String lattLong;


    public Integer getWoeid() {
        return woeid;
    }

    @Override
    public String toString() {
        return "CityModel{" +
                "title='" + title + '\'' +
                ", locationType='" + locationType + '\'' +
                ", woeid=" + woeid +
                ", lattLong='" + lattLong + '\'' +
                '}';
    }
}
