package fastcampus.ecommerce.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import fastcampus.ecommerce.api.domain.payment.PaymentMethod;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderRequester {

  private static final String BASE_URL = "http://localhost:8080/api/v1";
  private static final String PRODUCTS_URL = BASE_URL + "/products";
  private static final String ORDERS_URL = BASE_URL + "/orders";
  private static final PaymentMethod[] PAYMENT_METHODS = PaymentMethod.values();
  private static final Random RANDOM = new Random();
  private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static void main(String[] args) {
    int maxWorker = 20;
    ExecutorService executor = Executors.newFixedThreadPool(maxWorker);
    List<CompletableFuture<Void>> futures = new ArrayList<>();

    try {
      int page = 0;
      int size = 1000;
      boolean hasNextPage = true;
      while (hasNextPage && page < 10000) {
        String productJson = fetchProduct(page, size);
      }
    } catch (Exception e) {
      System.out.println("ERROR! " + e.getMessage());
    }

    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    executor.shutdown();
  }

  private static String fetchProduct(int page, int size) throws IOException, InterruptedException {
    String url = String.format("%s?page=%d&size=%d&sort=productId,asc", PRODUCTS_URL, page, size);
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .header("Content-Type", "appliation/json")
        .GET()
        .build();
    HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
    return response.body();
  }
}
