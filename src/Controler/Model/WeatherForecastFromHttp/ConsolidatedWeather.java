package Controler.Model.WeatherForecastFromHttp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsolidatedWeather{


    @SerializedName("weather_state_name")
    @Expose
    private String weatherStateName;
    @SerializedName("weather_state_abbr")
    @Expose
    private String weatherStateAbbr;
    @SerializedName("wind_direction_compass")
    @Expose
    private String windDirectionCompass;
    @SerializedName("min_temp")
    @Expose
    private Double minTemp;
    @SerializedName("max_temp")
    @Expose
    private Double maxTemp;
    @SerializedName("the_temp")
    @Expose
    private Double theTemp;
    @SerializedName("wind_speed")
    @Expose
    private Double windSpeed;
    @SerializedName("wind_direction")
    @Expose
    private Double windDirection;
    @SerializedName("air_pressure")
    @Expose
    private Double airPressure;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("visibility")
    @Expose
    private Double visibility;


    public String getWeatherStateName() {
        return weatherStateName;
    }

    public String getWeatherStateAbbr() {
        return weatherStateAbbr;
    }

    public String getWindDirectionCompass() {
        return windDirectionCompass;
    }

    public Double getMinTemp() {

        return minTemp - minTemp % 1;
    }

    public Double getMaxTemp() {
        return maxTemp - maxTemp % 1;
    }

    public Double getTheTemp() {
        return theTemp - theTemp % 1;
    }

    public Double getWindSpeed() {
        return windSpeed - windSpeed % 1;
    }

    public Double getAirPressure() {
        return airPressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getVisibility() {
        return visibility - visibility % 1;
    }


}
