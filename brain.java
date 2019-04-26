import java.util.Enumeration;
import java.util.Hashtable;

// Copyright 1999 Sam Hill. All rights reserved. www.samhill.co.uk

class brain
{
  private kohonen koh;
  private Hashtable memory;
  private int defaultneighbours;
  private String inputstring = "";
  
  private float int2float(int paramInt)
  {
    Integer localInteger = new Integer(paramInt);
    return localInteger.floatValue();
  }
  
  public boolean makebrain(int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    this.koh = new kohonen(paramInt1);
    this.memory = new Hashtable();
    this.memory.clear();
    boolean bool = this.koh.initialise(paramInt2, paramFloat, paramInt3);
    this.defaultneighbours = paramInt3;
    return bool;
  }
  
  public String querymemory(String paramString)
  {
    String str = (String)this.memory.get(paramString);
    return str;
  }
  
  public void addmemory(String paramString1, String paramString2)
  {
    this.memory.remove(paramString1);
    this.memory.put(paramString1, paramString2);
  }
  
  public void enterbrain(String paramString)
  {
    int i = paramString.length();int j = 0;int k = 0;
    Character localCharacter = new Character(' ');
    char c = ' ';
    for (j = 0; j < i; j++)
    {
      c = paramString.charAt(j);
      k = Character.getNumericValue(c) - 9;
      this.koh.setinput(int2float(k), j);
    }
    this.inputstring = paramString;
  }
  
  public void clearall()
  {
    boolean bool = true;
    bool = this.koh.resetinput();
    this.koh.resetneighbours(this.defaultneighbours);
  }
  
  public void clearinput()
  {
    boolean bool = true;
    bool = this.koh.resetinput();
  }
  
  public void brainiteration()
  {
    this.koh.setteach(true);
    boolean bool = false;
    int i = 0;int j = 0;
    float f = 100000.0F;
    bool = this.koh.calculatenetwork();
    i = this.koh.returnx();
    j = this.koh.returny();
    f = this.koh.returnval();
    bool = this.koh.adjustweights();
    this.koh.setteach(false);
  }
  
  public void decreasegain(float paramFloat)
  {
    this.koh.decreasegain(paramFloat);
  }
  
  public void decreaseneigh()
  {
    this.koh.decreaseneigh();
  }
  
  public int teachbrain(Hashtable paramHashtable, int paramInt)
  {
    clearall();
    String str1 = "";
    int j;
    for (int i = 0; i < 15; i++)
    {
      for (j = 0; j < 5; j++) {
        for (Enumeration localEnumeration1 = paramHashtable.keys(); localEnumeration1.hasMoreElements();)
        {
          str1 = (String)localEnumeration1.nextElement();
          enterbrain(str1);
          brainiteration();
          clearinput();
          decreasegain(new Float("0.02").floatValue());
        }
      }
      decreaseneigh();
    }
    int m;
    for (Enumeration localEnumeration2 = paramHashtable.keys(); localEnumeration2.hasMoreElements(); m < paramInt)
    {
      str1 = (String)localEnumeration2.nextElement();
      clearinput();
      enterbrain(str1);
      runnetwork();
      j = getwinx();
      int k = getwiny();
      float f1 = getneuron(j, k);
      String str2 = j + "," + k;
      addmemory(str2, str1);
      m = 0; continue;
      for (int n = 0; n < paramInt; n++)
      {
        float f2 = getneuron(m, n);
        if (f2 < f1 + 20.0F)
        {
          str2 = m + "," + n;
          addmemory(str2, str1);
        }
      }
      m++;
    }
    return 0;
  }
  
  public void runnetwork()
  {
    boolean bool = this.koh.calculatenetwork();
  }
  
  public String querybrain(String paramString)
  {
    clearinput();
    enterbrain(paramString);
    runnetwork();
    int i = getwinx();
    int j = getwiny();
    String str1 = i + "," + j;
    String str2 = querymemory(str1);
    return str2;
  }
  
  public int getwinx()
  {
    return this.koh.returnx();
  }
  
  public int getwiny()
  {
    return this.koh.returny();
  }
  
  public float getneuron(int paramInt1, int paramInt2)
  {
    return this.koh.getneuron(paramInt1, paramInt2);
  }
}
