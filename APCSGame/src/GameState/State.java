
package GameState;


public class State {
    
    private int frameDelay;
    private final String[] possibleStates = {"Walk", "Attack", "Jump", "Idle"};
    private int currentState;
    
    public State(int startingState){
        currentState = startingState;
        updateFrames();
    }
    
    private void updateFrames(){
        if (currentState == 0)
    }
    
}
