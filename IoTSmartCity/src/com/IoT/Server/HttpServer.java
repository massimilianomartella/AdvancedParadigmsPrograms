package com.IoT.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author Massimiliano Martella
 * @date Dec 30, 2015 12:04:48 PM
 */
public interface HttpServer {
	
	/**
	 * parse, get params
	 * @param is
	 * @return params
	 * @throws IOException
	 */
	Map<String, String> parse(InputStream is) throws IOException;
	
	/**
	 * read param from param string,
	 * @param paramStr params string, format: name1=value1&name2=value2\
	 * @param isBody whether the params from body,
	 * @return map of param key/value,
	 */
	Map<String, String> parseParam(String paramStr, boolean isBody);
	
	/**
	 * send response
	 * @param os
	 * @param paramMap
	 */
	void response(OutputStream os, Map<String, String> paramMap);
	
	/**
	 * send response
	 * @param os
	 */
	void response(OutputStream out);
	
	/**
	 * shutdown server,
	 * @throws IOException
	 */
	void terminate() throws IOException;
}
