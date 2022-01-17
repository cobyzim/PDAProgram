/*
 * This program takes a pda input file and determines what the alphabet is for
 * that pda and uses the given transitions to see if a user's input string 
 * would be accepted or not.
 */
package cs475assign2;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Coby Zimmerman
 */
public class CS475Assign2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String startState = null;
        String fromState;
        Character inputSymbol;
        Character stackPull;
        Character stackPush = null;
        String toState;
        
        Character emptyString = 'e';
        Character emptyStack = '$';
        
        ArrayList<String> acceptStates = new ArrayList<>();
        
        ArrayList<String> untrimmedStates = new ArrayList<>();
        ArrayList<String> states = new ArrayList<>();
        ArrayList<Character> untrimmedInputAlphabet = new ArrayList<>();
        ArrayList<Character> inputAlphabet = new ArrayList<>();
        ArrayList<Character> untrimmedStackAlphabet = new ArrayList<>();
        ArrayList<Character> stackAlphabet = new ArrayList<>();
        
        ArrayList<Transition> transitions = new ArrayList<>();
        
        String userInputString;
        String userConfirm;

        File textFile;
        
        //Sets up JFileChooser for user to choose a file
        JFileChooser chooseFile = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "TEXT FILES", "txt", "text");
        chooseFile.setFileFilter(filter);
        int returnVal = chooseFile.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            textFile = chooseFile.getSelectedFile();
            
            //Try to read what is in the selected file
            try {
                //Determine the start state from first line of file
                try (Scanner scanner = new Scanner(textFile)) {
                    //Determine the start state from first line of file
                    startState = scanner.nextLine();
                    
                    //Take second line of file and put each accept state into
                    //arrayList as separate members (as long as one space between them)
                    String acceptedStatesLine = scanner.nextLine();
                    acceptStates = new ArrayList<>
                            (Arrays.asList(acceptedStatesLine.split(" ")));
                    
                    //While loop to help figure out the alphabets and total states.
                    //Also makes new transition objects from file and adds them
                    //to the transitions arrayList
                    while (scanner.hasNextLine()) {
                        String[] splitTrans = scanner.nextLine().split("[( )]+");
                        fromState = splitTrans[0];
                        inputSymbol = splitTrans[1].charAt(0);
                        stackPull = splitTrans[2].charAt(0);
                        stackPush = splitTrans[3].charAt(0);
                        toState = splitTrans[4];
                        
                        if (!inputSymbol.equals(emptyString)) {
                            untrimmedInputAlphabet.add(inputSymbol);
                        }
                        if (!stackPull.equals(emptyString)) {
                            untrimmedStackAlphabet.add(stackPull);
                        }
                        if (!stackPush.equals(emptyString)) {
                            untrimmedStackAlphabet.add(stackPush);
                        }
                        
                        Transition transition = new Transition(fromState, 
                                inputSymbol, stackPull, stackPush, toState);
                        transitions.add(transition);  
                    }
                    
                    //System.out.println(transitions);
                    
                    //Determines and displays the input alphabet associated with 
                    //the PDA
                    for (Character i : untrimmedInputAlphabet) {
                        if (!inputAlphabet.contains(i)) {
                            inputAlphabet.add(i);
                        }
                    }
                    System.out.println("This is the input alphabet associated "
                            + "with the input PDA file: " + inputAlphabet);
                    
                    //Determines and displays the stack alphabet associated with 
                    //the PDA
                    for (Character i : untrimmedStackAlphabet) {
                        if (!stackAlphabet.contains(i)) {
                            stackAlphabet.add(i);
                        }
                    }
                    System.out.println("This is the stack alphabet associated "
                            + "with the input PDA file: " + stackAlphabet);
                    
                }
            } catch (FileNotFoundException ex) {
                ex.toString();
            }
            
            userConfirm = JOptionPane.showInputDialog(null, "Would you like to "
                    + "input a string (Y/N)?");
            
            //if for the cancel button to avoid null pointer
            if (userConfirm != null) {
                while(userConfirm.equals("Y")) {
                    userInputString = JOptionPane.showInputDialog("Please enter an "
                        + "input string with no spaces");
                    
                    //Append a whitespace onto string so that the last
                    //transition where the $ is popped from the stack can occur
                    //userInputString = userInputString + " ";
            
                    String current = startState;
                    ArrayList<String> fin;
                    fin = acceptStates;
                    Stack<Character> stack = new Stack<>();
                    
                    try {
                        //for each element in the user string
                        for (int i = 0; i < userInputString.length(); i++) {
                            /* 
                            check if any of the transition objects in the arraylist have 
                            this element associated with the current state. If so, change
                            the current state to new toState and break to move onto the
                            next character in the input string 
                            */
                            for (Transition j : transitions) {
                            
                                /*If this transition's from state is equal to current
                                  and the input symbol is an empty string and the 
                                  stack push is $, then push $ onto stack and set 
                                  current to the to state */
                                if (j.getFromState().equals(current) && 
                                   j.getInputSymbol().equals(emptyString) && 
                                        j.getStackPush().equals(emptyStack)) {
                                    stack.push(j.getStackPush());
                                    current = j.toState;
                                }
                            
                                /*If this transition's from state is equal to current 
                                  and the stack pull is an empty string and the 
                                  input symbol is equal to the user input character, 
                                  then push the stack push onto stack and set current 
                                  to the to state. Break to get to next char in 
                                  the input string. */
                                else if (j.getFromState().equals(current) && 
                                        j.getStackPull().equals(emptyString) && 
                                        j.getInputSymbol().equals(userInputString.charAt(i))) {
                                    stack.push(j.getStackPush());
                                    current = j.toState;
                                    break;
                                }
                            
                                /*If this transition's from state equals current 
                                  and the stack push is an empty string and the 
                                  input symbol is equal to the user input character,
                                  then pop off of the stack and set current to the 
                                  to state. Break to get to next char in the input
                                  string. (If nothing can be popped from stack, 
                                  show the user that their string is not accepted.*/
                                else if (j.getFromState().equals(current) && 
                                        j.getStackPush().equals(emptyString) && 
                                        j.getInputSymbol().equals(userInputString.charAt(i))) {
                                        stack.pop();
                                    current = j.toState;
                                    
                                    /*This is used to make sure that if there is
                                      a transition that needs to be made after all
                                      the string input has been read, it can be
                                      carried out.*/
                                    if (i == userInputString.length() - 1) {
                                        for (Transition k : transitions) {
                                            if (k.getFromState().equals(current) &&
                                                    k.getStackPull().equals(emptyStack)) {
                                                stack.pop();
                                                current = k.toState;
                                            }
                                        }
                                    }
                                    break;
                                }
                            
                                /*If this transition's from state equals current
                                  and the stack pull is $, then pop the $ from the 
                                  stack and set current to the to state. Break to 
                                  exit loop. (Only happens if this transition 
                                  happens before the last input has been read)*/
                                else if (j.getFromState().equals(current) &&
                                        j.getStackPull().equals(emptyStack)) {
                                    stack.pop();
                                    current = j.toState;
                                    break;
                                }
                            }
                        }
                        if (fin.contains(current) && stack.empty() == true) {
                            //System.out.println("The DFA accepted your string");
                            JOptionPane.showMessageDialog(null, "The pda ACCEPTED"
                                + " your string");
                        }
                        else {
                            //System.out.println("The DFA did not accept your string");
                            JOptionPane.showMessageDialog(null, "The pda DID NOT ACCEPT"
                                + " your string");
                        }
                    }
                    catch (EmptyStackException e) {
                                    JOptionPane.showMessageDialog(null, "The pda DID NOT ACCEPT"
                            + " your string");
                                    //return;
                    }
                    userConfirm = JOptionPane.showInputDialog(null, "Would you like"
                            + " to input another string (Y/N)?");
                }
            }
        } 
    }
}
