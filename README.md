# PingHTTP
HTTP request wrapper for Android and iOS

Simple HTTP wrapper

Android

  Get Example:
  
    ArrayList<Pair<String, String>> params = new ArrayList<>();
    params.add(new Pair<>("testParamOne", "testParamOne"));
    params.add(new Pair<>("testParamTwo", "testParamTwo"));
    
    ArrayList<Pair<String, String>> properties = new ArrayList<>();
    properties.add(new Pair<>("Test-Authorization", "testAuth"));
    properties.add(new Pair<>("test-api-key", "testApiKey"));
    
    Get get = new Get("http://httpbin.org", "/get", properties, params);
    get.req(new ResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    //Handle body and response
                }

                @Override
                public void onError(Response response) {
                    //Handle error
                }
    });
    
  Post Example:
  
    ArrayList<Pair<String, String>> params = new ArrayList<>();
    params.add(new Pair<>("testParamOne", "testParamOne"));
    params.add(new Pair<>("testParamTwo", "testParamTwo"));
    
    ArrayList<Pair<String, String>> properties = new ArrayList<>();
    properties.add(new Pair<>("Test-Authorization", "testAuth"));
    properties.add(new Pair<>("test-api-key", "testApiKey"));
    
    String body = "Test body";
    
    Post post = new Post("http://httpbin.org", "/post", body ,properties, params);
    post.req(new ResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    //Handle body and response
                }

                @Override
                public void onError(Response response) {
                    //Handle error
                }
    });

    
Swift

  Get Example:
  
    let properties: [(String, String)] = [("Test-Authorization", "testAuth"), ("test-api-key", "testApiKey")]
    let params: [(String, String)] = [("testParamOne", "testParamOne"), ("testParamTwo", "testParamTwo")]
    let get: Get = Get(url: "https://httpbin.org", endpoint: "/get", properties: properties, params: params)
    get.req { (response, status) in
        //Handle response and status
    }
  
  Post Example:
  
    let properties: [(String, String)] = [("Test-Authorization", "testAuth"), ("test-api-key", "testApiKey")]
    let params: [(String, String)] = [("testParamOne", "testParamOne"), ("testParamTwo", "testParamTwo")]
    let post: Post = Post(url: "https://httpbin.org", endpoint: "/get", body: body, properties: properties, params: params)
    post.req { (response, status) in
        //Handle response and status
    }

    
