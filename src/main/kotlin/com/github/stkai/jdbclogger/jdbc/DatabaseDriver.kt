package com.github.stkai.jdbclogger.jdbc

/**
 *  @author St.kai
 * @version 1.0
 * @date 2022-08-06 01:01
 */
enum class DatabaseDriver constructor(
    @SuppressWarnings("UnusedPrivateMember")
    private val productName: String?,
    val driverClassName: String
) {

    DERBY("Apache Derby", "org.apache.derby.jdbc.EmbeddedDriver"),

    H2("H2", "org.h2.Driver"),

    HSQLDB("HSQL Database Engine", "org.hsqldb.jdbc.JDBCDriver"),

    SQLITE("SQLite", "org.sqlite.JDBC"),

    MYSQL("MySQL", "com.mysql.cj.jdbc.Driver"),

    MARIADB("MariaDB", "org.mariadb.jdbc.Driver"),

    ORACLE("Oracle", "oracle.jdbc.OracleDriver"),

    POSTGRESQL("PostgreSQL", "org.postgresql.Driver"),

    REDSHIFT("Redshift", "com.amazon.redshift.jdbc.Driver"),

    HANA("HDB", "com.sap.db.jdbc.Driver"),

    JTDS(null, "net.sourceforge.jtds.jdbc.Driver"),

    SQLSERVER("Microsoft SQL Server", "com.microsoft.sqlserver.jdbc.SQLServerDriver"),

    FIREBIRD("Firebird", "org.firebirdsql.jdbc.FBDriver"),

    DB2("DB2", "com.ibm.db2.jcc.DB2Driver"),

    DB2_AS400("DB2 UDB for AS/400", "com.ibm.as400.access.AS400JDBCDriver"),

    TERADATA("Teradata", "com.teradata.jdbc.TeraDriver"),

    INFORMIX("Informix Dynamic Server", "com.informix.jdbc.IfxDriver"),

    PHOENIX("Apache Phoenix", "org.apache.phoenix.jdbc.PhoenixDriver"),

    TESTCONTAINERS(null, "org.testcontainers.jdbc.ContainerDatabaseDriver");
}
