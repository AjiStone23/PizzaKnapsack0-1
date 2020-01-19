/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizza01knapsack;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ajist
 */
public class Pizza01Knapsack {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		
		int targetMaxSlices = 0;
		int numberOfPizzas = 0;
		int slicesOfThisPizza;
		List<Integer> pizzaList = new ArrayList<>();
		List<Integer> pizzaIndexes = new ArrayList<>();
		List<Integer> greedIndexes = new ArrayList<>();

		
		try {

			File f = new File("C:\\Users\\ajist\\Desktop\\d_quite_big.in");
			Scanner sc = new Scanner(f);
			targetMaxSlices = sc.nextInt();
			numberOfPizzas = sc.nextInt();

			while (sc.hasNext()) {

				while (sc.hasNext()) {
					pizzaList.add(sc.nextInt());
				}
			}
		} catch (FileNotFoundException e) {

			System.out.println("File Not Found!");
		}

	       //calculate if problem is too big for dynamic algorithm
		BigInteger max = BigInteger.valueOf(targetMaxSlices).multiply(BigInteger.valueOf(numberOfPizzas));

		   //We start from the last pizza in the ascending pizza list (the biggest) if we enter while loop
		int k = pizzaList.size() - 1;

		   //use greedy algorithm if number of arrays to be created in next step is too big(max too big)
		while (max.compareTo(BigInteger.valueOf(50000000)) > 0) {

			slicesOfThisPizza = pizzaList.get(k);

			// if we can take the pizza we take it
			if (targetMaxSlices >= slicesOfThisPizza) {

				greedIndexes.add(0, k);

				targetMaxSlices -= slicesOfThisPizza;
				max = BigInteger.valueOf(targetMaxSlices).multiply(BigInteger.valueOf(numberOfPizzas));
				numberOfPizzas--;

			}
			//go to the next biggest pizza
			k--;
			// n steps until max is small enough for dynamic algorithm
		}

		
		//create matrix for dynamic algorithm		
		
		int[][] matrix = new int[numberOfPizzas][targetMaxSlices+1];

		// we fill in the first row of the matrix
		//for the first pizza(smallest) we make decisions for every subproblem, targetMaxSlices = 0......targetMaxSlices of this problem
		slicesOfThisPizza = pizzaList.get(0);
	
		for (int i = 0; i <= targetMaxSlices; i++) {

			// if we can take it we take it(better some pizza than no pizza at all! ;p)
			if (i >= slicesOfThisPizza) {

				//fill the matrix with the value of pizza slice
				matrix[0][i] = slicesOfThisPizza;
				
			}

		}
         //make decisions for every subproplem   
		 for (int i = 1; i < numberOfPizzas; i++) {

			slicesOfThisPizza = pizzaList.get(i);
			for (int j = 0; j <= targetMaxSlices; j++) {
				
                //if we can take the pizza(with the hypothesis that we havent got any pizza for this subproblem)
				//compare which decision yields the best, 
				//"this pizza slices"+"pizza value of subproblem with targetMaxSlices=this problem targetMaxSlices- this pizza slices"  vs 
				//" slices of pizza until this decision"
				
				if (j >= slicesOfThisPizza) {
					matrix[i][j] = Math.max(matrix[i - 1][j - slicesOfThisPizza] + slicesOfThisPizza, matrix[i - 1][j]);
				} else {
					matrix[i][j] = matrix[i - 1][j];
				}
			}

		}
		
		// backtrack to get the indexes of the choosen pizzas

		int j = targetMaxSlices ;

		for (int i = numberOfPizzas - 1; i > 0; i--) {

			while (j >= 0) {

				slicesOfThisPizza = pizzaList.get(i);

				if (slicesOfThisPizza <= j && slicesOfThisPizza + matrix[i - 1][j - slicesOfThisPizza] > matrix[i - 1][j]) {
					pizzaIndexes.add(0, i);

					j = j - slicesOfThisPizza;

					break;
				}

				break;

			}

		}
		
		//get indexes of decisions of first row
		if (pizzaList.get(0) <= j) {
			pizzaIndexes.add(0, 0);
		}
		
       
		//add indexes if we used greed algorithm
			pizzaIndexes.addAll(greedIndexes);

		System.out.println("**************************************INDEXES*************************************************");
		for (int i = 0; i < pizzaIndexes.size(); i++) {
			if (i % 15 == 0) {
				System.out.println("");
			}
			System.out.print(pizzaIndexes.get(i) + ", ");
		}

		System.out.println("\n\n*************************************SUM*************************************************");

		int sum = 0;
		for (int i = 0; i < pizzaIndexes.size(); i++) {

			if (i % 15 == 0) {
				System.out.println("");
			}

			slicesOfThisPizza = pizzaList.get(pizzaIndexes.get(i));
			System.out.print(slicesOfThisPizza + " + ");

			sum += slicesOfThisPizza;

		}
		System.out.println("");
		System.out.println("= " + sum);

	}

}
