package pl.bykowski.springbootclient;


import org.apache.el.stream.Stream;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class Client {

    //@EventListener(ApplicationReadyEvent.class)
    public void getDataFromApi() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User[]> exchange = restTemplate.exchange(
                "http://localhost:8080/api",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                User[].class);
        User[] body = exchange.getBody();
        Arrays.stream(body).forEach(System.out::println);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void getDataFromCurrencyApi() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Currency> exchange = restTemplate.exchange(
                "https://api.exchangeratesapi.io/latest?symbols=USD,GBP",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                Currency.class);
        System.out.println(exchange.getBody());
    }




    //@EventListener(ApplicationReadyEvent.class)
    public void deleteDataFromApi() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Boolean> exchange = restTemplate.exchange(
                "http://localhost:8080/api?id=1",
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Boolean.class);
        System.out.println(exchange.getBody());
    }

    public void addToApi() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        String json = "{\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"Joanna\",\n" +
                "        \"surname\": \"Malinowska\",\n" +
                "        \"age\": 23,\n" +
                "        \"sex\": \"female\"\n" +
                "    }";
        HttpEntity httpEntity = new HttpEntity(json, httpHeaders);
        restTemplate.exchange(
                "http://localhost:8080/api",
                HttpMethod.POST,
                httpEntity,
                Void.class);
    }
}
