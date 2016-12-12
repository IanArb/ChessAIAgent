import java.util.Stack;

/**
 * Created by ianarbuckle on 12/12/2016.
 *
 */
public class BestMove {
    private Stack whiteStack;
    private Stack blackStack;
    private Stack black;
    private Move bestMove;
    private int topWeight;

    public BestMove(Stack whiteStack, Stack blackStack, Stack black, Move bestMove, int topWeight) {
        this.whiteStack = whiteStack;
        this.blackStack = blackStack;
        this.black = black;
        this.bestMove = bestMove;
        this.topWeight = topWeight;
    }

    public Move getBestMove() {
        return bestMove;
    }

    public int getTopWeight() {
        return topWeight;
    }

    public BestMove invoke() {
        Move whiteMove;
        Move currentMove;
        while (!whiteStack.empty()) {
            whiteMove = (Move) whiteStack.pop();
            currentMove = whiteMove;

            calculateMoves(currentMove);

            calculateCenter(currentMove);

            //Reload Black pieces
            black = (Stack) blackStack.clone();
        }
        return this;
    }

    private void calculateCenter(Move currentMove) {
        int weight;//If the center of the board is available, these lines of code will attempt to occupy the center
        if ((currentMove.getStart().getYC() < currentMove.getLanding().getYC())
                && (currentMove.getLanding().getXC() == 3) && (currentMove.getLanding().getYC() == 3)
                || (currentMove.getLanding().getXC() == 4) && (currentMove.getLanding().getYC() == 3)
                || (currentMove.getLanding().getXC() == 3) && (currentMove.getLanding().getYC() == 4)
                || (currentMove.getLanding().getXC() == 4) && (currentMove.getLanding().getYC() == 4)) {
            weight = 1;

            //Update the bestmove
            if (weight > topWeight) {
                topWeight = weight;
                bestMove = currentMove;
            }
        }
    }

    private void calculateMoves(Move currentMove) {
        int weight;
        Square blackPosition;//Compare the White landing positions to the Black positions. If there is an available space, return capture else return random if not.
        while (!black.isEmpty()) {
            weight = 0;
            blackPosition = (Square) black.pop();
            if ((currentMove.getLanding().getXC() == blackPosition.getXC()) && (currentMove.getLanding().getYC() == blackPosition.getYC())) {
                //Check piece weight
                switch (blackPosition.getName()) {
                    case "BlackPawn":
                        weight = 2;
                        break;
                    case "BlackBishop":
                    case "BlackKnight":
                        weight = 3;
                        break;
                    case "BlackRook":
                        weight = 4;
                        break;
                    case "BlackQueen":
                        weight = 5;
                        break;
                    case "BlackKing":
                        weight = 6;
                        break;
                }
            }
            //Update the Bestmove
            if (weight > topWeight) {
                topWeight = weight;
                bestMove = currentMove;
            }
        }
    }
}
