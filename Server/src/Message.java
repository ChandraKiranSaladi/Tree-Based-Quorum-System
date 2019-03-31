import java.io.Serializable;

public class Message  implements Serializable{
//	, Comparable<Message> 
	private static final long serialVersionUID = 1L;
	private String timeStamp;
	private int senderUID;
	private MessageType msgtype;
	
	
	public Message(int senderUID, MessageType Msgtype) {
		this.senderUID = senderUID;
		this.msgtype = Msgtype;
	}
	
	public Message(String timeStamp, int senderUID,MessageType messageType ) {
		this.timeStamp = timeStamp;
		this.senderUID = senderUID;
		this.msgtype = messageType;
	}
	
	public Message(MessageType msgtype) {
		this.msgtype = msgtype;
	}
	
//	public Message(Message message) {
//		this(message.timeStamp, message.senderUID, message.msgtype);
//	}

	public String getTimeStamp() {
		return this.timeStamp;
	}
	
	public int getsenderUID() {
		return this.senderUID;
	}

	public MessageType getMsgType() {
		return this.msgtype;
	}

//	@Override
//	public int compareTo(Message msg) {
//		return 1;
//	}
}
