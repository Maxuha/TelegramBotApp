package life.good.goodlife.statics;

import java.io.BufferedReader;
import java.io.IOException;
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
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1");
        connection.setRequestProperty("Accept",
                " text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("Accept-Language", "en-us,en;q=0.5");
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String resultLine;
        result = new StringBuffer();
        while ((resultLine = reader.readLine()) != null) {
            result.append(resultLine);
        }
    }
}
