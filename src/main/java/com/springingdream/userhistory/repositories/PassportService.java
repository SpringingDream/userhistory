package com.springingdream.userhistory.repositories;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.springingdream.userhistory.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class PassportService {
    private final RestTemplate template;

    private String url = "http://my.dekinci.com:10004/api/passport";

    @Autowired
    public PassportService(RestTemplate template, @Qualifier("eurekaClient") EurekaClient eurekaClient) {
        Application application = eurekaClient.getApplication("marketplace-passport");
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String ip = instanceInfo.getHostName();
        int port = instanceInfo.getPort();
//        url = instanceInfo.getHomePageUrl() + "api/passport";
        this.template = template;
    }

    public boolean checkUserExists(Long pid) {
        try {
            User user = template.getForObject(url + "/" + pid, User.class);
            return user != null && user.getUid() != null;
        } catch (RestClientException e) {
            return false;
        }
    }
}
