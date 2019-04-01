import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;

public class Node {
	int UID, port;
	String filePath;
	String HostName;
	HashMap<Integer, NeighbourNode> uIDofNeighbors;
	ServerSocket serverSocket;
	Map<Integer,TCPClient> connectedClients = (Map<Integer, TCPClient>) Collections.synchronizedMap(new HashMap<Integer,TCPClient>());
	HashMap<Integer,ArrayList<Integer>> quorums = new HashMap<>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	int messageGrantCount, sentMessageCount, receivedMessageCount;
	int[] messageCountCS = new int[20];
	int tempMessageCount;
	Date csStart;
	long[] latency = new long[20];
	
	public Node(int UID, int port, String hostName, HashMap<Integer, NeighbourNode> uIDofNeighbors) {
		this.UID = UID;
		this.port = port;
		this.HostName = hostName;
		this.uIDofNeighbors = uIDofNeighbors;
	}

	public Node() {
	}

	public void sendMessageToQuorum(int quorumNumber, MessageType msgType, int csEntryCount) {
		synchronized (connectedClients) {
			ArrayList<Integer> quorum = quorums.get(quorumNumber);
			for(int x : quorum) {
				TCPClient client = connectedClients.get(x);
				try {
					if(msgType == MessageType.Request)
						System.out.println("Sending Request to: "+x);
					else 
						System.out.println("Sending Release to: "+x);
					client.getOutputWriter().writeObject(new Message(csStart, this.UID, msgType ));
					//						System.out.println("Connection Closed for UID:"+getsenderUID);
				} catch (IOException e) {
					e.printStackTrace();
				}
				incrementSentMessageCount();
			}
		}
	}

	public void sendCompletion() {
		incrementSentMessageCount();
		synchronized (connectedClients) {
			TCPClient client = connectedClients.get(1);
			try {
				System.out.println("Sending Completion to: 1");
				client.getOutputWriter().writeObject(new Message(new Date(),this.UID,MessageType.Completion));
				//						System.out.println("Connection Closed for UID:"+getsenderUID);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void attachServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public int getNodeUID() {
		return this.UID;
	}

	public int getNodePort() {
		return this.port;
	}

	public String getNodeHostName() {
		return this.HostName;
	}

	public HashMap<Integer, NeighbourNode> getNeighbors() {
		return this.uIDofNeighbors;
	}

	public void addClient(int UID, TCPClient client) {
		synchronized (connectedClients) {
			connectedClients.put(UID, client);
		}
	}


	public 	Map<Integer,TCPClient> getAllConnectedClients() {
		return this.connectedClients;
	}

	synchronized public void messageHandler(Message msg) {
		incrementReceivedMessageCount();
		if(msg.getMsgType() == MessageType.Grant) {
			incrementGrantMessageCount();
		}
		if(msg.getMsgType() == MessageType.Completion) {
			printReport();
		}
	}

	synchronized void incrementGrantMessageCount() {
		this.messageGrantCount++;
	}

	synchronized void incrementSentMessageCount() {
		this.sentMessageCount++;
	}

	synchronized void incrementReceivedMessageCount() {
		this.receivedMessageCount++;
	}

	synchronized void messageCountCS(int csEntryCount) {
		this.messageCountCS[csEntryCount]++;
	}

	public String getMyTimeStamp() {
		return sdf.format(new Date());
	}

	public void waitforGrantFromQuorum(int quorumNumber) {
		int numberOfGrants = this.quorums.get(quorumNumber).size();
		System.out.println("Waiting For Grant");
		while(numberOfGrants != this.messageGrantCount) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void printReport() {
		System.out.println("Total Sent Messages: "+sentMessageCount);
		System.out.println("Total Received Messages: "+receivedMessageCount);
		
		for(int i = 0; i < 20; i++) {
			System.out.println(" Total messages Exchanged for CS["+i+"]: " + messageCountCS[i]);
		}
		for(int i = 0; i < 20; i++) {
			System.out.println(" Latency for CS["+i+"]: " + latency[i]);
		}
	}

}
