/*
///A Simple Web Server (WebServer.java)

package http.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.nio.file.*;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

/**
 * Example program from Chapter 1 Programming Spiders, Bots and Aggregators in
 * Java Copyright 2001 by Jeff Heaton
 * 
 * WebServer is a very simple web-server. Any request is responded with a very
 * simple web-page.
 * 
 * @author Jeff Heaton
 * @version 1.0
 */
/* public class WebServer {

  /**
   * WebServer constructor.
   */
  /*ArrayList<String> progDispo = new ArrayList<String>();
  String[][] articles = new String[3][20];
	
	protected void start() {
    	ServerSocket s;
    	progDispo.add("/coucou");
    	progDispo.add("/test");
    	progDispo.add("/toto");
    	progDispo.add("/favicon.ico");
    	System.out.println("Webserver starting up on port 3000");
    	System.out.println("(press ctrl-c to exit)");

    	try {
      	// create the main server socket
    		s = new ServerSocket(3000);
    	} catch (Exception e) {
      		System.out.println("Error: " + e);
      		return;
    	}

    	System.out.println("Waiting for connection");
    	for (;;) {
    		try {
        		// wait for a connection
        		Socket remote = s.accept();
        		// remote is now the connected socket
        		System.out.println("Connection, sending data.");
        		BufferedReader in = new BufferedReader(new InputStreamReader(remote.getInputStream()));
        		PrintWriter out = new PrintWriter(remote.getOutputStream());
        		String str = ".";
        		String method = "";
				String resource ="";
				String versionHttp = "";
				String header = "";
				String body = "";
			
          		
          		str = in.readLine();
          		if(str != null && !str.equals("")){
      				String[] parts = str.split(" ");
					method = parts[0];
	  				resource = parts[1];
	  				versionHttp = parts[2]; 
	  				System.out.println("test1");
      			}
      			System.out.println(resource);
				//System.out.println(str);
				
			// Récupérer le header
 				while(str != null && !str.equals("")){
					str = in.readLine();
					header = header + str + "\n" ;
					System.out.println("test2");
				}

				//System.out.println(header);
			// Récupérer le body
				//str = in.readLine();
				while(str != null && !str.equals("")){
					body += str + "\n";
					str = in.readLine();
					System.out.println("test3");
				}
				//System.out.println(body);

				switch(method){
					case "GET":
						System.out.println("test4");
						getMethod(resource, versionHttp, header, out, remote);
					case "POST":
						postMethod(resource, versionHttp, header, out, remote, body);
					case "HEAD":
						String status = findStatus(resource);
						String response = versionHttp + " " + status + "\n" + header;
						out.println(response);
					case "PUT":
					default :
						//System.out.println("cas non supporté");
				} 	
        		out.flush();
        		remote.close();
    		} catch (Exception e) {
        		System.out.println("Error: " + e);
    		}
 		}
 	}

  /**
   * Start the application.
   * 
   * @param args
   *            Command line parameters are not used.
   */
	/*public static void main(String args[]) {
    	WebServer ws = new WebServer();
    	ws.start();
	}
  
  	public void getMethod(String resource, String versionHttp, String header, PrintWriter out, Socket remote) throws IOException{
  		String status = findStatus(resource);
		String response = versionHttp + " " + status + "\n"+ header +"\n";	
		System.out.println(response);
		out.println(response);
		out.flush();
		out.println("<H1>Welcome to the Ultra Mini-WebServer</H2>");
		out.flush();
		getResources(resource, remote);	
  	}

  	public void postMethod(String resource, String versionHttp, String header, PrintWriter out, Socket remote, String body)throws IOException{
  		String status = findStatus(body);
		String response = versionHttp +" " + status + "\n"+ header +"\n\n";	
		out.println(response);
		out.flush();
		getResources(body, remote);
  	}

  	public String findStatus(String resource){
		String res = "";
		if(progDispo.contains(resource))
			res="200 OK";
		else{
			File f= new File("./resources"+resource);
			if(f.exists())
				res="200 OK";
			else
				res="404 Not Found";
		}
		return res;
  	}

  	public void getResources(String resource, Socket remote) throws IOException{
  		System.out.println("resources" + resource);
		File fileName = new File("resources" + resource);
		int fileLength = (int) fileName.length();
		byte[] fileData = readFileData(fileName, fileLength);
		OutputStream outS = remote.getOutputStream();
		outS.write(fileData, 0, fileLength);
		outS.flush();
  	}

  	/*public void getFunction(){
  		for(int i=0; i<progDispo.size(); i++){
			if(name.equals(progDispo.get(i)))
				out.println("<H1>Output of the "+name+" method</H2>");
		}
  	}*/

  	/*public byte[] readFileData(File file, int filelength) throws IOException{
		FileInputStream fileIn = null;
		byte[] fileData = new byte[filelength];
	
		try{
			fileIn = new FileInputStream(file);
			fileIn.read(fileData);
		}finally{
			if(fileIn != null)
				fileIn.close();
		}
		return fileData;
  	}
  
}
*/
package http.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.nio.file.*;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

/**
 * Example program from Chapter 1 Programming Spiders, Bots and Aggregators in
 * Java Copyright 2001 by Jeff Heaton
 * 
 * WebServer is a very simple web-server. Any request is responded with a very
 * simple web-page.
 * 
 * @author Jeff Heaton
 * @version 1.0
 */
public class WebServer {

  /**
   * WebServer constructor.
   */
  ArrayList<String> progDispo = new ArrayList<String>();
	
  protected void start() {
    ServerSocket s;

    progDispo.add("/coucou");
    progDispo.add("/test");
    progDispo.add("/toto");
    progDispo.add("/favicon.ico");

    System.out.println("Webserver starting up on port 3000");
    System.out.println("(press ctrl-c to exit)");

    try {
      // create the main server socket
      s = new ServerSocket(3000);
    } catch (Exception e) {
      System.out.println("Error: " + e);
      return;
    }

    System.out.println("Waiting for connection");
    for (;;) {
      try {
        // wait for a connection
        Socket remote = s.accept();
        // remote is now the connected socket
        System.out.println("Connection, sending data.");
        BufferedReader in = new BufferedReader(new InputStreamReader(remote.getInputStream()));
        PrintWriter out = new PrintWriter(remote.getOutputStream());

        // read the data sent. We basically ignore it,
        // stop reading once a blank line is hit. This
        // blank line signals the end of the client HTTP
        // headers.

        String str = ".";
        String method = "";
		String resource ="";
		String versionHttp = "";
		String response = "";
		String status = "";
		String header = "";
		String body = "";
		String resGET="Adder.html";
		File fileName;
		// Get the url
        if (str != null && !str.equals("")) {
          str = in.readLine();
          System.out.println("Method :");
			System.out.println(str);
          if (str != null) {
			  String[] parts = str.split(" ");
			  if (parts.length > 0) {
				  method = parts[0];
				  resource = parts[1];
				  versionHttp = parts[2];
			  }
		  }
	}
	
	// Récupérer le header
 	while(str != null && !str.equals("")){
		str = in.readLine();
		header = header + str + "\n" ;
	}
 	System.out.println("Le header :");
  	System.out.println(header);

  	System.out.println('1');
      System.out.println(str);
  	str = in.readLine();
  	body += str;
  System.out.println('1');
          System.out.println('2');
    str = in.readLine();
          System.out.println(str);
    body += str;
          System.out.println('2');
          System.out.println('3');
      str = in.readLine();
          System.out.println(str);
      body += str;
          System.out.println('3');
          System.out.println('4');
      str = in.readLine();
          System.out.println(str);
      body += str;
          System.out.println('4');
	// Récupérer le body
		  /*
	while(str != null && !str.equals("")){
		body += str;
		str = in.readLine();
	}
	*/
	System.out.println("Body :");
		  System.out.println(body);

	 switch(method){
		case "GET":
			System.out.println("Dans le GET");
			status = findStatus(resource);
			response = versionHttp +" " + status + "\n\n";	
			//System.out.println("RESPONSE : " + response);
			out.println(response);
			out.flush();
			fileName = new File("resources" + resource);
			int fileLength = (int) fileName.length();
			byte[] fileData = readFileData(fileName, fileLength);
			OutputStream out2 = remote.getOutputStream();
			out2.write(fileData, 0, fileLength);
			out2.flush();
			break;
		case "HEAD":
			System.out.println("Dans le HEAD");
			response = versionHttp + " " + status + "\n" + header;
			out.println(response);
			out.flush();
			break;
		case "PUT":
			System.out.println("Dans le PUT");
			break;
		case "POST":
			System.out.println("Dans le POST");
			System.out.println("*****");
			System.out.println(body);
			System.out.println("*****");
			break;
	  } 	

        /*// Send the headers
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        out.println("Server: Bot");
        // this blank line signals the end of the headers
        out.println("");
	*/
	
        // Send the HTML page
        //out.println("<H1>Welcome to the Ultra Mini-WebServer</H2>");
        out.flush();
        remote.close();
      } catch (Exception e) {
        System.out.println("Error: " + e);
      }
    }
  }

  /**
   * Start the application.
   * 
   * @param args
   *            Command line parameters are not used.
   */
  public static void main(String args[]) {
    WebServer ws = new WebServer();
    ws.start();
  }

  public String findStatus(String resource){
	String res = "";
	if(progDispo.contains(resource))
		res="200 OK";
	else{
		File f= new File("./resources/"+resource);
		if(f.exists())
			res="200 OK";
		else
			res="404 Not Found";
	}
	return res;
  }

  public byte[] readFileData(File file, int filelength) throws IOException{
	FileInputStream fileIn = null;
	byte[] fileData = new byte[filelength];
	try{
		fileIn = new FileInputStream(file);
		fileIn.read(fileData);
	}finally{
		if(fileIn != null)
			fileIn.close();
	}
	return fileData;
  }
  
  
  public void getProgram(String name, PrintWriter out){
	for(int i=0; i<progDispo.size(); i++){
		if(name.equals(progDispo.get(i)))
			//System.out.println("<H1>Output of the "+name+" method</H2>");
			out.println("<H1>Output of the "+name+" method</H2>");
	}
  }

  public void getResources(File file, int filelength){
	
  }

}






