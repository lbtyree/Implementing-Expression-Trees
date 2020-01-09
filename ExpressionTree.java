import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.*;

public class ExpressionTree {

    public ExpressionNode root; // root node 

    //Constructor
    public ExpressionTree(String postfix){
        //create potential children nodes 
        ExpressionNode right;
        ExpressionNode left; 
        
        // split the postfix at every space 
        String[] splitUpPostfix = postfix.split(" ");
        // create stack of nodes from the tree 
        ArrayStack<ExpressionNode> nodeStack = new ArrayStack<ExpressionNode>();
        
        int i = 0; 
        while(i < splitUpPostfix.length){ //iterate through entire array length
            if(!findoperator(splitUpPostfix[i])) { // if an int 
                //create new node for the integer, node has no children 
                right= null; 
                left= null; 
                ExpressionNode isInt = new ExpressionNode(splitUpPostfix[i], left, right);
                nodeStack.push(isInt); //push to stack 

            } else { // is an operator 
                right = nodeStack.pop(); // pop two from stack 
                left = nodeStack.pop();
                //create new node with two children made from popped 
                ExpressionNode babyTree = new ExpressionNode(splitUpPostfix[i], left, right);
                nodeStack.push(babyTree); // push subtree back to stack 
                // set root equal to subtree
                root = babyTree;
            }
          i++; // move to next 
        }
    }
    public static class ExpressionNode {

        public String currentNode;
        public ExpressionNode left;
        public ExpressionNode right;
        
        // constructor , create node format 
        public ExpressionNode(String currentNode, ExpressionNode left, ExpressionNode right) {

            this.currentNode = currentNode; //is root at start 
            this.left = left;
            this.right = right;
        }
    }
    
     public int eval() {
        return eval(root); //recursively evaluated the tree starting at top 
    }
    
    private static int eval(ExpressionNode p) {//evals operator and performs operation 
        
        String nodE= p.currentNode; //the node being processed now 
        
        if(nodE.equals("+")) {
            return (eval(p.left) + eval(p.right)); //use eval recursively 
        }else if(nodE.equals("*")) {
            return (eval(p.left) * eval(p.right));
        }else if(nodE.equals("-")) {
            return (eval(p.left) - eval(p.right));
        }else if(nodE.equals("/")) {
            return (eval(p.left)/eval(p.right));
        }else {// return int in node (recursed down to 1 node tree) 
            Integer peek= Integer.parseInt(nodE); // no peek method, would also work
            return peek;  
        }
    }
    
    public String getPostfix() {
        return getPostfix(root); //recursive starting at top of tree, returns string formed below 
    }
    
    // creates a string out of given postfix 
    private static String getPostfix(ExpressionNode p) { 
       
        String nodE= p.currentNode;
        
         //make a string builder to form a string output 
        StringBuilder expPostfix = new StringBuilder(1000);
        
      if(nodE.equals("+")) { //recurse using postfix, starting at top of tree, adding in spaces  
        return expPostfix.append(getPostfix(p.left) + " " + getPostfix(p.right) + " " + "+").toString();
      }else if(nodE.equals("*")) {
        return expPostfix.append(getPostfix(p.left) + " " + getPostfix(p.right) + " " + "*").toString();
      }else if(nodE.equals("-")) {
        return expPostfix.append(getPostfix(p.left) + " " + getPostfix(p.right) + " " + "-").toString();
      }else if(nodE.equals("/")) {
        return expPostfix.append(getPostfix(p.left) + " " + getPostfix(p.right) + " " + "/").toString();
      }else{ // only one node, just return current 
        return nodE;
             }
        
      
  }
                
    
      //Helper method. Returns true if the character is either +, -, *, / 
    private boolean findoperator(String input){       
          if(input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/")) 
             return true;     
          else              
             return false;             
    }

    //Tester 
    public static void main(String[] args) {
        //Tests methods
        //create a postfix expression to be tested              
        try{
        ExpressionTree theTree = new ExpressionTree("34 2 - 5 *");
        theTree.getPostfix();
        }catch(Exception E){
        E.printStackTrace();
        throw new IndexOutOfBoundsException("This is not a postfix expression");
        }
    
        ExpressionTree theTree = new ExpressionTree("34 2 - 5 *");
        System.out.println(theTree.eval());
        System.out.println(theTree.getPostfix());

        
    }
}

