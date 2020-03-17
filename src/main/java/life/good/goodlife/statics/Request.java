package life.good.goodlife.statics;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Request {
    private static StringBuffer result;
    private static BufferedReader reader;
    private static Logger logger = LoggerFactory.getLogger(Request.class);

    public static String get(String address, Map<String, String> headers) {
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String resultLine;
            result = new StringBuffer();
            while ((resultLine = reader.readLine()) != null) {
                result.append(resultLine);
            }
        } catch (IOException e) {
            System.out.println("Stream input error - " + e.getMessage());
            result = new StringBuffer(address);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Stream close error - " + e.getMessage());
                    result = new StringBuffer(address);
                }
            }
        }
        return result.toString();
    }

    public static String get(String address) {
        try {
            getConnect(address);
        } catch (IOException e) {
            System.out.println("Stream input error - " + e.getMessage());
            result = new StringBuffer(address);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Stream close error - " + e.getMessage());
                    result = new StringBuffer(address);
                }
            }
        }
        return result.toString();
    }

    public static String get(String address, String entity) {
        try {
            getConnect(address);
        } catch (IOException e) {
            System.out.println("Stream input error - " + e.getMessage());
            result = new StringBuffer(entity);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Stream close error - " + e.getMessage());
                    result = new StringBuffer(entity);
                }
            }
        }
        return result.toString();
    }

    private static void getConnect(String address) throws IOException {
        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String resultLine;
        result = new StringBuffer();
        while ((resultLine = reader.readLine()) != null) {
            result.append(resultLine);
        }
    }

    public static InputStream getImageStream(String url) {
        InputStream in = null;
        try {
            URL connection = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) connection.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(500);
            urlConnection.setReadTimeout(5000);
            urlConnection.addRequestProperty("User-Agent",
                    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
            urlConnection.connect();
            if (HttpURLConnection.HTTP_OK == urlConnection.getResponseCode()) {
                in = urlConnection.getInputStream();
            } else {
                System.out.println("Error: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            System.out.println("Failed to load image - " + e.getMessage());
        }
        return in;
    }

    public static String post(String address, Map<String, String> header, Map<String, String> body, String type) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        RequestBody requestBody;
        if ("raw".equals(type)) {
            requestBody = RequestBody.create(
                    MediaType.parse("application/json"), body.get("raw"));
        } else {
            for (Map.Entry<String, String> entry : body.entrySet()) {
                formBody.add(entry.getKey(), entry.getValue());
            }
            requestBody = formBody.build();
        }
        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder();
        for (Map.Entry<String, String> entry : header.entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }
        okhttp3.Request request = requestBuilder.url(address).post(requestBody).build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            assert response.body() != null;
            return response.body().string();
        } catch (IOException e) {
            logger.error("Request error - " + e.getMessage());
        }
        return null;
    }
}
