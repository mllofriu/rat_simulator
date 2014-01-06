import javax.media.j3d.*;

public class CollisionBehavior extends Behavior {
    
    private WakeupCriterion conditions[] = new WakeupCriterion[2];
    private WakeupCondition condition;
    private CollisionReporter colrep;
    private int ID;
    
    public CollisionBehavior(CollisionReporter reporter, Node armingnode, int id) {
        colrep = reporter;
        conditions[0] = new WakeupOnCollisionEntry(armingnode, WakeupOnCollisionEntry.USE_GEOMETRY);
        conditions[1] = new WakeupOnCollisionExit(armingnode, WakeupOnCollisionExit.USE_GEOMETRY);
        condition = new WakeupOr( conditions );
        
        ID = id;
    }
    
    public void initialize() {
        wakeupOn(condition);
    }
    
    public void processStimulus(java.util.Enumeration enumeration) {
        
        while(enumeration.hasMoreElements()) {
            String typeofstimulus = enumeration.nextElement().toString();
            
            if( typeofstimulus.startsWith("javax.media.j3d.WakeupOnCollisionEntry") ) {
                colrep.addCollision(ID);
            }
            else {
                colrep.removeCollision(ID);
            }
        }
        
        wakeupOn(condition);
    }
    
}
