package Exceptionalhandling;

public class trycatch {
      public static void main(String[] args) {
    	  try {
    		  int res = 5/0;
    		  System.out.println(res);
    	  }
    	  catch(ArithmeticException e) {
    		  System.out.println(e);
    		  }
    	  finally {
    		  System.out.println("Finally Block executed");
    		  
    	  }
    	  System.out.println("Normal Excution");
      }
}
