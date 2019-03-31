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

			new FileRequestHandler(dsNode).listen();;
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	public static Node BuildNode(int hostNumIndex) {
		Node dsNode = new Node();
		try {
			dsNode = ParseConfigFile.read(
					"C:\\Users\\ehaacls\\OneDrive - The University of Texas at Dallas\\"
					+ "CS 6378 ( Advanced Operating Systems )\\Projects\\"
					+ "Ricart Agrawala Mutex\\src\\readFile.txt",
							InetAddress.getLocalHost().getHostName(), hostNumIndex);
		} catch (Exception e) {
			throw new RuntimeException("Unable to get nodeList", e);
		}
		return dsNode;
	}
}
