package Controler.Model;

import Controler.Model.WeatherForecastFromHttp.WeatherForecast;
import View.Alerts;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class GetWeatherForecast{
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final String baseURI = "https://www.metaweather.com/api/location/";
    private Integer woeidNO;
    private String uri;



    public GetWeatherForecast(Integer woeidNO) {
        this.woeidNO = woeidNO;
        this.uri = baseURI + woeidNO;
        this.sendGetAndReturnRespond();

    }

    private void close() {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendGetAndReturnRespond() {
        HttpGet request = new HttpGet(uri);
        String result = "";
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public WeatherForecast mapWeatherForecastToJavaClass() {
        Gson gson = new Gson();
        WeatherForecast weatherForecast = new WeatherForecast();
        try {
            weatherForecast = gson.fromJson(sendGetAndReturnRespond(), WeatherForecast.class);
        } catch (Exception e ){
            Alerts.cityIsNotAvailable();
        }
        return weatherForecast;

    }
}
