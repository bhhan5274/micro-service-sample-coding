package io.github.bhhan.example.apigateway.customer;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

/**
 * Created by hbh5274@gmail.com on 2021-01-14
 * Github : http://github.com/bhhan5274
 */

@ConfigurationProperties(prefix = "customer.destinations")
public class CustomerDestinations {

    @NotNull
    private String customerServiceUrl;

    public String getCustomerServiceUrl() {
        return customerServiceUrl;
    }

    public void setCustomerServiceUrl(String customerServiceUrl) {
        this.customerServiceUrl = customerServiceUrl;
    }
}

