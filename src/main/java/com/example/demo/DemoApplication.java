package com.example.demo;

import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.security.KeyStore;
import java.util.function.Supplier;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {

        char[] password = "changeit".toCharArray();

        SSLContext sslContext = SSLContextBuilder.create()
                .loadKeyMaterial(keyStore("src/main/resources/client.jks", password), password)
                .loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();

        CloseableHttpClient client = HttpClients.custom().setSSLContext(sslContext).build();

        HttpComponentsClientHttpRequestFactory requestFactory
                = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(client);

        return new RestTemplate(requestFactory);
    }

    private KeyStore keyStore(String file, char[] password) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        File key = ResourceUtils.getFile(file);
        try (InputStream in = new FileInputStream(key)) {
            keyStore.load(in, password);
        }
        return keyStore;
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {

        return args -> {
            Vehicle vehicle = restTemplate.getForObject(
                    "https://localhost:8080/vehicle", Vehicle.class);
           System.out.println(vehicle.toString());
        };
    }


}
