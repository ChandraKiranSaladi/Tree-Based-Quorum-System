import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

class NeighbourNode {
	String HostName;
	int PortNumber;

	NeighbourNode(String _hostName, int _portNumber) {
		this.HostName = _hostName;
		this.PortNumber = _portNumber;
	}
}

class ParseConfigFile {
	final static List<Node> nodeList = new ArrayList<>();

	public static Node read(String Path, String hostName, int hostNumIndex) throws IOException {
		System.out.println(hostNumIndex);
		BufferedReader b = new BufferedReader(new FileReader(Path));
		String readLine = "";

		// Filtering the File 
		String entireFileinString = "";
		while ((readLine = b.readLine()) != null) {
			entireFileinString += readLine;
		}
		b.close();
		entireFileinString.replaceAll("(?m)^#.*", "");
		entireFileinString.replaceAll("(?m)^\\s+$","");
		BufferedWriter writer = new BufferedWriter(new FileWriter(Path));
		writer.write(entireFileinString);
		writer.close();


		b = new BufferedReader(new FileReader(Path));
		int nodeNumber = Integer.parseInt(b.readLine());
		Node node = new Node();
		int myUID = -1;

			for (int xyz = 0; xyz < nodeNumber; xyz++) {
				readLine = b.readLine().trim();
				String[] s = readLine.split("\\s+");
				for(int i=0;i<s.length;i++)
					System.out.println(i+":"+s[i]);
				int UID = Integer.parseInt(s[0]);
				String Hostname = s[1];
				int Port = Integer.parseInt(s[2]);
				if (hostNumIndex == UID)
					myUID = UID;
				nodeList.add(new Node(UID, Port, Hostname, null));
			}

			node = nodeList.get(hostNumIndex);

		return node;
	}
}