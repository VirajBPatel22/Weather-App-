//package MyPackage;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Date;
//import java.util.Scanner;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//
///**
// * Servlet implementation class MyServlet
// */
//public class MyServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public MyServlet() {
//        super();
//    }
//
//    /**
//     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//     */
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.getWriter().append("Served at: ").append(request.getContextPath());
//    }
//
//    /**
//     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//     */
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String apiKey = "6314080a720b694092334cfadf13eed1";
//        String city = request.getParameter("city");
//        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
//        URL url = new URL(apiUrl);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//
//        try (InputStream inputStream = connection.getInputStream();
//             InputStreamReader reader = new InputStreamReader(inputStream);
//             Scanner scanner = new Scanner(reader)) {
//
//            StringBuilder responseContent = new StringBuilder();
//            while (scanner.hasNext()) {
//                responseContent.append(scanner.nextLine());
//            }
//
//            int responseCode = connection.getResponseCode();
//            if (responseCode == 200) {
//                Gson gson = new Gson();
//                JsonObject jsonObject = gson.fromJson(responseContent.toString(), JsonObject.class);
//
//                long dateTimestamp = jsonObject.get("dt").getAsLong() * 1000;
//                String date = new Date(dateTimestamp).toString();
//                double temperatureKelvin = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
//                int temperatureCelsius = (int) (temperatureKelvin - 273.15);
//                int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
//                double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
//                String weatherCondition = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
//                String iconCode = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
//
//                request.setAttribute("date", date);
//                request.setAttribute("city", city);
//                request.setAttribute("temperature", temperatureCelsius);
//                request.setAttribute("weatherCondition", weatherCondition);
//                request.setAttribute("humidity", humidity);
//                request.setAttribute("windSpeed", windSpeed);
//                request.setAttribute("weatherIcon", "https://openweathermap.org/img/wn/" + iconCode + "@2x.png");
//                request.setAttribute("weatherData", responseContent.toString());
//            } else {
//                request.setAttribute("errorMessage", "City not found. Please enter a valid city name.");
//            }
//
//            request.getRequestDispatcher("index.jsp").forward(request, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.setAttribute("errorMessage", "City not found. Please enter a valid city name.");
//            request.getRequestDispatcher("index.jsp").forward(request, response);
//        } finally {
//            connection.disconnect();
//        }
//    }
//}




package MyPackage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class MyServlet
 */
public class MyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String apiKey = "6314080a720b694092334cfadf13eed1";
        String city = request.getParameter("city");
        // URL-encode the city name
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + encodedCity + "&appid=" + apiKey;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (InputStream inputStream = connection.getInputStream();
             InputStreamReader reader = new InputStreamReader(inputStream);
             Scanner scanner = new Scanner(reader)) {

            StringBuilder responseContent = new StringBuilder();
            while (scanner.hasNext()) {
                responseContent.append(scanner.nextLine());
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(responseContent.toString(), JsonObject.class);

                long dateTimestamp = jsonObject.get("dt").getAsLong() * 1000;
                String date = new Date(dateTimestamp).toString();
                double temperatureKelvin = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
                int temperatureCelsius = (int) (temperatureKelvin - 273.15);
                int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
                double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
                String weatherCondition = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
                String iconCode = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();

                request.setAttribute("date", date);
                request.setAttribute("city", city);
                request.setAttribute("temperature", temperatureCelsius);
                request.setAttribute("weatherCondition", weatherCondition);
                request.setAttribute("humidity", humidity);
                request.setAttribute("windSpeed", windSpeed);
                request.setAttribute("weatherIcon", "https://openweathermap.org/img/wn/" + iconCode + "@2x.png");
                request.setAttribute("weatherData", responseContent.toString());
            } else {
                request.setAttribute("errorMessage", "City not found. Please enter a valid city name.");
            }

            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "City not found. Please enter a valid city name.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } finally {
            connection.disconnect();
        }
    }
}
