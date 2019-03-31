import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Scanner;

public class InvokeMain {
	public static void main(String[] args) {
		try {

			// build a node for each terminal

			// logic for assigning nodes - temporary
			//			Scanner scanner = new Scanner(System.in);

			//			int hostNumIndex = scanner.nextInt();
			// Integer.parseInt(args[0]);
			int hostNumIndex = Integer.parseInt(args[0]);;

			Node dsNode = BuildNode(hostNumIndex);

			System.out.println("Initializing Server with UID: " + dsNode.UID);

			// Start server thread
			Runnable serverRunnable = new Runnable() {
				public void run() {
					TCPServer server = new TCPServer(dsNode);
					// start listening for client requests
					server.listenSocket();
				}
			};
			Thread serverthread = new Thread(serverRunnable);
			serverthread.start();

			System.out.println("Server started and listening to client requests.........");

			Thread.sleep(10000);

			// Iterate through the node neighbors to send the Client Requests
			dsNode.uIDofNeighbors.entrySet().forEach((neighbour) -> {
				Runnable clientRunnable = new Runnable() {
					public void run() {
						TCPClient client = new TCPClient(dsNode.UID,
								neighbour.getValue().PortNumber, neighbour.getValue().HostName, dsNode.getNodeHostName(), neighbour.getKey(),
								dsNode);
						System.out.println("Client Connection sent from "+dsNode.UID+" to UID: "+neighbour.getKey()+" at Port: "+neighbour.getValue().PortNumber);
						// The following function calls starts the Socket Connections, and adds the client to a list to access
						// it later. Listen Messages is an infinite loop to preserve the socket connection
						client.listenSocket();
						client.sendHandShakeMessage();
						dsNode.addClient(dsNode.UID,client);
						client.listenToMessages();
					}
				};
				Thread clientthread = new Thread(clientRunnable);
				clientthread.start();
			});

			// Sleep so that all the Client connections are established		
			Thread.sleep(5000);
			new FileRequestAccess(dsNode);
			dsNode.printReport();
			
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Node BuildNode(int hostNumIndex) {
		Node dsNode = new Node();
		try {
			dsNode = ParseConfigFile.read(
					"C:\\Users\\kiran\\OneDrive - The University of Texas at Dallas\\"
					+ "CS 6378 ( Advanced Operating Systems )\\Projects\\"
					+ "Tree Based Quorum\\Client\\src\readFile.txt",
							InetAddress.getLocalHost().getHostName(), hostNumIndex);
		} catch (Exception e) {
			throw new RuntimeException("Unable to get nodeList", e);
		}
		return dsNode;
	}
}
