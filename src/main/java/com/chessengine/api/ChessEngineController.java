package com.chessengine.api;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chessengine.ChessEngineService;
import com.github.bhlangonijr.chesslib.Board;

@RestController
public class ChessEngineController
{
    @GetMapping("/api/move")
    @CrossOrigin(origins = {"https://epic-chess-engine.onrender.com/",
    "https://epic-chess-engine.netlify.app/", "http://localhost:3000/"})
    public MoveResponse GetEngineMove(@RequestParam Map<String, String> _queryParameters)
    {
        ChessEngineService _engine = new ChessEngineService(new Board());

        int _numPlies = 3;
        if (_queryParameters.containsKey("numPlies"))
        {
            try { _numPlies = Integer.parseInt(_queryParameters.get("numPlies")); }
            catch (Exception _e) {}
        }

        // load from pgn if provided
        if (_queryParameters.containsKey("pgn"))
        {
            for (String _sanMove : _queryParameters.get("pgn").split(" "))
            {
                if (_sanMove == null || _sanMove.length() == 0)
                    continue;
                
                if (Character.isDigit(_sanMove.charAt(0)))
                    continue;

                _engine.getBoard().doMove(_sanMove);
            }
        }

        else if (_queryParameters.containsKey("fen"))
            _engine.getBoard().loadFromFen(_queryParameters.get("fen"));

        return new MoveResponse(_engine.FindBestMove(_numPlies, true, true, true, null).getMove().toString());
    }
}