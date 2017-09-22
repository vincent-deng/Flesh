package com.ecjtu.flesh.db.table.impl

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.ecjtu.flesh.model.ModelManager
import com.ecjtu.flesh.model.models.NotificationModel
import java.lang.Exception

/**
 * Created by Ethan_Xiang on 2017/9/22.
 */
class NotificationTableImpl : BaseTableImpl() {

    override val sql: String
        get() = "CREATE TABLE tb_notification (\n" +
                "    _id               INTEGER PRIMARY KEY ASC AUTOINCREMENT,\n" +
                "    title             STRING,\n" +
                "    content           STRING,\n" +
                "    ticker            STRING,\n" +
                "    [limit]           INTEGER,\n" +
                "    time              STRING,\n" +
                "    time_limit        STRING,\n" +
                "    action_detail_url STRING,\n" +
                "    occurs            INTEGER\n" +
                ");\n"

    companion object {
        const val TABLE_NAME = "tb_notification"
    }

    override fun createTable(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(sql)
    }

    override fun deleteTable(sqLiteDatabase: SQLiteDatabase) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateTable(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        createTable(sqLiteDatabase)
    }

    fun addNotification(sqLiteDatabase: SQLiteDatabase, model: NotificationModel) {
        val content = ContentValues()
        content.put("_id", model.id)
        content.put("title", model.title)
        content.put("content", model.content)
        content.put("ticker", model.ticker)
        content.put("[limit]", model.limit)
        content.put("time", model.time)
        content.put("time_limit", model.timeLimit)
        content.put("action_detail_url", model.actionDetailUrl)
        content.put("occurs", 0)
        try {
            sqLiteDatabase.insertOrThrow(TABLE_NAME, null, content)
        } catch (ex: Exception) {
            sqLiteDatabase.update(TABLE_NAME, content, "_id=?", arrayOf(model.id.toString()))
        }
    }

    fun getAllNotification(sqLiteDatabase: SQLiteDatabase): List<NotificationModel> {
        val ret = ArrayList<NotificationModel>()
        val cursor = sqLiteDatabase.rawQuery("SELECT * FROM $TABLE_NAME", arrayOf())
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                ret.add(ModelManager.getNotificationModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getInt(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6)))
            }
        }
        cursor.close()
        return ret
    }

    fun updateNotification(sqLiteDatabase: SQLiteDatabase, model: NotificationModel) {
        val content = ContentValues()
        content.put("_id", model.id)
        content.put("content", model.content)
        content.put("[limit]", model.limit)
        content.put("time", model.time)
        content.put("time_limit", model.timeLimit)
        content.put("action_detail_url", model.actionDetailUrl)
        content.put("title", model.title)
        content.put("occurs", model.occurs)
        sqLiteDatabase.update(TABLE_NAME, content, "_id=?", arrayOf(model.id.toString()))
    }
}