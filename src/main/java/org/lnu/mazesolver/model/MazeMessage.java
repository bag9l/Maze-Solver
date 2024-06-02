package org.lnu.mazesolver.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MazeMessage {
    private int agentsCount;
    private Maze maze;
    private Agent[] agents;

    public MazeMessage(Maze maze, Agent[] agents) {
        this.maze = maze;
        this.agents = agents;
    }
}

