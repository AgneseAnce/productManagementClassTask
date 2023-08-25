import com.google.gson.Gson;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ProductService {

    private String BASE_URL;
    private final String PRODUCT_ENDPOINT = "/products";
    private Gson gson = new Gson();
    private HttpClient httpClient = HttpClient.newHttpClient();

    public ProductService(){
        this.loadAPIProperties();
    }

    public List<Product> getAllProducts() throws Exception{
          HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(this.BASE_URL + this.PRODUCT_ENDPOINT))
                .timeout(Duration.ofSeconds(30))
                 .header("Content-Type", "application/json")
                 .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200){
            throw new Exception("Request failed, API responded with code: " + response.statusCode());
        }

        List<Product> convertedListFromAPI = Arrays.asList(this.gson.fromJson(response.body(), Product[].class));
        return convertedListFromAPI;
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

    public void createProduct(Product product) throws Exception {

        String requestBody = gson.toJson(product);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI((this.BASE_URL + this.PRODUCT_ENDPOINT)))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201){
            throw new Exception("Request failed. Status code: " + response.statusCode());
        }

        Product createdProduct = gson.fromJson(response.body(), Product.class);

        if (createdProduct == null || createdProduct.get_id() == null){
            throw new Exception("Creation failed. Exception code: " + response.statusCode());
        }
    }

    public Product updateSingleProduct (Product product, String productID) throws Exception{
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(this.BASE_URL + this.PRODUCT_ENDPOINT + "/" + productID))
                .timeout(Duration.ofSeconds(30))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString
                                (this.gson.toJson(product)))
                .build();
        HttpResponse<String> response= this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200) {
            throw new Exception("Unable to update the product with ID " + productID
                    + ". Error code: " + response.statusCode());
        }
        return this.gson.fromJson(response.body(), Product.class);
    }

    public void deleteProductService (String productID) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(this.BASE_URL + this.PRODUCT_ENDPOINT + "/" + productID))
                .timeout(Duration.ofSeconds(30))
                .header("Accept", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200)
            throw new Exception("Unable to delete the product with id " + productID
                    + ". Error code: " + response.statusCode());
    }
}
