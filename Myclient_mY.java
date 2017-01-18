/**Basic HTTP Client
 * @author myishh
 * Last edit at 1/17/2017
 */
import java.io.*;
import java.net.*;


public class Myclient {
	public static void main(String[] args) throws IOException{
		String host = null, action = null, filename = null;
		int port;
		//check command
		try{
			//host prt_number GET/PUT file_name
			if(args.length<4)
				{throw new IllegalArgumentException("Missing command");}
			//get the host address
			host = args[0];
			//get port number
			port = Integer.parseInt(args[1]);
			//get action command
			action = args[2];
			//get file name
			filename = args[3];
			
			OutputStream clientLog = System.out;
			//start initial socket
			//get a network socket connection to host and port
			Socket clientSocket = new Socket(host, port);
			// get input stream of socket
			InputStream into_client = clientSocket.getInputStream();
			//get output stream of socket
			PrintWriter outof_client = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			
			//start get
			if(action.equals("GET")){
				//send HTTP comand to web server
				outof_client.printf(action + " /" + filename + " HTTP/1.1\r\n");
				outof_client.printf("Host: " + host + "\r\n\r\n");
				//send it
				outof_client.flush();
				
				//
				byte[] buffer = new byte[4896];
				int bytes_read;
				
				while((bytes_read = into_client.read(buffer)) != -1)
					clientLog.write(buffer, 0, bytes_read);
				
				clientLog.close();
			}
			else{
				if(action.equals("PUT")){
					//send file to Service
					InputStream from_file = null;
					File file = new File(filename);
					int ch;
					StringBuffer strContent = new StringBuffer("");
					
					try{
						from_file = new FileInputStream(file);
						
						//transfer it to char
						while((ch = from_file.read()) != -1)
							strContent.append((char)ch);
						
						from_file.close();
						System.out.println("file: " + strContent);
						
						//start upload file to server
						outof_client.println( action + " " + filename);
						outof_client.println(strContent + "\r\n");
						outof_client.flush();
						
						from_file.close();
					}
					catch(FileNotFoundException e){
						System.out.println("File " + file.getAbsolutePath() + "cannot be found.");
					}
					catch(IOException ioe){
						System.out.println("Exceptino while reading the file" + ioe);
					}
				}
				else{
					System.out.println( action + "is an Invalid HTTP request");
					System.exit(1);
				}
			}
			outof_client.close();
			clientSocket.close();
			System.exit(0);
		}
		catch(UnknownHostException e){
			System.err.println("Cannot understand host: " + host);
			System.exit(1);
		}
		catch(IOException e){
			System.err.println("Cannot get IO from:" + host);
			System.exit(1);
		}
	}
}
