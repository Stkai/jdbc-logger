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
import java.io.InputStream
import java.io.Reader
import java.math.BigDecimal
import java.net.URL
import java.sql.Blob
import java.sql.Clob
import java.sql.Connection
import java.sql.Date
import java.sql.NClob
import java.sql.ParameterMetaData
import java.sql.PreparedStatement
import java.sql.Ref
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.RowId
import java.sql.SQLWarning
import java.sql.SQLXML
import java.sql.Time
import java.sql.Timestamp
import java.util.Calendar

/**
 * @author St.kai
 * @version 1.0
 * @date 2022-07-17 17:59
 */
@SuppressWarnings("TooManyFunctions")
class PreparedStatementWrapper(private var statement: PreparedStatement, override var sql: String?) :
    StatementWrapper(statement, sql),
    PreparedStatement {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun <T : Any?> unwrap(iface: Class<T>?): T {
        return statement.unwrap(iface)
    }

    override fun isWrapperFor(iface: Class<*>?): Boolean {
        return statement.isWrapperFor(iface)
    }

    override fun close() {
        statement.close()
    }

    override fun executeQuery(): ResultSet {
        log.trace("executeQuery")
        return Logger.execute(null) { statement.executeQuery() }
    }

    override fun executeQuery(sql: String?): ResultSet {
        log.trace("executeQuery:{}", sql)
        return Logger.execute(sql) { this.statement.executeQuery(sql) }
    }

    override fun executeUpdate(): Int {
        log.trace("executeUpdate")
        return Logger.execute(null) { statement.executeUpdate() }
    }

    override fun executeUpdate(sql: String?): Int {
        log.trace("executeUpdate:{}", sql)
        return Logger.execute(sql) { statement.executeUpdate(sql) }
    }

    override fun executeUpdate(sql: String?, autoGeneratedKeys: Int): Int {
        log.trace("executeUpdate:{}", sql)
        return Logger.execute(sql) { statement.executeUpdate(sql, autoGeneratedKeys) }
    }

    override fun executeUpdate(sql: String?, columnIndexes: IntArray?): Int {
        log.trace("executeUpdate:{}", sql)
        return Logger.execute(sql) { statement.executeUpdate(sql, columnIndexes) }
    }

    override fun executeUpdate(sql: String?, columnNames: Array<out String>?): Int {
        log.trace("executeUpdate:{}", sql)
        return Logger.execute(sql) { statement.executeUpdate(sql, columnNames) }
    }

    override fun getMaxFieldSize(): Int {
        return statement.maxFieldSize
    }

    override fun setMaxFieldSize(max: Int) {
        statement.maxFieldSize = max
    }

    override fun getMaxRows(): Int {
        return statement.maxRows
    }

    override fun setMaxRows(max: Int) {
        statement.maxRows = max
    }

    override fun setEscapeProcessing(enable: Boolean) {
        statement.setEscapeProcessing(enable)
    }

    override fun getQueryTimeout(): Int {
        return statement.queryTimeout
    }

    override fun setQueryTimeout(seconds: Int) {
        statement.queryTimeout = seconds
    }

    override fun cancel() {
        statement.cancel()
    }

    override fun getWarnings(): SQLWarning {
        return statement.warnings
    }

    override fun clearWarnings() {
        statement.clearWarnings()
    }

    override fun setCursorName(name: String?) {
        statement.setCursorName(name)
    }

    override fun execute(): Boolean {
        log.trace("execute")
        return Logger.execute(null) { statement.execute() }
    }

    override fun execute(sql: String?): Boolean {
        log.trace("execute:{}", sql)
        return Logger.execute(sql) { statement.execute(sql) }
    }

    override fun execute(sql: String?, autoGeneratedKeys: Int): Boolean {
        log.trace("execute:{}", sql)
        return Logger.execute(sql) { statement.execute(sql, autoGeneratedKeys) }
    }

    override fun execute(sql: String?, columnIndexes: IntArray?): Boolean {
        log.trace("execute:{}", sql)
        return Logger.execute(sql) { statement.execute(sql, columnIndexes) }
    }

    override fun execute(sql: String?, columnNames: Array<out String>?): Boolean {
        log.trace("execute:{}", sql)
        return Logger.execute(sql) { statement.execute(sql, columnNames) }
    }

    override fun getResultSet(): ResultSet {
        return statement.resultSet
    }

    override fun getUpdateCount(): Int {
        return statement.updateCount
    }

    override fun getMoreResults(): Boolean {
        return statement.moreResults
    }

    override fun getMoreResults(current: Int): Boolean {
        return statement.getMoreResults(current)
    }

    override fun setFetchDirection(direction: Int) {
        statement.fetchDirection = direction
    }

    override fun getFetchDirection(): Int {
        return statement.fetchDirection
    }

    override fun setFetchSize(rows: Int) {
        statement.fetchSize = rows
    }

    override fun getFetchSize(): Int {
        return statement.fetchSize
    }

    override fun getResultSetConcurrency(): Int {
        return statement.resultSetConcurrency
    }

    override fun getResultSetType(): Int {
        return statement.resultSetType
    }

    override fun addBatch() {
        statement.addBatch()
    }

    override fun addBatch(sql: String?) {
        log.trace("addBatch:{}", sql)
        Logger.execute(sql) { statement.addBatch(sql) }
    }

    override fun clearBatch() {
        Logger.clearSql()
        statement.clearBatch()
    }

    override fun executeBatch(): IntArray {
        log.trace("executeBatch")
        return Logger.execute(null) { statement.executeBatch() }
    }

    override fun getConnection(): Connection {
        return statement.connection
    }

    override fun getGeneratedKeys(): ResultSet {
        return statement.generatedKeys
    }

    override fun getResultSetHoldability(): Int {
        return statement.resultSetHoldability
    }

    override fun isClosed(): Boolean {
        return statement.isClosed
    }

    override fun setPoolable(poolable: Boolean) {
        statement.isPoolable = poolable
    }

    override fun isPoolable(): Boolean {
        return statement.isPoolable
    }

    override fun closeOnCompletion() {
        statement.closeOnCompletion()
    }

    override fun isCloseOnCompletion(): Boolean {
        return statement.isCloseOnCompletion
    }

    override fun setNull(parameterIndex: Int, sqlType: Int) {
        Logger.setParam(parameterIndex, null)
        statement.setNull(parameterIndex, sqlType)
    }

    override fun setNull(parameterIndex: Int, sqlType: Int, typeName: String?) {
        Logger.setParam(parameterIndex, null)
        statement.setNull(parameterIndex, sqlType, typeName)
    }

    override fun setBoolean(parameterIndex: Int, x: Boolean) {
        Logger.setParam(parameterIndex, x)
        statement.setBoolean(parameterIndex, x)
    }

    override fun setByte(parameterIndex: Int, x: Byte) {
        Logger.setParam(parameterIndex, x)
        statement.setByte(parameterIndex, x)
    }

    override fun setShort(parameterIndex: Int, x: Short) {
        Logger.setParam(parameterIndex, x)
        statement.setShort(parameterIndex, x)
    }

    override fun setInt(parameterIndex: Int, x: Int) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setInt(parameterIndex, x)
    }

    override fun setLong(parameterIndex: Int, x: Long) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setLong(parameterIndex, x)
    }

    override fun setFloat(parameterIndex: Int, x: Float) {
        Logger.setParam(parameterIndex, x)
        statement.setFloat(parameterIndex, x)
    }

    override fun setDouble(parameterIndex: Int, x: Double) {
        Logger.setParam(parameterIndex, x)
        statement.setDouble(parameterIndex, x)
    }

    override fun setBigDecimal(parameterIndex: Int, x: BigDecimal?) {
        Logger.setParam(parameterIndex, x)
        statement.setBigDecimal(parameterIndex, x)
    }

    override fun setString(parameterIndex: Int, x: String?) {
        Logger.setParam(parameterIndex, x)
        log.trace("index:{}:value:{}", parameterIndex, x)
        statement.setString(parameterIndex, x)
    }

    override fun setBytes(parameterIndex: Int, x: ByteArray?) {
        Logger.setParam(parameterIndex, x)
        log.trace("index:{},x:{}", parameterIndex, x)
        statement.setBytes(parameterIndex, x)
    }

    override fun setDate(parameterIndex: Int, x: Date?) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setDate(parameterIndex, x)
    }

    override fun setDate(parameterIndex: Int, x: Date?, cal: Calendar?) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setDate(parameterIndex, x, cal)
    }

    override fun setTime(parameterIndex: Int, x: Time?) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setTime(parameterIndex, x)
    }

    override fun setTime(parameterIndex: Int, x: Time?, cal: Calendar?) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setTime(parameterIndex, x, cal)
    }

    override fun setTimestamp(parameterIndex: Int, x: Timestamp?) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setTimestamp(parameterIndex, x)
    }

    override fun setTimestamp(parameterIndex: Int, x: Timestamp?, cal: Calendar?) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setTimestamp(parameterIndex, x, cal)
    }

    override fun setAsciiStream(parameterIndex: Int, x: InputStream?, length: Int) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setAsciiStream(parameterIndex, x, length)
    }

    override fun setAsciiStream(parameterIndex: Int, x: InputStream?, length: Long) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setAsciiStream(parameterIndex, x, length)
    }

    override fun setAsciiStream(parameterIndex: Int, x: InputStream?) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setAsciiStream(parameterIndex, x)
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun setUnicodeStream(parameterIndex: Int, x: InputStream?, length: Int) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setUnicodeStream(parameterIndex, x, length)
    }

    override fun setBinaryStream(parameterIndex: Int, x: InputStream?, length: Int) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setBinaryStream(parameterIndex, x, length)
    }

    override fun setBinaryStream(parameterIndex: Int, x: InputStream?, length: Long) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setBinaryStream(parameterIndex, x, length)
    }

    override fun setBinaryStream(parameterIndex: Int, x: InputStream?) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setBinaryStream(parameterIndex, x)
    }

    override fun clearParameters() {
        Logger.clearParameters()
        statement.clearParameters()
    }

    override fun setObject(parameterIndex: Int, x: Any?, targetSqlType: Int) {
        Logger.setParam(parameterIndex, x)
        log.trace("index:{},x:{}", parameterIndex, x)
        statement.setObject(parameterIndex, x, targetSqlType)
    }

    override fun setObject(parameterIndex: Int, x: Any?) {
        Logger.setParam(parameterIndex, x)
        log.trace("index:{},x:{}", parameterIndex, x)
        statement.setObject(parameterIndex, x)
    }

    override fun setObject(parameterIndex: Int, x: Any?, targetSqlType: Int, scaleOrLength: Int) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setObject(parameterIndex, x, targetSqlType, scaleOrLength)
    }

    override fun setCharacterStream(parameterIndex: Int, reader: Reader?, length: Int) {
        log.trace("index:{},x:{}", parameterIndex, reader)
        statement.setCharacterStream(parameterIndex, reader, length)
    }

    override fun setCharacterStream(parameterIndex: Int, reader: Reader?, length: Long) {
        log.trace("index:{},x:{}", parameterIndex, reader)
        Logger.setParam(parameterIndex, reader)
        statement.setCharacterStream(parameterIndex, reader, length)
    }

    override fun setCharacterStream(parameterIndex: Int, reader: Reader?) {
        Logger.setParam(parameterIndex, reader)
        statement.setCharacterStream(parameterIndex, reader)
    }

    override fun setRef(parameterIndex: Int, x: Ref?) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setRef(parameterIndex, x)
    }

    override fun setBlob(parameterIndex: Int, x: Blob?) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setBlob(parameterIndex, x)
    }

    override fun setBlob(parameterIndex: Int, inputStream: InputStream?, length: Long) {
        Logger.setParam(parameterIndex, inputStream)
        statement.setBlob(parameterIndex, inputStream, length)
    }

    override fun setBlob(parameterIndex: Int, inputStream: InputStream?) {
        Logger.setParam(parameterIndex, inputStream)
        statement.setBlob(parameterIndex, inputStream)
    }

    override fun setClob(parameterIndex: Int, x: Clob?) {
        log.trace("index:{},x:{}", parameterIndex, x)
        Logger.setParam(parameterIndex, x)
        statement.setClob(parameterIndex, x)
    }

    override fun setClob(parameterIndex: Int, reader: Reader?, length: Long) {
        Logger.setParam(parameterIndex, reader)
        statement.setClob(parameterIndex, reader, length)
    }

    override fun setClob(parameterIndex: Int, reader: Reader?) {
        Logger.setParam(parameterIndex, reader)
        statement.setClob(parameterIndex, reader)
    }

    override fun setArray(parameterIndex: Int, x: java.sql.Array?) {
        Logger.setParam(parameterIndex, x)
        statement.setArray(parameterIndex, x)
    }

    override fun getMetaData(): ResultSetMetaData {
        return statement.metaData
    }

    override fun setURL(parameterIndex: Int, x: URL?) {
        Logger.setParam(parameterIndex, x)
        statement.setURL(parameterIndex, x)
    }

    override fun getParameterMetaData(): ParameterMetaData {
        return statement.parameterMetaData
    }

    override fun setRowId(parameterIndex: Int, x: RowId?) {
        Logger.setParam(parameterIndex, x)
        statement.setRowId(parameterIndex, x)
    }

    override fun setNString(parameterIndex: Int, value: String?) {
        Logger.setParam(parameterIndex, value)
        statement.setNString(parameterIndex, value)
    }

    override fun setNCharacterStream(parameterIndex: Int, value: Reader?, length: Long) {
        Logger.setParam(parameterIndex, value)
        statement.setNCharacterStream(parameterIndex, value, length)
    }

    override fun setNCharacterStream(parameterIndex: Int, value: Reader?) {
        Logger.setParam(parameterIndex, value)
        statement.setNCharacterStream(parameterIndex, value)
    }

    override fun setNClob(parameterIndex: Int, value: NClob?) {
        Logger.setParam(parameterIndex, value)
        statement.setNClob(parameterIndex, value)
    }

    override fun setNClob(parameterIndex: Int, reader: Reader?, length: Long) {
        Logger.setParam(parameterIndex, reader)
        statement.setNClob(parameterIndex, reader, length)
    }

    override fun setNClob(parameterIndex: Int, reader: Reader?) {
        Logger.setParam(parameterIndex, reader)
        statement.setNClob(parameterIndex, reader)
    }

    override fun setSQLXML(parameterIndex: Int, xmlObject: SQLXML?) {
        Logger.setParam(parameterIndex, xmlObject)
        statement.setSQLXML(parameterIndex, xmlObject)
    }
}
