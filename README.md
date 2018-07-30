Database Configuration
-

Default database configuration

    spring.datasource.url=jdbc:mysql://localhost:3306/dummy?useSSL=false
    spring.datasource.username=dummy
    spring.datasource.password=dummy
    spring.datasource.driver-class-name=com.mysql.jdbc.Driver
    spring.jpa.database-platform=org.hibernate.dialect.MySQL5Dialect
    spring.jpa.show-sql = true
    
Tenant Database configuration

    spring.datasource.url=jdbc:mysql://localhost:3306/<Tenant>?useSSL=false
    spring.datasource.username=<Tenant>_username
    spring.datasource.password=<Tenant>_password
    spring.datasource.driver-class-name=com.mysql.jdbc.Driver
    spring.jpa.database-platform=org.hibernate.dialect.MySQL5Dialect
    spring.jpa.show-sql = true
    
Configure Tenant
- Create database of the tenantId
- Create database user having write access to the created database only.
- start the application.

Create user like following

    CREATE DATABASE cp1;
    CREATE USER 'cp1_username'@'localhost' IDENTIFIED BY 'cp1_password';
    GRANT ALL PRIVILEGES ON cp1.* TO 'cp1_username'@'localhost' WITH GRANT OPTION;
    FLUSH PRIVILEGES;
    
    Run the script to create all the tables in newly created database.
    
    Badges
    [![Codacy Badge](https://api.codacy.com/project/badge/Grade/59253e3714414417a054c0727552ae59)](https://www.codacy.com/project/PreEmynence/springMultiTenantApp/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=preemynence/springMultiTenantApp&amp;utm_campaign=Badge_Grade_Dashboard)
