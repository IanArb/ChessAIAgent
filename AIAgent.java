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

    public Move randomMove(Stack possibilities){

        int moveID = rand.nextInt(possibilities.size());
        System.out.println("Agent randomly selected move : "+moveID);
        for(int i=1;i < (possibilities.size()-(moveID));i++){
            possibilities.pop();
        }
        return (Move) possibilities.pop();
    }

    public Move nextBestMove(Stack whiteStack, Stack blackStack) {
        Stack randomBackup = (Stack) whiteStack.clone();
        Stack black = (Stack) blackStack.clone();
        Move bestMove;
        int weight = 0;
        bestMove = null;

        BestMove bestMoveObject = new BestMove(whiteStack, blackStack, black, bestMove, weight).invoke();
        weight = bestMoveObject.getTopWeight();
        bestMove = bestMoveObject.getBestMove();
        // If available, use the best move.
        if (weight > 0) {
            System.out.println("The AI selected the next best move" + bestMove.toString());
            return bestMove;
        }
        return randomMove(randomBackup);

    }

  /*
  This agent looks ahead and tries to determine what the player is going to do

  Min Max

   */

    public Move twoLevelsDeep(Stack possibilities){
        Move selectedMove = new Move();
        return selectedMove;
    }
}
