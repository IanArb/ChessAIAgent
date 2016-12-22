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
            //Retrieve our possible current white moves from the stack
            whiteMove = (Move) whiteStack.pop();
            currentMove = whiteMove;

            //Call our method to calculate the best move
            calculateMoves(currentMove);

            //Call our method to calculate center pieces move
            calculateCenter(currentMove);

            //Generate copy of our black moves
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
                        //Assign constant value = 1
                        value = PieceConstants.PAWN_VALUE;
                        break;
                    case PieceConstants.BLACK_BISHOP:
                    case PieceConstants.BLACK_KNIGHT:
                        //Assign constant value = 3
                        value = PieceConstants.BISHOP_KNIGHT_VALUE;
                        break;
                    case PieceConstants.BLACK_ROOK:
                        //Assign constant value = 5
                        value = PieceConstants.ROOK_VALUE;
                        break;
                    case PieceConstants.BLACK_QUEEN:
                        //Assign constant value = 9
                        value = PieceConstants.QUEEN_VALUE;
                        break;
                    case PieceConstants.BLACK_KING:
                        //Assign constant value = infinity
                        value = PieceConstants.KING_VALUE;
                        break;
                }
            }
            //Assign the best move
            if (value > maxValue) {
                maxValue = value;
                bestMove = currentMove;
            }
        }
    }
}
