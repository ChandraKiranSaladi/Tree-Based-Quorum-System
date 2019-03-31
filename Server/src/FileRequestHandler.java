public class FileRequestHandler {

	Node dsNode;

	public FileRequestHandler(Node node) {
		this.dsNode = node;
	}

	public void listen() {
		// Listen for Requests
		while(true) {
			if(dsNode.isLocked() == false && dsNode.msgQueue.size() != 0 ) {
				Message msg = dsNode.getHeadMessageFromQueue();
				if(msg.getMsgType() == MessageType.Request) {
					dsNode.setLocked(true);
					dsNode.sendGrant(msg.getsenderUID());
				}
				else if(msg.getMsgType() == MessageType.Completion) {
					System.out.println("Total Sent Messages: "+ dsNode.getSentMessagesCount());
					System.out.println("Total Received Messages: "+ dsNode.getReceivedMessagesCount());
					break;
				}
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}