package org.lnu.mazesolver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lnu.mazesolver.model.Coordinate;
import org.lnu.mazesolver.model.Delay;
import org.lnu.mazesolver.model.MazeRequest;
import org.lnu.mazesolver.service.MazeService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/maze")
public class MazeController {

    private final MazeService mazeService;


    @MessageMapping("/maze-websocket.start")
    public void startSimulation(MazeRequest message) {
        mazeService.startSimulation(message);
    }

    @MessageMapping("/maze-websocket.stop")
    public void stop() {
        System.out.println("STOP");
        mazeService.stop();
    }

    @MessageMapping("/maze-websocket.brake")
    public void brake() {
        System.out.println("BRAKE");
        mazeService.brake();
    }

    @MessageMapping("/maze-websocket.resume")
    public void resume() {
        System.out.println("RESUME");
        mazeService.resume();
    }

    @MessageMapping("/maze-websocket.setDelay")
    public void setDelay(Delay delay) {
        System.out.println("DELAY: " + delay.getDelay());
        mazeService.setDelay(delay.getDelay());
    }

    @MessageMapping("/maze-websocket.setObstacle")
    public void setDelay(Coordinate obstacle) {
        System.out.println("OBSTACLE: " + obstacle);
        mazeService.setObstacle(obstacle);
    }

}
