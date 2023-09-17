package escape.impl.gamemanager;

import escape.required.GameObserver;

import java.util.ArrayList;
import java.util.List;

// This class is an implementation of the GameObserver interface
public class GameObserverImpl implements GameObserver {

    //Stores any messages the observers get for testing purposes
    private List<String> inbox;
    private Throwable cause;

    /**
     * Constructor for Game Observer
     */
    public GameObserverImpl(){
        this.inbox = new ArrayList<>();
    }

    /**
     * Notify the observer with a message
     * @param message the message sent to observer
     */
    @Override
    public void notify(String message){
        inbox.add(message);
        System.out.println(message);
    }

    /**
     * Notify the observer with a message and exception
     * @param message the message sent to observer
     * @param cause usually the exception that caused the state indicated
     * 	by the message
     */
    @Override
    public void notify(String message, Throwable cause){
        inbox.add(message);
        this.cause = cause;
        System.out.println(message + cause);
    }

    public List<String> getInbox(){
        return this.inbox;
    }



}
