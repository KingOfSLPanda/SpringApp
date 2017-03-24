package net.proselyte.springsecurityapp.service;

import org.json.JSONObject;

public interface JsonService {
    JSONObject readJsonFromUrl(String url);
    JSONObject sendPost() throws Exception;
}
