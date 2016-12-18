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

            black = (Stack) blackStack.clone();
        }
        return this;
    }

    private void calculateCenter(Move currentMove) {
        int weight;

        int yLanding = currentMove.getLanding().getYC();
        int yStart = currentMove.getStart().getYC();
        int xLanding = currentMove.getLanding().getXC();

        boolean isXCorPos1 = xLanding == 3;
        boolean isYCorPos1 = yLanding == 3;
        boolean isXCorPos2 = xLanding == 4;
        boolean isYCorPos2 = yLanding == 4;

        if ((yStart < xLanding)
                && isXCorPos1 && isYCorPos1
                || isXCorPos2 && isYCorPos1
                || isXCorPos1 && isYCorPos2
                || isXCorPos2 && isYCorPos2) {
            weight = 1;

            if (weight > topWeight) {
                topWeight = weight;
                bestMove = currentMove;
            }
        }
    }

    private void calculateMoves(Move currentMove) {
        int weight;
        //Compare the white landing positions to the black positions.
        Square blackPosition;
        while (!black.isEmpty()) {
            weight = 0;
            blackPosition = (Square) black.pop();
            // Get landing positions for black piece and assign values
            boolean isValidBlackXCor = currentMove.getLanding().getXC() == blackPosition.getXC();
            boolean isValidBlackYCor = currentMove.getLanding().getYC() == blackPosition.getYC();
            if (isValidBlackXCor && isValidBlackYCor) {
                switch (blackPosition.getName()) {
                    case BlackPieceType.BLACK_PAWN:
                        weight = 2;
                        break;
                    case BlackPieceType.BLACK_BISHOP:
                    case BlackPieceType.BLACK_KNIGHT:
                        weight = 3;
                        break;
                    case BlackPieceType.BLACK_ROOK:
                        weight = 4;
                        break;
                    case BlackPieceType.BLACK_QUEEN:
                        weight = 5;
                        break;
                    case BlackPieceType.BLACK_KING:
                        weight = 6;
                        break;
                }
            }
            
            if (weight > topWeight) {
                topWeight = weight;
                bestMove = currentMove;
            }
        }
    }
}
