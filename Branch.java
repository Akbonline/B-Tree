import java.util.*;
class Branch extends Node {         //Handles nodes of Internal branch of the tree
    ArrayList<Double> keys = new ArrayList<Double>();
    ArrayList<Node> values = new ArrayList<Node>();                 //These values are of list that carries references(node type) to the branches
    void insert(double key, Node right_child, Node left_child){
       if(this.keys.isEmpty()){             //If the node is empty
           this.keys.add(key);                                      //Adding in the internal node
           this.values.add(right_child);            //Adding the reference pointer to the right subtree
           this.values.add(left_child);             //Adding the reference pointer to the left subtree
       }
       else{

           int i = 0;                                   //Else traverse the nodes and insert in the right sorted order
           while(i < this.keys.size()){
               if(this.keys.get(i) > key) {

                   this.keys.add(i, key);
                   this.values.add(i + 1, left_child);
                   
                   return;
               }
               i++;
           }
           this.keys.add(key);
           this.values.add(left_child);
       }
       return;
   }
   
}
