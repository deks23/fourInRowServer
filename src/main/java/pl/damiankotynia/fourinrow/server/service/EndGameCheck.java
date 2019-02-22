package pl.damiankotynia.fourinrow.server.service;

public class EndGameCheck {

    /*
     *  @author Marco13
     * This method checks if there are four identical elements in a matrix
     * either horizontally, vertically, or diagonally
     */
    public static boolean checkForIdenticalFour(int[][] matrix)
    {

        /* We traverse each element in the matrix */
        for (int row = 0; row < matrix.length; row++)
        {
            for (int col = 0; col < matrix[row].length; col++)
            {
                // This is the current element in our matrix
                int element = matrix[row][col];

                /*
                 * If there are 3 elements remaining to the right of the current
                 * element's position and the current element equals each of
                 * them, then return true
                 */
                if (col <= matrix[row].length - 4
                        && element == matrix[row][col + 1]
                        && element == matrix[row][col + 2]
                        && element == matrix[row][col + 3])
                    return true;

                /*
                 * If there are 3 elements remaining below the current element's
                 * position and the current element equals each of them, then
                 * return true
                 */
                if (row <= matrix.length - 4 && element == matrix[row + 1][col]
                        && element == matrix[row + 2][col]
                        && element == matrix[row + 3][col])
                {
                    return true;
                }

                /*
                 * If we are in a position in the matrix such that there are
                 * diagonals remaining to the bottom right of the current
                 * element, then we check
                 */
                if (row <= matrix.length - 4 && col <= matrix[row].length - 4)
                {
                    // If the current element equals each element diagonally to
                    // the bottom right
                    if (element == matrix[row + 1][col + 1]
                            && element == matrix[row + 2][col + 2]
                            && element == matrix[row + 3][col + 3])
                        return true;
                }

                /*
                 * If we are in a position in the matrix such that there are
                 * diagonals remaining to the bottom left of the current
                 * element, then we check
                 */
                if (row <= matrix.length - 4 && col >= 3)
                {
                    // If the current element equals each element diagonally to
                    // the bottom left
                    if (element == matrix[row + 1][col - 1]
                            && element == matrix[row + 2][col - 2]
                            && element == matrix[row + 3][col - 3])
                        return true;
                }

            }
        }

        /*
         * If all the previous return statements failed, then we found no such
         * patterns of four identical elements in this matrix, so we return
         * false
         */
        return false;
    }
}
