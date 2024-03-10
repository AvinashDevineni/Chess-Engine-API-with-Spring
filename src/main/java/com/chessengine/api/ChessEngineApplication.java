package com.chessengine.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChessEngineApplication
{
    public static final String ENGINE_URL = "https://epic-chess-engine.onrender.com/";
    
    public static void main(String[] args)
    {
        SpringApplication.run(ChessEngineApplication.class, args);
    }
}
