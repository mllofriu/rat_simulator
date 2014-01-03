import support.Configuration;

/* Clase que representa a un esquema perceptual gen�rico.
   Alejandra Barrera
   Versi�n: 1
   Fecha: 15 de febrero de 2005
*/

public class PerceptualSchema
{
    public double act[][];
    public double max;
    public double min;
    public static final int DEFAULT_SIZE = Configuration.getInt("PerceptualSchema.DEFAULT_SIZE");

	public static final int POS_GAUSS_N180=4; // posicion de la gaussiana en el array para el affordance -180
	public static final int POS_GAUSS_N135=13;
	public static final int POS_GAUSS_N90=22;
	public static final int POS_GAUSS_N45=31;
	public static final int POS_GAUSS_0=40;
	public static final int POS_GAUSS_P45=49;
	public static final int POS_GAUSS_P90=58;
	public static final int POS_GAUSS_P135=67;
	public static final int POS_GAUSS_P180=76; // posicion de la gaussiana en el array para el affordance 180

	public static final int POS_GAUSS[]={POS_GAUSS_N180,POS_GAUSS_N135,POS_GAUSS_N90,POS_GAUSS_N45,POS_GAUSS_0,POS_GAUSS_P45,POS_GAUSS_P90,POS_GAUSS_P135,POS_GAUSS_P180};
	public static final int POS_GIROS[]={-180,-135,-90,-45,0,45,90,135,180};

	public PerceptualSchema() {
        this(DEFAULT_SIZE,1);
    }
	
    public PerceptualSchema(int i, int j)
    {
        act = new double[i][j];
        max = 0.0D;
        min = 0.0D;
    }
    
    public void zero() {
    	for (int pos=0;pos<act.length;pos++)
    		act[pos][0]=0;
    }

    public void set(double []lSchema) {
    	for (int pos=0;pos<act.length;pos++)
    		act[pos][0]=lSchema[pos];
    }

    public void addLineal(double altura, int centro, double delta) {
    	for (int pos=0;pos<act.length;pos++)
    		act[pos][0]=act[pos][0]+altura* Math.exp(-1 * Math.pow(pos-centro,2)/(2.0 * Math.pow(delta,2)));

    }
    
    public static void createLineal(double []lSchema, double altura, int centro, double delta){
    	for (int pos=0;pos<lSchema.length;pos++)
    		lSchema[pos]=altura* Math.exp(-1 * Math.pow(pos-centro,2)/(2.0 * Math.pow(delta,2)));

    }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String result = "[";
    	for (int pos=0;pos<act.length;pos++){
    		result=result+act[pos][0]+((pos==(act.length-1))?"]":", ");
    	}
    	return result;
	}
    
    

}