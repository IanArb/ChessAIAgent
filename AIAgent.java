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
        Move selectedMove = (Move)possibilities.pop();
        return selectedMove;
    }

  /*
  Pawn: 1 point
  Knight/Bishop: 3 point
  Rook: 5 point
  Queen: 9 point
  King: the game

  get all possible moves just like above with the random agent and then apply a utility function to work out which move to make
   */

    public Move nextBestMove(Stack whiteStack, Stack blackStack) {
        Stack randomBackup = (Stack) whiteStack.clone();
        Stack black = (Stack) blackStack.clone();
        Move bestMove;
        int TopWeight = 0;
        bestMove = null;

        BestMove bestMove1 = new BestMove(whiteStack, blackStack, black, bestMove, TopWeight).invoke();
        TopWeight = bestMove1.getTopWeight();
        bestMove = bestMove1.getBestMove();
        // If available, use the best move. Otherwise go Random.
        if (TopWeight > 0) {
            System.out.println("The AI selected the next best move" + bestMove);
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
