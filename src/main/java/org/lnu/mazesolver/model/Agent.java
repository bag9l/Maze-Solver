package org.lnu.mazesolver.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Agent {
    private final int ID;
    private Coordinate position;
    private double epsilon = 0.8f; //   exploration probability
    private double alpha = 0.1;//   learning rate
    private double gamma = 0.9;//   discount factor
    private State[][] states;
    private int goalsFound = 0;
    private int stepsCount = 0;

    public Agent(int x, int y, int width, int height, int ID) {
        this.ID = ID;
        this.position = new Coordinate(x, y);
        states = new State[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                states[i][j] = new State();
            }
        }

        epsilon = 0.75 + Math.random() * (0.75 - 0.5);
        alpha = 0.05 + Math.random() * (0.5 - 0.05);
        gamma = 0.5 + Math.random() * (0.99 - 0.5);
    }

    public int chooseAction() {
        stepsCount++;
        if (Math.random() < epsilon) {
            //  best
            return states[position.getX()][position.getY()].getBestAction();
        } else {
            //  random
            return (int) (Math.random() * 4);
        }
    }

    public int getPosX() {
        return position.getX();
    }

    public int getPosY() {
        return position.getY();
    }

    public void giveReward(int reward, int action, int newX, int newY) {
        double prevQ = states[position.getX()][position.getY()].qValues[action];
        double maxQ = states[newX][newY].getBestValue();

        //Q-Learning: prevQ= Q(t-1)+a(r+ y* maxQ - prevQ)
        states[position.getX()][position.getY()].qValues[action] = prevQ + alpha * (reward + gamma * maxQ - prevQ);

        position.setX(newX);
        position.setY(newY);
    }

    public void goalFound() {
        System.out.printf("Agent %s found the goal in %s steps in %s time\n", ID, stepsCount, goalsFound);
        this.goalsFound++;
        this.stepsCount = 0;}
}