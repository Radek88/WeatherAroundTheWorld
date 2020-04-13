package Controler.Model.WeatherForecastFromHttp;
import View.Alerts;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.NoSuchElementException;

public class GetCityCode{
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final String baseURI = "https://www.metaweather.com/api/location/search/?query=";
    private String city;
    private String uri;


    public GetCityCode(String cityName) {
        this.city = cityName.replaceAll("\\s+", "+");
        this.uri = baseURI + city;
    }


    private void close() {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String sendGetAndReturnRespond() {
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

    public Integer returnWoeidNo() {
        CityModel cityModel = new CityModel();
        String respondFromHTTPServer = this.sendGetAndReturnRespond();
        this.close();
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<CityModel>>(){
        }.getType();
        Collection<CityModel> collection = gson.fromJson(respondFromHTTPServer, collectionType);
        try {
            cityModel = collection.iterator().next();
        } catch (NoSuchElementException e) {
            Alerts.cityIsNotAvailable();

        }
        return cityModel.getWoeid();
    }


}
