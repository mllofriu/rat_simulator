import nslj.src.lang.NslDinDouble0;
import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutDouble1;
import nslj.src.lang.NslDoutDouble2;
import nslj.src.lang.NslModule;

/**
 * 
 */

/**
 * @author gtejera
 *
 */
public class APathIntegrationLayer extends NslModule {
	/**
	 * @param nslName
	 * @param nslParent
	 */
	public APathIntegrationLayer(String nslName, NslModule nslParent) {
		super(nslName, nslParent);
	}
	
	public NslDinDouble0 speed = new NslDinDouble0("speed", this);
	public NslDinDouble0 headDirection = new NslDinDouble0("headDirection", this);
	public NslDoutDouble1 outputPIL=null;
}