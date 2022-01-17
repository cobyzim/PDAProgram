/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs475assign2;

/**
 *
 * Transition class with constructor for transition objects, setters and getters
 * for the object data fields, and a toString method for debugging.
 * 
 * @author Coby Zimmerman
 */
public class Transition {
    public String fromState;
    public Character inputSymbol;
    public Character stackPull;
    public Character stackPush;
    public String toState;
    
    public Transition(String fromState, Character inputSymbol, 
            Character stackPull, Character stackPush, String toState) {
        this.fromState= fromState;
        this.inputSymbol = inputSymbol;
        this.stackPull = stackPull;
        this.stackPush = stackPush;
        this.toState = toState;
    }

    @Override
    public String toString() {
        return "Transition{" + "fromState=" + fromState + ", inputSymbol=" + inputSymbol + ", stackPull=" + stackPull + ", stackPush=" + stackPush + ", toState=" + toState + '}';
    }

    public String getFromState() {
        return fromState;
    }

    public Character getInputSymbol() {
        return inputSymbol;
    }

    public Character getStackPull() {
        return stackPull;
    }

    public Character getStackPush() {
        return stackPush;
    }

    public String getToState() {
        return toState;
    }

    public void setFromState(String fromState) {
        this.fromState = fromState;
    }

    public void setInputSymbol(Character inputSymbol) {
        this.inputSymbol = inputSymbol;
    }

    public void setStackPull(Character stackPull) {
        this.stackPull = stackPull;
    }

    public void setStackPush(Character stackPush) {
        this.stackPush = stackPush;
    }

    public void setToState(String toState) {
        this.toState = toState;
    }

    
    
    
}
