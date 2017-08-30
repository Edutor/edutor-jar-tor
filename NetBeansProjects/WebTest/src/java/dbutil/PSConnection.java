/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tobias Grundtvig
 */
public class PSConnection
{

    private final DBConnectInfo connectInfo;
    private final Map<String, PreparedStatement> map;
    private Connection conn;

    public PSConnection(DBConnectInfo connectInfo)
    {
        this.connectInfo = connectInfo;
        this.map = new HashMap<>();
        this.conn = null;
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException
    {
        checkConnection();
        PreparedStatement res = map.get(sql);
        if(res == null)
        {
            res = conn.prepareStatement(sql);
            map.put(sql, res);
        }
        return res;
    }
    
    public PreparedStatement getPreparedStatement(String sql, String[] columnNames) throws SQLException
    {
        checkConnection();
        PreparedStatement res = map.get(sql);
        if(res == null)
        {
            res = conn.prepareStatement(sql, columnNames);
            map.put(sql, res);
        }
        return res;
    }

    private void checkConnection() throws SQLException
    {

        if(conn != null)
        {
            if(conn.isValid(5))
            {
               return; 
            }
            conn.close();
            conn = null;
        }
        map.clear();
        // Open a connection
        conn = DriverManager.getConnection(connectInfo.getJDBCUrl(),
                                           connectInfo.getDBUser(),
                                           connectInfo.getDBPassword());
        
    }

}
