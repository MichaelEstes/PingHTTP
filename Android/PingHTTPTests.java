import android.support.v4.util.Pair;

import com.ticketmaster.presencelibrary.PingHTTP.Get;
import com.ticketmaster.presencelibrary.PingHTTP.Post;

import org.junit.Test;

import java.util.ArrayList;

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
    public void formatParamsValidTest() {
        final String expectedFormattedParams = "?name=value&name2=value2";
        ArrayList<Pair<String, String>> params = new ArrayList<>();
        params.add(new Pair<>("name", "value"));
        params.add(new Pair<>("name2", "value2"));
        Get get = new Get("url", "endpoint", params, 0);
        assertTrue(get.formatParams().equals(expectedFormattedParams));
    }

    @Test
    public void formatParamsInvalidTest() {
        ArrayList<Pair<String, String>> params = new ArrayList<>();
        Get get = new Get("url", "endpoint", params, 0);
        assertTrue(get.formatParams().isEmpty());
    }

    @Test
    public void connectionGetHTTPValidTest() {
        ArrayList<Pair<String, String>> params = new ArrayList<>();
        params.add(new Pair<>("testParamOne", "testParamOne"));
        params.add(new Pair<>("testParamTwo", "testParamTwo"));

        ArrayList<Pair<String, String>> properties = new ArrayList<>();
        properties.add(new Pair<>("Test-Authorization", "testAuth"));
        properties.add(new Pair<>("test-api-key", "testApiKey"));

        Get get = new Get("http://httpbin.org", "/get", properties, params);
        Pair<String, Integer> response = get.req();

        assertTrue(response.second == 200);
    }

    @Test
    public void connectionGetHTTPNoParamsValidTest() {
        ArrayList<Pair<String, String>> properties = new ArrayList<>();
        properties.add(new Pair<>("Test-Authorization", "testAuth"));
        properties.add(new Pair<>("test-api-key", "testApiKey"));

        Get get = new Get("http://httpbin.org", "/get", properties);
        Pair<String, Integer> response = get.req();

        assertTrue(response.second == 200);
    }

    @Test
    public void connectionGetHTTPNoPropertiesValidTest() {
        Get get = new Get("http://httpbin.org", "/get", null);
        Pair<String, Integer> response = get.req();

        assertTrue(response.second == 200);
    }

    @Test
    public void connectionGetHTTPNoPropertiesValidTestTwo() {
        ArrayList<Pair<String, String>> properties = new ArrayList<>();
        Get get = new Get("http://httpbin.org", "/get", properties);
        Pair<String, Integer> response = get.req();

        assertTrue(response.second == 200);
    }

    @Test
    public void connectionGetHTTPSValidTest() {
        ArrayList<Pair<String, String>> params = new ArrayList<>();
        params.add(new Pair<>("testParamOne", "testParamOne"));
        params.add(new Pair<>("testParamTwo", "testParamTwo"));

        ArrayList<Pair<String, String>> properties = new ArrayList<>();
        properties.add(new Pair<>("Test-Authorization", "testAuth"));
        properties.add(new Pair<>("test-api-key", "testApiKey"));

        Get get = new Get("https://httpbin.org", "/get", properties, params);
        Pair<String, Integer> response = get.req();

        assertTrue(response.second == 200);
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
    public void connectionPostHTTPValidTest() {
        ArrayList<Pair<String, String>> params = new ArrayList<>();
        params.add(new Pair<>("testParamOne", "testParamOne"));
        params.add(new Pair<>("testParamTwo", "testParamTwo"));

        ArrayList<Pair<String, String>> properties = new ArrayList<>();
        properties.add(new Pair<>("Test-Authorization", "testAuth"));
        properties.add(new Pair<>("test-api-key", "testApiKey"));

        String body = "Test body";

        Post post = new Post("http://httpbin.org", "/post", body ,properties, params);
        Pair<String, Integer> response = post.req();

        assertTrue(response.second == 200);
    }

    @Test
    public void connectionPostHTTPSValidTest() {
        ArrayList<Pair<String, String>> params = new ArrayList<>();
        params.add(new Pair<>("testParamOne", "testParamOne"));
        params.add(new Pair<>("testParamTwo", "testParamTwo"));

        ArrayList<Pair<String, String>> properties = new ArrayList<>();
        properties.add(new Pair<>("Test-Authorization", "testAuth"));
        properties.add(new Pair<>("test-api-key", "testApiKey"));

        String body = "Test body";

        Post post = new Post("https://httpbin.org", "/post", body ,properties, params);
        Pair<String, Integer> response = post.req();

        assertTrue(response.second == 200);
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

