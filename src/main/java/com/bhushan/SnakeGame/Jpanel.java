package com.bhushan.SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

@SuppressWarnings("serial")
public class Jpanel extends JPanel implements ActionListener {
      static final int width = 1200;
      static final int height = 600;
      int unit = 50;
      boolean flag = false;
      Random random;
      int fx;
      int fy;
      Timer timer;
      int length =3;
      int [] xsnake = new int[288];
      int [] ysnake = new int[288];
      int score;
      char dir = 'R';

    Jpanel(){
       this.setPreferredSize(new Dimension(width,height));
       this.setBackground(Color.black);
       random = new Random();
       this.setFocusable(true);
       this.addKeyListener(new key());
       gamestart();


      }
      public void gamestart(){

        spawnfood();
        flag = true;
        timer = new Timer(160, this);
        timer.start();



      }
      public void spawnfood(){
        fx = random.nextInt(width/unit)*50;
        fy = random.nextInt(height/unit)*50;
      }
      public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        draw(graphics);
      }
      public void draw(Graphics graphics){
        if(flag){
            //draw a food partical
            graphics.setColor(Color.yellow);
            graphics.fillOval(fx,fy,unit,unit);

            // draw a snake
            for(int i = 0; i < length; i++){
                graphics.setColor(Color.green);
                graphics.fillRect(xsnake[i],ysnake[i],unit,unit);
            }

            //use for drawing score
            graphics.setColor(Color.cyan);
            graphics.setFont(new Font("Comic Sans MS",Font.BOLD,40));
            FontMetrics fme = getFontMetrics(graphics.getFont());
            graphics.drawString("Score : "+ score,(width-fme.stringWidth("Score : "+ score))/2,graphics.getFont().getSize());

        }else{
            //to display score
            graphics.setColor(Color.cyan);
            graphics.setFont(new Font("Comic Sans MS",Font.BOLD,40));
            FontMetrics fme = getFontMetrics(graphics.getFont());
            graphics.drawString("Score : "+ score,(width-fme.stringWidth("Score : "+ score))/2,graphics.getFont().getSize());

            // to display string
            graphics.setColor(Color.red);
            graphics.setFont(new Font("Comic Sans MS",Font.BOLD,80));
            fme = getFontMetrics(graphics.getFont());
            graphics.drawString("Game Over ! "+ score,(width-fme.stringWidth("Game Over ! "+ score))/2,height/2);

            //use to display string
            graphics.setColor(Color.green);
            graphics.setFont(new Font("Comic Sans MS",Font.BOLD,40));
            fme = getFontMetrics(graphics.getFont());
            graphics.drawString("Press R to Replay "+ score,(width-fme.stringWidth("Press R to Replay "+ score))/2,height/2+150);
        }
      }
      public void move(){
        for(int i = length; i > 0;i--){
            xsnake[i] = xsnake[i-1];
            ysnake[i] = ysnake[i-1];
        }
        switch(dir){
            case 'R':xsnake[0] = xsnake[0] + unit;
            break;
            case 'L':xsnake[0] = xsnake[0] - unit;
            break;
            case 'U':ysnake[0] = ysnake[0] - unit;
            break;
            case 'D':ysnake[0] = ysnake[0] + unit;

        }
      }
      public void foodeaten(){
        if((xsnake[0] == fx) && (ysnake[0] == fy)){
            length++;
            score++;
            spawnfood();
        }
      }
      public void checkit(){
        if(ysnake[0]  < 0){
           flag = false;
          }
          if(ysnake[0]  > 600){
              flag = false;
          }
          if(xsnake[0]  < 0){
              flag = false;
          }
          if(xsnake[0]  > 1200){
              flag = false;
          }
          for(int i = length; i > 0; i--){
              if((xsnake[0] == xsnake[i]) && (ysnake[0] == ysnake[i] )){
                  flag = false;
              }
          }
          if(flag == false){
              timer.stop();
          }
      }

      public class key extends KeyAdapter{
        public void keyPressed(KeyEvent e){
         switch (e.getKeyCode()){
             case KeyEvent.VK_LEFT:
                 if(dir != 'R'){
                     dir = 'L';
                 }
                 break;
             case KeyEvent.VK_RIGHT:
                 if(dir != 'L'){
                     dir = 'R';
                 }
                 break;
             case KeyEvent.VK_UP:
                 if(dir != 'D'){
                     dir = 'U';
                 }
                 break;
             case KeyEvent.VK_DOWN:
                 if(dir != 'U'){
                     dir = 'D';
                 }
                 break;
             case KeyEvent.VK_R:
                 score = 0;
                 length = 3;
                 dir = 'R';
                 Arrays.fill(xsnake,0);
                 Arrays.fill(ysnake,0);
                 gamestart();

         }
        }
      }
        public void actionPerformed(ActionEvent evt){
           if(flag){
            move();
            foodeaten();
            checkit();
          }

          repaint();
    }

}