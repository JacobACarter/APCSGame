
package GameState;


public class State {
    
    private static volatile State instance;

    public enum CharacterState {
        RUN,IDLE,JUMP,HIT,HURT,CLIMB;
    }
    private CharacterState state = CharacterState.IDLE;

    public CharacterState getCharacterState() {
        return state;
    }

    public void setState(CharacterState state) {
        synchronized (this) {
            this.state = state;
        }
    }

    public static State getInstance() {
        if (instance == null) {
            synchronized (State.class) {
                if (instance == null)
                    instance = new State();
            }
        }
        return instance;
    }
    
    
    
}
