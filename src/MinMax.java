import java.io.*;
import java.util.*;

import javax.swing.tree.DefaultMutableTreeNode;


public class MinMax {
	static String dataname="aiminimax";
	static int childcount;
	static int level;
	static int[] leaf;
	static int count=0;
	static DefaultMutableTreeNode Tree=new DefaultMutableTreeNode();
	public static void main(String[] args) throws IOException {
		readData();
		MiniMax((DefaultMutableTreeNode)Tree.getRoot());
		System.out.println("level="+level);
		System.out.println("childcount="+childcount);
		System.out.println("count="+count);
		System.out.println("root="+Tree.getRoot());
	}
	public static void readData() throws IOException{
		FileReader fr=new FileReader("./"+dataname+".txt");
		BufferedReader br=new BufferedReader(fr);
		ArrayList<String> strarray = new ArrayList<String>();
		String s;
		String [] temp;
		while((s=br.readLine())!=null){
			strarray.add(s);
		}
		childcount=Integer.parseInt(strarray.get(0));
		level=Integer.parseInt(strarray.get(1));
		temp=strarray.get(2).split(",");
		leaf=new int[temp.length];
		
		ArrayList<DefaultMutableTreeNode> treenode = new ArrayList<DefaultMutableTreeNode>();
		
		for(int i=0;i<temp.length;i++){
			leaf[i]=Integer.parseInt(temp[i]);
			treenode.add(new DefaultMutableTreeNode(Integer.parseInt(temp[i])));
		}
		CreatTree(treenode);
	}
	public static void CreatTree(ArrayList<DefaultMutableTreeNode> treenode){
		if(treenode.size()!=1){
			DefaultMutableTreeNode node=new DefaultMutableTreeNode();
			ArrayList<DefaultMutableTreeNode> nodearray = new ArrayList<DefaultMutableTreeNode>();
			for(int i=0;i<treenode.size();i++){
				node.add(treenode.get(i));
				if((i+1)%childcount==0){
					nodearray.add(node);
					node=new DefaultMutableTreeNode();
				}
			}
			CreatTree(nodearray);
		}else{
			Tree=treenode.get(0);
			return;
		}
	}
	public static void MiniMax(DefaultMutableTreeNode node){
		for(int i=0;i<childcount;i++){
			
			if(node.getChildAt(i).toString().length()==0){
				MiniMax((DefaultMutableTreeNode)node.getChildAt(i));
			}
			count++;

			int childnode = nodeInt((DefaultMutableTreeNode)node.getChildAt(i));
			switch(node.getLevel()%2){
			case 0://第root層跟第2層 需要取Max

				if(node.getUserObject()==null || childnode>nodeInt(node)){
					node.setUserObject(childnode);
				}
				
				if(node.getParent()!=null&& node.getParent().toString().length()!=0 && nodeInt(node)>nodeInt((DefaultMutableTreeNode)node.getParent())){
					System.out.println(node.getUserObject());
					return;
				}
				break;
			case 1:	//第1層跟第3層 需要取min

				if(node.getUserObject()==null || childnode<nodeInt(node)){
					node.setUserObject(childnode);
				}
				if(node.getParent()!=null&& node.getParent().toString().length()!=0 && nodeInt(node)<nodeInt((DefaultMutableTreeNode)node.getParent())){
					System.out.println(node.getUserObject());
					return;
				}
				break;
			}
		}
	}
	public static Integer nodeInt(DefaultMutableTreeNode node){
		
		return (int)node.getUserObject();
	}
}
