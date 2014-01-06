/* Clase que representa la arquitectura Actor-Critic
   involucrada en el aprendizaje por reforzamiento.
   Alejandra Barrera
   Versi�n 1 (Abril, 2005)
 */

import java.lang.*;
import java.util.Random;
import java.io.*;
import java.util.*;

public class ActorCriticWG {
	private double p;
	private double oldp;
	private double rhat;
	private double v[];
	private double xbar[];
	private int IN_DIM;
	private final double WG_GAMMA_R = 0.84999999999999998D;
	private final double WG_BETA_R = 0.041000000000000002D;

	public ActorCriticWG(int i) {
		p = 0.0D;
		oldp = 0.0D;
		rhat = 0.0D;
		IN_DIM = i;
		v = new double[IN_DIM];
		xbar = new double[IN_DIM];
		Arrays.fill(v, 0);
		Arrays.fill(xbar, 0);
	}

	public void initTraces() {
		Arrays.fill(xbar, 0);
		oldp = 0.0D;
		p = 0.0D;
	}

	public void initReinforce(int i, double ad[], double dFood) {
		double d = 0.0D;
		double ad1[] = new double[IN_DIM];
		for (int k = 0; k < IN_DIM; k++) {
			if (ad1[k] < 1.0D)
				ad1[k] = 0.0D;
			else
				ad1[k] = ad[k];
		}
		if (dFood != -1)
			for (int l = 0; l < IN_DIM; l++)
				xbar[l] += 0.29999999999999996D * ad1[l];
		else
			for (int l = 0; l < IN_DIM; l++) {
				xbar[l] += 0.19999999999999996D * ad1[l];
			}
		oldp = p;
		for (int i1 = 0; i1 < IN_DIM; i1++)
			d += v[i1] * ad1[i1];
		p = d;
	}

	public double reinforce(double rValue, double ad[], double dFood) {
		if (rValue < 0.0D)
			p = 0.0D;
		rhat = rValue + (WG_GAMMA_R * p - oldp);
		for (int j1 = 0; j1 < IN_DIM; j1++) {
			v[j1]+= WG_BETA_R * rhat * xbar[j1];
			if (v[j1] < 0.0D)
				v[j1] = 0.0D;
			xbar[j1] *= 0.80000000000000004D;
		}
		return rhat;
	}
}