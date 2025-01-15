package com.demo.shopping_cart_service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnyObject {

    private String name;
    private Integer num;
    private List<String> values;

    public AnyObject(String name, Integer num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public Integer getNum() {
        return num;
    }

    public List<String> getValues() {
        return values;
    }

}
