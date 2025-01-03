package queuingMM1;
	import java.util.LinkedList;
	import java.util.Random;

	public class SimulatorMM1 {
	    
	/*
	 * Attributs
	 */
	    LinkedList<Double> queue;       // Storing the queue of arrival times
	    double clock;			    	// current time of simulation		
	    double endTime;					// the end time of arrivals
	    double nextArrival;				// the time of the next arrival to the system
	    double nextDeparture;			// the time of the next departure from the system
	    double lambda;					// the rate of arrivals to the system
	    double mu;						// the mean service time
	    double T;						// the mean of total time a customer spends in the queueing system
	    double Tq;						// the mean of time a customer spends in the queue before service begins
	    double Ts;						// the mean of service time (calculated)
	    double N;						// the mean number of customers in the system
	    double Nq;						// the mean queue length
	    double su;				    	// Server utilization (rho)
	    int nbArr;						// the number of arrivals (at the time of "clock")
	    int nbDep;						// the number of serviced costumers (Departure)(at the time of "clock")
	    
	    Random random;					//The method nextDouble() of class Random return pseudorandom double value, uniformly distributed between 0.0 and 1.0
	    
	/*
	 * Constructor
	 */
	    public SimulatorMM1 (double lambda, double mu, double endTime) {
	        this.lambda = lambda;
	        this.mu = mu;
	        this.endTime = endTime;
	        random = new Random();

	        clock = 0;
	        queue = new LinkedList<Double>();
	        nextArrival = exponential(lambda);
	        nextDeparture = Double.POSITIVE_INFINITY;  // no departures initially scheduled
	        
	        T = 0; Tq = 0; Ts = 0;
	        Nq= 0; N = 0;
	        nbArr = 0; nbDep = 0; su=0;
	        
	    }
	    
	    /*
	     * Generate exponential durations
	     */

	    public double exponential(double lambda) {
	        double x = Math.log(this.random.nextDouble())/(-lambda);
	        return x;
	    }
	    

	///////////////////////////Simulateurs////////////////////////////////////////////////////////////////////////////////////////////s///////////////////
	    public void simulate() {
	        double sumQ = 0;                 // sum (integral)of queue lengths 
	        //double sumQt = 0;              // last update time of queue length sum 
	        double nbQ = 0;                  // queue length (at the time of clock)
	        
	        double sumN = 0;                 // sum (integral)of the numbers of customers in the system
	        double nbS = 0;                  // number of costumers in the system (at the time of clock)


	        double busy = 0;                 // 1:server busy 0: server idle (not busy)
	        double susum = 0;                // Server utilization sum
	        //double susumt = 0;             // Last update time of server utilization sum
	        
	        double upt = 0;                  // last update time

	        double sumT=0;					// the sum of total times a customers spends in the queueing system
	        double sumTq=0;					// the sum of times a customers spends in the queue before service begins
	        double sumTs=0;					// the sum of service time (calculated)
	        
	        double interArrT, serviceT;

	        //while ((!queue.isEmpty())||(nextArrival<=endTime))
	        while ((!queue.isEmpty())||(nextArrival<=endTime)||(nextDeparture!=Double.POSITIVE_INFINITY))

	        // while (nextArrival<endTime)
	    	{
	    		if  ((nextArrival <= nextDeparture) &&(nextArrival<endTime))
	    		{ // a customer has arrived to the system
	    			clock = nextArrival;
	            	//////////////////////////////////////////////////////
	            	sumQ =sumQ+nbQ*(clock-upt);
	     	    	sumN = sumN+nbS*(clock-upt);
	     	    	//////////////////////////////////////////////////////
	     	    	susum+=busy*(clock-upt);
	     	    	upt=clock;
	            	//////////////////////////////////////////////////////
	    			nbArr++;
	     	    	nbS++;
	     	    	interArrT=exponential(lambda);
	     	    	nextArrival = nextArrival + interArrT; // Schedul the next arrival
	     	    	if (busy==0) { // server not busy, schedule a departure
	                /*--------------------------------------*/
	     	    		serviceT=exponential(mu);
	                	nextDeparture = clock + serviceT; // schedule a departure
	                    //schedule.addFirst(new Event(nextDeparture,TypeEvent.DEPARTURE));
	                    sumT = sumT + (nextDeparture - clock);
	                    //Tq = Tq + (clock - clock);
	                    sumTs = sumTs + (nextDeparture - clock);
	                 /*--------------------------------------*/
	                  busy=1;
	                } 
	                else { // server busy, add arrival to the queue
	                	queue.add(clock);
	                	nbQ++;
	                }
	            }
	            else {	// Departure of a customer occurre (after being served)
	            	clock = nextDeparture;
	            	nbDep++;
	            	//////////////////////////////////////////////////////
	            	sumQ =sumQ+nbQ*(clock-upt);
	     	    	sumN = sumN+nbS*(clock-upt);
	     	    	//////////////////////////////////////////////////////
	     	    	susum+=busy*(clock-upt);
	     	    	upt=clock;
	            	//////////////////////////////////////////////////////
	            	//schedule.remove(); // Delete scheduled Departure
	            	nbS--;
	            	if (nbQ>0) //if (nbS>0)
	            	{ // There are waiting customers, Schedule the departure of the first waiting customers
	            	//if (!schedule.isEmpty()) { 
	            		double first = queue.getFirst();
	            		queue.remove();		
	             		/*--------------------------------------*/
	                	nextDeparture = clock + exponential(mu);
	                    //schedule.addFirst(new Event(nextDeparture,TypeEvent.DEPARTURE));
	                    //nbS++;
	                    sumT += (nextDeparture - first);
	                    sumTq += (clock - first);
	                    sumTs += (nextDeparture - clock);
	                    /*--------------------------------------*/
	                    nbQ--;

	            	} else{ 
	            		nextDeparture = Double.POSITIVE_INFINITY; // No service scheduled (No customers waiting, no customers in service)
	            		busy=0;
	            	}
	            }   

	    	}
	    	N=sumN/clock;
	    	Nq=sumQ/clock;
	    	su=susum/clock;
	    	/**/
	    	
	    	T=sumT/nbDep;
	    	Tq=sumTq/nbArr;
	    	Ts=sumTs/nbDep;
	    	
	 }
	    
	    public void afficher() {

	    	//System.out.println("Modele de simulation");
	    	//System.out.println("requests: " + nbA);
	    	System.out.printf("N=%.5f\n",N);
	    	System.out.printf("Nq=%.5f\n",Nq);
	    	System.out.printf("T=%.5f\n",T);
	    	System.out.printf("Tq=%.5f\n",Tq);
	    	System.out.printf("Ts=%.5f\n",Ts);
	    		    	////////////////////////////////////////////
	    	//System.out.println("cptEvents-nbA-nbS=" + (cptEvents-nbA-nbS));
	    	//System.out.println("us = " + (clock-totalInact)/clock);
	    	//System.out.println("us = " + (Ts)/clock);
	    	//System.out.println("N2 = " + N/cptEvents);
	    	//System.out.println("Nq2 = " + Nq/cptEvents);

	    }
	    
	}

