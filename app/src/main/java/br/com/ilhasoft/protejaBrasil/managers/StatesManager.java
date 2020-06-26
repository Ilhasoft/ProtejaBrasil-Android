package br.com.ilhasoft.protejaBrasil.managers;

import java.util.ArrayList;
import java.util.List;

import br.com.ilhasoft.protejaBrasil.model.State;

/**
 * Created by johncordeiro on 11/10/15.
 */
public class StatesManager {

    public static final int DEFAULT_STATE_POSITION = 6;

    public static List<State> getStates() {
        final List<State> states = new ArrayList<>();
        states.add(new State("AC" ,"Acre", -9.97400f, -67.80757f));
        states.add(new State("AL", "Alagoas", -9.66625f, -35.73510f));
        states.add(new State("AP", "Amapá", 0.03446f, -51.06656f));
        states.add(new State("AM", "Amazonas", -3.10641f, -60.02643f));
        states.add(new State("BA", "Bahia", -12.95772f, -38.44940f));
        states.add(new State("CE", "Ceará", -3.82041f, -38.58398f));
        states.add(new State("DF", "Distrito Federal", -15.83454f, -47.94434f));
        states.add(new State("ES", "Espírito Santo", -20.31536f, -40.30177f));
        states.add(new State("GO", "Goiás", -16.67772f, -49.26763f));
        states.add(new State("MA", "Maranhão", -2.53238f, -44.30048f));
        states.add(new State("MT", "Mato Grosso", -15.59892f, -56.09489f));
        states.add(new State("MS", "Mato Grosso do Sul", -20.44351f, -54.64776f));
        states.add(new State("MG", "Minas Gerais", -19.91907f, -43.93857f));
        states.add(new State("PA", "Pará", -1.45974f, -48.48927f));
        states.add(new State("PB", "Paraíba", -7.11532f, -34.86105f));
        states.add(new State("PR", "Paraná", -25.42836f, -49.27325f));
        states.add(new State("PE", "Pernambuco", -8.05184f, -34.88837f));
        states.add(new State("PI", "Piauí", -5.08921f, -42.80163f));
        states.add(new State("RJ", "Rio de Janeiro", -22.91792f, -43.24219f));
        states.add(new State("RN", "Rio Grande do Norte", -5.80200f, -35.21161f));
        states.add(new State("RS", "Rio Grande do Sul", -30.04146f, -51.21457f));
        states.add(new State("RO", "Rondônia", -8.75946f, -63.89159f));
        states.add(new State("RR", "Roraima", 2.81954f, -60.67140f));
        states.add(new State("SC", "Santa Catarina", -27.59609f, -48.53631f));
        states.add(new State("SP", "São Paulo", -23.60175f, -46.64520f));
        states.add(new State("SE", "Sergipe", -10.90950f, -37.06787f));
        states.add(new State("TO", "Tocantins", -10.16889f, -48.33172f));

        return states;
    }

}
