import java.util.Random;

// Copyright 1999 Sam Hill. All rights reserved. www.samhill.co.uk

class kohonen
{
  private neuron[][] net;
  private int size;
  private int input;
  private float weighting;
  private int neighbours;
  private float gainterm = new Float("0.5").floatValue();
  private boolean training = true;
  private float tov = 10000.0F;
  private int topx;
  private int topy;
  
  public kohonen(int paramInt)
  {
    this.net = new neuron[paramInt][paramInt];
    this.size = paramInt;
    reset();
  }
  
  public void reset()
  {
    int i = 0;int j = 0;
    for (i = 0; i < this.size; i++) {
      for (j = 0; j < this.size; j++) {
        this.net[i][j] = new neuron();
      }
    }
  }
  
  public boolean resetinput()
  {
    int i = 0;int j = 0;int k = 0;
    boolean bool = true;
    for (i = 0; i < this.size; i++) {
      for (j = 0; j < this.size; j++) {
        for (k = 0; k < this.input; k++) {
          bool = this.net[i][j].setinput(k, 0.0F);
        }
      }
    }
    this.topx = 0;
    this.topy = 0;
    this.tov = 10000.0F;
    return bool;
  }
  
  public boolean initialise(int paramInt1, float paramFloat, int paramInt2)
  {
    int i = 0;int j = 0;int k = 0;
    float f = 0.0F;
    boolean bool = true;
    this.input = paramInt1;
    this.weighting = paramFloat;
    this.neighbours = paramInt2;
    Random localRandom = new Random();
    for (i = 0; i < this.size; i++) {
      for (j = 0; j < this.size; j++)
      {
        bool = this.net[i][j].reset();
        for (k = 0; k < paramInt1; k++)
        {
          f = localRandom.nextFloat() * 2.0F;
          bool = this.net[i][j].setinput(k, 0.0F);
          bool = this.net[i][j].setweight(k, f);
        }
      }
    }
    return true;
  }
  
  public boolean setinput(float paramFloat, int paramInt)
  {
    int i = 0;int j = 0;
    boolean bool = true;
    for (i = 0; i < this.size; i++) {
      for (j = 0; j < this.size; j++) {
        bool = this.net[i][j].setinput(paramInt, paramFloat);
      }
    }
    return bool;
  }
  
  public boolean calculatenetwork()
  {
    int i = 0;int j = 0;
    float f = 0.0F;
    Random localRandom = new Random();
    for (i = 0; i < this.size; i++) {
      for (j = 0; j < this.size; j++)
      {
        f = this.net[i][j].output();
        if (f < this.tov)
        {
          this.topx = i;
          this.topy = j;
          this.tov = f;
        }
        else if ((f == this.tov) && 
          (localRandom.nextFloat() > 0.5D))
        {
          this.topx = i;
          this.topy = j;
          this.tov = f;
        }
      }
    }
    return true;
  }
  
  public float getneuron(int paramInt1, int paramInt2)
  {
    return this.net[paramInt1][paramInt2].getvalue();
  }
  
  public int returnx()
  {
    return this.topx;
  }
  
  public int returny()
  {
    return this.topy;
  }
  
  public float returnval()
  {
    return this.tov;
  }
  
  public void setteach(boolean paramBoolean)
  {
    this.training = paramBoolean;
  }
  
  public boolean adjustweights()
  {
    int n = 0;int i1 = 0;
    boolean bool = true;
    if (this.training)
    {
      int i = this.topx - this.neighbours;
      if (i < 0) {
        i = 0;
      }
      int j = this.topy - this.neighbours;
      if (j < 0) {
        j = 0;
      }
      int k = this.topx + this.neighbours;
      if (k > this.size - 1) {
        k = this.size - 1;
      }
      int m = this.topx + this.neighbours;
      if (m > this.size - 1) {
        m = this.size - 1;
      }
      for (n = i; n < k; n++) {
        for (i1 = j; i1 < m; i1++) {
          bool = this.net[n][i1].adjustweights(this.gainterm);
        }
      }
    }
    return this.training;
  }
  
  public void decreasegain(float paramFloat)
  {
    this.gainterm -= this.gainterm * paramFloat;
  }
  
  public void decreaseneigh()
  {
    this.neighbours -= 1;
    if (this.neighbours < 1) {
      this.neighbours = 1;
    }
  }
  
  public void resetneighbours(int paramInt)
  {
    this.neighbours = paramInt;
    this.gainterm = new Float("0.5").floatValue();
  }
}