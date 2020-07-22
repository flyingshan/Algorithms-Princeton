import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Solver {
	private int initFlag;
	private int twinFlag;
	private Node finalNode;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	if (initial == null) throw new java.lang.IllegalArgumentException();
    	initFlag = 0;
    	twinFlag = 0;
    	MinPQ<Node> pqInit = new MinPQ<Node>();
    	MinPQ<Node> pqTwin = new MinPQ<Node>();
    	Node initNode = new Node(null,initial);
    	Node twinNode = new Node(null,initial.twin());
    	
    	// begin to solve using A*
    	pqInit.insert(initNode);
    	pqTwin.insert(twinNode);
    	while(true) {
	    	Node temp1 = pqInit.delMin();
	    	if (temp1.currentBoard.isGoal()) {
	    		initFlag = 1;
	    		finalNode = temp1;
	    		break;
	    	}
	    	else {
	    		for(Board b: temp1.currentBoard.neighbors()) {
	    			if (temp1.predecessor != null) {
		    			if (temp1.predecessor.currentBoard.equals(b)) ; // if neighbor node == predecessor, then do not enqueue
		    			else pqInit.insert(new Node(temp1,b));
	    			}
	    			else pqInit.insert(new Node(temp1,b));
	    		}
	    	}
	    	Node temp2 = pqTwin.delMin();
	    	if (temp2.currentBoard.isGoal()) {
	    		twinFlag = 1;
	    		finalNode = temp2;
	    		break;
	    	}
	    	else {
	    		for(Board b: temp2.currentBoard.neighbors()) {
	    			if (temp2.predecessor != null) {
		    			if (temp2.predecessor.currentBoard.equals(b)) ; // if neighbor node == predecessor, then do not enqueue
		    			else pqTwin.insert(new Node(temp2,b));
	    			}
	    			else pqTwin.insert(new Node(temp2,b));

	    		}
	    	}
    	}
    }
    
	private class Node implements Comparable<Node>{
		//private int hamming;
		private int manhatten;
		private final int move;
		private Board currentBoard;
		private Node predecessor;
		public Node (Node predecessor, Board currentBoard) {
			this.predecessor = predecessor;
			this.currentBoard = currentBoard;
			if (this.predecessor == null ) this.move = 0;
			else this.move = predecessor.move + 1;
			this.manhatten = currentBoard.manhattan();
			// this.hamming = currentBoard.hamming();
		}
		public int compareTo(Node that) {
			if ((this.manhatten+this.move) > (that.manhatten+that.move)) return +1;
			if ((this.manhatten+this.move) < (that.manhatten+that.move)) return -1;
			return 0;
		}
	}

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
    	if (initFlag == 1) return true;
    	else if (twinFlag == 1)return false;
    	else return false;
    }

    // min number of moves to solve initial board
    public int moves() {
    	if (twinFlag == 1) return -1;
    	else return finalNode.move;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
    	if (twinFlag == 1) return null;
    	else {
    	Stack<Board> solutionStack = new Stack<Board>();
    	Node cNode = finalNode;
    	while(cNode.predecessor!=null) {
    		solutionStack.push(cNode.currentBoard);
    		cNode = cNode.predecessor;
    	}
    	// push the initial board
    	solutionStack.push(cNode.currentBoard);
    	return solutionStack;
    	}
    }

    // test client (see below) 
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
