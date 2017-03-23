package net.proselyte.springsecurityapp.service;

import org.json.JSONObject;

/**
 * Created by Y50-70 on 19.03.2017.
 */
public interface JsonService {
    JSONObject readJsonFromUrl(String url);
}
