/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbutil;

import java.sql.Connection;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Tobias Grundtvig
 */
public class JDBCConnectionPool
{
    private final DBConnectInfo connectInfo;
    private final int max;
    private final ArrayBlockingQueue<PSConnection> queue;
    private int count;

    public JDBCConnectionPool(DBConnectInfo connectInfo, int max) throws ClassNotFoundException
    {
        this.connectInfo = connectInfo;
        this.max = max;
        this.queue = new ArrayBlockingQueue<>(max);
        Class.forName(connectInfo.getJDBCDriver());
    }
    
    public JDBCConnectionPool(String dbDriver, String dbUrl, String dbUser, String dbPassword, int max) throws ClassNotFoundException
    {
        this(new DBConnectInfo(dbDriver, dbUrl, dbUser, dbPassword), max);
    }
    
    public PSConnection getConnection() throws InterruptedException
    {
        if(queue.isEmpty() && count < max)
        {
            ++count;
            return new PSConnection(connectInfo);
        }
        return queue.take();
    }
    
    public void returnConnection(PSConnection conn)
    {
        if(!queue.offer(conn))
        {
            throw new RuntimeException("Pool is full. Who is creating extra connections????");
        }
    }
}
