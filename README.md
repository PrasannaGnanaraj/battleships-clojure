## Battleships (Clojure)

[Battleships](https://en.wikipedia.org/wiki/Battleship_(game)) game implemented in clojure.

It's a game server with simple specs that can play against another instance of itself or another server that implements the same API interaction.

1. Player A: Introduces itself `POST /games`
  - post params: `opponent_address=http://192.168.1.2:8765 opponent_name=John`
  - returns status `201 Created`, response body (string): game ID (UUID)
  - when can't play, returns status `503 Service Unavailable`
2. Player A: Shoots `POST /games/:game_id` `location=b6`
  - response string: `hit/miss/sank/you_won`
  - error when shot was made out of turn: status `403 Forbidden`
  - error `404 Not Found` when the game doesn't exist
  - when a player shoots 2x out of turn, they lose (game over)
  - Status redirect `303 See Other` to `/games/:id/game_over`, body explains why the game was over
  - if the other player hasn't made turn in 5 seconds, game ends with forfeit
3. Player B does step 2.

Brief rules:

1. Ships (number of ships x size): 1x4, 2x3, 3x2, 4x1
2. Ships are straight blocks (no L or T shapes) and must not overlap or touch (corners or sides)
3. Player who hits can shoot again until they miss
4. If player misses, the other player takes the turn
