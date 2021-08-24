import javax.swing.*;
import java.util.ArrayList;

public class Program {
    final static State startState = new State(getStateFromUser());

    final static State endState = new State(getStateFromUser());


    public static void main(String[] args) {
        State stateAfterM;

        System.out.println("Start State");
        startState.printState();

        System.out.println("End State");
        endState.printState();

        startState.printListOfMovements();

        Movement aMovement;

        for (int i = 0; i < startState.getListOfMovements().size(); i++) {
            aMovement = startState.getListOfMovements().get(i);
            startState.setState(startState.getTilesAfterMovement(startState.getTilesBeforeM(), aMovement, i));

        }

    }

    public static ArrayList<ArrayList> getEndStateTiles() {
        return endState.getTilesBeforeM();
    }

    public static ArrayList<ArrayList> getStateFromUser() {
        ArrayList<Integer> tilesNoRows = new ArrayList<>();
        ArrayList<ArrayList> orderedTilesWithRows;    // contains 3 arrayList which represent rows
        int number;
        String input;
        boolean correctInput = false;
        while (!correctInput) {
            input = JOptionPane.showInputDialog(null, "Enter numbers from 0 to 8 " +
                    "separated by 1 space (no repetition)");
            if (isInputValid(input)) {
                input = input.replaceAll("\\s", "");
                for (int i = 0; i < input.length(); i++) {
                    number = Integer.parseInt(String.valueOf(input.charAt(i)));
                    tilesNoRows.add(number);
                }
                correctInput = true;
            } else
                JOptionPane.showMessageDialog(null, "Input must numbers between 0 and 8 " +
                        "separated by 1 space " + "(no repetition of numbers)");
        }
        orderedTilesWithRows = convertStateToStateWithRows(tilesNoRows);

        return orderedTilesWithRows;
    }

    public static ArrayList<ArrayList> convertStateToStateWithRows(ArrayList<Integer> tiles) {
        /*
        This method is called in getStateFromUser() it converts an arrayList of integers to an
        ArrayList of ArrayLists which represent rows
                 */
        ArrayList<ArrayList> allRows = new ArrayList<>();
        ArrayList<Integer> row1 = new ArrayList<>();
        ArrayList<Integer> row2 = new ArrayList<>();
        ArrayList<Integer> row3 = new ArrayList<>();

        for (int i = 0; i < 3; i++)
            row1.add(tiles.get(i));

        for (int i = 3; i < 6; i++)
            row2.add(tiles.get(i));

        for (int i = 6; i < 9; i++)
            row3.add(tiles.get(i));

        allRows.add(row1);
        allRows.add(row2);
        allRows.add(row3);

        return allRows;

    }

    public static boolean isInputValid(String input) {
        String pattern = "[0-8][\\s][0-8][\\s][0-8][\\s][0-8][\\s][0-8][\\s][0-8][\\s][0-8][\\s][0-8][\\s][0-8]";
        boolean repeats = false;
        boolean matches = false;
        if (input.matches(pattern)) {
            matches = true;
            input = input.replaceAll("\\s", "");

            for (int i = 0; i < input.length(); i++)
                for (int j = i + 1; j < input.length(); j++)
                    if (input.charAt(i) == input.charAt(j))
                        repeats = true;
        }
        if (!repeats && matches)
            return true;
        else
            return false;

    }

}

class State
{
    private ArrayList<ArrayList> tilesBeforeM;
    private ArrayList<Movement> listOfMovements;



    State (ArrayList<ArrayList> tilesBeforeM)
    {
        this.tilesBeforeM = tilesBeforeM;
        this.listOfMovements = calculateListOfMovements(tilesBeforeM);

    }
    public ArrayList<ArrayList> getTilesBeforeM()
    {
        return tilesBeforeM;
    }

    public ArrayList<Movement> getListOfMovements()
    {
        return  listOfMovements;
    }

    public void setState(ArrayList<ArrayList> tilesBeforeM)
    {
        this.tilesBeforeM = tilesBeforeM;
    }

    public static ArrayList<Movement> calculateListOfMovements(ArrayList<ArrayList> tilesBeforeM)
    {
        ArrayList<Movement> listOfMovements = new ArrayList<>();


        boolean upperLeft = false, upperCenter = false, upperRight = false;
        boolean middleLeft = false, middleCentre = false, middleRight = false;
        boolean lowerLeft = false, lowerCenter = false, lowerRight = false;

        int tileNumber;

        if(tilesBeforeM.get(0).get(0).equals(0))
            upperLeft = true;
        else if(tilesBeforeM.get(0).get(1).equals(0))
            upperCenter = true;
        else if(tilesBeforeM.get(0).get(2).equals(0))
            upperRight = true;
        else if(tilesBeforeM.get(1).get(0).equals(0))
            middleLeft = true;
        else if(tilesBeforeM.get(1).get(1).equals(0))
            middleCentre = true;
        else if(tilesBeforeM.get(1).get(2).equals(0))
            middleRight = true;
        else if(tilesBeforeM.get(2).get(0).equals(0))
            lowerLeft = true;
        else if(tilesBeforeM.get(2).get(1).equals(0))
            lowerCenter = true;
        else if(tilesBeforeM.get(2).get(2).equals(0))
            lowerRight = true;

        Movement aMovement;

        if(upperLeft)
        {
            tileNumber = (int)(tilesBeforeM.get(0).get(1));
            aMovement = new Movement(tileNumber,"west");//,nextState,endState);
            listOfMovements.add(aMovement);

            tileNumber = (int)(tilesBeforeM.get(1).get(0));
            aMovement = new Movement(tileNumber,"north");//,nextState,endState);
            listOfMovements.add(aMovement);
        }
        else if(upperCenter)
        {
            tileNumber = (int)(tilesBeforeM.get(0).get(0));
            aMovement = new Movement(tileNumber,"east");//,nextState,endState);
            listOfMovements.add(aMovement);

            tileNumber = (int)(tilesBeforeM.get(0).get(2));
            aMovement = new Movement(tileNumber,"west");//,nextState,endState);
            listOfMovements.add(aMovement);

            tileNumber = (int)(tilesBeforeM.get(1).get(1));
            aMovement = new Movement(tileNumber,"north");//,nextState,endState);
            listOfMovements.add(aMovement);
        }
        else if(upperRight)
        {
            tileNumber = (int)(tilesBeforeM.get(0).get(1));
            aMovement = new Movement(tileNumber,"east");//,nextState,endState);
            listOfMovements.add(aMovement);

            tileNumber = (int)(tilesBeforeM.get(1).get(2));
            aMovement = new Movement(tileNumber,"north");//,nextState,endState);
            listOfMovements.add(aMovement);
        }
        else if(middleLeft)
        {
            tileNumber = (int)(tilesBeforeM.get(0).get(0));
            aMovement = new Movement(tileNumber,"south");//,nextState,endState);
            listOfMovements.add(aMovement);

            tileNumber = (int)(tilesBeforeM.get(1).get(1));
            aMovement = new Movement(tileNumber,"west");//,nextState,endState);
            listOfMovements.add(aMovement);

            tileNumber = (int)(tilesBeforeM.get(2).get(0));
            aMovement = new Movement(tileNumber,"north");//,nextState,endState);
            listOfMovements.add(aMovement);
        }
        else if(middleCentre)  // center is 0
        {
            tileNumber = (int)(tilesBeforeM.get(0).get(1));
            aMovement = new Movement(tileNumber,"south");//,nextState,endState);
            listOfMovements.add(aMovement);

            tileNumber = (int)(tilesBeforeM.get(2).get(1));
            aMovement = new Movement(tileNumber,"north");//,nextState,endState);
            listOfMovements.add(aMovement);

            tileNumber = (int)(tilesBeforeM.get(1).get(0));
            aMovement = new Movement(tileNumber,"east");//,nextState,endState);
            listOfMovements.add(aMovement);

            tileNumber = (int)(tilesBeforeM.get(1).get(2));
            aMovement = new Movement(tileNumber,"west");//,nextState,endState);
            listOfMovements.add(aMovement);
        }
        else if(middleRight)
        {
            tileNumber = (int)(tilesBeforeM.get(0).get(2));
            aMovement = new Movement(tileNumber,"south");// ,nextState,endState);
            listOfMovements.add(aMovement);

            tileNumber = (int)(tilesBeforeM.get(1).get(1));
            aMovement = new Movement(tileNumber,"east"); //,nextState,endState);
            listOfMovements.add(aMovement);

            tileNumber = (int)(tilesBeforeM.get(2).get(2));
            aMovement = new Movement(tileNumber,"north");// ,nextState,endState);
            listOfMovements.add(aMovement);
        }
        else if(lowerLeft)
        {
            tileNumber = (int)(tilesBeforeM.get(1).get(0));
            aMovement = new Movement(tileNumber,"south");// ,nextState,endState);
            listOfMovements.add(aMovement);

            tileNumber = (int)(tilesBeforeM.get(2).get(1));
            aMovement = new Movement(tileNumber,"east"); //,nextState,endState);
            listOfMovements.add(aMovement);
        }
        else if(lowerCenter)
        {
            tileNumber = (int)(tilesBeforeM.get(1).get(1));
            aMovement = new Movement(tileNumber,"south");// ,nextState,endState);
            listOfMovements.add(aMovement);

            tileNumber = (int)(tilesBeforeM.get(2).get(0));
            aMovement = new Movement(tileNumber,"west");// ,nextState,endState);
            listOfMovements.add(aMovement);

            tileNumber = (int)(tilesBeforeM.get(2).get(2));
            aMovement = new Movement(tileNumber,"east");// ,nextState,endState);
            listOfMovements.add(aMovement);
        }
        else if(lowerRight)
        {
            tileNumber = (int)(tilesBeforeM.get(1).get(2));
            aMovement = new Movement(tileNumber,"south" ); //,nextState,endState);
            listOfMovements.add(aMovement);

            tileNumber = (int)(tilesBeforeM.get(2).get(1));
            aMovement = new Movement(tileNumber,"east"); //,nextState,endState);
            listOfMovements.add(aMovement);
        }
        return listOfMovements;
    }
    public void printState()
    {
        int number;
        for(int i = 0; i < tilesBeforeM.size(); i++)
        {
            for(int j = 0; j < tilesBeforeM.get(i).size(); j++)
            {
                number = (int) tilesBeforeM.get(i).get(j);
                System.out.print(number + "\t\t");
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    public void printListOfMovements()
    {
        Movement aMovement;
        System.out.println("List of Movements");

        for(int i = 0; i < listOfMovements.size(); i++)
        {
            aMovement = listOfMovements.get(i);
            System.out.println("(" + (char) (i + 65) + ")\t" + aMovement.getNumber()
                    + " to the " + aMovement.getDirection() + ". H value is: "
                    + aMovement.getHValue( getTilesAfterMovement(tilesBeforeM,aMovement,9999) ) );
        }

    }

    public ArrayList<ArrayList> getTilesAfterMovement(ArrayList<ArrayList> tilesBeforeM, Movement aMovement, int z) {
        int number;
        ArrayList<ArrayList> tilesAfterM = new ArrayList<>();


        /*
     Creates a copy of start state which when changed does not affect the
     original start state
 */
        for (int i = 0; i < tilesBeforeM.size(); i++) {
            int numberToAdd;
            ArrayList<Integer> rowToAdd = new ArrayList<>();
            //tilesAfterM.add(new ArrayList<Integer>());
            for (int j = 0; j < tilesBeforeM.get(i).size(); j++) {
                numberToAdd = (int) tilesBeforeM.get(i).get(j);
                rowToAdd.add(numberToAdd);
            }
            tilesAfterM.add(rowToAdd);
        }


        int rowNoOfZero = 0, rowNoOfNumber = 0;
        int colNoOfZero = 0, colNoOfNumber = 0;

        number = aMovement.getNumber();

        for (int i = 0; i < tilesAfterM.size(); i++) {
            if (tilesAfterM.get(i).contains(0))
                rowNoOfZero = i;
            if (tilesAfterM.get(i).contains(number))
                rowNoOfNumber = i;
        }

        colNoOfZero = tilesAfterM.get(rowNoOfZero).indexOf(0);
        colNoOfNumber = tilesAfterM.get(rowNoOfNumber).indexOf(number);

       /*   swaps 0 and the number that is going to
           moved into 0s position
      */
        for (int i = 0; i < tilesAfterM.size(); i++) {
            if (i == rowNoOfZero)
                tilesAfterM.get(i).set(colNoOfZero, number);
            if (i == rowNoOfNumber)
                tilesAfterM.get(i).set(colNoOfNumber, 0);
        }
        if (z!=9999) {
          System.out.println("\nState After Movement (" + ((char) (z + 65)) + ")");
                  this.printState();
        }

        return tilesAfterM;
    }

}

class Movement
{
    private int number;
    private String direction;

    Movement(int number, String direction)
    {
        this.number = number;
        this.direction = direction;
    }

    public int getNumber()
    {
        return number;
    }

    public String getDirection()
    {
        return direction;
    }

    public int getHValue(ArrayList<ArrayList> tilesBeforeM)
    {
        final ArrayList<ArrayList> endStateTiles = Program.getEndStateTiles();

        int hValue = 0;
        int nextStateN, endStateN;

        ArrayList<Integer> stateBeforeMNot3x3 = new ArrayList<>();
        ArrayList<Integer> endStateNot3x3 = new ArrayList<>();

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                stateBeforeMNot3x3.add((int)tilesBeforeM.get(i).get(j));
                endStateNot3x3.add((int)endStateTiles.get(i).get(j));
            }
        }

        for(int i = 0; i < stateBeforeMNot3x3.size(); i++)
        {
            nextStateN = stateBeforeMNot3x3.get(i);
            endStateN = endStateNot3x3.get(i);
            if(nextStateN != 0 && endStateN != 0)
            {
                if(nextStateN != endStateN)
                    hValue++;
            }
        }
        return hValue;
    }

}
