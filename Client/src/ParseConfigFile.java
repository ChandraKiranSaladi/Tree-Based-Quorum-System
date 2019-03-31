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
		HashMap<Integer, NeighbourNode> map = new HashMap<>();
		HashMap<Integer, NeighbourNode> UIDofNeighbors = new HashMap<Integer, NeighbourNode>();
		BufferedReader b = new BufferedReader(new FileReader(Path));
		String readLine = "";
		String path = "";

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
		path = b.readLine();
		int nodeNumber = Integer.parseInt(b.readLine());
		Node node = new Node();
		int myUID = -1;
		try {

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
			int quorumNumbers = Integer.parseInt(b.readLine());

			for( int xyz = 0 ; xyz < quorumNumbers; xyz++ ) {
				String[] str = b.readLine().trim().split("\\s+");
				ArrayList<Integer> temp = new ArrayList<>();
				for( int i = 1; i < str.length; i++ )
					temp.add(Integer.parseInt(str[i]));
				node.quorums.put(Integer.parseInt(str[0]), temp);
			}

			int noofClientConnections = Integer.parseInt(b.readLine());

			for( int xyz = 0; xyz < noofClientConnections; xyz++) {
				String[] s = b.readLine().trim().split("\\s+");
				if (myUID == Integer.parseInt(s[0])) {
					for (int i = 1; i < s.length; i++) {
						UIDofNeighbors.put(Integer.parseInt(s[i]), map.get(Integer.parseInt(s[i])));
						System.out.println(s[0] + s[i]);
					}
				}
			}
			File folder = new File(path+"\\AOSProject2");
			if(folder.exists() && folder.isDirectory()) {
				for(File f : folder.listFiles())
					if(!f.isDirectory())
						f.delete();
			}
			else
				folder.mkdir();
			File tmp = new File(folder.getPath()+"\\log.txt");
			tmp.createNewFile();
			node.filePath = tmp.getAbsolutePath();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {

			b.close();
		}
		return node;
	}
}