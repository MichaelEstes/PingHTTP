package com.michaelestes.pinghttp;

import android.support.v4.util.Pair;

import org.junit.Test;

import java.util.ArrayList;

import okhttp3.internal.SslContextBuilder;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by Michael.Estes on 7/19/16.
 */

public class PingHTTPTests {

    @Test
    public void getValidTest() {
        Get get = new Get("url", "endpoint");
        assertTrue(get.isValid());
    }

    @Test
    public void getInvalidTest() {
        Get get = new Get("", "");
        assertFalse(get.isValid());
    }

    @Test
    public void getInvalidTestTwo() {
        Get get = new Get("url", "");
        assertFalse(get.isValid());
    }

    @Test
    public void getInvalidTestThree() {
        Get get = new Get(null, "");
        assertFalse(get.isValid());
    }

    @Test
    public void postValidTest() {
        Post post = new Post("url", "endpoint", "body");
        assertTrue(post.isValid());
    }

    @Test
    public void postValidTestTwo() {
        ArrayList<Pair<String, String>> params = new ArrayList<>();
        params.add(new Pair<>("testParamOne", "testParamOne"));
        params.add(new Pair<>("testParamTwo", "testParamTwo"));
        Post post = new Post("url", "endpoint", "body", params, 0);
        assertTrue(post.isValid());
    }

    @Test
    public void postInvalidTest() {
        Post post = new Post("", "", "");
        assertFalse(post.isValid());
    }

    @Test
    public void postInvalidTestTwo() {
        Post post = new Post("url", "", "");
        assertFalse(post.isValid());
    }

    @Test
    public void postInvalidTestThree() {
        Post post = new Post("url", "endpoint", "");
        assertFalse(post.isValid());
    }


    @Test
    public void reqInvalidTest() {
        Get get = new Get("", "");
        assertFalse(get.req() == new Pair<>("Error", -1));
    }

    @Test
    public void connectionGetHTTPValidTest() {
        try{
            MockWebServer server = new MockWebServer();
            server.enqueue(new MockResponse().setBody("").setResponseCode(200));
            server.start();
            String url = "http://" + server.getHostName() + ":" + server.getPort();

            ArrayList<Pair<String, String>> params = new ArrayList<>();
            params.add(new Pair<>("testParamOne", "testParamOne"));
            params.add(new Pair<>("testParamTwo", "testParamTwo"));

            ArrayList<Pair<String, String>> properties = new ArrayList<>();
            properties.add(new Pair<>("Test-Authorization", "testAuth"));
            properties.add(new Pair<>("test-api-key", "testApiKey"));

            Get get = new Get(url, "/get", properties, params);
            Pair<String, Integer> response = get.req();

            assertTrue(response.second == 200);
            server.shutdown();
        }catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void connectionGetHTTPNoParamsValidTest() {
        try{
            MockWebServer server = new MockWebServer();
            server.enqueue(new MockResponse().setBody("").setResponseCode(200));
            server.start();
            String url = "http://" + server.getHostName() + ":" + server.getPort();

            ArrayList<Pair<String, String>> properties = new ArrayList<>();
            properties.add(new Pair<>("Test-Authorization", "testAuth"));
            properties.add(new Pair<>("test-api-key", "testApiKey"));

            Get get = new Get(url, "/get", properties);
            Pair<String, Integer> response = get.req();

            assertTrue(response.second == 200);
            server.shutdown();
        }catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void connectionGetHTTPBadParamsValidTest() {
        try{
            MockWebServer server = new MockWebServer();
            server.enqueue(new MockResponse().setBody("").setResponseCode(200));
            server.start();
            String url = "http://" + server.getHostName() + ":" + server.getPort();

            ArrayList<Pair<String, String>> params = new ArrayList<>();

            ArrayList<Pair<String, String>> properties = new ArrayList<>();
            properties.add(new Pair<>("Test-Authorization", "testAuth"));
            properties.add(new Pair<>("test-api-key", "testApiKey"));

            Get get = new Get(url, "/get", properties, params);
            Pair<String, Integer> response = get.req();

            assertTrue(response.second == 200);
            server.shutdown();
        }catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void connectionGetHTTPNoPropertiesValidTest() {
        try{
            MockWebServer server = new MockWebServer();
            server.enqueue(new MockResponse().setBody("").setResponseCode(200));
            server.start();
            String url = "http://" + server.getHostName() + ":" + server.getPort();

            Get get = new Get(url, "/get", null);
            Pair<String, Integer> response = get.req();

            assertTrue(response.second == 200);
            server.shutdown();
        }catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void connectionGetHTTPNoPropertiesWithParamsValidTest() {
        try{
            MockWebServer server = new MockWebServer();
            server.enqueue(new MockResponse().setBody("").setResponseCode(200));
            server.start();
            String url = "http://" + server.getHostName() + ":" + server.getPort();

            ArrayList<Pair<String, String>> params = new ArrayList<>();
            params.add(new Pair<>("testParamOne", "testParamOne"));
            params.add(new Pair<>("testParamTwo", "testParamTwo"));

            Get get = new Get(url, "/get", params, 0);
            Pair<String, Integer> response = get.req();

            assertTrue(response.second == 200);
            server.shutdown();
        }catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void connectionGetHTTPNoPropertiesValidTestTwo() {
        try{
            MockWebServer server = new MockWebServer();
            server.enqueue(new MockResponse().setBody("").setResponseCode(200));
            server.start();
            String url = "http://" + server.getHostName() + ":" + server.getPort();

            ArrayList<Pair<String, String>> properties = new ArrayList<>();
            Get get = new Get(url, "/get", properties);
            Pair<String, Integer> response = get.req();

            assertTrue(response.second == 200);
            server.shutdown();
        }catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void connectionGetHTTPSTest() {
        try{
            MockWebServer server = new MockWebServer();
            server.enqueue(new MockResponse().setBody("").setResponseCode(200));
            server.useHttps(SslContextBuilder.localhost().getSocketFactory(), false);
            server.start();
            String url = "https://" + server.getHostName() + ":" + server.getPort();

            ArrayList<Pair<String, String>> params = new ArrayList<>();
            params.add(new Pair<>("testParamOne", "testParamOne"));
            params.add(new Pair<>("testParamTwo", "testParamTwo"));

            ArrayList<Pair<String, String>> properties = new ArrayList<>();
            properties.add(new Pair<>("Test-Authorization", "testAuth"));
            properties.add(new Pair<>("test-api-key", "testApiKey"));

            Get get = new Get(url, "/get", properties, params);
            Pair<String, Integer> response = get.req();

            assertTrue(response.second == -1);
            server.shutdown();
        }catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void connectionGetInvalidTest() {
        ArrayList<Pair<String, String>> params = new ArrayList<>();
        params.add(new Pair<>("testParamOne", "testParamOne"));
        params.add(new Pair<>("testParamTwo", "testParamTwo"));

        ArrayList<Pair<String, String>> properties = new ArrayList<>();
        properties.add(new Pair<>("Test-Authorization", "testAuth"));
        properties.add(new Pair<>("test-api-key", "testApiKey"));

        Get get = new Get("badurl", "/get", properties, params);
        Pair<String, Integer> response = get.req();

        assertTrue(response.second == -1);
    }

    @Test
    public void connectionGetBadResponseInvalidTest() {
        try{
            MockWebServer server = new MockWebServer();
            server.enqueue(new MockResponse().setBody("").setResponseCode(500));
            server.start();
            String url = "http://" + server.getHostName() + ":" + server.getPort();

            ArrayList<Pair<String, String>> properties = new ArrayList<>();
            Get get = new Get(url, "/get", properties);
            Pair<String, Integer> response = get.req();

            assertTrue(response.second == 500);
            server.shutdown();
        }catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void connectionPostHTTPValidTest() {
        try{
            MockWebServer server = new MockWebServer();
            server.enqueue(new MockResponse().setBody("").setResponseCode(200));
            server.start();
            String url = "http://" + server.getHostName() + ":" + server.getPort();

            ArrayList<Pair<String, String>> params = new ArrayList<>();
            params.add(new Pair<>("testParamOne", "testParamOne"));
            params.add(new Pair<>("testParamTwo", "testParamTwo"));

            ArrayList<Pair<String, String>> properties = new ArrayList<>();
            properties.add(new Pair<>("Test-Authorization", "testAuth"));
            properties.add(new Pair<>("test-api-key", "testApiKey"));

            String body = "Test body";

            Post post = new Post(url, "/post", body ,properties, params);
            Pair<String, Integer> response = post.req();

            assertTrue(response.second == 200);
            server.shutdown();
        }catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void connectionPostHTTPNoParamsValidTest() {
        try{
            MockWebServer server = new MockWebServer();
            server.enqueue(new MockResponse().setBody("").setResponseCode(200));
            server.start();
            String url = "http://" + server.getHostName() + ":" + server.getPort();

            ArrayList<Pair<String, String>> properties = new ArrayList<>();
            properties.add(new Pair<>("Test-Authorization", "testAuth"));
            properties.add(new Pair<>("test-api-key", "testApiKey"));

            String body = "Test body";

            Post post = new Post(url, "/post", body ,properties);
            Pair<String, Integer> response = post.req();

            assertTrue(response.second == 200);
            server.shutdown();
        }catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void connectionPostHTTPSTest() {
        try{
            MockWebServer server = new MockWebServer();
            server.enqueue(new MockResponse().setBody("").setResponseCode(200));
            server.useHttps(SslContextBuilder.localhost().getSocketFactory(), false);
            server.start();
            String url = "https://" + server.getHostName() + ":" + server.getPort();

            ArrayList<Pair<String, String>> params = new ArrayList<>();
            params.add(new Pair<>("testParamOne", "testParamOne"));
            params.add(new Pair<>("testParamTwo", "testParamTwo"));

            ArrayList<Pair<String, String>> properties = new ArrayList<>();
            properties.add(new Pair<>("Test-Authorization", "testAuth"));
            properties.add(new Pair<>("test-api-key", "testApiKey"));

            String body = "Test body";

            Post post = new Post(url, "/post", body ,properties, params);
            Pair<String, Integer> response = post.req();

            assertTrue(response.second == -1);
            server.shutdown();
        }catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void connectionPostInvalidTest() {
        ArrayList<Pair<String, String>> params = new ArrayList<>();
        params.add(new Pair<>("testParamOne", "testParamOne"));
        params.add(new Pair<>("testParamTwo", "testParamTwo"));

        ArrayList<Pair<String, String>> properties = new ArrayList<>();
        properties.add(new Pair<>("Test-Authorization", "testAuth"));
        properties.add(new Pair<>("test-api-key", "testApiKey"));

        String badBody = "";

        Post post = new Post("badurl", "/get", badBody, properties, params);
        Pair<String, Integer> response = post.req();

        assertTrue(response.second == -1);
    }

    @Test
    public void connectionPostInvalidTestTwo() {
        ArrayList<Pair<String, String>> params = new ArrayList<>();
        params.add(new Pair<>("testParamOne", "testParamOne"));
        params.add(new Pair<>("testParamTwo", "testParamTwo"));

        ArrayList<Pair<String, String>> properties = new ArrayList<>();
        properties.add(new Pair<>("Test-Authorization", "testAuth"));
        properties.add(new Pair<>("test-api-key", "testApiKey"));

        String body = "valid body";

        Post post = new Post("badurl", "/get", body, properties, params);
        Pair<String, Integer> response = post.req();

        assertTrue(response.second == -1);
    }
}

