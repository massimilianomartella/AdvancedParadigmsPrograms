package com.IoT.Server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

public class WorkerRunnable implements Runnable, HttpServer {

	protected Socket clientSocket = null;
	protected String serverText = null;
	protected Map<String, String> paramMap = null;

	public WorkerRunnable(Socket clientSocket, String serverText) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
	}

	public void run() {
		try {
			paramMap = parse(clientSocket.getInputStream());

			if (paramMap.isEmpty())
				response(clientSocket.getOutputStream());
			else
				response(clientSocket.getOutputStream(), paramMap);

		} catch (IOException e) {
			// report exception somewhere.
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, String> parse(InputStream is) throws IOException {
		Map<String, String> paramMap = new HashMap<String, String>();
		LineNumberReader lr = new LineNumberReader(new InputStreamReader(is));
		String inputLine = null;
		String method = null;
		String httpVersion = null;
		String uri = null;

		// read request line
		inputLine = lr.readLine();
		String[] requestCols = inputLine.split("\\s");
		method = requestCols[0];
		uri = requestCols[1];
		httpVersion = requestCols[2];
		System.out.println("http version:\t" + httpVersion);

		// read header
		while (StringUtils.isNotBlank(inputLine = lr.readLine())) {
			System.out.println("post header line:\t" + inputLine);
		}

		// parse GET param
		if (uri.contains("?")) {
			paramMap.putAll(parseParam(uri.split("\\?", 2)[1], false));
			// System.out.println("get body:\t" + paramMap.toString());
		} else if (method.toUpperCase().equals("POST")) {
			// read body - POST method
			StringBuffer bodySb = new StringBuffer();
			char[] bodyChars = new char[1024];
			int len;
			// ready() make sure it will not block,
			while (lr.ready() && (len = lr.read(bodyChars)) > 0) {
				bodySb.append(bodyChars, 0, len);
			}
			paramMap.putAll(parseParam(bodySb.toString(), true));
			// System.out.println("post body:\t" + bodySb.toString());
		}
		return paramMap;
	}

	@Override
	public Map<String, String> parseParam(String paramStr, boolean isBody) {
		String[] paramPairs = paramStr.trim().split("&");
		Map<String, String> paramMap = new HashMap<String, String>();

		String[] paramKv;
		for (String paramPair : paramPairs) {
			if (paramPair.contains("=")) {
				paramKv = paramPair.split("=");
				if (isBody) {
					// replace '+' to ' ', because in body ' ' is replaced by
					// '+' automatically when post,
					paramKv[1] = paramKv[1].replace("+", " ");
				}
				paramMap.put(paramKv[0], paramKv[1]);
			}
		}
		return paramMap;
	}

	@Override
	public void response(OutputStream out, Map<String, String> paramMap) {
		sendData(paramMap);
	}

	private void sendData(Map<String, String> paramMap) {
		if (!paramMap.get("id").isEmpty()) {
			System.out.println("primo parametro --> " + paramMap.get("id"));

			JSONObject obj = new JSONObject();

			obj.put("temperature", 28);
			obj.put("humidity", 30);
			obj.put("flipSwitch", "on");

			System.out.print(obj);
			try {
			PrintStream send = new PrintStream(clientSocket.getOutputStream());
			
			send.println("HTTP/1.1 200 OK");
			send.println("Content-type: text/html; Charset=UTF-8;");
			send.println("");
			send.println(obj);
			send.flush();
			send.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	@Override
	public void response(OutputStream out) {
		System.out.println("localhost");
		File file = new File("src/com/IoT/Client/pageClient.html");
		String mimeType = getMimeType(file);
		sendFile(file, mimeType);
	}

	/* Sends the requested file to the client */
	public void sendFile(File file, String fileType) {
		try {
			PrintStream send = new PrintStream(clientSocket.getOutputStream());
			// Buffer must not be to low, => fragments
			int length = 0; // (int) file.length();
			FileInputStream fileIn = new FileInputStream(file.toString());
			byte[] bytes = new byte[1024];
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			/* Write until bytes is empty */
			while ((length = fileIn.read(bytes)) != -1) {
				bos.write(bytes, 0, length);
				// send.write(bytes, 0, length);
				// send.flush();
			}
			bos.flush();
			bos.close();
			byte[] data1 = bos.toByteArray();

			String dataStr = new String(data1, "UTF-8");

			// System.out.print(dataStr);
			send.println("HTTP/1.1 200 OK");
			send.println("Content-type: " + fileType + "; Charset=UTF-8");
			send.println("");
			send.println(dataStr);

			send.flush();
			send.close();

			fileIn.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/* Finds out the MIME type of the requested file */
	public String getMimeType(File f) {
		String file = f.toString();
		String type = "";
		if (file.endsWith(".txt")) {
			type = "text/txt";
		} else if (file.endsWith(".html") || file.endsWith("htm")) {
			type = "text/html;";
		} else if (file.endsWith(".jpg")) {
			type = "image/jpg";
		} else if (file.endsWith(".png")) {
			type = "image/png";
		} else if (file.endsWith(".jpeg")) {
			type = "image/jpeg";
		} else if (file.endsWith(".gif")) {
			type = "image/gif";
		} else if (file.endsWith(".pdf")) {
			type = "application/pdf";
		} else if (file.endsWith(".mp3")) {
			type = "audio/mpeg";
		} else if (file.endsWith(".class")) {
			type = "application/octet-stream";
		} else if (file.endsWith(".mp4")) {
			type = "video/mp4";
		}
		return type;
	}
}
