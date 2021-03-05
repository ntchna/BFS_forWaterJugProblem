package com.company;

import java.io.*;
import java.util.*;
public class Main {
    public static LinkedList<Pour> visited= new LinkedList<Pour>();     //відвідані вершини
    public static Dictionary<String, String> backLink = new Hashtable<String,String>();
    public static Queue<Pour> q = new LinkedList<Pour>();   //черга
    public static Stack<String> sol = new Stack<String>();  //розвязок
    public static int jugOneMax;    // максимальний об'єм першого відра
    public static int jugTwoMax;    // максимальний об'єм другого відра
    public static int fill;     // шуканий об'єм


    public static void main(String args[])throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("_______________________________________\n");
        System.out.print("Water Jug Problem Solution By BFS\n");
        System.out.print("_______________________________________\n");
        System.out.print("Enter volume of jug one :   ");
        jugOneMax = Integer.parseInt(br.readLine());
        System.out.print("Enter volume of jug two :   ");
        jugTwoMax = Integer.parseInt(br.readLine());
        System.out.print("Enter volume to fill :   ");
        fill = Integer.parseInt(br.readLine());

        Pour initial = new Pour(0,0);
        q.add(initial);
        try{
            generateEnque();}
        catch(Exception e){
            System.out.println("No Solution exists");
        }
    }


    // Позначте поточний вузол як відвіданий
    public static void generateEnque(){
        Pour p = q.poll();
        if(!isVisited(p)){
            visited.add(p);
            generateStates(p);
        }
        generateEnque();
    }

    public static void generateStates(Pour p){
        //наповнюємо відра
        System.out.print("Children of ("+p.jugOne+","+p.jugTwo+") are: ");
        if(p.jugOne < jugOneMax)    // перше відро наповнене повністю
        {
            Pour pNew = new Pour(jugOneMax,p.jugTwo);
            q.add(pNew);
            System.out.print(" ("+pNew.jugOne+","+pNew.jugTwo+")");
            if(backLink.get("("+pNew.jugOne+","+pNew.jugTwo+")")==null)
            {
                backLink.put("("+pNew.jugOne+","+pNew.jugTwo+")", "("+p.jugOne+","+p.jugTwo+")");
                checkIfSolution(pNew);
            }
        }

        if(p.jugTwo < jugTwoMax)    // руге відро наповнене не повністю
        {
            Pour pNew = new Pour(p.jugOne,jugTwoMax);
            q.add(pNew);
            System.out.print(" ("+pNew.jugOne+","+pNew.jugTwo+")");
            if(backLink.get("("+pNew.jugOne+","+pNew.jugTwo+")")==null)
            {
                backLink.put("("+pNew.jugOne+","+pNew.jugTwo+")", "("+p.jugOne+","+p.jugTwo+")");
                checkIfSolution(pNew);
            }
        }

        if(p.jugOne>0)  // перше відро не пусте
        {
            Pour pNew = new Pour(0,p.jugTwo);
            q.add(pNew);
            System.out.print(" ("+pNew.jugOne+","+pNew.jugTwo+")");
            if(backLink.get("("+pNew.jugOne+","+pNew.jugTwo+")")==null)
            {
                backLink.put("("+pNew.jugOne+","+pNew.jugTwo+")", "("+p.jugOne+","+p.jugTwo+")");
                checkIfSolution(pNew);
            }
        }


        if(p.jugTwo>0)  //друге відро не пусте
        {
            Pour pNew = new Pour(p.jugOne,0);q.add(pNew);
            System.out.print(" ("+pNew.jugOne+","+pNew.jugTwo+")");
            if(backLink.get("("+pNew.jugOne+","+pNew.jugTwo+")")==null)
            {
                backLink.put("("+pNew.jugOne+","+pNew.jugTwo+")", "("+p.jugOne+","+p.jugTwo+")");
                checkIfSolution(pNew);
            }
        }

        if(p.jugOne<jugOneMax && p.jugTwo!=0)   // перше відро не повне, друге - не пусте
        {
            if(p.jugOne+p.jugTwo > jugOneMax)   // загальний об'єм більше максимального об'єму першого відра
            {
                int One = jugOneMax;
                int Two = p.jugOne+p.jugTwo - jugOneMax;
                Pour pNew = new Pour(One,Two);
                q.add(pNew);
                System.out.print(" ("+pNew.jugOne+","+pNew.jugTwo+")");
                if(backLink.get("("+pNew.jugOne+","+pNew.jugTwo+")")==null)
                {
                    backLink.put("("+pNew.jugOne+","+pNew.jugTwo+")", "("+p.jugOne+","+p.jugTwo+")");
                    checkIfSolution(pNew);
                }
            }

            else if(p.jugOne+p.jugTwo < jugOneMax)   // загальний об'єм менше максимального об'єму другого відра
            {
                Pour pNew = new Pour(p.jugOne+p.jugTwo,0);
                q.add(pNew);
                System.out.print(" ("+pNew.jugOne+","+pNew.jugTwo+")");
                if(backLink.get("("+pNew.jugOne+","+pNew.jugTwo+")")==null)
                    backLink.put("("+pNew.jugOne+","+pNew.jugTwo+")", "("+p.jugOne+","+p.jugTwo+")");
                checkIfSolution(pNew);
            }
        }

        if(p.jugTwo<jugTwoMax && p.jugOne!=0)       // друге відро не повне, перше - не пусте
        {
            if(p.jugTwo+p.jugOne > jugTwoMax)       // загальний об'єм більше максимального об'єму другого відра
            {
                int Two = jugTwoMax;
                int One = p.jugTwo+p.jugOne - jugTwoMax;
                Pour pNew = new Pour(One,Two);
                System.out.print(" ("+pNew.jugOne+","+pNew.jugTwo+")");
                if(backLink.get("("+pNew.jugOne+","+pNew.jugTwo+")")==null)
                {
                    backLink.put("("+pNew.jugOne+","+pNew.jugTwo+")", "("+p.jugOne+","+p.jugTwo+")");
                    checkIfSolution(pNew);
                }
            }
            else if(p.jugOne+p.jugTwo < jugTwoMax)      // загальний об'єм менше максимального об'єму першого відра
            {
                Pour pNew = new Pour(0,p.jugOne+p.jugTwo);
                q.add(pNew);
                System.out.print(" ("+pNew.jugOne+","+pNew.jugTwo+")");
                if(backLink.get("("+pNew.jugOne+","+pNew.jugTwo+")")==null)
                {
                    backLink.put("("+pNew.jugOne+","+pNew.jugTwo+")", "("+p.jugOne+","+p.jugTwo+")");
                    checkIfSolution(pNew);
                }
            }
        }

        System.out.println();
    }

    public static boolean checkIfSolution(Pour p){      // перевірка рішення
        if(p.jugOne == fill && p.jugTwo==0){
            System.out.print("\n");
            System.out.print("\nSolution\n");
            System.out.print("_______________________________________\n");
            String parent = "("+p.jugOne+","+p.jugTwo+")";

            while(!parent.equals("(0,0)")){

                sol.push(parent);
                parent = backLink.get(parent);
            }

            sol.push("(0,0)");
            for(int i=sol.size()-1;i>=0;i--)
            {
                System.out.println(sol.get(i));
            }
            System.exit(0);}

        return false;
    }

    public static boolean isVisited(Pour p){
        for(Pour check : visited)
        {
            if(p.jugOne==check.jugOne && p.jugTwo==check.jugTwo)
                return true;
        }
        return false;
    }
}