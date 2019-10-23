
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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;


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
  //ArrayList<String> progDispo = new ArrayList<String>();
	
	protected void start() {
		ServerSocket s;

    	/*progDispo.add("/coucou");
    	progDispo.add("/test");
    	progDispo.add("/toto");
    	progDispo.add("/favicon.ico");
		*/

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
  				if(resource.equals("/")){
  				  getMethod("/index.html", "HTTP/1.1", remote, out);
  				}else{
  					switch(method){
              case "GET":
						    System.out.println("Dans le GET");
						    getMethod(resource, versionHttp, remote, out);
						    break;
              case "HEAD":
						    System.out.println("Dans le HEAD");
						    getHeader(resource, versionHttp, remote, out);
						    break;
              case "PUT":
						    System.out.println("Dans le PUT");
						    putMethod(resource, versionHttp, remote, out, in);
						    break;
              case "POST":
						    System.out.println("Dans le POST");
						    postMethod(resource, versionHttp, remote, out, in);
                break;
            } 	
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
	public static void main(String args[]) {
    	WebServer ws = new WebServer();
    	ws.start();
  	}

  	public void getMethod(String resource, String versionHttp, Socket remote, PrintWriter out)throws IOException{
  		//BufferedOutputStream pour gérer autre chose que des String 
      BufferedOutputStream bOutS = new BufferedOutputStream(remote.getOutputStream());
  		try{
  		//Création fichier voulu 
			File fileName = new File("resources" + resource);
      //Si le fichier existe alors on peut faire le get
			if(fileName.exists() && fileName.isFile()){
        //Création du Header 
				String response = getHeader(versionHttp, "200 OK", resource, fileName.length());	
				bOutS.write(response.getBytes());
				bOutS.flush();
        //Récupération contenu du file / Utilisation de byte car générique 
				BufferedInputStream fileIn = new BufferedInputStream(new FileInputStream(fileName));
				byte[] buffer = new byte[256];
				int nbRead;
				while((nbRead = fileIn.read(buffer)) != -1) {
					bOutS.write(buffer, 0, nbRead);
				}
				fileIn.close();
				bOutS.flush();
			}else{
        //Si fichier n'existe pas = not found 
				String response = getHeader(versionHttp, "404 Not Found", "notfound.html", fileName.length());	
				bOutS.write(response.getBytes());
				bOutS.flush();
			}
  		}catch(Exception e){
  			System.out.println("Error: " + e);
  			try {
  				String response = getHeader(versionHttp,"500 Internal Server Error");
  				bOutS.write(response.getBytes());
				bOutS.flush();	
			} catch (Exception e2) {};
  		} 	
  	}

  	public void getHeader(String resource, String versionHttp, Socket remote, PrintWriter out) throws IOException{
      //BufferedOutputStream pour gérer autre chose que des String 
  		BufferedOutputStream bOutS = new BufferedOutputStream(remote.getOutputStream());
  		try{
  			File fileName = new File("resources" + resource);
      //On voit si ressource disponible 
			if(fileName.exists() && fileName.isFile()){
				String response = getHeader(versionHttp, "200 OK", resource, fileName.length());	
				bOutS.write(response.getBytes());
				bOutS.flush();
			}else{
				String response = getHeader(versionHttp, "404 Not Found", "/notfound.html", fileName.length());	
				bOutS.write(response.getBytes());
				bOutS.flush();
			}
  		}catch(Exception e){
  			System.out.println("Error: " + e);
  			try {
  				String response = getHeader(versionHttp,"500 Internal Server Error");
  				bOutS.write(response.getBytes());
				bOutS.flush();	
			} catch (Exception e2) {};
  		} 	
  	}

 	public void postMethod(String resource, String versionHttp, Socket remote, PrintWriter out,  BufferedReader in)throws IOException{
  		File fileName = new File("resources" + resource);
  		//Test pour savoir si la ressource existe
      boolean test = fileName.exists();
      //BufferedOutputStream pour le retour du header
  		BufferedOutputStream bOutS = new BufferedOutputStream(remote.getOutputStream());
      //BufferedOutputStream pour écrire dans le fichier 
  		BufferedOutputStream fileBOutS = new BufferedOutputStream(new FileOutputStream(fileName, test));
  		try{
        //Récupérer le body 
  			byte[] buffer = new byte[1024];
        String str = in.readLine();
        //Problème : lecture uniquement de la première ligne du body -> récupérer la taille du contenu du buffer et faire un while dessus 
			  while(str != null && !str.equals("")) {
          str = in.readLine();
          //Ecriture dans le fichier arpès récupération de la ligne dans le body 
				  fileBOutS.write(str.getBytes(), 0, str.getBytes().length);
			   }
  			fileBOutS.flush();

        //Si fichier existe code 200 dans le header
  			if(test){
				  String response = getHeader(versionHttp, "200 OK", resource, fileName.length());	
				  bOutS.write(response.getBytes());
				  bOutS.flush();
  			}else{
        //Si fichier existe pas fichier code 201 dans le header
  				String response = getHeader(versionHttp, "201 CREATED", resource, fileName.length());	
				  bOutS.write(response.getBytes());
				  bOutS.flush();
  			}
  		}catch(Exception e){
  			System.out.println("Error: " + e);
  			try {
  				String response = getHeader(versionHttp,"500 Internal Server Error");
  				bOutS.write(response.getBytes());
				bOutS.flush();	
			} catch (Exception e2) {};
  		}
  	}


  	public void putMethod(String resource, String versionHttp, Socket remote, PrintWriter out,  BufferedReader in) throws IOException{
  		File fileName = new File("resources" + resource);
  		//Test pour savoir si la ressource existe
      boolean test = fileName.exists();
      //BufferedOutputStream pour le retour du header
      BufferedOutputStream bOutS = new BufferedOutputStream(remote.getOutputStream());
      //BufferedOutputStream pour écrire dans le fichier 
      BufferedOutputStream fileBOutS = new BufferedOutputStream(new FileOutputStream(fileName, test));
  		try{
        //Efface ce qui est déjà présent dans le fichier 
  			PrintWriter pw = new PrintWriter(fileName);
  			pw.close();

  			 //Récupérer le body 
        byte[] buffer = new byte[1024];
        String str = in.readLine();
        while(str != null && !str.equals("")) {
          str = in.readLine();
          //Ecrit dans le fichier en même temps qu'il récupère le body
          fileBOutS.write(str.getBytes(), 0, str.getBytes().length);
         }
        fileBOutS.flush();

        //Si fichier existe code 200 dans le header
  			if(test){
				  String response = getHeader(versionHttp, "200 OK", resource, fileName.length());	
				  bOutS.write(response.getBytes());
				  bOutS.flush();
  			}else{
          //Si fichier existe pas fichier code 201 dans le header
  				String response = getHeader(versionHttp, "201 CREATED", resource, fileName.length());	
				  bOutS.write(response.getBytes());
				  bOutS.flush();
  			}		
  		}catch(Exception e){
  			System.out.println("Error: " + e);
  			try {
  				String response = getHeader(versionHttp,"500 Internal Server Error");
  				bOutS.write(response.getBytes());
				bOutS.flush();	
			} catch (Exception e2) {};
  		}
  	}

  	public String getHeader(String versionHttp, String status, String resource, long length){
  		String res = versionHttp + " " + status + "\n";
      //Différents types qui peuvent être récupérer : dans l'idéal récupérer le content-type du header
  		if(resource.endsWith(".html"))
        res += "Content-Type: text/html\n";
		  else if(resource.endsWith(".mp4"))
        res += "Content-Type: video/mp4\n";
		  else if(resource.endsWith(".png"))
        res += "Content-Type: image/png\n";
		  else if(resource.endsWith(".jpeg"))
			  res += "Content-Type: image/jpg\n";
		  else if(resource.endsWith(".mp3"))
        res += "Content-Type: audio/mp3\n";
      else if(resource.endsWith(".avi"))
        res += "Content-Type: video/x-msvideo\n";
		  else if(resource.endsWith(".css"))
        res += "Content-Type: text/css\n";
      else if(resource.endsWith(".pdf"))
        res += "Content-Type: application/pdf\n";
		  res += "Content-Length: " + length + "\n";
		  res += "Server: Bot\n";
		  res += "\n";
  		return res;
  	}

  	public String getHeader(String versionHttp, String status){
  		String res = versionHttp + " " + status + "\n";
  		return res;
  	}

}






