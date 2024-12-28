
package org.apache.ibatis;

public class HTest {

  public static void main(String[] args) {
    int a = 0;
    synchronized (HTest.class) {
      a ++;
    }
  }

}
