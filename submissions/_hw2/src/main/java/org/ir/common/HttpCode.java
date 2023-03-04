package org.ir.common;

import java.util.HashMap;
import java.util.Map;

public class HttpCode {
    private static Map<Integer, String> httpStatusCodes;

    static {
        httpStatusCodes = new HashMap<>();
        httpStatusCodes.put(100, "Continue");
        httpStatusCodes.put(101, "Switching Protocols");
        httpStatusCodes.put(102, "Processing");
        httpStatusCodes.put(103, "Early Hints");
        httpStatusCodes.put(200, "OK");
        httpStatusCodes.put(201, "Created");
        httpStatusCodes.put(202, "Accepted");
        httpStatusCodes.put(203, "Non-Authoritative Information");
        httpStatusCodes.put(204, "No Content");
        httpStatusCodes.put(205, "Reset Content");
        httpStatusCodes.put(206, "Partial Content");
        httpStatusCodes.put(207, "Multi-Status");
        httpStatusCodes.put(208, "Already Reported");
        httpStatusCodes.put(226, "IM Used");
        httpStatusCodes.put(300, "Multiple Choices");
        httpStatusCodes.put(301, "Moved Permanently");
        httpStatusCodes.put(302, "Found");
        httpStatusCodes.put(303, "See Other");
        httpStatusCodes.put(304, "Not Modified");
        httpStatusCodes.put(305, "Use Proxy");
        httpStatusCodes.put(306, "Switch Proxy");
        httpStatusCodes.put(307, "Temporary Redirect");
        httpStatusCodes.put(308, "Permanent Redirect");
        httpStatusCodes.put(400, "Bad Request");
        httpStatusCodes.put(401, "Unauthorized");
        httpStatusCodes.put(402, "Payment Required");
        httpStatusCodes.put(403, "Forbidden");
        httpStatusCodes.put(404, "Not Found");
        httpStatusCodes.put(405, "Method Not Allowed");
        httpStatusCodes.put(406, "Not Acceptable");
        httpStatusCodes.put(407, "Proxy Authentication Required");
        httpStatusCodes.put(408, "Request Timeout");
        httpStatusCodes.put(409, "Conflict");
        httpStatusCodes.put(410, "Gone");
        httpStatusCodes.put(411, "Length Required");
        httpStatusCodes.put(412, "Precondition Failed");
        httpStatusCodes.put(413, "Payload Too Large");
        httpStatusCodes.put(414, "URI Too Long");
        httpStatusCodes.put(415, "Unsupported Media Type");
        httpStatusCodes.put(416, "Range Not Satisfiable");
        httpStatusCodes.put(417, "Expectation Failed");
        httpStatusCodes.put(418, "I'm a teapot");
        httpStatusCodes.put(421, "Misdirected Request");
        httpStatusCodes.put(422, "Unprocessable Entity");
        httpStatusCodes.put(423, "Locked");
        httpStatusCodes.put(424, "Failed Dependency");
        httpStatusCodes.put(425, "Too Early");
        httpStatusCodes.put(426, "Upgrade Required");
        httpStatusCodes.put(428, "Precondition Required");
        httpStatusCodes.put(429, "Too Many Requests");
        httpStatusCodes.put(431, "Request Header Fields Too Large");
        httpStatusCodes.put(451, "Unavailable For Legal Reasons");
        httpStatusCodes.put(500, "Internal Server Error");
        httpStatusCodes.put(501, "Not Implemented");
        httpStatusCodes.put(502, "Bad Gateway");
        httpStatusCodes.put(503, "Service Unavailable");
        httpStatusCodes.put(504, "Gateway Timeout");
        httpStatusCodes.put(505, "HTTP Version Not Supported");
        httpStatusCodes.put(506, "Variant Also Negotiates");
        httpStatusCodes.put(507, "Insufficient Storage");
        httpStatusCodes.put(508, "Loop Detected");
        httpStatusCodes.put(510, "Not Extended");
        httpStatusCodes.put(511, "Network Authentication Required");
    }

    public static String getDescriptionForCode(Integer code) {
        return (httpStatusCodes.get(code) != null) ? httpStatusCodes.get(code) : "Incorrect HTTP status code";
    }
}
