package com.chessengine.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChessEngineApplication
{
    public static final String PICKLE_ENGINE_URL = "http://localhost:3000";
    
    public static void main(String[] args)
    {
        SpringApplication.run(ChessEngineApplication.class, args);
    }
}
