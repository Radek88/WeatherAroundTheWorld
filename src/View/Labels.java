package View;

import Controler.Model.WeatherForecastFromHttp.WeatherForecast;
import javafx.scene.control.Label;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Labels{

    WeatherForecast instance;

    public Labels(WeatherForecast instance, Label label1,Label label2,Label label3,Label label4,Label label5,Label label6) {
        this.instance = instance;
        setDaysLabels(instance,label1,label2,label3,label4,label5,label6);
    }

    public Labels() {
    }

    public void setDaysLabels(WeatherForecast instance, Label label1,Label label2,Label label3,Label label4,Label label5,Label label6) {
        //Prepare date received from server as String for convert to Date.utill format
        String dateString = instance.getTime().substring(0, 10);
        //Convert String to Date
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-mm-dd").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //List of Labels which will be changed acc to Day of week;
        ArrayList<Label> listOfLabels = getLabels(label1,label2,label3,label4,label5,label6);
        //List of days acc to number of day returned by Data class
        Map<Integer, String> weekDaysList = getDayOfWeekMap();
        //Get actual day
        Integer actualDayNumber = date.getDay();
        //Change labels
        changeLabels(listOfLabels, weekDaysList, actualDayNumber);


    }
    private void changeLabels(ArrayList<Label> listOfLabels, Map<Integer, String> weekDaysList, Integer
            actualDayNumber) {
        for (int i = 0; i < 6; i++) {
            listOfLabels.get(i).setText(weekDaysList.get(actualDayNumber));
            actualDayNumber++;
            if (actualDayNumber > 6) {
                actualDayNumber = 0;
            }
        }
    }

    private Map<Integer, String> getDayOfWeekMap() {
        Map<Integer, String> weekDaysList = new HashMap<>();
        weekDaysList.put(1, "Mon");
        weekDaysList.put(2, "Tue");
        weekDaysList.put(3, "Wed");
        weekDaysList.put(4, "Thu");
        weekDaysList.put(5, "Fri");
        weekDaysList.put(6, "Sat");
        weekDaysList.put(0, "Sun");
        return weekDaysList;
    }

    private ArrayList<Label> getLabels(Label label1,Label label2,Label label3,Label label4,Label label5,Label label6) {
        ArrayList<Label> listOfLabels = new ArrayList<>();
        listOfLabels.add(label1);
        listOfLabels.add(label2);
        listOfLabels.add(label3);
        listOfLabels.add(label4);
        listOfLabels.add(label5);
        listOfLabels.add(label6);
        return listOfLabels;
    }

}
