package com.tproteiri.tp2otimizacao;

import com.tproteiri.tp2otimizacao.model.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bernardo on 11/30/16.
 */

public class GraphUtils {
    public void getObjective(double[][] array)
    {
        ArrayList<Variable> variables = new ArrayList<Variable>();

        for(int x = 0; x<array.length; x++)
        {
            for(int y = 0; y < array[0].length; y++)
            {
                variables.add(new Variable(String.valueOf((char)x)+String.valueOf((char)y), array[x][y]));
            }
        }
    }
}
