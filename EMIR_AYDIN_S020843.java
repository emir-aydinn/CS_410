// EMİR AYDIN
// S020843

import java.util.*;
import java.io.*;

public class EMIR_AYDIN_S020843 {

    public static void main(String[] args) throws FileNotFoundException {

        File myFile = new File( "G2.txt");
        String CNF="";
        Scanner cfg= new Scanner(myFile);
        ArrayList<String>variables_cfg=new ArrayList<>();
        ArrayList<String>variables_cnf=new ArrayList<>();
        Map<String, ArrayList<String>> trans=new HashMap<String, ArrayList<String>>();

        while(cfg.hasNextLine()){
            CNF+=cfg.next()+"\n";
            String line = cfg.nextLine();
            line=cfg.next();
            while (!line.equals("TERMINAL")){ // non-terminals

                CNF+=(line)+"\n";
                variables_cfg.add(line);
                variables_cnf.add(line);
                trans.put(line,new ArrayList<String>());
                line=cfg.next();
            }
            CNF+="TERMINALS";
            line = cfg.nextLine();
            while (!line.equals("RULES")){// terminals
                CNF+=(line)+"\n";
                line=cfg.next();
            }
            line = cfg.next();
            CNF+="RULES"+"\n";
            while (!line.equals("START")){ //RULES
                String rule_item=line;
                String[] splitted=rule_item.split(":");
                trans.get(splitted[0]).add(splitted[1]);
                line=cfg.next();
            }
            if(line.equals("START")){ // start variable
                line=cfg.next();

            }
        }
        cfg.close();


        String cnf_st="Sn";
        trans.put(cnf_st,new ArrayList<String>());
        trans.get(cnf_st).add("S");
        variables_cnf.add(0,cnf_st);

        int lenght=variables_cnf.size();
        String epsilon="e";

        //Loop for eleminating epsilon values
        for(int i=0;i<lenght;i++){
            String current=variables_cnf.get(i);
            if(trans.get(current).contains(epsilon)){
                int inxe=trans.get(current).indexOf(epsilon);
                trans.get(current).remove(inxe);
                for(int j=0;j<lenght;j++){
                    String other=variables_cnf.get(j);
                    if(trans.get(other).contains(current)){
                        trans.get(other).add(epsilon);

                    }
                }

                int len2=trans.get(current).size();
                for(int k=0;k<lenght;k++){
                    for(int m=0;m<trans.get(variables_cnf.get(k)).size();m++){
                        String s=trans.get(variables_cnf.get(k)).get(m);
                        if(s.contains(current)){
                            String[] subs=s.split(current);
                            for(String u:subs){
                                if(!trans.get(variables_cnf.get(k)).contains(u)){
                                    trans.get(variables_cnf.get(k)).add(u);
                                }

                            }

                        }
                    }
                }
                i=0;
            }
        }

        //Single non-terminal elemination
        for(int i=0;i<lenght;i++){
            String current=variables_cnf.get(i);
            for(int p=0;p<lenght;p++){
                String temp=variables_cnf.get(p);
                if(trans.get(temp).contains(current)){
                    int ind=trans.get(temp).indexOf(current);
                    trans.get(temp).remove(ind);
                    for(int j=0;j<trans.get(current).size();j++){
                        //String other=variables_cnf.get(j);
                        if(!trans.get(temp).contains(trans.get(current).get(j))){
                            trans.get(temp).add(trans.get(current).get(j));
                        }
                    }
                }
            }

        }

        for(int i=0;i<lenght;i++){
            for(String s:trans.get(variables_cnf.get(i))){
                CNF+=(variables_cnf.get(i))+":"+ s +"\n";

            }

        }
        CNF+="START"+"\n"+cnf_st;
        System.out.println(CNF);
    }
}