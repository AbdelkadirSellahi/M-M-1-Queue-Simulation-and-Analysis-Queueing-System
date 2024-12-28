# M/M/1 Queue Simulation and Analysis

This project provides a simulation and analytical comparison for M/M/1 queuing systems, implemented in Java. The approach demonstrates the close alignment between simulation results and analytical models, validating the implementation.

## Project Overview

### Components
1. **AnalyticMMS**: Implements the analytical model for M/M/S systems, with M/M/1 as a special case (S=1).
2. **SimulatorMM1**: Simulates an M/M/1 queuing system using event-based logic.
3. **Simulation**: Compares analytical results with simulation results for given parameters (e.g., arrival rate `lambda`, service rate `mu`).

### **Class 1: `AnalyticMMS`**
This class computes analytical metrics for an M/M/S system.

#### **Attributes**
- `lambda`: Arrival rate (\(\lambda\)).
- `mu`: Service rate (\(\mu\)).
- `S`: Number of servers.

#### **Methods**
1. **`rho()`**  
   Computes the total system utilization (\(\rho = \lambda / \mu\)).  
   - For \( S = 1 \), \(\rho\) represents how busy the server is.

2. **`a()`**  
   Computes the per-server utilization (\(a = \lambda / (S \cdot \mu)\)).  
   Ensures the system's stability (\(a < 1\)).

3. **`p0()`**  
   Calculates \(P_0\), the probability that the system is empty (no clients in the system).  
   - Formula:  
     \[
     P_0 = \frac{1}{\sum_{k=0}^{S-1} \frac{\rho^k}{k!} + \frac{\rho^S}{S!} \cdot \frac{1}{1 - a}}
     \]

4. **`erlangC()`**  
   Computes the **Erlang C formula**, the probability that all servers are busy.  
   - Formula:  
     \[
     C = P_0 \cdot \frac{\rho^S}{S! \cdot (S - \rho)}
     \]

5. **Queue Metrics**:
   - **`N()`**: Average number of clients in the system.  
     \[
     N = \rho \cdot \left(1 + \frac{C}{S - \rho}\right)
     \]
   - **`Nq()`**: Average number of clients in the queue.  
     \[
     N_q = \rho \cdot \frac{C}{S - \rho}
     \]
   - **`Ns()`**: Average number of clients being served.  
     \[
     N_s = \frac{\lambda}{\mu}
     \]
   - **`T()`**: Average time spent in the system.  
     \[
     T = \frac{N}{\lambda}
     \]
   - **`Tq()`**: Average time spent in the queue.  
     \[
     T_q = \frac{N_q}{\lambda}
     \]

6. **Factorial (`fact()`)**  
   Helper function to compute factorials.

7. **`afficher()`**  
   Prints analytical results for comparison.

---

### **Class 2: `SimulatorMM1`**
This class implements a discrete-event simulation for the M/M/1 queue.

#### **Attributes**
- **Queue-Related:**  
  - `queue`: Tracks arrival times of customers waiting for service.
  - `clock`: Current simulation time.
  - `nextArrival`, `nextDeparture`: Timestamps for the next arrival and departure.
- **Parameters:**  
  - `lambda`, `mu`: Arrival and service rates.
  - `endTime`: Time limit for the simulation.
- **Metrics:**  
  - `T`, `Tq`, `Ts`: Average total time, queue time, and service time.
  - `N`, `Nq`: Average number of customers in the system and in the queue.
  - `su`: Server utilization.
  - `nbArr`, `nbDep`: Number of arrivals and departures.

#### **Methods**
1. **`exponential(lambda)`**  
   Generates random exponential inter-arrival or service times using:  
   \[
   t = -\frac{\ln(U)}{\lambda}
   \]  
   where \(U\) is a uniform random variable.

2. **`simulate()`**  
   Performs the simulation:
   - **Initialization:** Start with no arrivals or departures scheduled.
   - **Events:**  
     - **Arrival:**  
       - Increment arrivals and system size.
       - If the server is idle, schedule a departure; otherwise, add the customer to the queue.
     - **Departure:**  
       - Decrement system size and departures.
       - If the queue is non-empty, schedule a departure for the next customer; otherwise, set the server idle.
   - **Metrics Calculation:**  
     Compute averages and totals (e.g., \(T\), \(N\), \(Tq\), etc.).

3. **`afficher()`**  
   Prints simulation results.

---

### **Class 3: `Simulation`**
This class runs the comparison between the analytical model and the simulation.

#### **Steps:**
1. Define parameters \( \lambda \) and \( \mu \).
2. Run the simulation for a long period (e.g., 1,000,000 time units).
3. Calculate analytical results using `AnalyticMMS`.
4. Compare metrics:
   - \(N\), \(Nq\), \(T\), \(Tq\), \(T_s\).
   - Print normalized differences between analytical and simulated results.

#### **Purpose**
Demonstrates:
- **Accuracy of the simulation**: Metrics closely match analytical results.
- **Applicability of the analytical model**: Assumes exponential inter-arrival and service times, which may not hold in real-world systems.

### Features
- **AnalyticMMS**:
  - Computes system utilization (`rho` and `a`).
  - Determines the probability that the system is empty (`p0`).
  - Calculates the probability of waiting (`Erlang C`).
  - Provides metrics like average number of customers in the system (`N`), in the queue (`Nq`), and in service (`Ns`), as well as average waiting times (`T` and `Tq`).

- **SimulatorMM1**:
  - Simulates arrivals and departures using an event-driven approach.
  - Tracks metrics such as server utilization, queue length, and system performance over time.

- **Simulation**:
  - Executes both the analytical and simulation models and compares their results for validation.
  - Computes normalized deviations for various metrics to ensure accuracy.

---

### **Key Observations**
1. **Validation**  
   The simulation matches the analytical results, validating its correctness.
2. **Flexibility**  
   By modifying \( S \) in `AnalyticMMS`, it’s possible to model an M/M/S queue.
3. **Real-World Systems**  
   Simulation serves as a first step towards more realistic modeling. For example, inter-arrival and service times can follow empirical or theoretical distributions (e.g., Thinning method).

---
## Key Concepts
- **M/M/1 Queue**:
  - Single-server queuing system.
  - Poisson process for arrivals.
  - Exponential service times.
  - First-Come, First-Served (FCFS) discipline.

- **Simulation vs Analytical Models**:
  - Analytical models rely on strict assumptions (e.g., exponential distributions).
  - Simulation provides flexibility and realism, useful when assumptions deviate from real-world scenarios.

## Usage

### Running the Simulation
1. Clone the repository and open the project in your preferred Java IDE.
2. Set the values of `lambda` and `mu` in the `Simulation` class.
3. Run the `Simulation` class to compare analytical and simulation results.

### Output
The program outputs:
1. Metrics from both analytical and simulation models.
2. Normalized deviations between the models to validate simulation accuracy.

### Example Output
```
Lambd = 2.0
Mu = 3.0
=================Modele de simulation M/M/1=================
N=0.66667
Nq=0.22222
T=0.33333
Tq=0.11111
Ts=0.33333
============================================================
=================Modele analytique M/M/1====================
N=0.66667
Nq=0.22222
T=0.33333
Tq=0.11111
Ts=0.33333
============================================================
==Ecarts normalises entre les mesures du modele analytique et le modele de simulation==
Ecart N = 0.00000
Ecart Nq = 0.00000
Ecart T = 0.00000
Ecart Tq = 0.00000
Ecart Ts = 0.00000
============================================================
```

## Extending the Project
- Modify `AnalyticMMS` to implement M/M/S for multiple servers (S > 1).
- Replace exponential arrival and service times with empirical or other theoretical distributions for realistic modeling.

## Requirements
- Java JDK 8 or higher.
- Any IDE supporting Java (e.g., IntelliJ, Eclipse, NetBeans).

## Acknowledgments
- This implementation serves as a foundational step toward simulating real-world queuing systems, which often deviate from classical assumptions.

## 💬 **Contributing**

We welcome contributions! If you have ideas or improvements:
1. Fork the repository.
2. Create a new branch: `git checkout -b my-feature-branch`.
3. Submit a pull request.


## 💬 **Contact**

Feel free to open an issue or reach out for collaboration!  

**Author**: *Abdelkadir Sellahi*

**Email**: *abdelkadirsellahi@gmail.com* 

**GitHub**: [Abdelkadir Sellahi](https://github.com/AbdelkadirSellahi)
