package queuingMM1;

public class Simulation {
	
	public static void main (String[] args) {
    	double lambda=2;
    	double mu=3;
    	SimulatorMM1 sm=new SimulatorMM1(lambda,mu,1000000);
    	sm.simulate();
    	System.out.println("Lambd = " + lambda);
    	System.out.println("Mu = " + mu);

    	System.out.println("=================Modele de simulation M/M/1=================");
    	sm.afficher();
    	System.out.println("============================================================");
    	AnalyticMMS am=new AnalyticMMS(lambda,mu,1);		
		System.out.println("=================Modele analytique M/M/1====================");
		am.afficher();
    	System.out.println("============================================================");

		System.out.println("==Ecarts normalises entre les mesures du modele analytique et le modele de simulation==");
    	System.out.printf("Ecart N = %.5f\n",(sm.N-am.N())/sm.N);
    	System.out.printf("Ecart Nq = %.5f\n", (sm.Nq-am.Nq())/sm.Nq);
    	System.out.printf("Ecart T = %.5f\n", (sm.T-am.T())/sm.T);
    	System.out.printf("Ecart Tq = %.5f\n",(sm.Tq-am.Tq())/sm.Tq);
    	System.out.printf("Ecart Ts = %.5f\n",(sm.Ts-1/mu)/sm.Ts);
    	System.out.println("============================================================");

  
    }


}
