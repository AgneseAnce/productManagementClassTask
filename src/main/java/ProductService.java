import com.google.gson.Gson;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.net.http.HttpClient;

public class ProductService {

    private String BASE_URL;
    private final String PRODUCT_ENDPOINT = "/todos";
    private Gson gson = new Gson();
    private HttpClient httpClient = HttpClient.newHttpClient();

    public ProductService(){
        this.loadAPIProperties();
    }

    private void loadAPIProperties() {
        try {
            PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
            propertiesConfiguration.load("application.properties");
            this.BASE_URL = propertiesConfiguration.getString("api.url");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
