import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class NeighbourNode {
	String HostName;
	int PortNumber;

	NeighbourNode(String _hostName, int _portNumber) {
		this.HostName = _hostName;
		this.PortNumber = _portNumber;
	}
}

class ParseConfigFile {
	final static HashMap<String,Node> nodeList = new HashMap<>();

	public static Node read(String Path, String hostName) throws IOException {
		System.out.println(hostName);
		HashMap<Integer, NeighbourNode> map = new HashMap<>();
		HashMap<Integer, NeighbourNode> UIDofNeighbors = new HashMap<Integer, NeighbourNode>();
		BufferedReader b = new BufferedReader(new FileReader(Path));
		String readLine = "";
		String path = "";

		// Filtering the File 
		String entireFileinString = "";
		while ((readLine = b.readLine()) != null) {
			entireFileinString += readLine+"\n";
		}
		b.close();
		entireFileinString = entireFileinString.replaceAll("(?m)^#.*", "");
		entireFileinString = entireFileinString.replaceAll("(?m)^[ \t]*\r?\n","");

		String[] line = entireFileinString.split("\n");
		int no = 0;

		b = new BufferedReader(new FileReader(Path));
		path = line[no++];
		int nodeNumber = Integer.parseInt(line[no++]);
		Node node = new Node();
		int myUID = -1;
		try {

			for (int xyz = 0; xyz < nodeNumber; xyz++) {
				readLine = line[no++].trim();
				String[] s = readLine.split("\\s+");
				for(int i=0;i<s.length;i++)
					System.out.println(i+":"+s[i]);
				int UID = Integer.parseInt(s[0]);
				String Hostname = s[1];
				int Port = Integer.parseInt(s[2]);
				map.put(UID, new NeighbourNode(Hostname, Port));
				if (hostName.equals(Hostname))
					myUID = UID;
				nodeList.put(Hostname,new Node(UID, Port, Hostname, UIDofNeighbors));
			}

			node = nodeList.get(hostName);
			int quorumNumbers = Integer.parseInt(line[no++]);

			for( int xyz = 0 ; xyz < quorumNumbers; xyz++ ) {
				String[] str = line[no++].trim().split("\\s+");
				ArrayList<Integer> temp = new ArrayList<>();
				for( int i = 1; i < str.length; i++ )
					temp.add(Integer.parseInt(str[i]));
				node.quorums.put(Integer.parseInt(str[0]), temp);
			}

			int noofClientConnections = Integer.parseInt(line[no++]);

			for( int xyz = 0; xyz < noofClientConnections; xyz++) {
				String[] s = line[no++].trim().split("\\s+");
				if (myUID == Integer.parseInt(s[0])) {
					for (int i = 1; i < s.length; i++) {
						UIDofNeighbors.put(Integer.parseInt(s[i]), map.get(Integer.parseInt(s[i])));
						System.out.println(s[0] + s[i]);
					}
				}
			}
			
			node.uIDofNeighbors = UIDofNeighbors;
			File folder = new File(path+"/Output");
			if(folder.exists() && folder.isDirectory()) {
				for(File f : folder.listFiles())
					if(!f.isDirectory())
						f.delete();
			}
			else
				folder.mkdir();
			File tmp = new File(folder.getPath());
			tmp.createNewFile();
			node.filePath = tmp.getPath();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {

			b.close();
		}
		return node;
	}
}