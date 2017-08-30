/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codechecktest;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tobias Grundtvig
 */
public class CodeCheckTest
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        //Try some illegal stuff
        //File f = new File("/home/pi/CodeCheck/dead.txt");
        //f.delete();
        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        
        if(a == 8 || a==15)
        {
            try
            {
                Thread.sleep(60000);
            }
            catch(InterruptedException ex)
            {
                Logger.getLogger(CodeCheckTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(a == -5)
        {
            throw new RuntimeException("a==-5 exception thrown!");
        }
        
        System.out.println(Integer.toString(a-b));
    }
    
}
