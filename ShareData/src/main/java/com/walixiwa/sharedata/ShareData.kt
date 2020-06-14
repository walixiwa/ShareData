package com.walixiwa.sharedata

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.io.Serializable

/**
 * 使用SQLiteDatabase代替SharePreference
 */

class ShareData {
    companion object {
        private var database: SQLiteDatabase? = null
        private const val TABLE = "CREATE TABLE IF NOT EXISTS shareData (`key` text PRIMARY KEY,value text, owner text,timeMillis integer)"

        fun init(context: Context, version: Int) {
            val dataBasePath = context.getDatabasePath("shareData_$version.db").absolutePath
            this.database = context.openOrCreateDatabase(dataBasePath, Context.MODE_PRIVATE, null)
            this.database?.execSQL(TABLE)
        }

        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ShareData()
        }
    }

    fun put(key: String, value: String, owner: String) {
        val action = "replace into shareData  VALUES ('$key','$value','$owner',${System.currentTimeMillis()})"
        database?.execSQL(action)
    }

    fun put(dataModel: DataModel) {
        val action = "replace into shareData  VALUES ('${dataModel.key}','${dataModel.value}','${dataModel.owner}',${System.currentTimeMillis()})"
        database?.execSQL(action)
    }

    /**
     * 清空指定owner下的指定记录
     */
    fun delete(key: String, owner: String) {
        val action = "delete from shareData where key = '$key' and owner = '$owner'"
        database?.execSQL(action)
    }

    /**
     * 清空所有记录
     */
    fun clear() {
        val action = "delete from shareData"
        database?.execSQL(action)
    }

    /**
     * 清空某个owner下的所有记录
     */
    fun clear(owner: String) {
        val action = "delete from shareData where owner = '$owner'"
        database?.execSQL(action)
    }

    /**
     * 取出所有记录
     */
    fun get(): List<DataModel> {
        val array = ArrayList<DataModel>()
        val action = "select * from shareData order by timeMillis desc"
        val cursor: Cursor? = database?.rawQuery(action, null)
        try {
            cursor?.let {
                if (it.moveToFirst()) {
                    do {
                        array.add(DataModel().apply {
                            this.key = it.getString(0)
                            this.value = it.getString(1)
                            this.owner = it.getString(2)
                            this.timeMillis = it.getLong(3)
                        })
                    } while (it.moveToNext())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if ((null != cursor) && (!cursor.isClosed)) {
                cursor.close()
            }
        }
        return array
    }

    /**
     * 取出某个分组的所有记录
     */
    fun get(owner: String): List<DataModel> {
        val array = ArrayList<DataModel>()
        val action = "select * from shareData where owner = '$owner' order by timeMillis desc"
        val cursor: Cursor? = database?.rawQuery(action, null)
        try {
            cursor?.let {
                if (it.moveToFirst()) {
                    do {
                        array.add(DataModel().apply {
                            this.key = it.getString(0)
                            this.value = it.getString(1)
                            this.owner = it.getString(2)
                            this.timeMillis = it.getLong(3)
                        })
                    } while (it.moveToNext())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if ((null != cursor) && (!cursor.isClosed)) {
                cursor.close()
            }
        }
        return array
    }

    /**
     * 取出指定key和指定owner的value
     */
    fun get(key: String, defaultValue: String, owner: String): String {
        var value = ""
        val action = "select * from shareData where key = '$key' and owner = '$owner'"
        val cursor: Cursor? = database?.rawQuery(action, null)
        try {
            cursor?.let {
                if (it.moveToFirst()) {
                    do {
                        value = it.getString(2)
                    } while (it.moveToNext())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if ((null != cursor) && (!cursor.isClosed)) {
                cursor.close()
            }
        }
        return if (value.isBlank()) {
            defaultValue
        } else {
            value
        }
    }

    class DataModel : Serializable {
        var key = ""
        var value = ""
        var owner = ""
        var timeMillis: Long = 0
    }
}