package Controler;

import Controler.Model.WeatherForecastFromHttp.ConsolidatedWeather;
import Controler.Model.WeatherForecastFromHttp.GetCityCode;
import Controler.Model.WeatherForecastFromHttp.WeatherForecast;
import View.Alerts;
import View.AutoCompleteTextField;
import View.CityList;
import View.Labels;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import Controler.Model.*;
import java.util.*;

public class Controller{
    @FXML
    private GridPane gridPaneBottom = new GridPane();
    @FXML
    private AutoCompleteTextField textField = new AutoCompleteTextField();
    @FXML
    private Pane mainPane;
    @FXML
    private Button search;
    @FXML
    private Label LCity, Day1, Day2, Day3, Day4, Day5, Day6;
    @FXML
    private ListView<CheckBox> listView;
    @FXML
    private ListView<String> listOfLocation;
    @FXML
    private GridPane gridPaneData;
    @FXML
    private ObservableList<CheckBox> listOfCheckboxes;
    private ObservableList<String> listOfCityNames;
    private Map<String, String> weatherValueHashMap;
    private WeatherForecast defaultInstance ;
    private Labels dayNameLabels;


    public void initialize() {
        CityList.addAllCity();
        loadAutoCompletionList();
        loadDataComboBox();
        loadCityNamesList();
        gridPaneBottom.autosize();
        mainPane.autosize();
    }



    @FXML
    void searchButtonClick() {
        clearAll();
        String cityNameLowerCase = getCityNameWithoutInterruptions();
        primarySetupOfWeatherForecast(cityNameLowerCase);

    }

    // Return string without additional white spaces
    private String getCityNameWithoutInterruptions() {

        String cityName = textField.getText();
        return cityName.toLowerCase().trim().replaceAll("\\s+", " ");
    }

    private void primarySetupOfWeatherForecast(String cityNameLowerCase) {

        if (!cityNameLowerCase.isEmpty() && CityList.anyMatch(cityNameLowerCase)) {
            defaultInstance = returnWeatherForecastObject(cityNameLowerCase);
            LCity.setText(LCity.getText() + " " + cityNameLowerCase.toUpperCase());
            dayNameLabels = new Labels(defaultInstance, Day1,Day2,Day3,Day4,Day5,Day6);
            dayNameLabels.setDaysLabels(defaultInstance, Day1,Day2,Day3,Day4,Day5,Day6);
            showSelectedParameters(defaultInstance);

        } else {
            Alerts.cityIsNotAvailable();
        }
    }

    @FXML
    void enterTap(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            searchButtonClick();
        }

    }

    //Send request to Http using city name -> look class GetCityCode
    WeatherForecast returnWeatherForecastObject(String cityName) {
        GetCityCode getCityCode = new GetCityCode(cityName);
        GetWeatherForecast getWeatherForecast = new GetWeatherForecast(getCityCode.returnWoeidNo());
        getWeatherForecast.sendGetAndReturnRespond();
        return getWeatherForecast.mapWeatherForecastToJavaClass();
    }

    List<ConsolidatedWeather> returnSixDayPrediction(WeatherForecast weatherForecast) {
        return weatherForecast.getConsolidatedWeather();
    }

    //Preparing list of combobox for user, to choice which data will be display in report.
    void loadDataComboBox() {
        listOfCheckboxes = FXCollections.observableArrayList();
        listOfCheckboxes.addAll(new CheckBox("Weather state")
                , new CheckBox("Average temperature")
                , new CheckBox("Maximum temperature")
                , new CheckBox("Minimum temperature")
                , new CheckBox("Wind speed")
                , new CheckBox("Wind direction")
                , new CheckBox("Air pressure")
                , new CheckBox("Visibility")
                , new CheckBox("Humidity"));
        listView.setItems(listOfCheckboxes);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listOfCheckboxes.forEach(cbox -> cbox.setSelected(true));
    }




    private void loadCityNamesList() {

        listOfCityNames = FXCollections.observableArrayList();
        listOfCityNames.addAll(CityList.returnOryginalCityList());
        listOfLocation.setItems(listOfCityNames);
        listOfLocation.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }
    private void loadAutoCompletionList() {
        textField.getEntries().addAll(CityList.returnOryginalCityList());
        textField.getEntries().addAll(CityList.returnCityListLowerCases());
    }

    @FXML
    void choseCityFromList(MouseEvent event) {
        if (event.getClickCount() == 2) {
            textField.setText(listOfLocation.getSelectionModel().getSelectedItem());
        }
    }



    //Display all selected parameters of weather forecast acc to user choice.
    private void showSelectedParameters(WeatherForecast instance) {
        int rowIndex = 1;
        int columnIndex = 1;
        gridPaneData.autosize();
        for (CheckBox cb : listOfCheckboxes
        ) {
            if (cb.isSelected()) {
                gridPaneData.addRow(rowIndex, new Text(" " + cb.getText() + "\n"));
                rowIndex++;
            }
        }
        rowIndex = 1;
        for (
                int numberOfDay = 0;
                numberOfDay < 6; numberOfDay++) {
            buildActualWeatherValueMapAccToMarkedOption(instance, numberOfDay);
            for (CheckBox tmpChbox : listOfCheckboxes) {
                if (tmpChbox.isSelected()) {
                    String stringToAdd = weatherValueHashMap.get(tmpChbox.getText());
                    gridPaneData.add(new Text(stringToAdd + "\n"), columnIndex, rowIndex);
                    setWeatherIcons(columnIndex, numberOfDay);
                    rowIndex++;
                }
            }
            columnIndex++;
            rowIndex = 1;
        }

    }

    private void setWeatherIcons(int columnIndex, int numberOfDay) {
        gridPaneData.add(returnIconAccToWeatherStateAberration(defaultInstance.getConsolidatedWeather().get(numberOfDay).getWeatherStateAbbr()), columnIndex, 0);
    }

    //Method use for preparing hash map with values of weather forecast model fields
    private void buildActualWeatherValueMapAccToMarkedOption(WeatherForecast instance, Integer numberOfDay) {
        weatherValueHashMap = new HashMap<>();
        weatherValueHashMap.put("Weather state",
                instance.getConsolidatedWeather().get(numberOfDay).getWeatherStateName());
        weatherValueHashMap.put("Average temperature",
                instance.getConsolidatedWeather().get(numberOfDay).getTheTemp().toString());
        weatherValueHashMap.put("Maximum temperature",
                instance.getConsolidatedWeather().get(numberOfDay).getMaxTemp().toString());
        weatherValueHashMap.put("Minimum temperature",
                instance.getConsolidatedWeather().get(numberOfDay).getMinTemp().toString());
        weatherValueHashMap.put("Wind speed",
                instance.getConsolidatedWeather().get(numberOfDay).getWindSpeed().toString());
        weatherValueHashMap.put("Air pressure",
                instance.getConsolidatedWeather().get(numberOfDay).getAirPressure().toString());
        weatherValueHashMap.put("Humidity",
                instance.getConsolidatedWeather().get(numberOfDay).getHumidity().toString());
        weatherValueHashMap.put("Wind direction",
                instance.getConsolidatedWeather().get(numberOfDay).getWindDirectionCompass());
        weatherValueHashMap.put("Visibility",
                instance.getConsolidatedWeather().get(numberOfDay).getVisibility().toString());


    }

    private void clearAll() {
        defaultInstance = new WeatherForecast();
        gridPaneData.getChildren().remove(0, gridPaneData.getChildren().size());
        LCity.setText("Weather forecast:");
    }

    private ImageView returnIconAccToWeatherStateAberration(String aberration) {
        String url = "";

        switch (aberration) {
            case "c":
                url = "c.png";
                break;
            case "h":
                url = "h.png";
                break;
            case "hc":
                url = "hc.png";
                break;
            case "hr":
                url = "hr.png";
                break;
            case "lc":
                url = "lc.png";
                break;
            case "lr":
                url = "lr.png";
                break;
            case "s":
                url = "s.png";
                break;
            case "sl":
                url = "sl.png";
                break;
            case "sn":
                url = "sn.png";
                break;
            case "t":
                url = "t.png";
                break;
        }
        ImageView imageView = new ImageView();
        Image iconImage = new Image(url);
        imageView.setImage(iconImage);

        return imageView;
    }


}
