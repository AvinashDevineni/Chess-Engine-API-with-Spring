package com.chessengine.api;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chessengine.ChessEngine;
import com.github.bhlangonijr.chesslib.Board;

@RestController
public class ChessEngineController
{
    @GetMapping("/api/move")
    @CrossOrigin(origins = {"https://epic-chess-engine.onrender.com/",
    "https://epic-chess-engine.netlify.app/"})
    public MoveResponse GetEngineMove(@RequestParam Map<String, String> _queryParameters)
    {
        ChessEngine _engine = new ChessEngine(new Board());
        _engine.getBoard().loadFromFen(_queryParameters.get("fen"));

        int _numPlies = 3;
        if (_queryParameters.containsKey("numPlies"))
        {
            try { _numPlies = Integer.parseInt(_queryParameters.get("numPlies")); }
            catch (Exception _e) {}
        }

        return new MoveResponse(_engine.FindBestMove(_numPlies, true, true, true, null).getMove().toString());
    }
}