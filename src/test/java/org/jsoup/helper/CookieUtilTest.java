package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the functionality of the CookieUtil class.
 * It includes tests for applying cookies to a request, converting URLs to URIs, and storing cookies.
 */
public class CookieUtilTest {

    @Test
    public void testApplyCookiesToRequest() throws IOException {
        HttpConnection.Request req = new HttpConnection.Request();
        HttpURLConnection con = (HttpURLConnection) new URL("http://test.com").openConnection();
        req.cookie("name", "value");

        CookieUtil.applyCookiesToRequest(req, con);

        assertEquals("name=value", con.getRequestProperty("Cookie"));
    }

    @Test
    public void testAsUri() throws IOException, URISyntaxException {
        URL url = new URL("http://test.com");
        assertEquals(url.toURI(), CookieUtil.asUri(url));
    }

    @Test
    public void testStoreCookies() throws IOException {
        HttpConnection.Request req = new HttpConnection.Request();
        URL url = new URL("http://test.com");
        Map<String, List<String>> resHeaders = new HashMap<>();
        resHeaders.put("Set-Cookie", Collections.singletonList("name=value"));

        CookieUtil.storeCookies(req, url, resHeaders);

        assertEquals("value", req.cookie("name"));
    } 
}