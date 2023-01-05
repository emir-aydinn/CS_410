// EMİR AYDIN
// S020843

import java.util.*;
import java.io.*;
public class Emir_Aydin_S020843 {

    public static class Rule{
        String currentState,currentSymbol,newSymbol,newState,direction;


        public Rule(String curSt,String curSym,String newSym,String d,String newSt){
                this.currentState = curSt;
                this.currentSymbol = curSym;
                this.newSymbol = newSym;
                this.newState = newSt;
                this.direction = d;

        }
        public Rule(){}

        public void setCurrentState(String currentState) {
            this.currentState = currentState;
        }

        public void setCurrentSymbol(String currentSymbol) {
            this.currentSymbol = currentSymbol;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public void setNewState(String newState) {
            this.newState = newState;
        }

        public void setNewSymbol(String newSymbol) {
            this.newSymbol = newSymbol;
        }
    }

    public static void fit_array (String[] arr,Scanner scn){
        for(int i = 0; i < arr.length; i++){
            arr[i] = scn.next();
        }

    }
    public static void main(String[] args) throws FileNotFoundException{

        File myFile = new File( "Input_Emir_Aydın_S020843.txt");
        Scanner reader= new Scanner(myFile);
         //while(reader.hasNextLine()){}

        int n_input_alph = Integer.parseInt(reader.next());
        String [] alphabet = new String[n_input_alph];
        fit_array(alphabet,reader);

        int n_tape = Integer.parseInt(reader.next());
        ArrayList<String> tapes= new ArrayList<>();
        for(int i = 0; i < n_tape; i++){
            tapes.add(reader.next()) ;
        }
        String blank = reader.next();
        tapes.add(blank) ;

        int n_states = Integer.parseInt(reader.next());
        String[] all_states = new String [n_states];
        fit_array(all_states,reader);

        String init_state = reader.next();
        String accept_state = reader.next();
        String reject_state = reader.next();

        int n_rules = (all_states.length-2) * tapes.size();
        ArrayList<Rule> rules=new ArrayList<>();
        for(int k=0; k < n_rules; k++){
            String st1=reader.next();
            String sym1=reader.next();
            String Sym2=reader.next();
            String direction = reader.next();
            String st2=reader.next();
            Rule rl= new Rule(st1,sym1,Sym2,direction,st2);
            rules.add(rl);
        }

        ArrayList<String> strings_to_detect=new ArrayList<>();
        //reader.next()
        while(reader.hasNext()){
            String input_s=reader.next();
            strings_to_detect.add(input_s);
        }

        reader.close();

        for(String s : strings_to_detect){
            s="b"+s+"b";
            String[] inputs = s.split("");

            //System.out.println(inputs[0]);

            Rule initial_rule=new Rule();
            for(Rule rlu:rules){
                if(rlu.currentState.equals(init_state)&&rlu.currentSymbol.equals(inputs[1])){
                    initial_rule.setCurrentState(rlu.currentState);
                    initial_rule.setCurrentSymbol(rlu.currentSymbol);
                    initial_rule.setNewSymbol(rlu.newSymbol);
                    initial_rule.setDirection(rlu.direction);
                    initial_rule.setNewState(rlu.newState);
                    break;
                }
            }
            ArrayList<String> visitedStates = new ArrayList<>();

            String current_st=init_state;
            int iter=1;
            String current_symbol=inputs[1];
            //String nextState= initial_rule.newState;
            String result="";
            while (!current_st.equals(accept_state) && !current_st.equals(reject_state)){
                visitedStates.add(current_st);
                Rule ruletoapply=null;
                for( Rule rule:rules){
                    if(rule.currentState.equals(current_st)&&rule.currentSymbol.equals(current_symbol)){
                        ruletoapply=rule;
                        break;

                    }
                }
                inputs[iter]=ruletoapply.newSymbol;
                if(ruletoapply.direction.equals("R")){
                    iter++;
                    current_symbol=inputs[iter];
                }
                if(ruletoapply.direction.equals("L")){
                    iter--;
                    current_symbol=inputs[iter];

                }
                current_st=ruletoapply.newState;
            }
            if(current_st.equals(accept_state)){
                visitedStates.add(accept_state);
                result=("Accepted");
            }
            if(current_st.equals(reject_state)){
                visitedStates.add(reject_state);
                result=("Rejected");

            }
            System.out.println("* * * * * * * * * * * * ");
            System.out.print("ROUT: ");
            for(String state:visitedStates){
                System.out.print(state+" ");
            }
            System.out.println();
            System.out.print("RESULT: "+result+"\n");
            System.out.println("* * * * * * * * * * * * ");


        }





    }
}