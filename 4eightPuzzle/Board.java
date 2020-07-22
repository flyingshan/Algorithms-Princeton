import edu.princeton.cs.algs4.Stack;
public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
	private int[][] board;
	// pass the tile values to board
    public Board(int[][] tiles) {
    	board = new int[tiles.length][tiles[0].length];
    	for (int i = 0; i<tiles.length;i++) {
    		for (int j = 0; j<tiles.length;j++) {
    			board[i][j] = tiles[i][j];
    		}
    	}
    }
                                           
    // string representation of this board
    public String toString() {
    	StringBuilder boardString = new StringBuilder();
    	boardString.append(board.length + "\n");
    	for (int i = 0; i<board.length;i++) {
    		for (int j = 0; j<board.length;j++) {
    			boardString.append(String.format("%2d ", board[i][j]));
    		}
    		boardString.append("\n");
    	}
    	return boardString.toString();		
    }

    // board dimension n
    public int dimension() {
    	return board.length;
    }

    // number of tiles out of place
    public int hamming() {
    	int cnt = 0;
    	int n = board.length;
    	for (int i = 0; i<board.length;i++) {
    		for (int j = 0; j<board.length;j++) {
    			if (board[i][j] != (i*n+j+1)) cnt++;
    		}
    	}
    	cnt--;
    	return cnt;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
    	int cnt = 0;
    	int dx;
    	int dy;
    	int n = board.length;
    	for (int i = 0; i<board.length;i++) {
    		for (int j = 0; j<board.length;j++) {
    			if (board[i][j] != 0) {
    				dx = Math.abs(((board[i][j]-1) / n) - i);
    				dy = Math.abs(((board[i][j]-1) % n) - j);
    				cnt = cnt + dx + dy;
    			}
    		}
    	}
    	return cnt;
    }

    // is this board the goal board?
    public boolean isGoal() {
    	int n = board.length;
    	for (int i = 0; i<board.length;i++) {
    		for (int j = 0; j<board.length;j++) {
    			if ((i==board.length-1)&&(j==board.length-1)) {
    				if (board[i][j]!=0) return false;
    			}
    			else {
    				if (board[i][j] != (i*n+j+1)) return false;
    			}
    		}
    	}
    	return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
    	int n = board.length;
    	if (y == null)  
    		return false;
    	if (y == this) 
    		return true;
    	
    	if (y.getClass().isInstance(this)) // can y transform into the type of this?
    	{
    		Board boardY = (Board) y;
    		if (this.board.length != boardY.board.length) return false;
    		for (int i = 0; i < n; i++) 
    			for (int j = 0; j < n; j++) 
    				if (this.board[i][j] != boardY.board[i][j]) 
    					return false;
    	}
    	else 
    	{
			return false;
		}
    	
    	return true;
    }

    private Board swap(int x, int y , int x1, int y1) {
    	Board newBoard = new Board(this.board);
    	int temp;
    	temp = newBoard.board[x][y];
    	newBoard.board[x][y] = newBoard.board[x1][y1];
    	newBoard.board[x1][y1] = temp;
    	return newBoard;    	
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors(){
    	Stack<Board> boardStack = new Stack<Board>();
    	//int n = board.length;
    	int px = 0;
    	int py = 0;
    	int flag = 0;
    	for (int i = 0; i<board.length;i++) {
    		for (int j = 0; j<board.length;j++) {
    			if (board[i][j] == 0) {
    				px = i;
    				py = j;
    				flag = 1;
    				break;
    				}
    		}
    		if (flag == 1) break;
    	}
    	if (px == 0) {
    		if (py == 0) {
    			boardStack.push(this.swap(px,py,px+1,py));
    			boardStack.push(this.swap(px,py,px,py+1));
    		}
    		else if (py == board.length-1) {
    			boardStack.push(this.swap(px,py,px+1,py));
    			boardStack.push(this.swap(px,py,px,py-1));
    		}
    		else {
    			boardStack.push(this.swap(px,py,px+1,py));
    			boardStack.push(this.swap(px,py,px,py+1));
    			boardStack.push(this.swap(px,py,px,py-1));
    		}
    	}
    	else if (px == board.length-1) {
    		if (py == 0) {
    			boardStack.push(this.swap(px,py,px-1,py));
    			boardStack.push(this.swap(px,py,px,py+1));
    		}
    		else if (py == board.length-1) {
    			boardStack.push(this.swap(px,py,px-1,py));
    			boardStack.push(this.swap(px,py,px,py-1));
    		}
    		else {
    			boardStack.push(this.swap(px,py,px-1,py));
    			boardStack.push(this.swap(px,py,px,py+1));
    			boardStack.push(this.swap(px,py,px,py-1));
    		}
    	}
    	else {
    		if (py == 0) {
    			boardStack.push(this.swap(px,py,px+1,py));
    			boardStack.push(this.swap(px,py,px-1,py));
    			boardStack.push(this.swap(px,py,px,py+1));
    		}
    		else if (py == board.length-1) {
    			boardStack.push(this.swap(px,py,px+1,py));
    			boardStack.push(this.swap(px,py,px-1,py));
    			boardStack.push(this.swap(px,py,px,py-1));
    		}
    		else {
        		boardStack.push(this.swap(px,py,px+1,py));
    			boardStack.push(this.swap(px,py,px-1,py));
    			boardStack.push(this.swap(px,py,px,py+1));
    			boardStack.push(this.swap(px,py,px,py-1));
    		}
    	}
    	return boardStack;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
    	if (this.board[0][0] == 0) return this.swap(1, 0, 1, 1);
    	else if (this.board[0][1] == 0) return this.swap(1, 0, 1, 1);
    	else return this.swap(0, 0, 0, 1); 
    }

    // unit testing (not graded)
    public static void main(String[] args) {
    	System.out.print(Math.abs((1 / 2)-1));
    	
    	System.out.print(Math.abs((1 % 2) - 0));
    }

}