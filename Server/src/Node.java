import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.io.IOException;
import java.net.*;

public class Node {
	int UID, port;
	String path;
	String HostName;
	HashMap<Integer, NeighbourNode> uIDofNeighbors;
	ServerSocket serverSocket;
	List<TCPClient> connectedClients = Collections.synchronizedList(new ArrayList<TCPClient>());
	BlockingQueue<Message> msgQueue;
	int sentMessageCount;
	int receivedMessageCount;
	private boolean locked = false;

	public Node(int UID, int port, String hostName, HashMap<Integer, NeighbourNode> uIDofNeighbors) {
		this.UID = UID;
		this.port = port;
		this.HostName = hostName;
		this.uIDofNeighbors = uIDofNeighbors;
		this.msgQueue = new PriorityBlockingQueue<Message>();
	}

	public Node() {
	}

	public Message getHeadMessageFromQueue() {
		if (this.msgQueue.peek() != null) {
			Message msg = this.msgQueue.peek();
			this.msgQueue.remove();
			return msg;
		}
		return null;
	}

	public Message getMessageFromQueue() {
		Message msg = null;
		try {
			msg = this.msgQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return msg;
	}

	synchronized public void addMessageToQueue(Message msg) {
		//			setMyTimeStamp(Math.max(msg.timeStamp,getMyTimeStamp()));
		incrementReceivedMessages();
		if(msg.getMsgType() == MessageType.Release)
			setLocked(false);
		else
			msgQueue.add(msg);
	}

	public void sendGrant(int UID) {
		synchronized (connectedClients) {
			for(TCPClient client : connectedClients){
				if(UID == client.getServerUID()) {
					try {
						System.out.println("Sending Grant to UID: "+ UID);
						client.getOutputWriter().writeObject(new Message(this.UID,MessageType.Grant));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			incrementSentMessages();
		}
	}

	public void sendCompletion() {
		synchronized (connectedClients) {
			for(TCPClient client : connectedClients){
				try {
					System.out.println("Sending Completion to UID: "+ client.getServerUID());
					client.getOutputWriter().writeObject(new Message(this.UID,MessageType.Completion));
				} catch (IOException e) {
					e.printStackTrace();
				}
				incrementSentMessages();
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

	synchronized public void setLocked(boolean status){
		this.locked = status;
	}
	
	public boolean isLocked() {
		return this.locked;
	}
	
	public HashMap<Integer, NeighbourNode> getNeighbors() {
		return this.uIDofNeighbors;
	}

	public void addClient(TCPClient client) {
		synchronized (connectedClients) {
			connectedClients.add(client);
		}
	}


	public List<TCPClient> getAllConnectedClients() {
		return this.connectedClients;
	}

	synchronized public void incrementSentMessages() {
		this.sentMessageCount++;
	}
	
	synchronized public void incrementReceivedMessages() {
		this.receivedMessageCount++;
	}
	
	public int getSentMessagesCount() {
		return this.sentMessageCount; 
	}
	
	public int getReceivedMessagesCount() {
		return this.receivedMessageCount; 
	}
}
