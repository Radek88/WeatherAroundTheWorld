package Controler.Model.WeatherForecastFromHttp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Source{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("crawl_rate")
    @Expose
    private Integer crawlRate;


}
