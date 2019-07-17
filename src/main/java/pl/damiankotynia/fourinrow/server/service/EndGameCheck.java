package pl.damiankotynia.fourinrow.server.service;

public class EndGameCheck {


    public static boolean checkForIdenticalFour(int[][] matrix)
    {


        for (int row = 0; row < matrix.length; row++)
        {
            for (int col = 0; col < matrix[row].length; col++)
            {

                int element = matrix[row][col];
                if (element!=0){

                    if (col <= matrix[row].length - 4
                            && element == matrix[row][col + 1]
                            && element == matrix[row][col + 2]
                            && element == matrix[row][col + 3])
                        return true;


                    if (row <= matrix.length - 4 && element == matrix[row + 1][col]
                            && element == matrix[row + 2][col]
                            && element == matrix[row + 3][col])
                    {
                        return true;
                    }


                    if (row <= matrix.length - 4 && col <= matrix[row].length - 4)
                    {

                        if (element == matrix[row + 1][col + 1]
                                && element == matrix[row + 2][col + 2]
                                && element == matrix[row + 3][col + 3])
                            return true;
                    }

                    if (row <= matrix.length - 4 && col >= 3)
                    {

                        if (element == matrix[row + 1][col - 1]
                                && element == matrix[row + 2][col - 2]
                                && element == matrix[row + 3][col - 3])
                            return true;
                    }
                }

            }
        }
        return false;
    }
}
