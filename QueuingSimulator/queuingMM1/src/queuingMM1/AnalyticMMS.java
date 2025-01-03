package queuingMM1;
public class AnalyticMMS {
	private double lambda;
	private double mu;
	private int S;
	
	public AnalyticMMS(double lambda, double mu, int S) {
		this.lambda=lambda;
		this.mu=mu;
		this.S=S;
		
	}
	/*
	 * Le taux d’utilisation des serveurs (a)
	 */
	public double rho() {
		return lambda/mu;
	}
	/*
	 * Le taux d’utilisation des serveurs (a)
	 */
	public double a() {
		return lambda/(S*mu);
	}
	
	/*
	 * P0: La probabilité que le système est vide (Aucun client dans le système) 
	 */
	public double p0() {
		double sum=0;
		for (int k=0;k<=S-1;k++)
			sum+=Math.pow(rho(), k)/fact(k);
		sum+=Math.pow(rho(),S)/fact(S)*(1/(1-a()));
		return 1/sum;
			
	}


	/*
	 * La probabilité d'attente (probabilité que tous les serveurs c soient occupés) (Erlang C)
	 */
	public double erlangC() {
		//Code
		return p0()*Math.pow(rho(),S)/(fact(S-1)*(S-rho()));
	}


	
	/*
	 * Le nombre moyen de clients dans le système (N)
	 */
	public double N() {
		//Code
		return rho()*(1+erlangC()/(S-rho()));
	}


	
	/*
	 * Le nombre moyen de clients en attente (Nq)
	 */
	public double Nq() {
		//Code
		return  rho()*erlangC()/(S-rho());
	}

	
	/*
	 * Le nombre moyen de clients en service (Ns)
	 */
	public double Ns() {
		//Code
		return lambda/mu;
	}


	
	/*
	 * Le temps d'attente moyen dans le système (T))
	 */
	public double T() {
		//Code
		return N()/lambda;
	}

    /*
     * Le temps d'attente moyen dans la file d'attente (Tq)
     */
	public double Tq() {
		//Code
		return Nq()/lambda;
	}
	
    /*
     * Factoriel
     */
	//public int factoriel(int n) {
		//Code
	//}
	
	//....
	/*
	 * Factoriel
	 */
		
		public double fact(int a) {
			if (a==0)
				return 1;
			else {
				int p=1;
				for (int i=1;i<=a;i++)
					p*=i;
				return p;
			}
		}
		public void afficher() {
			System.out.printf("N=%.5f\n",N());
			System.out.printf("Nq=%.5f\n",Nq());
			System.out.printf("T=%.5f\n",T());
			System.out.printf("Tq=%.5f\n",Tq());
			System.out.printf("Ts=%.5f\n",1/this.mu);
		}


	
	
}
