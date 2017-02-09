import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {

		// FILL THIS METHOD

		// explored list is a Boolean array that indicates if a state associated with a given position in the maze has already been explored. 
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		// ...

		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();
		// initialize the root state and add
		// to frontier list
		// ...

		State rootState = new State(maze.getPlayerSquare(),null,0,1);
		int herrusticValSqaure = (int) Math.pow((maze.getGoalSquare().X - rootState.getX()), 2) + 
				(int) Math.pow((maze.getGoalSquare().Y - rootState.getY()), 2);
		int herrusticVal = (int) Math.sqrt(herrusticValSqaure);
		int fValue = herrusticVal+rootState.getGValue();
		StateFValuePair rootStateFValPair = new StateFValuePair(rootState, fValue);
		frontier.add(rootStateFValPair);
		cost++;
		maxDepthSearched++;



		while (!frontier.isEmpty()) {

			// return true if a solution has been found
			// maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// update the maze if a solution found
			// use frontier.poll() to extract the minimum stateFValuePair.
			// use frontier.add(...) to add stateFValue pairs

			State currentState = frontier.poll().getState();
			explored[currentState.getX()][currentState.getY()] = true;


			if (currentState.isGoal(maze)) {
				traceBack(currentState.getParent());
				return true;
			} else {
				ArrayList<State> succList = currentState.getSuccessors(explored, maze);
				for (int i = 0; i < succList.size(); i++) {
					int hValSq = (int) Math.pow((maze.getGoalSquare().X - succList.get(i).getX()), 2) + 
							(int) Math.pow((maze.getGoalSquare().Y - succList.get(i).getY()), 2);
					int hVal = (int) Math.sqrt(hValSq);
					int fVal = hVal + succList.get(i).getGValue();
					StateFValuePair succStateFValPair = 
							new StateFValuePair(succList.get(i), fVal);
					for (Iterator<StateFValuePair> itr = frontier.iterator(); itr.hasNext();){
						StateFValuePair temp = itr.next();
						if (temp.getState().getX() ==  succStateFValPair.getState().getX() && 
								temp.getState().getY() ==  succStateFValPair.getState().getY()) {
							if (succStateFValPair.getState().getGValue() < temp.getState().getGValue()){
								itr.remove();
							} 
						}	
					}
					frontier.add(succStateFValPair);
				}
			}

			if (frontier.size() > maxSizeOfFrontier) {
				maxSizeOfFrontier = frontier.size();
			}
			noOfNodesExpanded++;
		}
		//return false if no solution
		return false;
	}

	private void traceBack (State state) {
		if (state.getSquare().equals(maze.getPlayerSquare())) {
			return;
		}
		maze.setOneSquare(state.getSquare(), '.');
		traceBack(state.getParent());
		cost++;
		maxDepthSearched++;
	}
}
