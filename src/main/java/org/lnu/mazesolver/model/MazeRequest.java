package org.lnu.mazesolver.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MazeRequest {
    private int agentsCount;
    private int[][] maze;
    private Agent[] agents;
    private Coordinate start;
    private Coordinate goal;

    public MazeRequest(int[][] maze, Agent[] agents) {
        this.maze = maze;
        this.agents = agents;
    }

    public MazeRequest(int[][] maze, Agent[] agents, Coordinate start, Coordinate goal) {
        this.maze = maze;
        this.agents = agents;
        this.start = start;
        this.goal = goal;
    }
}
