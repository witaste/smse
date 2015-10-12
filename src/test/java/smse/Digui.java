package smse;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

public class Digui {

	@Test
	public void drawTree() {
		List<Node> nodeList = new ArrayList<Node>();
		nodeList.add(new Node("0001", "null", "中国"));
		nodeList.add(new Node("0002", "0001", "河北"));
		nodeList.add(new Node("0003", "0001", "辽宁"));
		nodeList.add(new Node("0004", "0002", "石家庄"));
		nodeList.add(new Node("0005", "0003", "沈阳"));
		nodeList.add(new Node("0006", "null", "美国"));
		nodeList.add(new Node("0007", "0006", "纽约"));
		System.out.println(children("null", nodeList));
	}

	// 递归条件：1. 延伸 2.有尽头
	public JSONArray children(String my, List<Node> nodes) {
		JSONArray myChildren = new JSONArray();
		for (Node node : nodes) {
			String id = node.getId();
			String pid = node.getPid();
			if (my.equals(pid)) {
				JSONObject myChild = new JSONObject();
				myChild.put("id", id);
				myChild.put("pid", pid);
				myChild.put("name", node.getName());
				JSONArray myChildChildren = children(id, nodes);
				if (myChildChildren.size() > 0)
					myChild.put("children", myChildChildren);
				myChildren.add(myChild);
			}
		}
		return myChildren;
	}

}

class Node {
	public Node(String id, String pid, String name) {
		this.id = id;
		this.pid = pid;
		this.name = name;
	}

	private String id;
	private String pid;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", pid=" + pid + ", name=" + name + "]";
	}
}