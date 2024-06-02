package org.lnu.mazesolver.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.lnu.mazesolver.model.Agent;
import org.lnu.mazesolver.model.Coordinate;
import org.lnu.mazesolver.model.Maze;
import org.lnu.mazesolver.model.MazeRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class MazeService {

    private final SimpMessagingTemplate messagingTemplate;


    private boolean isBrake = false;
    private boolean isLearn = true;
    private int delay = 100;

    private Maze maze;
    private Agent[] agents;
    private int agentsCount;
    private Coordinate start;
    private Coordinate goal;

    @Setter
    private Coordinate obstacle;

    public void setDelay(int delay) {
        if (delay < 100) {
            delay = 100;
        }
        this.delay = delay;
    }

    public void stop() {
        this.isLearn = false;
    }

    public void resume() {
        this.isLearn = true;
    }

    public void brake() {
        this.isBrake = true;
    }

    public void startSimulation(MazeRequest message) {
        System.out.println("START");
        this.isBrake = false;
        for (int[] raw : message.getMaze()) {
            System.out.println(Arrays.toString(raw));
        }
        System.out.println(message);

        agentsCount = message.getAgentsCount();
        maze = new Maze(message.getMaze());
        agents = new Agent[agentsCount];
        start = message.getStart();
        goal = message.getGoal();

        for (int i = 0; i < agentsCount; i++) {
            agents[i] = new Agent(start.getX(), start.getY(), maze.getWidth(), maze.getHeight(), i);
        }
        messagingTemplate.convertAndSend("/topic/maze", new MazeRequest(agentsCount, maze.getMaze(), agents, start, goal));

        while (!isBrake) {
            if (isLearn) {
                moveAgents();
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void moveAgents() {

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }

        if (maze == null || agents == null) {
            return;
        }

        // Створюємо ExecutorService з фіксованим пулом потоків
        ExecutorService executorService = Executors.newFixedThreadPool(agentsCount);

        // Створюємо CountDownLatch з числом потоків, яке нам потрібно очікувати
        CountDownLatch latch = new CountDownLatch(agentsCount);

        for (Agent agent : agents) {
            executorService.submit(() -> {
                try {
                    // Дії, які потрібно виконати в окремому потоці
                    step(agent);
                    // Тут можна виконувати будь-яку необхідну логіку
                } finally {
                    // Зменшуємо лічильник CountDownLatch
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
        } finally {
            executorService.shutdown();
        }

        messagingTemplate.convertAndSend("/topic/maze", new MazeRequest(agentsCount, maze.getMaze(), agents, start, goal));
    }

    private void step(Agent a) {
        int xPos = a.getPosX();
        int yPos = a.getPosY();
        int chosenAction = a.chooseAction();

        switch (chosenAction) {
            case 0:
                xPos--;
                break;
            case 1:
                yPos++;
                break;
            case 2:
                xPos++;
                break;
            case 3:
                yPos--;
                break;
            default:
                break;
        }

        if (xPos < 0 || xPos >= maze.getWidth() || yPos < 0 || yPos >= maze.getHeight()
                || maze.getValue(yPos, xPos) == 0) {
            a.giveReward(-1, chosenAction, a.getPosX(), a.getPosY());
        } else if (Objects.nonNull(obstacle) && obstacle.getX() == xPos && obstacle.getY() == yPos) {
            a.giveReward(-5, chosenAction, a.getPosX(), a.getPosY());
        } else if (xPos == goal.getX() && yPos == goal.getY()) {
            a.giveReward(100, chosenAction, start.getX(), start.getY());
            a.goalFound();
        } else {
            a.giveReward(0, chosenAction, xPos, yPos);
        }
    }
}

