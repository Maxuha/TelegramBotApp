package life.good.goodlife.statics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class Request {
    private static StringBuffer result;
    private static BufferedReader reader;

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
}
