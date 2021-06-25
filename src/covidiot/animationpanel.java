package covidiot;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;

public class animationpanel extends JPanel implements ActionListener {

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlstudentanel;

    Timer t = new Timer(100, this);
    final StopWatch timer = new StopWatch();

    private int numstudentoints = 50;
    private Student[] student = new Student[20];
    private Paramedic[] paramedic = new Paramedic[20];
    private Postman[] postman = new Postman[20];
    private OfficeWorker[] officeworker = new OfficeWorker[20];
    private int circlesize = 10;
    private int infectdistance = 10;
    private int height = 700;
    private int width = 700;
    private int countDead = 0;
    private int infected = 0;
    private int countImmune = 0;
    private JLabel Dpeople, Ipeople, Immune;
     private int printed=0;
     private int printed2=0;
     private int printedOW=0;
     private int printedP=0;
     private int printedPM=0;
     private int printedS=0;
     private int printedOW2=0;
      private int printedPM2=0;
       private int printedP2=0;
        private int printedS2=0;
        
    private Random r = new Random();

    

    animationpanel(int h, int w) {
        width = w;
        height = h;
        setPreferredSize(new Dimension(width, height));
        Dpeople = new JLabel();
        Ipeople = new JLabel();
        Immune = new JLabel();
        setLocation(height, width);

        for (int i = 0; i < student.length; i++) {
            int x = r.nextInt(width);
            int y = r.nextInt(height);
            student[i] = new Student(x, y);
            student[i].setId(i);
        }
        for (int i = 0; i < paramedic.length; i++) {
            int x = r.nextInt(width);
            int y = r.nextInt(height);
            paramedic[i] = new Paramedic(x, y);
            paramedic[i].setId(i);
            paramedic[i].setAge();
        }
        for (int i = 0; i < postman.length; i++) {
            int x = r.nextInt(width);
            int y = r.nextInt(height);
            postman[i] = new Postman(x, y);
            postman[i].setId(i);
        }
        for (int i = 0; i < officeworker.length; i++) {
            int x = r.nextInt(width);
            int y = r.nextInt(height);
            officeworker[i] = new OfficeWorker(x, y);
            officeworker[i].setId(i);
            officeworker[i].setAge();
        }
        student[1].infected = 1;
        paramedic[1].infected = 1;
        postman[1].infected = 1;
        officeworker[1].infected = 1;
        student[0].infected = 1;
        paramedic[0].infected = 1;
        postman[0].infected = 1;
        officeworker[0].infected = 1;
        infected = 8;
        Dpeople.setForeground(Color.green);
        Ipeople.setForeground(Color.green);
        Immune.setForeground(Color.green);
        Dpeople.setText("Dead: " + countDead);
        Immune.setText("Recover: " + countImmune);
        add(Dpeople);
        add(Ipeople);
        add(Immune);//To change body of generated methods, choose Tools | Templates.
    }

   

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < student.length; i++) {
            student[i].move();
        }
        for (int i = 0; i < paramedic.length; i++) {
            paramedic[i].move();
        }
        for (int i = 0; i < postman.length; i++) {
            postman[i].move();
        }
        for (int i = 0; i < officeworker.length; i++) {
            officeworker[i].move();
        }

        Dpeople.setText("Dead: " + countDead);
        Ipeople.setText("Infected: " + infected);
        Immune.setText("Recover: " + countImmune);
        handlecollision();
        repaint();
         contactTracerToFileStudent();
        contactTracerToFileOfficeWorker();
        contactTracerToFileParamedic();
        contactTracerToFilePostman();
        
        forgetfulContactTracerToFileStudent();
        forgetfulContactTracerToFileOfficeWorker();
        forgetfulContactTracerToFileParamedic();
        forgetfulContactTracerToFilePostman();
        
    }

   public void handlecollision() {

        for (int i = 0; i < student.length; i++) {
            for (int j = 1; j < student.length; j++) {
                int xx = student[i].x - student[j].x;
                int yy = student[i].y - student[j].y;
                double distance = Math.sqrt(xx * xx + yy * yy);

                if (distance < infectdistance) {
                    if (student[i].infected > 0 && !student[j].immune && student[j].infected == 0) {
                        student[j].infected++;
                        infected++;
                       System.out.print(student[i].getId() + " infected " + student[j].getId() + " at " + student[j].x + " " + student[j].y);
                       System.out.println(" - - [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                        contactTracer(student[i], student[j]);
                    }

                    if (student[j].infected > 0 && !student[i].immune && student[i].infected == 0) {
                        student[i].infected++;
                        infected++;
                        System.out.print(student[j].getId() + " infected " + student[i].getId() + " at " + student[i].x + " " + student[i].y);
                        System.out.println(" - - [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                        contactTracer(student[j], student[i]);
                    }
                }
                if (student[i].immune && student[i].immunePrintCount == 0) {
                    infected--;
                    countImmune++;
                    System.out.print(student[i].id + " has recovered");
                    System.out.println(" - - [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                    student[i].immunePrintCount = 1;

                }
                if (student[i].dead && student[i].deathPrintCount == 0) {
                    countDead++;
                    infected--;
                    System.out.print(student[i].id + " is dead");
                    System.out.println(" - - [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                    student[i].deathPrintCount = 1;
                }
            }
        }
        for (int i = 0; i < paramedic.length; i++) {
            for (int j = 1; j < paramedic.length; j++) {
                int xx = paramedic[i].x - paramedic[j].x;
                int yy = paramedic[i].y - paramedic[j].y;
                double distance = Math.sqrt(xx * xx + yy * yy);

                if (distance < infectdistance) {
                    if (paramedic[i].infected > 0 && !paramedic[j].immune && paramedic[j].infected == 0) {
                        paramedic[j].infected++;
                        infected++;
                        System.out.print(paramedic[i].getId() + " infected " + paramedic[j].getId() + " at " + paramedic[j].x + " " + paramedic[j].y);
                        System.out.println(" - - [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                        contactTracer(paramedic[i], paramedic[j]);
                        forgetfulContactTracer(paramedic[i], paramedic[j]);
                    }

                    if (paramedic[j].infected > 0 && !paramedic[i].immune && paramedic[i].infected == 0) {
                        paramedic[i].infected++;
                        infected++;
                        System.out.print(paramedic[j].getId() + " infected " + paramedic[i].getId() + " at " + paramedic[i].x + " " + paramedic[i].y);
                        System.out.println(" - - [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                        contactTracer(paramedic[j], paramedic[i]);
                        forgetfulContactTracer(paramedic[j], paramedic[i]);
                    }
                }
                if (paramedic[i].immune && paramedic[i].immunePrintCount == 0) {
                    infected--;
                    countImmune++;
                    System.out.print(paramedic[i].id + " has recovered");
                    System.out.println(" - - [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                    paramedic[i].immunePrintCount = 1;

                }
                if (paramedic[i].dead && paramedic[i].deathPrintCount == 0) {
                    countDead++;
                    infected--;
                    System.out.print(paramedic[i].id + " is dead");
                    System.out.println(" - - [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                    paramedic[i].deathPrintCount = 1;
                }
            }
        }
        for (int i = 0; i < postman.length; i++) {
            for (int j = 1; j < postman.length; j++) {
                int xx = postman[i].x - postman[j].x;
                int yy = postman[i].y - postman[j].y;
                double distance = Math.sqrt(xx * xx + yy * yy);

                if (distance < infectdistance) {
                    if (postman[i].infected > 0 && !postman[j].immune && postman[j].infected == 0) {
                        postman[j].infected++;
                        infected++;
                        System.out.print(postman[i].getId() + " infected " + postman[j].getId() + " at " + postman[j].x + " " + postman[j].y);
                        System.out.println(" - - [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                        contactTracer(postman[i], postman[j]);
                        forgetfulContactTracer(postman[i], postman[j]);
                    }

                    if (postman[j].infected > 0 && !postman[i].immune && postman[i].infected == 0) {
                        postman[i].infected++;
                        infected++;
                        System.out.print(postman[j].getId() + " infected " + postman[i].getId() + " at " + postman[i].x + " " + postman[i].y);
                        System.out.println(" - - [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                        contactTracer(postman[j], postman[i]);
                        forgetfulContactTracer(postman[j], postman[i]);
                    }
                }
                if (postman[i].immune && postman[i].immunePrintCount == 0) {
                    infected--;
                    countImmune++;
                    System.out.print(postman[i].id + " has recovered");
                    System.out.println(" - - [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                    postman[i].immunePrintCount = 1;

                }
                if (postman[i].dead && postman[i].deathPrintCount == 0) {
                    countDead++;
                    infected--;
                    System.out.print(postman[i].id + " is dead");
                    System.out.println(" - - [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                    postman[i].deathPrintCount = 1;
                }
            }
        }
        for (int i = 0; i < officeworker.length; i++) {
            for (int j = 1; j < officeworker.length; j++) {
                int xx = officeworker[i].x - officeworker[j].x;
                int yy = officeworker[i].y - officeworker[j].y;
                double distance = Math.sqrt(xx * xx + yy * yy);

                if (distance < infectdistance) {
                    if (officeworker[i].infected > 0 && !officeworker[j].immune && officeworker[j].infected == 0) {
                        officeworker[j].infected++;
                        infected++;
                        System.out.print(officeworker[i].getId() + " infected " + officeworker[j].getId() + " at " + officeworker[j].x + " " + officeworker[j].y);
                        System.out.println(" - - [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                        contactTracer(officeworker[i], officeworker[j]);
                        forgetfulContactTracer(officeworker[i], officeworker[j]);
                    }

                    if (officeworker[j].infected > 0 && !officeworker[i].immune && officeworker[i].infected == 0) {
                        officeworker[i].infected++;
                        infected++;
                        System.out.print(officeworker[j].getId() + " infected " + officeworker[i].getId() + " at " + officeworker[i].x + " " + officeworker[i].y);
                        System.out.println(" - - [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                        contactTracer(officeworker[j], officeworker[i]);
                        forgetfulContactTracer(officeworker[j], officeworker[i]);
                    }
                }
                if (officeworker[i].immune && officeworker[i].immunePrintCount == 0) {
                    infected--;
                    countImmune++;
                    System.out.print(officeworker[i].id + " has recovered");
                    System.out.println(" - - [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                    officeworker[i].immunePrintCount = 1;

                }
                if (officeworker[i].dead && officeworker[i].deathPrintCount == 0) {
                    countDead++;
                    infected--;
                    System.out.print(officeworker[i].id + " is dead");
                    System.out.println(" - - [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                    officeworker[i].deathPrintCount = 1;
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
          for(int i=100;i<150;i++){
            for(int j=100;j<150;j++){
                g.setColor(Color.BLACK);
                g.fillRect(i, j, 10, 10);
            }
        }
        for(int i=550;i<600;i++){
            for(int j=150;j<200;j++){
                g.setColor(Color.BLACK);
                g.fillRect(i, j, 10, 10);
            }
        }
        for(int i=500;i<600;i++){
            for(int j=500;j<600;j++){
                g.setColor(Color.BLACK);
                g.fillRect(i, j, 10, 10);
            }
        }
        for(int i=150;i<200;i++){
            for(int j=400;j<500;j++){
                g.setColor(Color.BLACK);
                g.fillRect(i, j, 10, 10);
            }
        }for (int i = 0; i < student.length; i++) {
            if (student[i].dead) {
                g.setColor(Color.blue);
                student[i].immune = false;
                //need to make it dissastudentear or stostudent at least
            } else if (student[i].infected > 0) {
                g.setColor(Color.red);
            } else if (student[i].immune) {
                g.setColor(Color.green);
            } else {
                g.setColor(Color.yellow);
            }
            g.fillOval(student[i].x, student[i].y, circlesize, circlesize);
        }
        for (int i = 0; i < paramedic.length; i++) {
            if (paramedic[i].dead) {
                g.setColor(Color.blue);
                paramedic[i].immune = false;
            } else if (paramedic[i].infected > 0) {
                g.setColor(Color.red);
            } else if (paramedic[i].immune) {
                g.setColor(Color.green);
            } else {
                g.setColor(Color.yellow);
            }
            g.fillArc(paramedic[i].x, paramedic[i].y, circlesize + 15, circlesize + 15, 75, 40);
        }
        for (int i = 0; i < postman.length; i++) {
            if (postman[i].dead) {
                g.setColor(Color.blue);
                postman[i].immune = false;
            } else if (postman[i].infected > 0) {
                g.setColor(Color.red);
            } else if (postman[i].immune) {
                g.setColor(Color.green);
            } else {
                g.setColor(Color.yellow);
            }
            g.fillRect(postman[i].x, postman[i].y, circlesize, circlesize);
        }
        for (int i = 0; i < officeworker.length; i++) {
            if (officeworker[i].dead) {
                g.setColor(Color.blue);
                officeworker[i].immune = false;
            } else if (officeworker[i].infected > 0) {
                g.setColor(Color.red);
            } else if (officeworker[i].immune) {
                g.setColor(Color.green);
            } else {
                g.setColor(Color.WHITE);
            }
            g.fillRect(officeworker[i].x, officeworker[i].y, circlesize, circlesize);
        }
        setBackground(Color.BLUE);
        t.start();
    }

   private void contactTracer(Student infector,Student infected) {//
     infector.person.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y )+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
//               System.out.println(p[i].child); 
//           
      
         
    }

    private void contactTracer(Paramedic infector, Paramedic infected) {//
infector.person.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
    }

    private void contactTracer(Postman infector, Postman infected) {//
infector.person.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
    }

    private void contactTracer(OfficeWorker infector, OfficeWorker infected) {//
infector.person.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
    }


         private void forgetfulContactTracer(Paramedic infector, Paramedic infected) {//
       if(r.nextInt(2)==0){    
        if(infector.getAge()=="Child"){
            if(r.nextInt(101)>r.nextInt(31)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
            }
            
        }
        if(infector.getAge()=="Adult"){
            if(r.nextInt(101)>r.nextInt(51)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
            }
            if(infector.getAge()=="Senior Citizen"){
            if(r.nextInt(101)>r.nextInt(71)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
               infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
            }
    }
      
      
        }
       }
       else if(r.nextInt(2)==1){
           
        if(infector.getAge()=="Child"){
            if(r.nextInt(101)>r.nextInt(31)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }
            
        }
        if(infector.getAge()=="Adult"){
            if(r.nextInt(101)>r.nextInt(51)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }
            if(infector.getAge()=="Senior Citizen"){
            if(r.nextInt(101)>r.nextInt(71)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }   
           
            }
           
       }
       
       }
       else{
           
           if(infector.getAge()=="Child"){
            if(r.nextInt(101)>r.nextInt(31)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }
            
        }
        if(infector.getAge()=="Adult"){
            if(r.nextInt(101)>r.nextInt(51)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }
            if(infector.getAge()=="Senior Citizen"){
            if(r.nextInt(101)>r.nextInt(71)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }   
           
            }
           
       }
           
       }
    }
       
    

          private void forgetfulContactTracer(Postman infector, Postman infected) {//
       if(r.nextInt(2)==0){    
        if(infector.getAge()=="Child"){
            if(r.nextInt(101)>r.nextInt(31)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
            }
            
        }
        if(infector.getAge()=="Adult"){
            if(r.nextInt(101)>r.nextInt(51)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
            }
            if(infector.getAge()=="Senior Citizen"){
            if(r.nextInt(101)>r.nextInt(71)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
               infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
            }
    }
      
      
        }
       }
       else if(r.nextInt(2)==1){
           
        if(infector.getAge()=="Child"){
            if(r.nextInt(101)>r.nextInt(31)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }
            
        }
        if(infector.getAge()=="Adult"){
            if(r.nextInt(101)>r.nextInt(51)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }
            if(infector.getAge()=="Senior Citizen"){
            if(r.nextInt(101)>r.nextInt(71)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }   
           
            }
           
       }
       
       }
       else{
           
           if(infector.getAge()=="Child"){
            if(r.nextInt(101)>r.nextInt(31)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }
            
        }
        if(infector.getAge()=="Adult"){
            if(r.nextInt(101)>r.nextInt(51)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }
            if(infector.getAge()=="Senior Citizen"){
            if(r.nextInt(101)>r.nextInt(71)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }   
           
            }
           
       }
           
       }
    }

            private void forgetfulContactTracer(OfficeWorker infector, OfficeWorker infected) {//
       if(r.nextInt(2)==0){    
        if(infector.getAge()=="Child"){
            if(r.nextInt(101)>r.nextInt(31)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
            }
            
        }
        if(infector.getAge()=="Adult"){
            if(r.nextInt(101)>r.nextInt(51)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
            }
            if(infector.getAge()=="Senior Citizen"){
            if(r.nextInt(101)>r.nextInt(71)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
               infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
            }
    }
      
      
        }
       }
       else if(r.nextInt(2)==1){
           
        if(infector.getAge()=="Child"){
            if(r.nextInt(101)>r.nextInt(31)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }
            
        }
        if(infector.getAge()=="Adult"){
            if(r.nextInt(101)>r.nextInt(51)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }
            if(infector.getAge()=="Senior Citizen"){
            if(r.nextInt(101)>r.nextInt(71)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }   
           
            }
           
       }
       
       }
       else{
           
           if(infector.getAge()=="Child"){
            if(r.nextInt(101)>r.nextInt(31)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }
            
        }
        if(infector.getAge()=="Adult"){
            if(r.nextInt(101)>r.nextInt(51)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }
            if(infector.getAge()=="Senior Citizen"){
            if(r.nextInt(101)>r.nextInt(71)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }   
           
            }
           
       }
           
       }
    }
        
         private void forgetfulContactTracer(Student infector, Student infected) {//
       if(r.nextInt(2)==0){    
        if(infector.getAge()=="Child"){
            if(r.nextInt(101)>r.nextInt(31)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
            }
            
        }
        if(infector.getAge()=="Adult"){
            if(r.nextInt(101)>r.nextInt(51)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
            }
            if(infector.getAge()=="Senior Citizen"){
            if(r.nextInt(101)>r.nextInt(71)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
               infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
            }
    }
      
      
        }
       }
       else if(r.nextInt(2)==1){
           
        if(infector.getAge()=="Child"){
            if(r.nextInt(101)>r.nextInt(31)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }
            
        }
        if(infector.getAge()=="Adult"){
            if(r.nextInt(101)>r.nextInt(51)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }
            if(infector.getAge()=="Senior Citizen"){
            if(r.nextInt(101)>r.nextInt(71)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" but "+"  forgot the location"+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }   
           
            }
           
       }
       
       }
       else{
           
           if(infector.getAge()=="Child"){
            if(r.nextInt(101)>r.nextInt(31)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }
            
        }
        if(infector.getAge()=="Adult"){
            if(r.nextInt(101)>r.nextInt(51)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }
            if(infector.getAge()=="Senior Citizen"){
            if(r.nextInt(101)>r.nextInt(71)){
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + timer.getElapsedDay() + " ; Time: " + timer.getElapsedHour() + ":" + timer.getElapsedMinute() + "]");
                
            }
            else{
                infector.forgetfulPerson.addNode(Integer.toString(infected.id)+" at "+Integer.toString(infected.x)+" "+Integer.toString(infected.y)+ " [Day: " + "(this person forgot the day of contact)" + "]");
            }   
           
            }
           
       }
           
       }
    }
         
        
        
        
        
        
        
        
        
    
        private void contactTracerToFileStudent(){//Student
         if(infected==0&&printedS==0){
          for(int i=0;i<student.length;i++){
              if(!(student[i].person.isEmpty())){
                          System.out.println("people "+student[i].id+ " has infected :\n");                                           
                        student[i].person.showList();
                 
                  
                  try {
                      ToFile("people "+student[i].id+ " has infected :\n",true);
                  } catch (IOException ex) {
                      Logger.getLogger(animationpanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
                  try {
                      ToFile(student[i].person.showList2(),true);
                  } catch (IOException ex) {
                      Logger.getLogger(animationpanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
                                    
                    System.out.println("\n");
              }
//               System.out.println(p[i].child); 
//           
      
          }
//           graph.showGraph();
           printedS++;
       }
    }
        
      private void contactTracerToFileParamedic(){//Student
         if(infected==0&&printedP==0){
          for(int i=0;i<paramedic.length;i++){
              if(!(paramedic[i].person.isEmpty())){
                          System.out.println("people "+paramedic[i].id+ " has infected :\n");                                           
                        paramedic[i].person.showList();
                 
                  
                  try {
                      ToFile("people "+paramedic[i].id+ " has infected :\n",true);
                  } catch (IOException ex) {
                      Logger.getLogger(animationpanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
                  try {
                      ToFile(paramedic[i].person.showList2(),true);
                  } catch (IOException ex) {
                      Logger.getLogger(animationpanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
                                    
                    System.out.println("\n");
              }
//               System.out.println(p[i].child); 
//           
      
          }
//           graph.showGraph();
           printedP++;
       }
    }
      
      
        
        
        
        private void contactTracerToFilePostman(){//
         if(infected==0&&printedPM==0){
          for(int i=0;i<postman.length;i++){
              if(!(postman[i].person.isEmpty())){
                          System.out.println("people "+postman[i].id+ " has infected :\n");                                           
                        postman[i].person.showList();
                 
                  
                  try {
                      ToFile("people "+postman[i].id+ " has infected :\n",true);
                  } catch (IOException ex) {
                      Logger.getLogger(animationpanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
                  try {
                      ToFile(postman[i].person.showList2(),true);
                  } catch (IOException ex) {
                      Logger.getLogger(animationpanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
                                    
                    System.out.println("\n");
              }
//               System.out.println(p[i].child); 
//           
      
          }
//           graph.showGraph();
           printedPM++;
       }
    } 
        
    

        
        
        
        
  
    
    
    
     private void contactTracerToFileOfficeWorker(){
         if(infected==0&&printedOW==0){
          for(int i=0;i<officeworker.length;i++){//need to change based on initial id
              if(!(officeworker[i].person.isEmpty())){
                          System.out.println("people "+officeworker[i].id+ " has infected :\n");                                           
                        officeworker[i].person.showList();
                 
                  
                  try {
                      ToFile("people "+officeworker[i].id+ " has infected :\n",true);
                  } catch (IOException ex) {
                      Logger.getLogger(animationpanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
                  try {
                      ToFile(officeworker[i].person.showList2(),true);
                  } catch (IOException ex) {
                      Logger.getLogger(animationpanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
                                    
                    System.out.println("\n");
              }
//               System.out.println(p[i].child); 
//           
      
          }
//           graph.showGraph();
           printedOW++;
       }
    }
     
     
       private void forgetfulContactTracerToFilePostman(){
         if(infected==0&&printedPM2==0){
          for(int i=0;i<postman.length;i++){//need to change to initial id 
              if(!(postman[i].forgetfulPerson.isEmpty())){
                         // System.out.println("people "+p[i].id+ " has infected :\n");                                           
                       // p[i].person.showList();
                 
                  
                  try {
                      ToFile2("people "+postman[i].id+ " has infected :\n",true);
                  } catch (IOException ex) {
                      Logger.getLogger(animationpanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
                  try {
                      ToFile2(postman[i].person.showList2(),true);
                  } catch (IOException ex) {
                      Logger.getLogger(animationpanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
                                    
                    System.out.println("\n");
              }
//               System.out.println(p[i].child); 
//           
      
          }
//           graph.showGraph();
           printedPM2++;
       }
    }
       
       
       private void forgetfulContactTracerToFileParamedic(){
         if(infected==0&&printedP2==0){
          for(int i=0;i<paramedic.length;i++){//need to change to initial id 
              if(!(paramedic[i].forgetfulPerson.isEmpty())){
                         // System.out.println("people "+p[i].id+ " has infected :\n");                                           
                       // p[i].person.showList();
                 
                  
                  try {
                      ToFile2("people "+paramedic[i].id+ " has infected :\n",true);
                  } catch (IOException ex) {
                      Logger.getLogger(animationpanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
                  try {
                      ToFile2(paramedic[i].person.showList2(),true);
                  } catch (IOException ex) {
                      Logger.getLogger(animationpanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
                                    
                    System.out.println("\n");
              }
//               System.out.println(p[i].child); 
//           
      
          }
//           graph.showGraph();
           printedP2++;
       }
    }
       
         private void forgetfulContactTracerToFileStudent(){
         if(infected==0&&printedS2==0){
          for(int i=0;i<student.length;i++){//need to change to initial id 
              if(!(student[i].forgetfulPerson.isEmpty())){
                         // System.out.println("people "+p[i].id+ " has infected :\n");                                           
                       // p[i].person.showList();
                 
                  
                  try {
                      ToFile2("people "+student[i].id+ " has infected :\n",true);
                  } catch (IOException ex) {
                      Logger.getLogger(animationpanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
                  try {
                      ToFile2(student[i].person.showList2(),true);
                  } catch (IOException ex) {
                      Logger.getLogger(animationpanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
                                    
                    System.out.println("\n");
              }
//               System.out.println(p[i].child); 
//           
      
          }
//           graph.showGraph();
           printedS2++;
       }
    }
         
         private void forgetfulContactTracerToFileOfficeWorker(){
         if(infected==0&&printedOW2==0){
          for(int i=0;i<officeworker.length;i++){//need to change to initial id 
              if(!(officeworker[i].forgetfulPerson.isEmpty())){
                         // System.out.println("people "+p[i].id+ " has infected :\n");                                           
                       // p[i].person.showList();
                 
                  
                  try {
                      ToFile2("people "+officeworker[i].id+ " has infected :\n",true);
                  } catch (IOException ex) {
                      Logger.getLogger(animationpanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
                  try {
                      ToFile2(officeworker[i].person.showList2(),true);
                  } catch (IOException ex) {
                      Logger.getLogger(animationpanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
                                    
                    System.out.println("\n");
              }
//               System.out.println(p[i].child); 
//           
      
          }
//           graph.showGraph();
           printedOW2++;
       }
    }
         
       
       
       
    
  public void ToFile(String content, boolean append) throws IOException {
  // append - true for writing to the end of the file rather to the beginning
  
  
  try ( PrintWriter writer = new PrintWriter(new FileWriter("./log.txt", append))) {
      writer.flush();
    writer.print(content);
    
  }
  
  
}

    public void ToFile2(String content, boolean append) throws IOException {
        try ( PrintWriter writer2 = new PrintWriter(new FileWriter("./forgetfulLog.txt", append))) {
      writer2.flush();
    writer2.print(content);
    
  }
    }

    private void contactTracerToFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
    


