import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

// Copyright 1999 Sam Hill. All rights reserved. www.samhill.co.uk

public class applet
  extends Applet
{
  public Button resetbut;
  public Button initbut;
  public Button homebut;
  public Button mastbut;
  public Button actionbut;
  public Button learnbut;
  public Button setout;
  public Label lab1;
  public Label neighlab;
  public Label inputlab;
  public Label sizelab;
  public Label wordlab;
  public Label resultlab;
  public Label outrequest;
  public TextField neighbox;
  public TextField inputbox;
  public TextField sizebox;
  public TextField wordbox;
  public TextField outstring;
  public Panel pl = new Panel();
  public Panel statpl = new Panel();
  public Panel datapl = new Panel();
  public Panel outv = new Panel();
  public List resultlis;
  public Checkbox cb1 = new Checkbox("Training mode", true);
  private brain mybrain = new brain();
  private int netsize = 50;
  private int dealing;
  
  private int str2int(String paramString)
  {
    try
    {
      return Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
    return 0;
  }
  
  private float int2float(int paramInt)
  {
    Integer localInteger = new Integer(paramInt);
    return localInteger.floatValue();
  }
  
  public URL str2url(String paramString)
  {
    URL localURL;
    try
    {
      localURL = new URL(getDocumentBase(), paramString);
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localURL = null;
    }
    return localURL;
  }
  
  class Listener
    extends MouseAdapter
  {
    public void mousePressed(MouseEvent paramMouseEvent)
    {
      Component localComponent = paramMouseEvent.getComponent();
      boolean bool = false;
      if (applet.this.homebut == localComponent) {
        applet.this.getAppletContext().showDocument(applet.this.str2url("http://www.samhill.co.uk/"), "_blank");
      }
      if (applet.this.initbut == localComponent)
      {
        applet.this.lab1.setText("Creating a brain....");
        bool = applet.this.mybrain.makebrain(applet.this.str2int(applet.this.sizebox.getText()), applet.this.str2int(applet.this.inputbox.getText()), 
          applet.this.str2int(applet.this.neighbox.getText()), applet.this.int2float(applet.access$1(applet.this, applet.this.inputbox.getText()) - 1));
        applet.this.netsize = applet.this.str2int(applet.this.sizebox.getText());
        applet.this.lab1.setText("Brain created.");
        applet.this.drawnet();
        applet.this.actionbut.setEnabled(true);
        applet.this.mastbut.setEnabled(true);
        applet.this.learnbut.setEnabled(true);
      }
      String str1;
      int j;
      int k;
      int m;
      if (applet.this.learnbut == localComponent)
      {
        applet.this.mybrain.clearall();
        str1 = "";
        for (j = 0; j < 15; j++)
        {
          for (k = 0; k < 5; k++) {
            for (m = 0; m < applet.this.resultlis.getItemCount(); m++)
            {
              str1 = applet.this.resultlis.getItem(m);
              applet.this.lab1.setText("Training... " + str1);
              applet.this.mybrain.enterbrain(str1);
              applet.this.mybrain.brainiteration();
              applet.this.mybrain.clearinput();
              applet.this.mybrain.decreasegain(new Float("0.02").floatValue());
            }
          }
          applet.this.drawnet();
          applet.this.mybrain.decreaseneigh();
        }
        applet.this.lab1.setText("Training Complete...");
        applet.this.drawnet();
        applet.this.lab1.setText("Developing Memory Map...");
        
        str1 = applet.this.resultlis.getItem(0);
        applet.this.outstring.setText(str1);
        applet.this.outrequest.setText(str1 + " replies with:");
        applet.this.outv.setVisible(true);
        applet.this.dealing = 0;
        applet.this.lab1.setText("Memory map and Training complete...");
      }
      if (applet.this.setout == localComponent)
      {
        str1 = "";
        
        str1 = applet.this.resultlis.getItem(applet.this.dealing);
        applet.this.mybrain.clearinput();
        applet.this.lab1.setText("Plotting... " + str1);
        applet.this.mybrain.enterbrain(str1);
        applet.this.mybrain.runnetwork();
        j = applet.this.mybrain.getwinx();
        k = applet.this.mybrain.getwiny();
        float f1 = applet.this.mybrain.getneuron(j, k);
        String str4 = j + "," + k;
        applet.this.mybrain.addmemory(str4, str1);
        for (m = 0; m < applet.this.netsize; m++) {
          for (int n = 0; n < applet.this.netsize; n++)
          {
            float f2 = applet.this.mybrain.getneuron(m, n);
            if (f2 < f1 + 20.0F)
            {
              str4 = m + "," + n;
              applet.this.mybrain.addmemory(str4, applet.this.outstring.getText());
            }
          }
        }
        applet.this.outv.setVisible(false);
        applet.this.dealing += 1;
        if (applet.this.dealing < applet.this.resultlis.getItemCount())
        {
          str1 = applet.this.resultlis.getItem(applet.this.dealing);
          applet.this.outstring.setText(str1);
          applet.this.outrequest.setText(str1 + " replies with:");
          applet.this.outv.setVisible(true);
        }
        else
        {
          applet.this.lab1.setText("Training and Memory mapping complete...");
        }
      }
      if (applet.this.mastbut == localComponent)
      {
        applet.this.resultlis.removeAll();
        applet.this.resultlis.addItem("cabbage");
        applet.this.resultlis.addItem("sausage");
        applet.this.resultlis.addItem("hello");
        applet.this.resultlis.addItem("goodbye");
        applet.this.actionbut.setLabel("Add to list...");
        applet.this.resultlab.setText("Words:");
        applet.this.learnbut.setEnabled(true);
        applet.this.lab1.setText("Default words added to list...");
      }
      if (applet.this.resetbut == localComponent)
      {
        applet.this.lab1.setText("Neural Net - Kohonen Simulation - (c) Copyright 1999 Sam Hill");
        applet.this.sizebox.setText("50");
        applet.this.inputbox.setText("10");
        applet.this.neighbox.setText("25");
        applet.this.cb1.setState(true);
        applet.this.actionbut.setLabel("Add to list...");
        applet.this.actionbut.setEnabled(false);
        applet.this.learnbut.setEnabled(false);
        applet.this.mastbut.setEnabled(false);
        applet.this.mybrain = new brain();
        applet.this.resultlab.setText("Words:");
        applet.this.resultlis.removeAll();
      }
      if (applet.this.actionbut == localComponent) {
        if (applet.this.cb1.getState())
        {
          applet.this.resultlis.addItem(applet.this.wordbox.getText());
          applet.this.wordbox.setText("");
        }
        else
        {
          applet.this.lab1.setText("Querying Neural Network....");
          applet.this.mybrain.clearinput();
          applet.this.lab1.setText("Network is responding....");
          applet.this.mybrain.enterbrain(applet.this.wordbox.getText());
          applet.this.mybrain.runnetwork();
          applet.this.drawnet();
          int i = applet.this.mybrain.getwinx();
          j = applet.this.mybrain.getwiny();
          applet.this.plotneuron(i, j);
          String str2 = i + "," + j;
          String str3 = applet.this.mybrain.querymemory(str2);
          applet.this.lab1.setText("Network Reply: " + str3);
          applet.this.resultlis.addItem(applet.this.wordbox.getText() + "->" + str3);
        }
      }
      if (applet.this.cb1 == localComponent) {
        if (applet.this.cb1.getState())
        {
          applet.this.actionbut.setLabel("Query network");
          applet.this.resultlab.setText("Result:");
          applet.this.learnbut.setEnabled(false);
          applet.this.resultlis.removeAll();
        }
        else
        {
          applet.this.actionbut.setLabel("Add to list...");
          applet.this.resultlab.setText("Words:");
          applet.this.learnbut.setEnabled(true);
          applet.this.resultlis.removeAll();
        }
      }
    }
    
    Listener() {}
  }
  
  public String trimstring(String paramString)
  {
    String str = "";
    int i = 0;int j = 1;
    if ((j = paramString.indexOf('.', i)) != -1) {
      str = paramString.substring(i, j);
    } else {
      str = paramString;
    }
    return str;
  }
  
  public void plotneuron(int paramInt1, int paramInt2)
  {
    int i = 0;int j = 0;int k = paramInt1;int m = paramInt2;
    Graphics localGraphics = getGraphics();
    float f = 0.0F;
    i = 425 / this.netsize;
    j = 275 / this.netsize;
    f = this.mybrain.getneuron(k, m);
    localGraphics.setColor(new Color(255, 255, 255));
    localGraphics.fillRect(k * i, m * j, i, j);
  }
  
  public void drawnet()
  {
    int i = 0;int j = 0;int k = 0;int m = 0;
    Graphics localGraphics = getGraphics();
    float f = 0.0F;
    localGraphics.setColor(Color.blue);
    k = 425 / this.netsize;
    m = 275 / this.netsize;
    for (i = 0; i < this.netsize; i++) {
      for (j = 0; j < this.netsize; j++)
      {
        f = this.mybrain.getneuron(i, j);
        localGraphics.setColor(docolor(f));
        localGraphics.fillRect(i * k, j * m, k, m);
      }
    }
  }
  
  private Color docolor(float paramFloat)
  {
    Color localColor = new Color(Integer.parseInt("ffffff", 16));
    int i = str2int(trimstring(String.valueOf(paramFloat)));
    if (paramFloat < 255.0F) {
      localColor = new Color(i, 0, 0);
    }
    if ((paramFloat > 255.0F) && (paramFloat < 510.0F)) {
      localColor = new Color(255, i - 255, 0);
    }
    if ((paramFloat > 510.0F) && (paramFloat < 765.0F)) {
      localColor = new Color(255 - (i - 510), 255, 0);
    }
    if ((paramFloat > 765.0F) && (paramFloat < 1020.0F)) {
      localColor = new Color(0, 255, i - 765);
    }
    if ((paramFloat > 1020.0F) && (paramFloat < 1275.0F)) {
      localColor = new Color(0, 255 - (i - 1020), 255);
    }
    if ((paramFloat > 1275.0F) && (paramFloat < 1530.0F)) {
      localColor = new Color(i - 1275, 0, 255);
    }
    if ((paramFloat > 1530.0F) && (paramFloat < 1785.0F)) {
      localColor = new Color(255, i - 1530, 255);
    }
    if (paramFloat > 1785.0F) {
      localColor = new Color(255, 255, 255);
    }
    return localColor;
  }
  
  public void paint(Graphics paramGraphics)
  {
    showStatus("Neural Net - Kohonen Simulation - (c) Copyright 1999 Sam Hill");
  }
  
  public void init()
  {
    setBackground(Color.white);
    setLayout(null);
    this.pl.setBackground(Color.gray);
    this.pl.setBounds(425, 0, 175, 300);
    add(this.pl);
    this.pl.setLayout(null);
    this.statpl.setBackground(Color.gray);
    this.statpl.setBounds(0, 275, 450, 300);
    add(this.statpl);
    this.statpl.setLayout(null);
    this.datapl.setBackground(Color.lightGray);
    this.datapl.setBounds(0, 215, 450, 275);
    add(this.datapl);
    this.datapl.setLayout(null);
    this.outv.setLayout(null);
    this.outv.setBackground(Color.gray);
    this.outv.setBounds(50, 50, 200, 100);
    this.outv.setVisible(false);
    add(this.outv);
    this.setout = new Button("Okay");
    this.setout.setBounds(25, 70, 150, 25);
    this.setout.addMouseListener(new applet.Listener());
    this.outv.add(this.setout);
    this.outrequest = new Label("blah replies with:");
    this.outrequest.setForeground(Color.yellow);
    this.outrequest.setBounds(25, 10, 170, 15);
    this.outv.add(this.outrequest);
    this.outstring = new TextField("");
    this.outstring.setBounds(25, 40, 150, 20);
    this.outv.add(this.outstring);
    this.initbut = new Button("Initialise Network");
    this.initbut.setBounds(25, 180, 125, 25);
    this.initbut.addMouseListener(new applet.Listener());
    this.pl.add(this.initbut);
    this.resetbut = new Button("Reset All");
    this.resetbut.setBounds(25, 210, 125, 25);
    this.resetbut.addMouseListener(new applet.Listener());
    this.pl.add(this.resetbut);
    this.mastbut = new Button("Default words");
    this.mastbut.setBounds(25, 240, 125, 25);
    this.mastbut.addMouseListener(new applet.Listener());
    this.mastbut.setEnabled(false);
    this.pl.add(this.mastbut);
    this.homebut = new Button("Sam's Homepage");
    this.homebut.setBounds(25, 270, 125, 25);
    this.homebut.addMouseListener(new applet.Listener());
    this.pl.add(this.homebut);
    this.actionbut = new Button("Add to list...");
    this.actionbut.setBounds(5, 30, 90, 25);
    this.actionbut.addMouseListener(new applet.Listener());
    this.actionbut.setEnabled(false);
    this.datapl.add(this.actionbut);
    this.learnbut = new Button("Learn Words");
    this.learnbut.setBounds(95, 30, 90, 25);
    this.learnbut.addMouseListener(new applet.Listener());
    this.learnbut.setEnabled(false);
    this.datapl.add(this.learnbut);
    this.wordbox = new TextField("");
    this.wordbox.setBounds(50, 5, 130, 20);
    this.wordbox.addMouseListener(new applet.Listener());
    this.datapl.add(this.wordbox);
    this.wordlab = new Label("Input:");
    this.wordlab.setForeground(Color.blue);
    this.wordlab.setBounds(12, 10, 75, 15);
    this.datapl.add(this.wordlab);
    this.resultlab = new Label("Words:");
    this.resultlab.setForeground(Color.blue);
    this.resultlab.setBounds(195, 10, 35, 15);
    this.datapl.add(this.resultlab);
    this.resultlis = new List();
    this.resultlis.setBounds(240, 5, 175, 50);
    this.resultlis.addMouseListener(new applet.Listener());
    this.datapl.add(this.resultlis);
    this.neighbox = new TextField("25");
    this.neighbox.setBounds(25, 20, 125, 25);
    this.neighbox.addMouseListener(new applet.Listener());
    this.pl.add(this.neighbox);
    this.neighlab = new Label("Neighbours:");
    this.neighlab.setForeground(Color.blue);
    this.neighlab.setBounds(25, 5, 125, 15);
    this.pl.add(this.neighlab);
    this.inputbox = new TextField("10");
    this.inputbox.setBounds(25, 65, 125, 25);
    this.inputbox.addMouseListener(new applet.Listener());
    this.pl.add(this.inputbox);
    this.inputlab = new Label("Max input nodes:");
    this.inputlab.setForeground(Color.blue);
    this.inputlab.setBounds(25, 50, 125, 15);
    this.pl.add(this.inputlab);
    this.sizebox = new TextField("50");
    this.sizebox.setBounds(25, 115, 125, 25);
    this.sizebox.addMouseListener(new applet.Listener());
    this.pl.add(this.sizebox);
    this.sizelab = new Label("Size of Network (x*x):");
    this.sizelab.setForeground(Color.blue);
    this.sizelab.setBounds(25, 100, 125, 15);
    this.pl.add(this.sizelab);
    this.cb1.addMouseListener(new applet.Listener());
    this.cb1.setForeground(Color.blue);
    this.cb1.setBounds(25, 150, 125, 15);
    this.pl.add(this.cb1);
    this.lab1 = new Label("Neural Net - Kohonen Simulation - (c) Copyright 1999 Sam Hill");
    this.lab1.setForeground(Color.yellow);
    this.lab1.setBounds(15, 7, 420, 15);
    this.statpl.add(this.lab1);
  }
}
