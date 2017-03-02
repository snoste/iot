package com.example.android.iot;

/**
 * Created by s168877 on 1/10/2017.
 */

import android.content.ContentValues;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static String mUrlSecurity="http://192.168.1.132:8080/api/security";
    //http://192.168.56.1:8080/api/security/Login/%7B%22Email%22:%22fafa@%22,%22Password%22:%22fagaa%22%7D

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the Leshan Server and return a list of Client objects.
     */
    public static List<LeshanClient> fetchLeshanClientData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }


        List<LeshanClient> leshanclients = extractFeatureFromJson(jsonResponse);


        return leshanclients;
    }

    public static DeviceIOT fetchDeviceData(String serverUrl,String ClientName,String ObjectUrl)
    {
        DeviceIOT Device =new DeviceIOT();
        String requestUrl=serverUrl+"/"+ClientName+ObjectUrl;
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }


        Device = extractDeviceDataFromJson(jsonResponse);



        return Device;

    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
public static String requestLogin(String details)
{ String Url=mUrlSecurity+"/Login/"+details;
    URL url = createUrl(Url);
    //makeHttpRequest(Url);

    String jsonResponse = null;
    try {
        jsonResponse = makeHttpRequest(url);
    } catch (IOException e) {
        Log.e(LOG_TAG, "Problem making Login request.", e);
    }

    return jsonResponse;
}

    public static void PostData(String Url)
    {

        URL url = createUrl(Url);
        //makeHttpRequest(Url);

        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }


/*

        try {
            URL url = createUrl(Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            conn.setRequestMethod("GET");
            conn.connect();

        } catch (IOException e) {
            e.printStackTrace();

        }
*/
    }



    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

public static DeviceIOT extractDeviceDataFromJson(String deviceObjectJSONResponse)

{
    if (TextUtils.isEmpty(deviceObjectJSONResponse)) {
        return null;
    }
    DeviceIOT device = new DeviceIOT();


    try {

        // Create a JSONObject from the JSON response string
        JSONObject baseJsonResponse = new JSONObject(deviceObjectJSONResponse);
        JSONObject content = baseJsonResponse.getJSONObject("content");
        JSONArray ResourceArray = content.getJSONArray("resources");

        for (int i = 0; i < ResourceArray.length(); i++) {


            JSONObject currentResource = ResourceArray.getJSONObject(i);

            String Id = currentResource.getString("id");
            String Value = currentResource.getString("value");

            device.addResource(Id,Value);

        }
    }
            catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }



            return  device;


}
    private static List<LeshanClient> extractFeatureFromJson(String leshanclientJSONResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(leshanclientJSONResponse)) {
            return null;
        }


        List<LeshanClient> leshanclients = new ArrayList<>();


        try {

            // Create a JSONObject from the JSON response string
            JSONArray clientArray = new JSONArray(leshanclientJSONResponse);




            for (int i = 0; i < clientArray.length(); i++) {


                JSONObject currentClient = clientArray.getJSONObject(i);

                String ClientName = currentClient.getString("endpoint");

                LeshanClient leshanclient = new LeshanClient(ClientName);
                JSONArray objectArray = currentClient.getJSONArray("objectLinks");


                for (int j=0;j<objectArray.length();j++)
                {

                    JSONObject currentObject = objectArray.getJSONObject(j);
                    String ObjectUrl = currentObject.getString("url");
                    leshanclient.addUrl(ObjectUrl);



                }

                leshanclient.UpdateDeviceData();

                leshanclients.add(leshanclient);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the Clientname JSON results", e);
        }

        // Return the list of earthquakes
        return leshanclients;
    }







}