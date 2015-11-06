package pap.test;

import java.util.*;
import java.io.*;

public class Quadratic {
    static String print(double real, double imag)
    {   if (Math.abs(imag)<1E-6)
            return ""+real;
        else 
            return "("+real+","+imag+")";
    }
    public static void main(String[] args)
    {   double A,B,C,D;
      try {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("A = ");
        A = Double.parseDouble(br.readLine());
        if (Math.abs(A)<1E-3)
        {   System.out.println("Not a quadratic equation.");
            return;
        }
        System.out.print("B = ");
        B = Double.parseDouble(br.readLine());
        System.out.print("C = ");
        C = Double.parseDouble(br.readLine());
      }
      catch (Exception e) {
        System.err.println("An error occured while reading input parameters.");
        return;
      }
        A = 2*A;
        D = B*B-2*A*C;
        if (Math.abs(D)<1E-3)
        {   System.out.println("x = "+(-B/A));
            return;
        }
        if (D>0)
            System.out.println("x1 = "+print((-B+Math.sqrt(D))/A, 0)+"\nx2 = "+print((-B-Math.sqrt(D))/A, 0));
        else
            System.out.println("x1 = "+print(-B/A,Math.sqrt(-D)/A)+"\nx2 = "+print(-B/A,-Math.sqrt(-D)/A));
    }
}