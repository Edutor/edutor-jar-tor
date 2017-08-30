/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbutil;

/**
 *
 * @author Tobias Grundtvig
 */
public class DBConnectInfo
{
    private final String jdbcDriver;
    private final String jdbcUrl;
    private final String dbUser;
    private final String dbPassword;

    public DBConnectInfo(String jdbcDriver, String jdbcUrl, String dbUser, String dbPassword)
    {
        this.jdbcDriver = jdbcDriver;
        this.jdbcUrl = jdbcUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public String getJDBCDriver()
    {
        return jdbcDriver;
    }

    public String getJDBCUrl()
    {
        return jdbcUrl;
    }

    public String getDBUser()
    {
        return dbUser;
    }

    public String getDBPassword()
    {
        return dbPassword;
    }
    
    
}
