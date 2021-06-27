# multiconstraint-knapsackproblem

# Team Members:
Sueda BİLEN 150117044
Özge SALTAN 150517059
Zehra KURU 150119841

# 0-1 Multi-constraint Knapsack Problem
Multi-constraint Knapsack problem which has multiple constraints, such as m knapsacks and 
n items. When an item is selected, it puts the item into every knapsack but items’ weights may 
be different for different knapsacks. Also knapsacks’ capacities may differ. 
# General Variables:
Common used variables that we used for implementing the algorithm:
• int knapsackNum, to hold amount of knapsacks.
• int itemNum, to hold amount of items.
• int counter, it statically manages Knapsack method every iteration.
• int totalValue, holds calculated total value.
• boolean whileBreaker, to detect the end of the program.
Submitted To: Due Date:
Asst. Prof. Ömer Korçak 16/06/20212
• arrayList<Integer> values, holds values of knapsack items.
• arrayList<Integer> capacityList, holds capacities of knapsacks as a list.
• arrayList<Integer> finalCommons, holds only common elements.
• arrayList<Integer> commonElements, holds all common elements after Knapsack method 
executed.
• arrayList<Integer> weights, holds all knapsacks values in it.
Algorithm Explanation:
After our research and discussions, we decided to specify general 0/1 knapsack method to solve 
the multi constraint knapsack problem. 
At first, we thought of filling all the knapsacks one by one and taking the common items for all 
knapsacks. Then, if any knapsack is not filled yet, their capacities are not 0 or less than 
remaining items’ values, we take the common items for a few knapsacks and we fill the 
knapsacks with those items. We thought of continuing this algorithm until one of knapsacks’ 
capacities is full or cannot be filled anymore because the remaining capacity is less than 
remaining items’ values.
