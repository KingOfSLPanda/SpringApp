package net.proselyte.springsecurityapp.service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Y50-70 on 25.03.2017.
 */
public interface CloudinaryService {
    Map saveSomething() throws IOException;
    String changeSize(String url) throws IOException;
    String changeGray(String url) throws IOException;
    String changeSepia(String url) throws IOException;
}
