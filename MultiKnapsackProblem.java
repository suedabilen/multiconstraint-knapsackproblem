//Sueda Bilen 150117044
//Zehra Kuru 150119841
//Özge Saltan 150517059

//ALGORITHMS PROJECT2 / 0-1 MULTI-CONSTRAINT KNAPSACK PROBLEM

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class MultiKnapsackProblem {

    private static int knapsackNum;
    private static int itemNum;
    private static int counter;
    private static int totalValue;
    private static boolean whileBreaker = false;
    private static Set<Integer> commons = new HashSet<>();
    private static ArrayList<Integer> values = new ArrayList<Integer>();
    private static ArrayList<Integer> capacityList = new ArrayList<Integer>();
    private static ArrayList<Integer> finalCommons = new ArrayList<Integer>();
    private static ArrayList<Integer> commonElements = new ArrayList<Integer>();
    private static HashMap<Integer, ArrayList<Integer>> weights = new HashMap<Integer, ArrayList<Integer>>();

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.print("Enter the file name (without extension): ");
        String fileName = input.nextLine();
        File sourcefile = new File(fileName + ".txt");
        while (!sourcefile.exists()) {
            System.out.println("Source file does not exist!");
            System.out.print("Enter the file name again: ");
            fileName = input.nextLine();
            sourcefile = new File(fileName + ".txt");
        }

        // Create a Scanner for the file
        Scanner input2;

        try {
            //taking values to arraylists,variables from input file
            input2 = new Scanner(sourcefile);
            knapsackNum = Integer.parseInt(input2.next());
            itemNum = Integer.parseInt(input2.next());

            int temp;
            for (int i = 0; i < itemNum; i++) {
                temp = Integer.parseInt(input2.next());
                values.add(temp);

            }

            for (int i = 0; i < knapsackNum; i++) {
                temp = Integer.parseInt(input2.next());
                capacityList.add(temp);

            }

            for (int i = 0; i < knapsackNum; i++) {
                ArrayList<Integer> temporary = new ArrayList<Integer>();
                for (int j = 0; j < itemNum; j++) {
                    temp = Integer.parseInt(input2.next());
                    temporary.add(temp);

                }
                weights.put(i, temporary);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        //If capacity of knapsacks is not 0 and iterations are completed.
        while(!capacityList.contains(0) && whileBreaker == false ) {

            //counter for Knapsack method to control commonElements creation and element adding.
            counter = 1;
            //for loop to calculating knapsack max values,add common elements
            for (int i = 0; i < knapsackNum; i++) {
                int[] weightArray = weights.get(i).stream().mapToInt(Integer::intValue).toArray();
                int[] valuesArray = values.stream().mapToInt(Integer::intValue).toArray();
                Knapsack(capacityList.get(i), weightArray, valuesArray, itemNum);
            }

            countFrequencies(commonElements);

            for(int j= 0 ; j< finalCommons.size(); j++) {
                weights.values().remove(finalCommons.get(j));
            }

        }








        //calculates total value
        for (int k = 0; k < finalCommons.size(); k++) {
            totalValue += values.get(finalCommons.get(k));
        }
        //writing the output file
        try {
            FileWriter output = new FileWriter("output" + fileName.charAt(fileName.length()-1) + ".txt");

            output.write(totalValue + "\n");

            for (int count = 0; count < itemNum; count++) {

                if (finalCommons.contains(count)) {
                    output.write("1" + "\n");
                } else {
                    output.write("0" + "\n");
                }

            }

            output.close();
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {

            System.out.println("An error occurred.");
            e.printStackTrace();

        }

    }

    //method to get common elements of knapsacks
    public static void countFrequencies(ArrayList<Integer> list) {

        boolean newElementAdded=false;
        // hashmap to store the frequency of element
        Map<Integer, Integer> hm = new HashMap<Integer, Integer>();

        for (int i : list) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }
        int counterFreq = 0;
        // displaying the occurrence of elements in the arraylist
        ArrayList<Integer> temporary = new ArrayList<Integer>();
        for (Map.Entry<Integer, Integer> val : hm.entrySet()) {
            //if item is common for all knapsacks
            if (val.getValue() == knapsackNum) {
                if (!finalCommons.contains(val.getKey())) {
                    //capacitylist--capacities

                    for (int temp = 0; temp < capacityList.size(); temp++) {
                        //if item does not exceed the capacity for all knapsacks
                        if (capacityList.get(temp) - weights.get(temp).get(val.getKey()) >= 0) {
                            counterFreq++;
                            if (counterFreq == capacityList.size()) {
                                for (int k = 0; k < capacityList.size(); k++) {
                                    capacityList.set(k, capacityList.get(k) - weights.get(k).get(val.getKey()));

                                }
                                //add item to finalCommons list
                                finalCommons.add(val.getKey());
                                //finalCommons edited boolean
                                newElementAdded = true;
                            }

                        }

                    }
                    counterFreq = 0;

                }


            }
            else{
                //less common item added to temporary arrayList
                temporary.add(val.getKey());
            }
        }
        counterFreq = 0;



        // sort finalCommon list in descending order
        Collections.sort(temporary, Collections.reverseOrder());


        boolean lessCommonAdded = false;
        for (Map.Entry<Integer, Integer> val2 : hm.entrySet()) {
            if ( newElementAdded == false) {
                for(int i = 0; i < temporary.size(); i++) {
                    if (!finalCommons.contains(val2.getKey())) {
                        //capacitylist--capacities

                        for (int temp = 0; temp < capacityList.size(); temp++) {
                            if (capacityList.get(temp) - weights.get(temp).get(val2.getKey()) >= 0) {
                                counterFreq++;
                                if (counterFreq == capacityList.size()) {
                                    for (int k = 0; k < capacityList.size(); k++) {
                                        //decrease the capacity of knapsack
                                        capacityList.set(k, capacityList.get(k) - weights.get(k).get(val2.getKey()));

                                    }
                                    finalCommons.add(val2.getKey());
                                    //item added to finalCommons so lessCommonAdded should be true
                                    lessCommonAdded = true;
                                }
                            }

                        }
                        counterFreq = 0;
                    }
                    //If no item added then break the loop.
                    if(lessCommonAdded == false){
                        whileBreaker = true;
                    }
                }


            }
        }
    }

    static void Knapsack(int capacities, int weights[], int values[], int itemCount) {

        int itemTotal, capacity;
        int K[][] = new int[itemCount + 1][capacities + 1];

        //creates knapsack multi-dimensional array
        for (itemTotal = 0; itemTotal <= itemCount; itemTotal++) {
            for (capacity = 0; capacity <= capacities; capacity++) {
                if (itemTotal == 0 || capacity == 0)
                    K[itemTotal][capacity] = 0;
                else if (weights[itemTotal - 1] <= capacity)
                    K[itemTotal][capacity] = Math.max(values[itemTotal - 1] +
                            K[itemTotal - 1][capacity - weights[itemTotal - 1]], K[itemTotal - 1][capacity]);
                else
                    K[itemTotal][capacity] = K[itemTotal - 1][capacity];
            }
        }

        //total value result
        int result = K[itemCount][capacities];
        capacity = capacities;
        //arraylist to hold knapsack choosen values positions
        ArrayList<Integer> temp = new ArrayList<Integer>();

        for (itemTotal = itemCount; itemTotal > 0 && result > 0; itemTotal--) {

            if (result == K[itemTotal - 1][capacity])
                continue;

            else {

                result = result - values[itemTotal - 1];
                capacity = capacity - weights[itemTotal - 1];


                //if the function is visited for the first time
                if (counter == 1) {
                    commonElements.add(itemTotal - 1);
                }
                //if the function is visited again,hold values in the temp arraylist
                else if (counter > 1) {
                    temp.add(itemTotal - 1);
                }

            }

        }


        //compares arraylists,to find common positions
        Set<Integer> set = new HashSet<>();

        // Adding elements from common elements
        for (int i : commonElements) {
            commons.add(i);
        }

        // Adding elements from temp arraylist
        for (int i : temp) {
            set.add(i);
        }

        // find common elements
        commons.retainAll(set);

        //if function is not visited for first time then add position values to commonElements arraylist
        if (counter > 1) {
            commonElements.addAll(commons);
        }

        counter++;

    }

}
