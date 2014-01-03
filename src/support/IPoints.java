package support;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Vector;

/**
 * 
 */

/**
 * @author gtejera
 *
 */
public interface IPoints {
	public abstract Vector<Point2D.Double> getDataPoints() throws IOException;
}
