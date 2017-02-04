package com.michaelestes.pinghttp;

import android.support.v4.util.Pair;
import android.util.Log;

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
        get.req(new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                assertTrue(false);
            }

            @Override
            public void onError(Response response) {
                assertTrue(response.error);
            }
        });
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

            get.req(new ResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    assertTrue(response.responseCode == 200);
                }

                @Override
                public void onError(Response response) {
                    assertTrue(response.error);
                }
            });

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

            get.req(new ResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    assertTrue(response.responseCode == 200);
                }

                @Override
                public void onError(Response response) {
                    assertTrue(response.error);
                }
            });

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

            get.req(new ResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    assertTrue(response.responseCode == 200);
                }

                @Override
                public void onError(Response response) {
                    assertTrue(response.error);
                }
            });

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

            get.req(new ResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    assertTrue(response.responseCode == 200);
                }

                @Override
                public void onError(Response response) {
                    assertTrue(response.error);
                }
            });

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

            get.req(new ResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    assertTrue(response.responseCode == 200);
                }

                @Override
                public void onError(Response response) {
                    assertTrue(response.error);
                }
            });

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

            get.req(new ResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    assertTrue(response.responseCode == 200);
                }

                @Override
                public void onError(Response response) {
                    assertTrue(response.error);
                }
            });

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

            get.req(new ResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    assertTrue(false);
                }

                @Override
                public void onError(Response response) {
                    assertTrue(response.error);
                }
            });

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

        get.req(new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                assertTrue(false);
            }

            @Override
            public void onError(Response response) {
                assertTrue(response.error);
            }
        });
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

            get.req(new ResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    assertTrue(false);
                }

                @Override
                public void onError(Response response) {
                    assertTrue(response.error);
                }
            });

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

            post.req(new ResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    assertTrue(response.responseCode == 200);
                }

                @Override
                public void onError(Response response) {
                    assertTrue(response.error);
                }
            });

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

            post.req(new ResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    assertTrue(response.responseCode == 200);
                }

                @Override
                public void onError(Response response) {
                    assertTrue(response.error);
                }
            });

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

            post.req(new ResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    assertTrue(false);
                }

                @Override
                public void onError(Response response) {
                    assertTrue(response.error);
                }
            });

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

        post.req(new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                assertTrue(false);
            }

            @Override
            public void onError(Response response) {
                assertTrue(response.error);
            }
        });
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

        post.req(new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                assertTrue(false);
            }

            @Override
            public void onError(Response response) {
                assertTrue(response.error);
            }
        });
    }
}

