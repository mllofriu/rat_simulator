/* Clase que representa el remapeo del
   ancla del ambiente.
   Alejandra Barrera
   Versi�n: 1 (marzo, 2005)
*/


import java.lang.*;

public class Convolution
{
	private int dnrDim1;
	private int dnrDim2;
	// tamaño de la matriz de movimiento?
	private final int maskDim1= 3;
	private final int maskDim2= 3;
	private double convAux[][];
	private double mask0[][];
	private double mask1[][];
	private double mask2[][];
	private double mask3[][];
	private double mask4[][];
	private double mask5[][];
	private double mask6[][];
	private double mask7[][];
	private int istart;
	private int jstart;
	private int iend;
	private int jend;
	private final int kd1= maskDim1 / 2;
	private final int kd2= maskDim2 / 2;
	private int astart;
	private int bstart;
	private int aend;
	private int bend;
		
	// se le pasa como parametro desde la DLR DYNAMIC_REMAPPING_HEIGHT y DYNAMIC_REMAPPING_WIDTH 
	public Convolution (int d1, int d2)	{
		dnrDim1= d2*2;
		dnrDim2= d1*2;
		// al multiplicar por dos meneja las mismas dimensiones que la DLR
		convAux= new double[dnrDim1][dnrDim2];
		// mascaras de movimiento con solo un uno en la direccion preferida
		mask0= new double[maskDim1][maskDim2];
		mask1= new double[maskDim1][maskDim2];
		mask2= new double[maskDim1][maskDim2];
		mask3= new double[maskDim1][maskDim2];
		mask4= new double[maskDim1][maskDim2];
		mask5= new double[maskDim1][maskDim2];
		mask6= new double[maskDim1][maskDim2];
		mask7= new double[maskDim1][maskDim2];
		for (int i=0; i<maskDim1; i++)
			for (int j=0; j<maskDim2; j++) {
				mask0[i][j]= 0.0;
				mask1[i][j]= 0.0;
				mask2[i][j]= 0.0;
				mask3[i][j]= 0.0;
				mask4[i][j]= 0.0;
				mask5[i][j]= 0.0;
				mask6[i][j]= 0.0;
				mask7[i][j]= 0.0;
			}
		mask0[1][0] = 1.0D;
        mask1[2][0] = 1.0D;
        mask2[2][1] = 1.0D;
        mask3[2][2] = 1.0D;
        mask4[1][2] = 1.0D;
        mask5[0][2] = 1.0D;
        mask6[0][1] = 1.0D;
        mask7[0][0] = 1.0D;

        for(int i1 = 0; i1 < dnrDim1; i1++)
            for(int l1 = 0; l1 < dnrDim2; l1++)
                convAux[i1][l1] = 0.0D;

        istart = 0;
        jstart = 0;
        iend = dnrDim1;
        jend = dnrDim2;
        if(maskDim1 % 2 == 0)
        {
            astart = istart - kd1; 
            aend = istart + kd1;
        } else
        {
            astart = istart - kd1; // astart queda en -1, kd1 es 1
            aend = istart + (kd1 + 1); // aend queda en 2
        }
        if(maskDim2 % 2 == 0)
        {
            bstart = jstart - kd2;
            bend = jstart + kd2;
        } else
        {
            bstart = jstart - kd2; // -1
            bend = jstart + (kd2 + 1); // 2
        }
    }

	public void buildConvolution(int oldDir, int newDir, double PIps2dimAux[][])
	{
		// si la direccion coincide es porque se movio el robot, debe mover la DLR de forma acorde a la direccion del movimiento de newDir
		if(oldDir == newDir)
        {
            switch(newDir)
            {
            case 0:
                buildConv(PIps2dimAux, mask0);
                buildConv(PIps2dimAux, mask0);
                break;

            case 1: 
                buildConv(PIps2dimAux, mask1);
                buildConv(PIps2dimAux, mask1);
                break;

            case 2: 
                buildConv(PIps2dimAux, mask2);
                buildConv(PIps2dimAux, mask2);
                break;

            case 3: 
                buildConv(PIps2dimAux, mask3);
                buildConv(PIps2dimAux, mask3);
                break;

            case 4:
                buildConv(PIps2dimAux, mask4);
                buildConv(PIps2dimAux, mask4);
                break;

            case 5: 
                buildConv(PIps2dimAux, mask5);
                buildConv(PIps2dimAux, mask5);
                break;

            case 6:
                buildConv(PIps2dimAux, mask6);
                buildConv(PIps2dimAux, mask6);
                break;

            case 7: 
                buildConv(PIps2dimAux, mask7);
                buildConv(PIps2dimAux, mask7);
                break;
            }
        }
	}

	// recibe como parametro la matriz de DLR (pi) y la mascara de movimiento (mask) para actualizar acordemente pi dependiendo del movimiento
	public void buildConv(double pi[][], double mask[][]) {
		int index_a;
		int index_b;
        for(int k = istart; k < iend; k++)
        {
            for(int l2 = jstart; l2 < jend; l2++)
            {
                convAux[k][l2] = 0.0D;
                for(int k4 = astart; k4 < aend; k4++)
                {
                    for(int l4 = bstart; l4 < bend; l4++)
                    {
                        index_a = k4 + kd1;
                        index_b = l4 + kd2;
                        if(k - k4 >= 0 && l2 - l4 >= 0 && k - k4 < dnrDim1 && l2 - l4 < dnrDim2)
                            convAux[k][l2] += mask[index_a][index_b] * pi[k - k4][l2 - l4];
                    }
                }
            }
        }
        int i5 = 0;
        int j5 = 0;
        double d = 2D;
        
        // encuentro en convAux la primer celda con valor >=1 y devuelvo en i5 y j5 su posicion
        for(int l = 0; l < dnrDim1; l++)
        {
            for(int i3 = 0; i3 < dnrDim2; i3++)
                if(convAux[l][i3] >= 1.0D)
                {
                    i5 = l;
                    j5 = i3;
                    // break
                    i3 = dnrDim2;
                    l = dnrDim1;
                }
        }
        
        //System.err.println("Convolution::pos "+j5+", "+i5);
        
        for(int j1 = 0; j1 < dnrDim1; j1++)
        {
            for(int k3 = 0; k3 < dnrDim2; k3++)
                convAux[j1][k3] = Math.exp((double)(-1 * (j1 - i5) * (j1 - i5)) / (2D * (d * d)) + (double)(-1 * (k3 - j5) * (k3 - j5)) / (2D * (d * d)));
        }
        for(int i2 = 0; i2 < dnrDim1; i2++)
        {
            for(int j4 = 0; j4 < dnrDim2; j4++)
                pi[i2][j4] = convAux[i2][j4];
        }	
	}
}	
