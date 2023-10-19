package org.example;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Worker worker = new Worker();
        worker.start(new Project());
    }
}
