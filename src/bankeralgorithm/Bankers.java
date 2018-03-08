package bankeralgorithm; // uses the package I named bakeralgorithm

import java.util.Scanner; // imports the scanner library

public class Bankers { // declares a class called Bankers

	private int need[][], allocate[][], max[][], avail[][], np, nr; // declares 4 two-dimensional integers arrays, and 2 integer variables

	private void input() { // declares a private method called input that returns nothing
		Scanner sc = new Scanner(System.in); // creates a scanner object called sc (for accessing the keyboard)

		System.out.print("Enter no. of processes and resources : "); // prints to console

		np = sc.nextInt(); // sets the value of np to the keyboard int input (used for the number of
							// processes)
		nr = sc.nextInt(); // sets the value of nr to the keyboard int input (used for the number of
							// resource types)
		need = new int[np][nr]; // creates a two-dimensional array with np number of rows and nr number of
								// columns
		max = new int[np][nr]; // creates another two-dimensional array with np number of rows and nr number of
								// columns
		allocate = new int[np][nr]; // creates a third two-dimensional array with np number of rows and nr number of
									// columns
		avail = new int[1][nr]; // creates a two dimensional array with 1 row and nr number of columns

		System.out.println("Enter allocation matrix -->"); // prints to console

		for (int i = 0; i < np; i++) {
			for (int j = 0; j < nr; j++) {
				allocate[i][j] = sc.nextInt(); // loops through the allocate matrix reading from keyboard setting the rows and
												// columns
			}
		}

		System.out.println("Enter max matrix -->"); // prints to console

		for (int i = 0; i < np; i++) {
			for (int j = 0; j < nr; j++) {
				max[i][j] = sc.nextInt(); // loops through the max matrix reading from keyboard setting the rows and
											// columns
			}
		}

		System.out.println("Enter available matrix -->"); // prints to console

		for (int j = 0; j < nr; j++) {
			avail[0][j] = sc.nextInt(); // loops through the available matrix reading from keyboard setting the first
										// row and columns
		}

		sc.close(); // closes the scanner object

	}

	private int[][] calc_need() { // declares a private method called calc_need that returns an integer matrix
		for (int i = 0; i < np; i++)
			for (int j = 0; j < nr; j++)
				need[i][j] = max[i][j] - allocate[i][j]; // loops through the rows and columns setting the need matrix. The need matrix
															// is the max matrix minus the allocated matrix

		return need; // returns that newly created need matrix
	}

	private boolean check(int i) { // declares a private method called check which takes in an integer and returns
									// a boolean

		for (int j = 0; j < nr; j++) {

			if (avail[0][j] < need[i][j]) { // loops through each resource type and checks if what is needed is available

				return false; // returns false if the need is greater than what's available
			}
		}

		return true; // otherwise returns true
	}

	public void isSafe() { // declares a public method called isSafe and returns nothing

		input(); // calls the input method which asks the user for all the matrix values

		calc_need(); // calls the calc_need method which returns need matrix

		boolean done[] = new boolean[np]; // declares a boolean array of size np

		int j = 0; // declares an int j and initializes it to 0

		while (j < np) { // the program loops until all process are done

			boolean allocated = false; // initializes a boolean called allocated to false

			for (int i = 0; i < np; i++) { // loops through each process

				if (!done[i] && check(i)) { // checks if the process is done and if resources can be allocated to that
											// process

					for (int k = 0; k < nr; k++) { // loops through number of resources
						avail[0][k] = avail[0][k] - need[i][k] + max[i][k]; // adds the resources that were being used to the available
					}

					System.out.println("Allocated process : " + i); // prints to console if the process gets it's max resources

					allocated = done[i] = true; // sets that process as done in the done array

					j++; // increments j by 1
				}
			}

			if (!allocated) {
				break; // breaks the while loop if the program canot allocate any resources (deadlock
						// happened)
			}
		}

		if (j == np) { // if all processes are allocated
			System.out.println("\nSafely allocated"); // then print to console that no deadlock can happen

		} else { // otherwise
			System.out.println("All processes cannot be allocated safely"); // print that the deadlock would occur

		}
	}

	public static void main(String[] args) { // program starts executing here
		new Bankers().isSafe(); // create and instance of the Bankers class and run the isSafe method
	}
}