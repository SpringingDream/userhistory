package com.springingdream.userhistory.repositories;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.springingdream.userhistory.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductsService {
    private final RestTemplate template;

    private String url = "http://my.dekinci.com:10000/products";

    @Autowired
    public ProductsService(RestTemplate template, @Qualifier("eurekaClient") EurekaClient eurekaClient) {
        Application application = eurekaClient.getApplication("marketplace-products");
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String hostname = instanceInfo.getHostName();
        int port = instanceInfo.getPort();
//        url = "http://" + hostname + ":" + port + "/products";
        this.template = template;
    }

    public boolean checkProductExists(Long pid) {
        try {
            Product product = template.getForObject(url + "/" + pid, Product.class);
            return product != null && product.getId() != null;
        } catch (RestClientException e) {
            return false;
        }
    }

    @Bean
    public static RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
