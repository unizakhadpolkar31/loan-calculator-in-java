import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class CaptchaVerifier {
    public static boolean verifyCaptcha(String captchaResponse, String secretKey) {
        try {
            String url = "https://www.google.com/recaptcha/api/siteverify";
            String data = "secret=" + secretKey + "&response=" + captchaResponse;
            
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            
            try (OutputStream os = connection.getOutputStream()) {
                os.write(data.getBytes("UTF-8"));
            }
            
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String response = br.lines().collect(Collectors.joining());
                // Parse and handle the reCAPTCHA response here
                // Check if the 'success' field is true
                // You may also want to check the 'score' and other fields for additional security.
                // For example: JSONObject json = new JSONObject(response);
                // boolean success = json.getBoolean("success");
                // double score = json.getDouble("score");
                // ...
                return success;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
