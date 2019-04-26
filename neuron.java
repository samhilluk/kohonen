import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

// Copyright 1999 Sam Hill. All rights reserved. www.samhill.co.uk

class neuron
{
  private float outvalue = 1.0E8F;
  private Hashtable weights = new Hashtable();
  private Hashtable inputs = new Hashtable();
  
  public neuron()
  {
    reset();
  }
  
  public boolean reset()
  {
    try
    {
      Random localRandom = new Random();
      float f = localRandom.nextFloat() * 1785.0F;
      this.outvalue = f;
      this.weights.clear();
      this.inputs.clear();
    }
    catch (Exception localException)
    {
      System.out.println(String.valueOf(localException));
    }
    return true;
  }
  
  public boolean setinput(int paramInt, float paramFloat)
  {
    boolean bool = true;
    try
    {
      String str = String.valueOf(paramInt);
      this.inputs.remove(str);
      this.inputs.put(str, new Float(paramFloat));
    }
    catch (Exception localException)
    {
      bool = false;
    }
    return bool;
  }
  
  public boolean setweight(int paramInt, float paramFloat)
  {
    boolean bool = true;
    try
    {
      String str = String.valueOf(paramInt);
      this.weights.remove(str);
      this.weights.put(str, new Float(paramFloat));
    }
    catch (Exception localException)
    {
      bool = false;
    }
    return bool;
  }
  
  public float output()
  {
    float f1 = 0.0F;
    float f2 = 0.0F;float f3 = 0.0F;float f4 = 0.0F;
    String str = "";
    try
    {
      for (Enumeration localEnumeration = this.inputs.keys(); localEnumeration.hasMoreElements();)
      {
        str = (String)localEnumeration.nextElement();
        Float localFloat = (Float)this.weights.get(str);
        f3 = localFloat.floatValue();
        localFloat = (Float)this.inputs.get(str);
        f4 = localFloat.floatValue();
        f2 = f4 - f3;
        f2 *= f2;
        f1 += f2;
      }
    }
    catch (Exception localException) {}
    this.outvalue = f1;
    return f1;
  }
  
  public float getvalue()
  {
    return this.outvalue;
  }
  
  public boolean adjustweights(float paramFloat)
  {
    boolean bool = true;
    float f1 = 0.0F;float f2 = 0.0F;
    
    String str = "";
    Hashtable localHashtable = new Hashtable();
    try
    {
      for (Enumeration localEnumeration = this.weights.keys(); localEnumeration.hasMoreElements();)
      {
        str = (String)localEnumeration.nextElement();
        Float localFloat = (Float)this.weights.get(str);
        f2 = localFloat.floatValue();
        localFloat = (Float)this.inputs.get(str);
        float f3 = localFloat.floatValue();
        f1 = f2 + paramFloat * (f3 - f2);
        localHashtable.remove(str);
        localHashtable.put(str, new Float(f1));
      }
    }
    catch (Exception localException)
    {
      bool = false;
    }
    this.weights = localHashtable;
    return bool;
  }
}
