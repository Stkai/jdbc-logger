/*
 * Copyright (c) 2022 St.kai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.stkai.jdbclogger.wrapper

import com.github.stkai.jdbclogger.util.Logger
import org.slf4j.LoggerFactory
import java.sql.Blob
import java.sql.CallableStatement
import java.sql.Clob
import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.NClob
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLWarning
import java.sql.SQLXML
import java.sql.Savepoint
import java.sql.Statement
import java.sql.Struct
import java.util.Properties
import java.util.concurrent.Executor

/**
 * @author St.kai
 * @version 1.0
 * @date 2022-07-17 15:00
 */
@SuppressWarnings("TooManyFunctions")
class ConnectionWrapper(private var connection: Connection) : Connection {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun <T : Any?> unwrap(iface: Class<T>?): T {
        return connection.unwrap(iface)
    }

    override fun isWrapperFor(iface: Class<*>?): Boolean {
        return connection.isWrapperFor(iface)
    }

    override fun close() {
        connection.close()
    }

    override fun createStatement(): Statement {
        return StatementWrapper(
            connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            ),
            null
        )
    }

    override fun createStatement(resultSetType: Int, resultSetConcurrency: Int): Statement {
        return StatementWrapper(connection.createStatement(resultSetType, resultSetConcurrency), null)
    }

    override fun createStatement(resultSetType: Int, resultSetConcurrency: Int, resultSetHoldability: Int): Statement {
        return StatementWrapper(
            connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability),
            null
        )
    }

    /**
     * ??????????????? resultSetType ?????? TYPE_SCROLL_INSENSITIVE
     */
    override fun prepareStatement(sql: String?): PreparedStatement {
        log.trace(sql)
        Logger.setSql(sql)
        return PreparedStatementWrapper(
            connection.prepareStatement(
                sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            ),
            sql
        )
    }

    override fun prepareStatement(sql: String?, resultSetType: Int, resultSetConcurrency: Int): PreparedStatement {
        log.trace(sql)
        Logger.setSql(sql)
        return PreparedStatementWrapper(
            connection.prepareStatement(sql, resultSetType, resultSetConcurrency),
            sql
        )
    }

    override fun prepareStatement(
        sql: String?,
        resultSetType: Int,
        resultSetConcurrency: Int,
        resultSetHoldability: Int
    ): PreparedStatement {
        log.trace(sql)
        Logger.setSql(sql)
        return PreparedStatementWrapper(
            connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability),
            sql
        )
    }

    override fun prepareStatement(sql: String?, autoGeneratedKeys: Int): PreparedStatement {
        log.trace(sql)
        Logger.setSql(sql)
        return PreparedStatementWrapper(connection.prepareStatement(sql, autoGeneratedKeys), sql)
    }

    override fun prepareStatement(sql: String?, columnIndexes: IntArray?): PreparedStatement {
        log.trace(sql)
        Logger.setSql(sql)
        return PreparedStatementWrapper(connection.prepareStatement(sql, columnIndexes), sql)
    }

    override fun prepareStatement(sql: String?, columnNames: Array<out String>?): PreparedStatement {
        log.trace(sql)
        Logger.setSql(sql)
        return PreparedStatementWrapper(connection.prepareStatement(sql, columnNames), sql)
    }

    override fun prepareCall(sql: String?): CallableStatement {
        log.trace(sql)
        Logger.setSql(sql)
        return connection.prepareCall(sql)
    }

    override fun prepareCall(sql: String?, resultSetType: Int, resultSetConcurrency: Int): CallableStatement {
        log.trace(sql)
        Logger.setSql(sql)
        return connection.prepareCall(sql, resultSetType, resultSetConcurrency)
    }

    override fun prepareCall(
        sql: String?,
        resultSetType: Int,
        resultSetConcurrency: Int,
        resultSetHoldability: Int
    ): CallableStatement {
        log.trace(sql)
        Logger.setSql(sql)
        return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability)
    }

    override fun nativeSQL(sql: String?): String {
        log.trace(sql)
        Logger.setSql(sql)
        return this.connection.nativeSQL(sql)
    }

    override fun setAutoCommit(autoCommit: Boolean) {
        connection.autoCommit = autoCommit
    }

    override fun getAutoCommit(): Boolean {
        return connection.autoCommit
    }

    override fun commit() {
        connection.commit()
    }

    override fun rollback() {
        connection.rollback()
    }

    override fun rollback(savepoint: Savepoint?) {
        connection.rollback(savepoint)
    }

    override fun isClosed(): Boolean {
        return connection.isClosed
    }

    override fun getMetaData(): DatabaseMetaData {
        return connection.metaData
    }

    override fun setReadOnly(readOnly: Boolean) {
        connection.isReadOnly = readOnly
    }

    override fun isReadOnly(): Boolean {
        return connection.isReadOnly
    }

    override fun setCatalog(catalog: String?) {
        connection.catalog = catalog
    }

    override fun getCatalog(): String {
        return connection.catalog
    }

    override fun setTransactionIsolation(level: Int) {
        connection.transactionIsolation = level
    }

    override fun getTransactionIsolation(): Int {
        return connection.transactionIsolation
    }

    override fun getWarnings(): SQLWarning? {
        return connection.warnings
    }

    override fun clearWarnings() {
        connection.clearWarnings()
    }

    override fun getTypeMap(): MutableMap<String, Class<*>> {
        return connection.typeMap
    }

    override fun setTypeMap(map: MutableMap<String, Class<*>>?) {
        connection.typeMap = map
    }

    override fun setHoldability(holdability: Int) {
        connection.holdability = holdability
    }

    override fun getHoldability(): Int {
        return connection.holdability
    }

    override fun setSavepoint(): Savepoint {
        return connection.setSavepoint()
    }

    override fun setSavepoint(name: String?): Savepoint {
        return connection.setSavepoint(name)
    }

    override fun releaseSavepoint(savepoint: Savepoint?) {
        connection.releaseSavepoint(savepoint)
    }

    override fun createClob(): Clob {
        return connection.createClob()
    }

    override fun createBlob(): Blob {
        return connection.createBlob()
    }

    override fun createNClob(): NClob {
        return connection.createNClob()
    }

    override fun createSQLXML(): SQLXML {
        return connection.createSQLXML()
    }

    override fun isValid(timeout: Int): Boolean {
        return connection.isValid(timeout)
    }

    override fun setClientInfo(name: String?, value: String?) {
        connection.setClientInfo(name, value)
    }

    override fun setClientInfo(properties: Properties?) {
        connection.clientInfo = properties
    }

    override fun getClientInfo(name: String?): String {
        return connection.getClientInfo(name)
    }

    override fun getClientInfo(): Properties {
        return connection.clientInfo
    }

    override fun createArrayOf(typeName: String?, elements: Array<out Any>?): java.sql.Array {
        return connection.createArrayOf(typeName, elements)
    }

    override fun createStruct(typeName: String?, attributes: Array<out Any>?): Struct {
        return connection.createStruct(typeName, attributes)
    }

    override fun setSchema(schema: String?) {
        connection.schema = schema
    }

    override fun getSchema(): String {
        return connection.schema
    }

    override fun abort(executor: Executor?) {
        connection.abort(executor)
    }

    override fun setNetworkTimeout(executor: Executor?, milliseconds: Int) {
        connection.setNetworkTimeout(executor, milliseconds)
    }

    override fun getNetworkTimeout(): Int {
        return connection.networkTimeout
    }
}
