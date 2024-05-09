package com.chessengine.api;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chessengine.ChessEngineService;
import com.github.bhlangonijr.chesslib.Board;

@RestController
public class ChessEngineController
{
    private static final String NUM_PLIES_PARAM = "numPlies";

    private static final String PGN_PARAM = "pgn";
    private static final String FEN_PARAM = "fen";

    private static final String QUIESCENCE_BOOL_PARAM = "useQuiescence";

    @GetMapping("/api/move")
    @CrossOrigin(origins = {"https://epic-chess-engine.onrender.com/",
    "https://epic-chess-engine.netlify.app/", "https://avashthegoat.github.io/"})
    public MoveResponse GetEngineMove(@RequestParam Map<String, String> _queryParameters)
    {
        ChessEngineService _engine = new ChessEngineService(new Board());

        int _numPlies = 3;
        if (_queryParameters.containsKey(NUM_PLIES_PARAM))
        {
            try { _numPlies = Integer.parseInt(_queryParameters.get(NUM_PLIES_PARAM)); }
            catch (Exception _e) {}
        }

        // load from pgn if provided
        if (_queryParameters.containsKey(PGN_PARAM))
        {
            for (String _sanMove : _queryParameters.get(PGN_PARAM).split(" "))
            {
                if (_sanMove == null || _sanMove.length() == 0)
                    continue;
                
                if (Character.isDigit(_sanMove.charAt(0)))
                    continue;

                _engine.getBoard().doMove(_sanMove);
            }
        }

        else if (_queryParameters.containsKey(FEN_PARAM))
            _engine.getBoard().loadFromFen(_queryParameters.get(FEN_PARAM));

        boolean _shouldUseQuiescence = true;
        if (_queryParameters.containsKey(QUIESCENCE_BOOL_PARAM))
            _shouldUseQuiescence = Boolean.parseBoolean(_queryParameters.get(QUIESCENCE_BOOL_PARAM));

        return new MoveResponse(_engine.FindBestMove(_numPlies, true, true, _shouldUseQuiescence, null).getMove().toString());
    }
}