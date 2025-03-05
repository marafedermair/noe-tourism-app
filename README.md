# NOE Tourism App Configuration Guide

## Database Configuration

This document explains how to properly configure the `application.properties` file to connect to the Database.

### Required Changes

You need to replace the following variables in the `application.properties` file with your actual database credentials:

| Variable | Description | Example |
|----------|-------------|---------|
| `$HOST` | The database server hostname or IP address | `db.example.com` or `192.168.1.100` |
| `$PORT` | The port on which the SQL Server is running | `1433` (default SQL Server port) |
| `$DB-NAME` | The name of your database | `noe_tourism_db` |
| `$USERNAME` | Your database username | `db_user` |
| `$PASSWORD` | Your database password | `your_secure_password` |

### Example of a Completed Configuration

```properties
spring.application.name=noe-tourism-app

# Database connection
spring.datasource.url=jdbc:sqlserver://db.noe-tourism.com:1433;databaseName=noe_tourism_db;encrypt=true;trustServerCertificate=true
spring.datasource.username=tourism_app_user
spring.datasource.password=SecureP@ssw0rd!
spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect

# Server Configuration
server.port=8080
```

### Important Notes

1. Do not commit your completed `application.properties` file with actual credentials to version control
2. Consider using environment variables or a secrets management solution for production environments
3. The `trustServerCertificate=true` setting is allowing connections to SQL Server without validating the server's certificate - this should be properly configured in production

### Troubleshooting

If you encounter connection issues:

- Verify the database server is running and accessible from your network
- Check that the provided credentials have appropriate permissions
- Ensure the database name is spelled correctly
- Confirm the port is not blocked by a firewall
