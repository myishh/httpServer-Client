/**Basic HTTP Server
 * @author myishh
 * Last edit at 1/17/2017
 */
import java.io.*;
import java.net.*;

public class Myservice {
	public static void main(String args[]) {
		

		ServerSocket serverSocket = null;
		try {
			// Create socket to lisenting port args[0]
			try {
				serverSocket = new ServerSocket(Integer.parseInt(args[0]));
			} 
			catch (IOException e) {
				System.err.println("Could not listen on port:" + args[0]);
				System.exit(1);
			}
			System.out.println("==========START SERVER=========");
			// Start listening, I set to wait until get an "EXIT"
			while(!args[0].equals("EXIT")) {
				Socket client = null;
				try {
					// Wait for a client to connect until get a socket
					client = serverSocket.accept();
					System.out.println("==========CONNECT TO CLIENT=========");
				}
				catch (IOException e) { 
					System.err.println("Connection failed.");
					System.exit(1);
				}
				
				// Get input and output streams to talk to the client from the socket
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
				
				//get the command from client and replay
				String line;    
				String[] request = null;
				String filename= "";
				StringBuffer strContent = new StringBuffer("");
				try {
					while((line = in.readLine()) != null) {
						System.out.println(line);
						if (request == null) {
							request= line.split(" ");
							continue;	
						}
						if (line.length() == 0) break;
						strContent.append(line + "\r\n");
					}
					filename = request[1];
					
					// Ignore other request
					if(!(request[0].equals("GET")) && !(request[0].equals("PUT")))
						out.println("400 Bad Request"); 
					out.flush();
				}
				catch(IOException ioe) {
					System.out.println("Exception while reading the request" + ioe);
				}
				
				
				if(request[0].equals("GET")) {
					System.out.println("==========START GET=========");
					// Read File from Local server and send it to the client       
					int ch;
					InputStream from_file = null;
					File file = new File("Reciever/" +filename);  
					
					try {	
						
						from_file = new FileInputStream(file);
						
						while((ch = from_file.read()) != -1)
							strContent.append((char)ch);
						
						from_file.close();
						System.out.println("File contents :");
						
						// Start sending our reply, using the HTTP 1.0 protocol
						out.println("HTTP/1.0 200 OK");              // Version & status code
						out.println("Content-Type: text/plain");   // The type of data we send
						out.println(strContent);
						out.flush();
					}
					catch(FileNotFoundException e) {
						System.out.println("File " + file.getAbsolutePath() + " could not be found on filesystem");
						out.println("404 Not Found");
						out.flush();
					}
					
					catch(IOException ioe) {
						System.out.println("Exception while reading the file" + ioe);
					}
					System.out.println("==========FINISH SEND index.html=========");
				}// end GET
				
				
				if (request[0].equals("PUT")) {
					System.out.println("==========START PUT=========");
					filename = "Reciever/" + filename;
					
					//Create a file
					OutputStream creat_file = new FileOutputStream(filename);
					//Write to the file
					PrintWriter write_file = new PrintWriter(filename);
					write_file.println(strContent);
					
					//Reply to client
					out.println("200 OK File Created");
					out.flush();
					
					creat_file.close();
					write_file.close();
				}//end PUT
				// Close the streams and socket
				out.close();
				in.close();
				client.close();
			} // Loop end
			serverSocket.close();
		}
		// If anything goes wrong, print an error message
		catch (Exception e) {
			System.err.println(e);
			System.err.println("Usage: java HttpServer <port>");
		}
		
	} // End Main
}
