import java.util.*;
class Tree {            //The main tree class
	static Node parent = null;      
	static int order;
	public Tree(int order){         //Creating a tree of 'm' ordered B+Tree
		this.order = order;
	}
	static Tree delete(Map input,Integer key){      // Removes the element from the tree

        parent=null;
		Iterator capsule=input.entrySet().iterator();   //Iterates through the whole insert buffer
		Tree bpt=new Tree(order);           //Creating a dummy tree space to work on 
		String removeThis= new Integer(key).toString();             
		while(capsule.hasNext()){                   //Since the elements were inserted in sorted order, there is no need of sorting again
			Map.Entry<String,String> mapelement=(Map.Entry)capsule.next();//Iterating through the B+ Tree to find the key
			if(!mapelement.getKey().equals(removeThis)){                        //If key not equal to the element, leave it as it is          
                bpt.push(Double.parseDouble(mapelement.getKey()), mapelement.getValue());   //node[i-1]=node[i+1]
                bpt.balance();                                      //balance the left B+ Tree
			}
        }
		return bpt;
	}
	
	 static Node push(Node root, Leaf leaf){
		if(!root.Edge){           // Checking if the root is in the leaf node
			root.overFlow = false;
			ArrayList<Double> roots = new ArrayList<Double>();      //Arraylist of internal subroots 
			roots = ((Branch)root).keys; 
			int k = 0;
			while(k < roots.size()){            //traversing through all the elements

				if(roots.get(k) > leaf.key || k == roots.size()-1) {
					int newIndex = roots.get(k) > leaf.key ? k : k + 1;  //Inserting in sorted order
					
					Node node = new Node();
					node = push(((Branch)root).values.get(newIndex), leaf); 
					
					if(node.overFlow){  //OverFlow condition check
						
						((Branch)root).insert(((Branch)node).keys.get(0), ((Branch)node).values.get(0),
								((Branch)node).values.get(1)); 
						if(((Branch)root).keys.size() == order){ 
							int splitPosition = ((int) Math.ceil(((double)order)/2)) - 1;       //Splits from the mid element
							Branch branch = new Branch();
							Branch subbranch = new Branch();
							branch.insert(((Branch)root).keys.get(splitPosition), root, subbranch);     //Creating new subroot for the internal branches
							((Branch)root).keys.remove(splitPosition);
							
							for(int i = splitPosition, j = splitPosition; i < order-1; i++){    //Keep on spliting and removing the mid element until balanced
								subbranch.keys.add(((Branch)root).keys.remove(j));
							}
							int j = splitPosition + 1;
							for(int i = splitPosition+1; i <= order; i++){ 
								subbranch.values.add(((Branch)root).values.remove(j));      
							}
							branch.overFlow = true;                     
							return branch;
						}
						return root;            //Returning the sorted sequence of the tree
					}
					else 
						return root;
					
				}
				k++;
			}
		}
		else{ 
			((Edge)root).push(leaf);    //If space left in the node, insert it in

			if(((Edge)root).bush.size() == order){  //Does the Tree overflow after insertion?
				int index = order/2; 
				Edge node = new Edge();
				for(int i = index, j = index; i < order; i++){ 
					node.push(((Edge)root).bush.remove(j));             //if not: do nothing else: split again
				}
				node.next = ((Edge)root).next;
				((Edge)root).next = node;
				node.prev = ((Edge)root);
				if(node.next != null)
					node.next.prev = node;
				
				Branch split = new Branch();
				split.keys.add(node.bush.get(0).key);   //Spliting and balancing the overflow "After" Inserting a new node
				split.overFlow = true;
				split.values.add((Edge)root);
				split.values.add(node);
				return split;   //returning the balanced tree
			}
			else
				return root;
		}
		
		return root;
	}
	 static void push(double key, String value){        //Inserting in the Leaf Position
		Leaf child = new Leaf(key, value);
		if(parent == null){             //If the Tree is empty, then make the only element as parent
			parent = new Edge();
			((Edge)parent).push(new Leaf(key, value));
		}
		else{
			parent = push(parent, child);       //Else add it recursively in the right position(Could be a leaf/subroot of the subtree)
		}
    }
    void balance(){};       
	static String find(double key){         //Single Search 
		Node node = new Node();
		node = parent;
		//IF NODE IS NOT A LEAF
		while(!node.Edge){          //Traverse until we don't reach the last level(Leaf)
			int i = 0;
			while(i < ((Branch)node).keys.size() && ((Branch)node).keys.get(i) < key){ 
				i++;    //If in between, we get the right position, add the element
			}
			node = ((Branch)node).values.get(i);    
		}
		
		StringBuilder str = new StringBuilder();           //Finally inserts the element in the right place

		while((Edge)node != null){
			for(int i=0; i < ((Edge)node).bush.size(); i++){        //Finally including the element in Edge Level as well
				double LeafKey = ((Edge)node).bush.get(i).key;  //Insertion in the doubly linked list at ground level
				if(LeafKey == key){
					str.append(((Edge)node).bush.get(i).val).append(", ");
				}
				else if( LeafKey > key){ 
				
					break;
				}
			}
			node = ((Edge)node).next;
		}
		if(str.toString().equals("")){          //Null Condition Check
			return "Null";	
		}
			
		else{
			return str.toString();
		}
	}

	 static StringBuilder extractRange(StringBuilder str, Node node, Double key1, Double key2){     //Range wise Search 
		
		ArrayList<Leaf> leafList = new ArrayList<Leaf>();
		leafList = ((Edge)node).bush;               //Traversing throughout the in bound range
		for(int i = 0; i < leafList.size(); i++){
			if(leafList.get(i).key >= key1 && leafList.get(i).key <= key2 ){
				str.append("(").append(leafList.get(i).key).append(",").append(leafList.get(i).val).append("), ");  //Appends all the <Key:Value> between the range
			}
			if(leafList.get(i).key > key2){
				return str;
			}
		}           //Finally Returns the str that carries all the elements
		
		return str;
	}
	
	
	 static String find(double key1, double key2){      //Function that makes use of the range function above 
		Node node = new Node();             
		node = parent;
		while(!node.Edge){ 
			int index = 0;
			while(index < ((Branch)node).keys.size() && ((Branch)node).keys.get(index) < key1){
				index++;
			}
			node = ((Branch)node).values.get(index); 
		}
	
		StringBuilder str = new StringBuilder(); 
		str = extractRange(str, node, key1, key2); //Described above
		while(((Edge)node).next != null){
			if(((Edge)node).next.bush.get(0).key > key2){   //If the element is at the very 1st iteration
				break;
			}
			else{ 
				str = extractRange(str, ((Edge)node).next, key1, key2); 
			}
			node = ((Edge)node).next; 
		}
		
		if(str.toString().equals("")){
			return "Null";	
		}	
		else{
			return str.toString();              //Returns in string form to print in the output text file
		}
			
	}
	
}