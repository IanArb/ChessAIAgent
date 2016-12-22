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
    private int maxValue;

    public BestMove(Stack whiteStack, Stack blackStack, Stack black, Move bestMove, int maxValue) {
        this.whiteStack = whiteStack;
        this.blackStack = blackStack;
        this.black = black;
        this.bestMove = bestMove;
        this.maxValue = maxValue;
    }

    public Move getBestMove() {
        return bestMove;
    }

    public int getMaxValue() {
        return maxValue;
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
        int value;

        int yLanding = currentMove.getLanding().getYC();
        int yStart = currentMove.getStart().getYC();
        int xLanding = currentMove.getLanding().getXC();

        //form the center of the board coordinates
        boolean isXCorPos1 = xLanding == 3;
        boolean isYCorPos1 = yLanding == 3;
        boolean isXCorPos2 = xLanding == 4;
        boolean isYCorPos2 = yLanding == 4;

        if ((yStart < xLanding)
                && isXCorPos1 && isYCorPos1
                || isXCorPos2 && isYCorPos1
                || isXCorPos1 && isYCorPos2
                || isXCorPos2 && isYCorPos2) {
            value = 1;

            if (value > maxValue) {
                maxValue = value;
                bestMove = currentMove;
            }
        }
    }

    private void calculateMoves(Move currentMove) {
        int value;
        //Compare the white landing positions to the black positions.
        Square blackPosition;
        while (!black.isEmpty()) {
            value = 0;
            blackPosition = (Square) black.pop();

            // Get landing positions for black piece and assign values
            boolean isValidBlackXCor = currentMove.getLanding().getXC() == blackPosition.getXC();
            boolean isValidBlackYCor = currentMove.getLanding().getYC() == blackPosition.getYC();

            if (isValidBlackXCor && isValidBlackYCor) {
                switch (blackPosition.getName()) {
                    case PieceConstants.BLACK_PAWN:
                        value = PieceConstants.PAWN_VALUE;
                        break;
                    case PieceConstants.BLACK_BISHOP:
                    case PieceConstants.BLACK_KNIGHT:
                        value = PieceConstants.BISHOP_KNIGHT_VALUE;
                        break;
                    case PieceConstants.BLACK_ROOK:
                        value = PieceConstants.ROOK_VALUE;
                        break;
                    case PieceConstants.BLACK_QUEEN:
                        value = PieceConstants.QUEEN_VALUE;
                        break;
                    case PieceConstants.BLACK_KING:
                        value = PieceConstants.KING_VALUE;
                        break;
                }
            }
            
            if (value > maxValue) {
                maxValue = value;
                bestMove = currentMove;
            }
        }
    }
}
