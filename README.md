# httpServer-Client
In this project I have implemented an HTTP client and server running a pared down version of HTTP/1.0. Both of the server and client are written in Java.

# Web Server
The Web server is capable of processing one request. Specifically, the Web server can do such things:
(1) Initialize the server. Server should take command line arguments specifying a port number.
(2) Wait for a client connection on the port number specified by command line argument.
(3) When a client connection is accepted, read the HTTP request.
(4) Construct a valid HTTP response including status line. Basically, it can handle two methods: GET and PUT.
(5) Close the client connection and loop back to wait for the next client connection to arrive.

# Client
The client could take command line arguments specifying a server name or IP address, the port, the method, and the path of the requested object on the server. There are two methods of HTTP: GET and PUT.
- GET
The basic client GET action:
(1) Connect to the server via a connection-oriented socket.
(2) Submit a valid HTTP/1.0 GET request for the supplied URL.
(3) Read (from the socket) the server's response and display it as program output.
- PUT
The basic client PUT action:
(1) Connect to the server via a connection-oriented socket.
(2) Submit a PUT request for the supplied file.
(3) Send the file to the server.
(4) Wait for server's reply.
