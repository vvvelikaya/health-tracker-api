package com.diploma.web.rest.util;

import org.springframework.http.HttpHeaders;

public class HeaderUtil {

    public static HttpHeaders createEntityCreationAlert(String entity, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Info-message", entity + " created");
        headers.add("Info-param", param);
        return headers;
    }

    public static HttpHeaders createEntityUpdateAlert(String entity, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Info-message", entity + " updated");
        headers.add("Info-param", param);
        return headers;
    }

    public static HttpHeaders createEntityDeletionAlert(String entity, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Info-message", entity + " deleted");
        headers.add("Info-param", param);
        return headers;
    }
}
