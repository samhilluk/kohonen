import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Label;
import java.awt.List;
import java.awt.TextComponent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Copyright 1999 Sam Hill. All rights reserved. www.samhill.co.uk

class applet$Listener
  extends MouseAdapter
{
  private final applet this$0;
  
  applet$Listener(applet paramapplet)
  {
    this.this$0 = paramapplet;this.this$0 = paramapplet;
  }
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    Component localComponent = paramMouseEvent.getComponent();
    boolean bool = false;
    if (this.this$0.homebut == localComponent) {
      this.this$0.getAppletContext().showDocument(this.this$0.str2url("http://www.samhill.co.uk/"), "_blank");
    }
    if (this.this$0.initbut == localComponent)
    {
      this.this$0.lab1.setText("Creating a brain....");
      bool = applet.access$0(this.this$0).makebrain(applet.access$1(this.this$0, this.this$0.sizebox.getText()), applet.access$1(this.this$0, this.this$0.inputbox.getText()), 
        applet.access$1(this.this$0, this.this$0.neighbox.getText()), applet.access$2(this.this$0, applet.access$1(this.this$0, this.this$0.inputbox.getText()) - 1));
      applet.access$4(this.this$0, applet.access$1(this.this$0, this.this$0.sizebox.getText()));
      this.this$0.lab1.setText("Brain created.");
      this.this$0.drawnet();
      this.this$0.actionbut.setEnabled(true);
      this.this$0.mastbut.setEnabled(true);
      this.this$0.learnbut.setEnabled(true);
    }
    String str1;
    int j;
    int k;
    int m;
    if (this.this$0.learnbut == localComponent)
    {
      applet.access$0(this.this$0).clearall();
      str1 = "";
      for (j = 0; j < 15; j++)
      {
        for (k = 0; k < 5; k++) {
          for (m = 0; m < this.this$0.resultlis.getItemCount(); m++)
          {
            str1 = this.this$0.resultlis.getItem(m);
            this.this$0.lab1.setText("Training... " + str1);
            applet.access$0(this.this$0).enterbrain(str1);
            applet.access$0(this.this$0).brainiteration();
            applet.access$0(this.this$0).clearinput();
            applet.access$0(this.this$0).decreasegain(new Float("0.02").floatValue());
          }
        }
        this.this$0.drawnet();
        applet.access$0(this.this$0).decreaseneigh();
      }
      this.this$0.lab1.setText("Training Complete...");
      this.this$0.drawnet();
      this.this$0.lab1.setText("Developing Memory Map...");
      
      str1 = this.this$0.resultlis.getItem(0);
      this.this$0.outstring.setText(str1);
      this.this$0.outrequest.setText(str1 + " replies with:");
      this.this$0.outv.setVisible(true);
      applet.access$6(this.this$0, 0);
      this.this$0.lab1.setText("Memory map and Training complete...");
    }
    if (this.this$0.setout == localComponent)
    {
      str1 = "";
      
      str1 = this.this$0.resultlis.getItem(applet.access$5(this.this$0));
      applet.access$0(this.this$0).clearinput();
      this.this$0.lab1.setText("Plotting... " + str1);
      applet.access$0(this.this$0).enterbrain(str1);
      applet.access$0(this.this$0).runnetwork();
      j = applet.access$0(this.this$0).getwinx();
      k = applet.access$0(this.this$0).getwiny();
      float f1 = applet.access$0(this.this$0).getneuron(j, k);
      String str4 = j + "," + k;
      applet.access$0(this.this$0).addmemory(str4, str1);
      for (m = 0; m < applet.access$3(this.this$0); m++) {
        for (int n = 0; n < applet.access$3(this.this$0); n++)
        {
          float f2 = applet.access$0(this.this$0).getneuron(m, n);
          if (f2 < f1 + 20.0F)
          {
            str4 = m + "," + n;
            applet.access$0(this.this$0).addmemory(str4, this.this$0.outstring.getText());
          }
        }
      }
      this.this$0.outv.setVisible(false); applet 
        tmp844_841 = this.this$0;applet.access$6(tmp844_841, applet.access$5(tmp844_841) + 1);
      if (applet.access$5(this.this$0) < this.this$0.resultlis.getItemCount())
      {
        str1 = this.this$0.resultlis.getItem(applet.access$5(this.this$0));
        this.this$0.outstring.setText(str1);
        this.this$0.outrequest.setText(str1 + " replies with:");
        this.this$0.outv.setVisible(true);
      }
      else
      {
        this.this$0.lab1.setText("Training and Memory mapping complete...");
      }
    }
    if (this.this$0.mastbut == localComponent)
    {
      this.this$0.resultlis.removeAll();
      this.this$0.resultlis.addItem("cabbage");
      this.this$0.resultlis.addItem("sausage");
      this.this$0.resultlis.addItem("hello");
      this.this$0.resultlis.addItem("goodbye");
      this.this$0.actionbut.setLabel("Add to list...");
      this.this$0.resultlab.setText("Words:");
      this.this$0.learnbut.setEnabled(true);
      this.this$0.lab1.setText("Default words added to list...");
    }
    if (this.this$0.resetbut == localComponent)
    {
      this.this$0.lab1.setText("Neural Net - Kohonen Simulation - (c) Copyright 1999 Sam Hill");
      this.this$0.sizebox.setText("50");
      this.this$0.inputbox.setText("10");
      this.this$0.neighbox.setText("25");
      this.this$0.cb1.setState(true);
      this.this$0.actionbut.setLabel("Add to list...");
      this.this$0.actionbut.setEnabled(false);
      this.this$0.learnbut.setEnabled(false);
      this.this$0.mastbut.setEnabled(false);
      applet.access$7(this.this$0, new brain());
      this.this$0.resultlab.setText("Words:");
      this.this$0.resultlis.removeAll();
    }
    if (this.this$0.actionbut == localComponent) {
      if (this.this$0.cb1.getState())
      {
        this.this$0.resultlis.addItem(this.this$0.wordbox.getText());
        this.this$0.wordbox.setText("");
      }
      else
      {
        this.this$0.lab1.setText("Querying Neural Network....");
        applet.access$0(this.this$0).clearinput();
        this.this$0.lab1.setText("Network is responding....");
        applet.access$0(this.this$0).enterbrain(this.this$0.wordbox.getText());
        applet.access$0(this.this$0).runnetwork();
        this.this$0.drawnet();
        int i = applet.access$0(this.this$0).getwinx();
        j = applet.access$0(this.this$0).getwiny();
        this.this$0.plotneuron(i, j);
        String str2 = i + "," + j;
        String str3 = applet.access$0(this.this$0).querymemory(str2);
        this.this$0.lab1.setText("Network Reply: " + str3);
        this.this$0.resultlis.addItem(this.this$0.wordbox.getText() + "->" + str3);
      }
    }
    if (this.this$0.cb1 == localComponent) {
      if (this.this$0.cb1.getState())
      {
        this.this$0.actionbut.setLabel("Query network");
        this.this$0.resultlab.setText("Result:");
        this.this$0.learnbut.setEnabled(false);
        this.this$0.resultlis.removeAll();
      }
      else
      {
        this.this$0.actionbut.setLabel("Add to list...");
        this.this$0.resultlab.setText("Words:");
        this.this$0.learnbut.setEnabled(true);
        this.this$0.resultlis.removeAll();
      }
    }
  }
}
