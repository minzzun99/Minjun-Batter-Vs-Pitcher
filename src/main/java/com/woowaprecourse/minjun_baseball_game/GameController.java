package com.woowaprecourse.minjun_baseball_game;

import com.woowaprecourse.minjun_baseball_game.service.BaseballGameService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private final BaseballGameService service;

    @PostMapping("/start")
    public GameStartResponse startGame(@RequestBody GameStratRequest request) {

    }
}
