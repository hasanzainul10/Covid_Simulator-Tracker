package covidiot;

import java.util.Random;

public class Student {
    public int x;
    public int y;
    public boolean immune = false;
    public int infected = 0;
    public int velocityx = 0;
    public int velocityy = 0;
    public int infectedduration = 150;
    private int height = 700;
    private int width = 700;
    public Random r = new Random();
    int id = 0;
    boolean dead = false;
    public int death = 0;
    public int deathPrintCount = 0;
    public int immunePrintCount = 0;
     LinkedList<String> person=new LinkedList<String>();
   LinkedList<String> forgetfulPerson=new LinkedList<String>();
   public int rebeliousness=r.nextInt(100);
   public int forgetfulness=r.nextInt(100);
   private String age="Child";

    public Student(int x, int y) {
        this.x = x;
        this.y = y;

          velocityx = 5;
        velocityy = 5;
    }

    public int setId(int i) {
        id = i;
        return id;
    }

    public int getId() {
        return id;
    }

    public void move() {
        if (!dead) {
            x += velocityx;
            y += velocityy;

        for(int i=145;i<=155;i++){
               for(int j=395;j<=505;j++)
               {
                  
                        if ((x==i&&y==j)) 
                        {
                            velocityx = -velocityx;
                            velocityy = -velocityy;
                        }
                                 }       
            } 
            for(int i=195;i<=205;i++){
               for(int j=395;j<=505;j++)
               {
                 if(!((j>405) && (j<435)))
                   {
                        if ((x==i&&y==j)) 
                        {
                            velocityx = -velocityx;
                            velocityy = -velocityy;
                        }
                   
                   else
                       continue; 
                }       
            } }
           
            
            
            for(int i=145;i<=205;i++){
               for(int j=395;j<=405;j++)
               {
               
                        if ((x==i&&y==j)) 
                        {
                            velocityx = -velocityx;
                            velocityy = -velocityy;
                        }
                   }
            }               
            for(int i=145;i<=205;i++){
               for(int j=495;j<=505;j++)
               {
                   if ((x==i&&y==j)) {
                    velocityx = -velocityx;
                    velocityy = -velocityy;
                    }  
                }       
            }
            for(int i=495;i<=505;i++){
               for(int j=495;j<=605;j++)
               {
                  
                        if ((x==i&&y==j)) 
                        {
                            velocityx = -velocityx;
                            velocityy = -velocityy;
                        }
                                 }       
            } 
            for(int i=595;i<=605;i++){
               for(int j=495;j<=605;j++)
               {
                   if ((x==i&&y==j)) {
                    velocityx = -velocityx;
                    velocityy = -velocityy;
                    }  
                }       
            } 
           
            
            
            for(int i=495;i<=605;i++){
               for(int j=495;j<=505;j++)
               {
                 if(!((i>500) && (i<530)))
                   {
                        if ((x==i&&y==j)) 
                        {
                            velocityx = -velocityx;
                            velocityy = -velocityy;
                        }
                   }
                   else
                       continue;                }       
            }for(int i=495;i<=605;i++){
               for(int j=595;j<=605;j++)
               {
                   if ((x==i&&y==j)) {
                    velocityx = -velocityx;
                    velocityy = -velocityy;
                    }  
                }       
            }
            
            for(int i=545;i<=555;i++){
               for(int j=145;j<=205;j++)
               {
                   if(!((j>150) && (j<180)))
                   {
                        if ((x==i&&y==j)) 
                        {
                            velocityx = -velocityx;
                            velocityy = -velocityy;
                        }
                   }
                   else
                       continue; 
                }       
            } 
            for(int i=595;i<=605;i++){
               for(int j=145;j<=205;j++)
               {
                   if ((x==i&&y==j)) {
                    velocityx = -velocityx;
                    velocityy = -velocityy;
                    }  
                }       
            } 
            
            
            
            for(int i=545;i<=605;i++){
               for(int j=145;j<=155;j++)
               {
                   if ((x==i&&y==j)) {
                    velocityx = -velocityx;
                    velocityy = -velocityy;
                    }  
                }       
            }for(int i=545;i<=605;i++){
               for(int j=195;j<=205;j++)
               {
                   if ((x==i&&y==j)) {
                    velocityx = -velocityx;
                    velocityy = -velocityy;
                    }  
                }       
            }
            
            for(int i=95;i<=105;i++){
               for(int j=95;j<=155;j++)
               {
                   if ((x==i&&y==j)) {
                    velocityx = -velocityx;
                    velocityy = -velocityy;
                    }  
                }       
            } 
           
            for(int j=95;j<=105;j++){
               for(int i=95;i<=155;i++)
               {
                    if ((x==i&&y==j)) 
                    {
                        velocityx = -velocityx;
                        velocityy = -velocityy;
                    }  
                }       
            } 
            
            for(int i=145;i<=155;i++){
               for(int j=95;j<=155;j++)
               {
                   if ((x==i&&y==j)) {
                    velocityx = -velocityx;
                    velocityy = -velocityy;
                    }  
                }       
            } 
           
            for(int j=145;j<=155;j++){
               for(int i=95;i<=155;i++)
               {
                   if(!((i>110) && (i<140)))
                   {
                        if ((x==i&&y==j)) 
                        {
                            velocityx = -velocityx;
                            velocityy = -velocityy;
                        }
                   }
                   else
                       continue;      
                }       
            }
           if (x > (width) || x < 0) {// bounce when touch x boundary
                velocityx = -velocityx;
            }
            if (y > (height) || y < 0) {
                velocityy = -velocityy;
            }
            if (infected > 0) {
                infected++;
            }
            if (infected > infectedduration) {// bounce when touch x boundary
                death();
                if (death > 10) {
                    dead = true;
                } else {
                    infected = 0;
                    immune = true;
                }
            }
        } else {
            x = x + 1000;//move the dead to graeyard
            y = y + 1000;

            if (x > width || x < 0 && !dead) {
                velocityx = -velocityx;
            }
            if (y > height || y < 0 && !dead) {
                velocityy = -velocityy;
            }

            if (x > width || x < 0 && dead) {
                velocityx = velocityx;
                x = 1000;
                y = 1000;
            }
            if (y > height || y < 0 && dead) {
                velocityy = velocityy;
                x = +1000;
                y = +1000;
            }

            if (infected > 0) {
                infected++;
            }
            if (infected > infectedduration) {
                death();//death will control the chance of dying 
                if (death > 10) {
                    dead = true;
                }
                if (dead) {//so the dead wont get immunity or infected..Should we make it infectous?
                    infected = 0;
                    immune = false;

                } else {
                    infected = 0;
                    immune = true;

                }
            }
        }
    }

    private int death() {
        return death = r.nextInt(15);
    }
    public String getAge(){
        return age;
    }
}
