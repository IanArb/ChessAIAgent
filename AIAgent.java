import java.util.*;

public class AIAgent{
    Random rand;

    public AIAgent(){
        rand = new Random();
    }

/*
  The method randomMove takes as input a stack of potential moves that the AI agent
  can make. The agent uses a random number generator to randomly select a move from
  the inputted Stack and returns this to the calling agent.
*/

    public Move randomMove(Stack possibilities) {

        int moveID = rand.nextInt(possibilities.size());
        System.out.println("Agent randomly selected move : "+moveID);
        for(int i=1;i < (possibilities.size()-(moveID));i++){
            possibilities.pop();
        }
        return (Move) possibilities.pop();
    }

    public Move nextBestMove(Stack whiteStack, Stack blackStack) {
        Stack random = (Stack) whiteStack.clone();
        Stack black = (Stack) blackStack.clone();
        Move bestMove;
        int value = 0;
        bestMove = null;

        BestMove bestMoveObject = new BestMove(whiteStack, blackStack, black, bestMove, value).invoke();
        //Retrieve max value and best move object
        value = bestMoveObject.getMaxValue();
        bestMove = bestMoveObject.getBestMove();
        // If available, use the best move.
        if (value > 0) {
            System.out.println("The AI selected the next best move " + bestMove);
            return bestMove;
        }
        //Return random move if best move is not available
        return randomMove(random);

    }


    // My AI implementation would be considered as a "dumb" AI

    public Move twoLevelsDeep(Stack whiteStack, Stack blackStack, int depth) {
        Stack black = (Stack) blackStack.clone();
        Move bestMove;
        int value = 0;
        bestMove = null;

        BestMove bestMoveObject = new BestMove(whiteStack, blackStack, black, bestMove, value).invoke();
        value = bestMoveObject.getMaxValue();
        bestMove = bestMoveObject.getBestMove();

        if(depth == PieceConstants.MAX_DEPTH) {
            return null;
        }

        if (value > 0) {
            //Print out for testing
            System.out.println("The AI selected the next best move " + bestMove);
            //Use recursion for deep search
            return twoLevelsDeep(whiteStack, blackStack, depth);
        }
        //return bestMove if two levels deep move not available
        return nextBestMove(whiteStack, blackStack);
    }

}
