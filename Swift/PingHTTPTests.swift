//
//  PingHTTPTests.swift
//  PingHTTP
//
//  Created by Michael Estes on 7/15/16.
//

import Foundation
import XCTest

class PingHTTPTests: XCTestCase {
    
    let genesisGet: GenesisGet = GenesisGet()
    
    override func setUp() {
        super.setUp()
    }
    
    override func tearDown() {
        super.tearDown()
    }
    
    func testGetRequestValid() {
        let testGet: Get = Get(url: "url", endpoint: "endpoint")
        XCTAssertTrue(testGet.isValid())
    }
    
    func testGetRequestInvalid() {
        let testGet: Get = Get(url: "", endpoint: "")
        XCTAssertFalse(testGet.isValid())
    }
    
    func testPostRequestValid() {
        let testPost: Post = Post(url: "url", endpoint: "endpoint", body: "body")
        XCTAssertTrue(testPost.isValid())
    }
    
    func testPostRequestInvalid() {
        let testPost: Post = Post(url: "", endpoint: "", body: "")
        XCTAssertFalse(testPost.isValid())
    }
    
    func testFormatParamsValid() {
        let expectedFormattedParams: String = "?name=value&name2=value2"
        let params: [(String, String)] = [("name", "value"), ("name2", "value2")]
        let testGet: Get = Get(url: "url", endpoint: "endpoint", params: params)
        XCTAssertTrue(testGet.formatParams() == expectedFormattedParams)
    }
    
    func testFormatParamsInvalid() {
        let params: [(String, String)] = Array()
        let testGet: Get = Get(url: "url", endpoint: "endpoint", params: params)
        XCTAssertTrue(testGet.formatParams().isEmpty)
    }
    
    func testConnectionGetValid() {
        let asyncExpectation = expectationWithDescription("longRunningFunction")
        var testStatus: Int = -1
        
        let properties: [(String, String)] = [("Test-Authorization", "testAuth"), ("test-api-key", "testApiKey")]
        let params: [(String, String)] = [("testParamOne", "testParamOne"), ("testParamTwo", "testParamTwo")]
        let get: Get = Get(url: "https://httpbin.org", endpoint: "/get", properties: properties, params: params)
        get.req { (response, status) in
            testStatus = status
            asyncExpectation.fulfill()
        }
        self.waitForExpectationsWithTimeout(5) { error in
            XCTAssertTrue(testStatus == 200)
        }
    }
    
    func testConnectionGetInvalid() {
        let asyncExpectation = expectationWithDescription("longRunningFunction")
        var testStatus: Int = -1
        
        let properties: [(String, String)] = [("Test-Authorization", "testAuth"), ("test-api-key", "testApiKey")]
        let params: [(String, String)] = [("testParamOne", "testParamOne"), ("testParamTwo", "testParamTwo")]
        let get: Get = Get(url: "badurl", endpoint: "/get", properties: properties, params: params)
        get.req { (response, status) in
            testStatus = status
            asyncExpectation.fulfill()
        }
        self.waitForExpectationsWithTimeout(5) { error in
            XCTAssertTrue(testStatus == -1)
        }
    }
}
