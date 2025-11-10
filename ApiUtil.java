import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ApiUtil {
    public static JSONObject fetchBookInfo(String isbn) {
        try {
            String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            JSONObject json = new JSONObject(content.toString());
            if (json.has("items")) {
                JSONObject item = json.getJSONArray("items").getJSONObject(0);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                JSONObject result = new JSONObject();
                result.put("title", volumeInfo.optString("title", ""));
                result.put("authors", volumeInfo.optJSONArray("authors"));
                result.put("description", volumeInfo.optString("description", ""));
                result.put("imageLinks", volumeInfo.optJSONObject("imageLinks"));
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
