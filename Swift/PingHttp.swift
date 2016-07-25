//
//  PingHTTP.swift
//  PingHTTP
//
//  Created by Michael Estes on 7/14/16.
//  Copyright © 2016 Ticketmaster. All rights reserved.
//

import Foundation

protocol Request {
    var url: String {get set}
    var endpoint: String {get set}
    var body: String {get set}
    var properties: [(String, String)] {get set}
    var params: [(String, String)] {get set}
    var method: String {get set}
}

extension Request {
    func formatParams() -> String {
        if(!params.isEmpty){
            var formattedParams = "?"
            for param in params {
                formattedParams += "\(param.0)=\(param.1)&"
            }
            return formattedParams.substringWithRange(Range<String.Index>(formattedParams.startIndex..<formattedParams.endIndex.advancedBy(-1)))
        }
        return ""
    }
    
    func isValid() -> Bool {
        switch self.method {
        case "POST":
            return !self.url.isEmpty && !self.body.isEmpty
        default:
            return !self.url.isEmpty
        }
    }
    
    func req(callback: (response: String, status: Int) -> ()){
        if(self.isValid()){
            var requestUrl =  self.url
            requestUrl += self.endpoint
            if(!params.isEmpty){
                requestUrl += self.formatParams()
            }
            let request = NSMutableURLRequest(URL: NSURL(string: requestUrl)!);
            request.HTTPMethod = self.method
            if(!self.body.isEmpty){
             request.HTTPBody = self.body.dataUsingEncoding(NSUTF8StringEncoding)
            }
            if(!self.properties.isEmpty){
                for property in self.properties{
                    request.setValue(property.1, forHTTPHeaderField: property.0)
                }
            }
            
            NSURLSession.sharedSession().dataTaskWithRequest(request, completionHandler: {(data: NSData?, response: NSURLResponse?, error: NSError?) in
                if(error != nil){
                    print("Error")
                    callback(response: "Error", status: -1)
                    return
                }
                var status: Int? = nil
                if let httpResponse = response as? NSHTTPURLResponse {
                    status = httpResponse.statusCode
                }
                callback(response: NSString(data: data!, encoding: NSUTF8StringEncoding)! as String, status: status!)
                return
            }).resume()
        }
    }
}

class Get: Request {
    var url: String
    var endpoint: String
    var body: String
    var properties: [(String, String)]
    var params: [(String, String)]
    var method: String = "GET"
    
    init(url: String, endpoint: String, properties: [(String, String)] = Array(), params: [(String, String)] = Array()){
        self.url = url
        self.endpoint = endpoint
        self.body = ""
        self.params = params
        self.properties = properties
    }
}

class Post: Request {
    var url: String
    var endpoint: String
    var body: String
    var properties: [(String, String)]
    var params: [(String, String)]
    var method: String = "POST"
    
    init(url: String, endpoint: String, body: String, properties: [(String, String)] = Array(), params: [(String, String)] = Array()){
        self.url = url
        self.endpoint = endpoint
        self.body = body
        self.params = params
        self.properties = properties
    }
}
