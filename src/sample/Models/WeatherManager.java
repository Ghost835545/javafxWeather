package sample.Models;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class WeatherManager {

    private JSONArray json_specific;
    public ArrayList<String> str;
    public ArrayList<String> temp0;
    public ArrayList<Float> razn;
    public Float max;
    public long minDay;

    public WeatherManager(String city) {

    }
    public String convertUTC(long d){

        long unixSeconds = d;
// convert seconds to milliseconds
        Date date = new java.util.Date(unixSeconds*1000L);
// the format of your date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("d MMMM, yyyy");
// give a timezone reference for formatting (see comment at the bottom)
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("Europe/Moscow"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
    //Build a String from the read Json file
    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    //Reads and returns the JsonObject
    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
    private Float tofloat(String a){
        try {
            float number = Float.parseFloat(a);
            return  number;
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
       return 0f;
    }
    private long toLong(String a){
        try {
            long number = Long.parseLong(a);
            return  number;
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0L;
    }
    //method to get the weather of the selected city
    public Boolean getWeather(String latitude, String longitude){
        str = new ArrayList<>();
        temp0 = new ArrayList<>();
        razn = new ArrayList<>();;

        boolean success = true;
        int d = 0;

        JSONObject json;
         //get specific data in jsonobject variable
        JSONObject json_temp;

        SimpleDateFormat df2 = new SimpleDateFormat("EEEE", Locale.ENGLISH); //Entire word/day as output
        Calendar c = Calendar.getInstance();

        //connects and asks the api to sen the json file
        try {
            json = readJsonFromUrl("https://api.openweathermap.org/data/2.5/onecall?lat="+latitude+"&lon="+longitude+"&units=metric"+"&appid=33e9b641515014c78d7f2f6114f0cf5d");
        } catch (IOException e) {
            return false;
        }

        //receives the particular data in the read Json File
        json_specific = json.getJSONArray("daily");
        max = tofloat(json_specific.getJSONObject(0).get("pressure").toString());
        float minRazn = tofloat(json_specific.getJSONObject(0).getJSONObject("temp").get("night").toString()) -
                tofloat(json_specific.getJSONObject(0).getJSONObject("temp").get("morn").toString());
        minDay = toLong(json_specific.getJSONObject(0).get("dt").toString());

        str.add(convertUTC(toLong(json_specific.getJSONObject(0).get("dt").toString())));
        temp0.add(json_specific.getJSONObject(0).get("pressure").toString());
        razn.add(minRazn);


        for (int i = 1; i<5; i++){
            if (tofloat(json_specific.getJSONObject(i).get("pressure").toString())>max) {
                max = tofloat(json_specific.getJSONObject(i).get("pressure").toString());
                convertUTC(toLong(json_specific.getJSONObject(i).get("dt").toString()));
            }

            if (minRazn > computeRazn(i)){
                minRazn = computeRazn(i);
                minDay = toLong(json_specific.getJSONObject(i).get("dt").toString());
            }
            razn.add(computeRazn(i));
            str.add(convertUTC(toLong(json_specific.getJSONObject(i).get("dt").toString())));
            temp0.add(json_specific.getJSONObject(i).get("pressure").toString());

        }

        return true;
    }

    private float computeRazn(Integer i){
        float r = tofloat(json_specific.getJSONObject(i).getJSONObject("temp").get("night").toString()) -
                tofloat(json_specific.getJSONObject(i).getJSONObject("temp").get("morn").toString());
        if (r < 0) r= -r;
        return r;

    }


}
