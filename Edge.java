import java.util.*;
class Edge extends Node {	//Tackles with all Element<key,Value> to be inserted in the last level(Leaf level)
	 ArrayList<Leaf> bush = new ArrayList<Leaf>();  //Doubly Linked List with info stored as List of [keys:values]
	 Edge prev = null;
	 Edge next = null;
	 Edge() {
		this.Edge = true;
	}
	 void push(Leaf child){     //Pushing the element in already sorted leaf location
		if(bush.isEmpty()){
			bush.add(child);
		}
		else{ 
			for(int i = 0; i < bush.size(); i++){   //Only One condition check since all the elements are already sorted
				if(bush.get(i).key > child.key){
					bush.add(i, child);             //adding the leaf into bush(Arraylist of leaves)
					return;
				}
			}
			bush.add(child);
		}
	}
}