package test;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SolutionQueensAttack {
	
	static class Table {
		private int size;
		
		private Map<String, Position> positions = new HashMap<>();
		
		public Table(int size) {
			this.size = size;
		}

		public Optional<Position> getNextWest(int row, int column) {
			return Optional.ofNullable(getOrCreatePosition(row ,  (column - 1)));
		}
		
		public Optional<Position> getNextEast(int row, int column) {
			return Optional.ofNullable(getOrCreatePosition(row ,  (column + 1)));
		}
		
		public Optional<Position> getNextNorth(int row, int column) {
			return Optional.ofNullable(getOrCreatePosition((row + 1) ,  (column)));
		}
		
		public Optional<Position> getNextSouth(int row, int column) {
			return Optional.ofNullable(getOrCreatePosition((row - 1) ,  (column)));
		}
		
		
		public Optional<Position> getNextNorthEast(int row, int column) {
			return Optional.ofNullable(getOrCreatePosition((row + 1) ,  (column + 1)));
		}
		
		public Optional<Position> getNextNorthWest(int row, int column) {
			return Optional.ofNullable(getOrCreatePosition((row + 1) ,  (column - 1)));
		}
		
		public Optional<Position> getNextSouthEast(int row, int column) {
			return Optional.ofNullable(getOrCreatePosition((row - 1) ,  (column + 1)));
		}
		
		public Optional<Position> getNextSouthWest(int row, int column) {
			return Optional.ofNullable(getOrCreatePosition((row - 1) ,  (column - 1)));
		}
		
		public int getNextPossibleMovementsCount(Position position, BiFunction<Integer, Integer, Optional<Position>> direction) {
			int count = 0;
			Optional<Position> nextPosition = Optional.of(position);
			while(nextPosition.isPresent()) {
				Position pos = nextPosition.get();
				nextPosition = direction.apply(pos.getRow(), nextPosition.get().getColumn());
				if(nextPosition.isPresent() && !nextPosition.get().isOccupied()) {
					count++;
				} else {
					break;
				}
			} 
			return count;
		}
		
		
		public Position getPosition(int row, int column) {
			if(row >= 1 && row <= size && column >= 1 && column <= size) {
				return positions.get(row + ":" +  column);
			}
			return null;
		}
		
		public Position getOrCreatePosition(int row, int column) {
			if(row >= 1 && row <= size && column >= 1 && column <= size) {
				Position position = getPosition(row, column);
				if( position == null ) {
					position = new Position(row, column);
					positions.put(row + ":" + column, position);
				}
				return position;
			}
			return null;
			
			
		}
		
		
	}
	
	static class Position {
		private int row;
		private int column;
		
		private String coin;
		
		public Position(int row, int column) {
			super();
			this.row = row;
			this.column = column;
		}

		public String getCoin() {
			return coin;
		}

		public void setCoin(String coin) {
			this.coin = coin;
		}
		
		public boolean isOccupied() {
			return coin != null;
		}

		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public int getColumn() {
			return column;
		}

		public void setColumn(int column) {
			this.column = column;
		}
		
		
	}

    // Complete the queensAttack function below.
    static int queensAttack(int n, int k, int r_q, int c_q, int[][] obstacles) {
    	Table table = new Table(n);
    	Position queenPosition = table.getOrCreatePosition(r_q, c_q);
    	queenPosition.setCoin("Queen");
    	
    	for(int i = 0; i < obstacles.length; i++) {
    		int[] row = obstacles[i];
    		Position obsPosition = table.getOrCreatePosition(row[0], row[1]);
    		obsPosition.setCoin("Obstacle" + (i + 1));
    	}
    	
    	int totalCount = 0;
    	totalCount += table.getNextPossibleMovementsCount(queenPosition, table::getNextEast);
    	totalCount += table.getNextPossibleMovementsCount(queenPosition, table::getNextWest);
    	totalCount += table.getNextPossibleMovementsCount(queenPosition, table::getNextNorth);
    	totalCount += table.getNextPossibleMovementsCount(queenPosition, table::getNextSouth);
    	totalCount += table.getNextPossibleMovementsCount(queenPosition, table::getNextNorthEast);
    	totalCount += table.getNextPossibleMovementsCount(queenPosition, table::getNextNorthWest);
    	totalCount += table.getNextPossibleMovementsCount(queenPosition, table::getNextSouthEast);
    	totalCount += table.getNextPossibleMovementsCount(queenPosition, table::getNextSouthWest);
    	
    	return totalCount;
    }

    private static final Scanner scanner = getScanner();

	private static Scanner getScanner()  {
		try {
			return new Scanner(new FileInputStream(System.getenv("INPUT_PATH")));
		} catch (FileNotFoundException e) {
			return new Scanner(System.in);
		}
	}

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nk = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nk[0]);

        int k = Integer.parseInt(nk[1]);

        String[] r_qC_q = scanner.nextLine().split(" ");

        int r_q = Integer.parseInt(r_qC_q[0]);

        int c_q = Integer.parseInt(r_qC_q[1]);

        int[][] obstacles = new int[k][2];

        for (int i = 0; i < k; i++) {
            String[] obstaclesRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 2; j++) {
                int obstaclesItem = Integer.parseInt(obstaclesRowItems[j]);
                obstacles[i][j] = obstaclesItem;
            }
        }

        int result = queensAttack(n, k, r_q, c_q, obstacles);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
