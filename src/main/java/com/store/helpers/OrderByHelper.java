package com.store.helpers;

import com.store.enums.OrderBy;

public class OrderByHelper {

    public static OrderBy getOrderByOption(String key) {
        if (key.toLowerCase().equals("asc")) return OrderBy.ASC;
        return OrderBy.DESC;
    }

}
