package com.divya.myFirstProject.thirdParty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class  MetalRateAPI {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public double fetchMetalSpotPrice(String metal, String currency) {
        try {
            String url = "https://goldbroker.com/api/spot-price?metal=" +metal+"&currency=" +currency+"&weight_unit=g";
            Request request = new Request.Builder().url(url).get().build();
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    SpotPriceResponse result = mapper.readValue(responseBody, SpotPriceResponse.class);
                    System.out.println("Fetched at " + result.date + " | Price (USD/g): " + result.value);
                    return result.value;
                } else {
                    System.err.println("API call failed with status: " + response.code());
                    return -1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static class SpotPriceResponse {
        public String date;
        public String weight_unit;
        public double ask;
        public double mid;
        public double bid;
        public double value;
        public double performance;
    }
}
