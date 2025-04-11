/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */
// Maze Solver by Oliver Faris

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // Initialize variables
        ArrayList<MazeCell> solution = new ArrayList<>();
        Stack<MazeCell> path = new Stack<>();
        MazeCell currentCell = maze.getEndCell();

        // Add path of cells to a stack
        path.add(currentCell);
        while (currentCell.getRow() != maze.getStartCell().getRow() && currentCell.getCol() != maze.getStartCell().getCol()) {
            currentCell = currentCell.getParent();
            path.add(currentCell);
        }
        path.add(maze.getStartCell());

        // Reverse the order of the path by emptying stack into an ArrayList
        while (!path.isEmpty()) {
            solution.add(path.pop());
        }
        return solution;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // Initialize variables
        int row = maze.getStartCell().getRow();
        int col = maze.getStartCell().getCol();
        Stack<MazeCell> cellsToExplore = new Stack<>();
        MazeCell currentCell = maze.getStartCell();

        // While not at the end cell
        while (currentCell.getRow() != maze.getEndCell().getRow() || currentCell.getCol() != maze.getEndCell().getCol()) {
            // If valid cell, add to stack and check other directions
            // Check north
            if (maze.isValidCell(row -1, col)) {
                cellsToExplore.add(maze.getCell(row -1, col));
                // Make this cell explored
                maze.getCell(row -1, col).setExplored(true);
            }

            // Check east
            if (maze.isValidCell(row, col +1)) {
                cellsToExplore.add(maze.getCell(row, col +1));
                maze.getCell(row, col +1).setExplored(true);
            }

            // Check south
            if (maze.isValidCell(row +1, col)) {
                cellsToExplore.add(maze.getCell(row +1, col));
                maze.getCell(row +1, col).setExplored(true);
            }

            // Check west
            if (maze.isValidCell(row, col -1)) {
                cellsToExplore.add(maze.getCell(row, col -1));
                maze.getCell(row, col -1).setExplored(true);
            }

            // Go to the new position in the grid from the top of the stack
            MazeCell newCell = cellsToExplore.pop();
            newCell.setParent(currentCell);
            currentCell = newCell;
            // Set new coordinates
            row = currentCell.getRow();
            col = currentCell.getCol();

        }
        return getSolution();
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // Initialize variables
        int row = maze.getStartCell().getRow();
        int col = maze.getStartCell().getCol();
        Queue<MazeCell> cellsToExplore = new LinkedList<>();
        MazeCell currentCell = maze.getStartCell();

        currentCell.setExplored(true);
        cellsToExplore.add(currentCell);

        // While not at the end cell
        while (!cellsToExplore.isEmpty()) {
            // Go to the new position in the grid
            currentCell = cellsToExplore.remove();
            // Set new coordinates
            row = currentCell.getRow();
            col = currentCell.getCol();

            // If valid cell, add to queue and check other directions
            // Check north
            if (maze.isValidCell(row -1, col)) {
                cellsToExplore.add(maze.getCell(row -1, col));
                maze.getCell(row -1, col).setParent(currentCell);
                // Make this cell explored
                maze.getCell(row -1, col).setExplored(true);
            }

            // Check east
            if (maze.isValidCell(row, col +1)) {
                cellsToExplore.add(maze.getCell(row, col +1));
                maze.getCell(row, col +1).setParent(currentCell);
                maze.getCell(row, col +1).setExplored(true);
            }

            // Check south
            if (maze.isValidCell(row +1, col)) {
                cellsToExplore.add(maze.getCell(row +1, col));
                maze.getCell(row +1, col).setParent(currentCell);
                maze.getCell(row +1, col).setExplored(true);
            }

            // Check west
            if (maze.isValidCell(row, col -1)) {
                cellsToExplore.add(maze.getCell(row, col -1));
                maze.getCell(row, col -1).setParent(currentCell);
                maze.getCell(row, col -1).setExplored(true);
            }

        }
        return getSolution();
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
