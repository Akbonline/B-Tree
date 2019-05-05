import java.util.*;
class Leaf {    //Leaf node contains key: value
    double key;
    String val;
   
   public Leaf(double key, String val){     //constructor to initialize the Leaf node
       this.key = key;
       this.val = val;
   }
}
class Node {        //Node properties to determine rotation:-
    int order;          //Maximum number of child that it can have
    boolean Edge = false;   //is the node leaf?
    boolean overFlow = false;   //Is the node full and thus, does it require Splitting?
   
}