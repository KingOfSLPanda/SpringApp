package net.proselyte.springsecurityapp.service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Y50-70 on 25.03.2017.
 */
public interface CloudinaryService {
    Map saveSomething() throws IOException;
    Map changeSize(int w, int h);
    String sepia(Map map);
}
