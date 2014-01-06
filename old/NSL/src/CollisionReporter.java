import java.util.Vector;
public class CollisionReporter {
    
    private Vector<Integer> reportedCollisions;
    private boolean collided = false;
    
    public CollisionReporter() {
        reportedCollisions = new Vector<Integer>();
    }
    
    public void addCollision(int ObjectID) {
        reportedCollisions.addElement(new Integer(ObjectID));
            collided = true;
    }
    
    public void removeCollision(int ObjectID) {
        reportedCollisions.removeElement(new Integer(ObjectID));
        if(reportedCollisions.size() == 0)
            collided = false;
    }
    
    public boolean checkForCollidedObject(int ObjectID) {
        return reportedCollisions.contains(new Integer(ObjectID));
    }
    
    public boolean isCollided() {
        return collided;
    }
    
    public int collisionsReported() {
        return reportedCollisions.size();
    }
}
