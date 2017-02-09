import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Breadth-First Search (BFS)
 * 
 * You should fill the search() method of this class.
 */
public class BreadthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public BreadthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main breadth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		// FILL THIS METHOD

		// explored list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];

		// ...

		// Queue implementing the Frontier list
		LinkedList<State> queue = new LinkedList<State>();

		State rootState = new State(maze.getPlayerSquare(), null, 0, 1);
		queue.add(rootState);
		cost++;
		maxDepthSearched++;

		while (!queue.isEmpty()) {
			// return true if find a solution
			// maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// update the maze if a solution found

			// use queue.pop() to pop the queue.
			// use queue.add(...) to add elements to queue
			State currentState = queue.pop();
			explored[currentState.getX()][currentState.getY()] = true;
			noOfNodesExpanded++;

			if (currentState.isGoal(maze)) {
				traceBack(currentState.getParent());	
				return true;
			} else {
				boolean isExist = false;
				ArrayList<State> succList = currentState.getSuccessors(explored, maze);
				for (int i = 0; i < succList.size(); i++) {
					isExist = false;
					State newState = succList.get(i);
					for (int j = 0; j < queue.size(); j++) {
						if ((queue.get(j).getX() == newState.getX()) && (queue.get(j).getY() == newState.getY())) {
							isExist = true;
							break;
						}
					} 
					if (!isExist) {
						queue.add(newState);
					}
				}
			}
			if (queue.size() >= maxSizeOfFrontier) {
				maxSizeOfFrontier = queue.size();
			}
		}
		return false;
		// return false if no solution
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
