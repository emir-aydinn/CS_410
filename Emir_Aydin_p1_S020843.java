// EMİR AYDIN
// S020843

import java.util.*;
import java.io.*;
public class Emir_Aydin_S020843 {
    public static class State{
        String name;
        boolean finaal=false;
        boolean start= false;
        public Map<String, List<State>> trans=new HashMap<String, List<State>>();

        State(String s){
            this.name=s;
            add_trans("1", new ArrayList<State>());
            add_trans("0", new ArrayList<State>());
        }
        State(){
            add_trans("1", new ArrayList<State>());
            add_trans("0", new ArrayList<State>());
        }
        public void set_name(String s){
            this.name=s;
        }
        public void add_trans(String st, List <State>s){
            trans.put(st,s);
        }
        public List<State> next_state(String c){
            return trans.get(c);
        }
        public Map<String, List<State>> get_transition(){
            return this.trans;
        }
        public String getName(){
            return this.name;
        }
        public boolean is_final(){
            return finaal;
        }
        public void set_final(boolean f){
            this.finaal=f;
        }
        public boolean is_start(){
            return start;
        }
        public void set_start(boolean s){
            this.start=s;
        }
    }
    public static String edit_String(String input){
        // Converting input string to character array
        char temp[] = input.toCharArray();

        // Sorting temp array using
        Arrays.sort(temp);

        // Deleting the duplicate elements in array
        int index = 0;
        for(int i=0;i<temp.length;i++){
            int j;
            for(j=0;j<i;j++){
                if (temp[i] == temp[j])
                {
                    break;
                }
            }
            if (j == i)
            {
                temp[index++] = temp[i];
            }


        }
        // Returning new sorted and edited string
        return String.valueOf(Arrays.copyOf(temp, index));
    }
    public static void main(String[] args) throws FileNotFoundException {

        File myFile = new File( "NFA2.txt");
        String dfa="";
        ArrayList<State>nfa_states=new ArrayList<>();
        ArrayList<State>dfa_states=new ArrayList<>();

        Scanner nfa= new Scanner(myFile);
        while(nfa.hasNextLine()) {
            String line = nfa.nextLine();
            while (!line.equals("STATES")) { // reading ALPHABET
                dfa+=(line)+"\n";
                line = nfa.nextLine();
            }
            line = nfa.nextLine();
            while (!line.equals("START")) { // reading STATES
                State newSt= new State(line);
                nfa_states.add(newSt);
                line = nfa.nextLine();
            }
            line = nfa.nextLine();
            while (!line.equals("FINAL")) { // reading START state
                for (State s: nfa_states) {
                    if(s.getName().equals(line)){
                        s.set_start(true);
                    }
                }
                line = nfa.nextLine();
            }
            line = nfa.nextLine();
            while (!line.equals("TRANSITIONS")) { // reading FINAL state
                for (State s: nfa_states) {
                    if(s.getName().equals(line)){
                        s.set_final(true);
                    }
                }
                line = nfa.nextLine();
            }
            line = nfa.next();
            while (!line.equals("END")) { // reading TRANSITIONS
                String st=line;
                String tr= nfa.next();
                //System.out.println(tr);
                String nextName=nfa.next();
                for (State s: nfa_states) {
                    if(s.getName().equals(st)){
                        for (State s0: nfa_states){
                            if(s0.getName().equals(nextName)){

                                s.get_transition().get(tr).add(s0);
                            }
                        }
                    }

                }
                //System.out.println(line+" " + tr + " "+nextName);
                line = nfa.next();

            }
            if (line.equals("END ")) {
                break;
            }
        }
        nfa.close();
        //System.out.println(dfa); //print the Alphabet in dfa output

        for (State first:nfa_states) {
            if(first.is_start()) {
                dfa_states.add(first);
            }
        }

        int length=dfa_states.size();
        for(int i=0;i<length;i++) {
            State nextly=dfa_states.get(i);
            State n1 = new State();
            State n2 = new State();
            String next1names = "";
            String next0names = "";
            if(!nextly.get_transition().get("0").isEmpty()) {
                for (State s : nextly.get_transition().get("0")) {
                    if (s.is_final()) {
                        n2.set_final(true);
                    }
                    next0names += s.getName();
                    for (State sub : s.get_transition().get("0")) {
                        n2.get_transition().get("0").add(sub);
                    }
                    for (State sub : s.get_transition().get("1")) {
                        n2.get_transition().get("1").add(sub);
                    }

                }
                String name0=edit_String(next0names);
                nextly.get_transition().get("0").clear();
                nextly.get_transition().get("0").add(n2);
                n2.set_name(name0);
                boolean n2c = false;
                for (int k = 0; k < dfa_states.size(); k++) {
                    if (dfa_states.get(k).getName().equals(n2.getName())) {
                        n2c = true;
                    }
                }
                if (!n2c) {
                    dfa_states.add(n2);
                    length++;
                }
            }
            if(!nextly.get_transition().get("1").isEmpty()) {
                for (State s : nextly.get_transition().get("1")) {
                    if (s.is_final()) {
                        n1.set_final(true);
                    }
                    next1names += s.getName();
                    for (State sub : s.get_transition().get("0")) {
                        n1.get_transition().get("0").add(sub);
                    }
                    for (State sub : s.get_transition().get("1")) {
                        n1.get_transition().get("1").add(sub);
                    }
                }
                String name1= edit_String(next1names);
                nextly.get_transition().get("1").clear();
                nextly.get_transition().get("1").add(n1);
                n1.set_name(name1);
                boolean n1c = false;
                for (int j = 0; j < dfa_states.size(); j++) {
                    if (dfa_states.get(j).getName().equals(n1.getName())) {
                        n1c = true;
                    }
                }
                if (!n1c) {
                    dfa_states.add(n1);
                    length++;
                }
            }
        }
        dfa+="STATES"+"\n";
        for(State a:dfa_states){
            dfa+= a.getName()+ "\n";
        }
        dfa+="START"+"\n"+dfa_states.get(0).getName()+"\n";
        dfa+="FINAL"+"\n";
        for(State a:dfa_states){
            if(a.is_final()) {
                dfa+=(a.getName())+"\n";
            }
        }
        dfa+="TRANSITIONS"+"\n";
        for(State a:dfa_states){
            for(State ss: a.get_transition().get("0")){
                dfa+= a.getName()+" "+"0"+" "+ss.getName()+"\n";
            }
            for(State ss: a.get_transition().get("1")){
                dfa+= a.getName()+" "+"1"+" "+ss.getName()+"\n";
            }
        }
        dfa+="END";
        System.out.println(dfa);
    }
}
