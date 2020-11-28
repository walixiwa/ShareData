package com.walixiwa.sharedata

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.io.Serializable

/**
 * @文件名: ShareData
 * @创建者: 进阶的面条
 * @创建日期: 2020/8/25 8:32
 * @描述: 使用SQLiteDatabase代替SharePreference
 */
class ShareData {
    companion object {
        private var database: SQLiteDatabase? = null
        private const val table = "CREATE TABLE IF NOT EXISTS shareData (keyName text PRIMARY KEY,value text, owner text,timeMillis integer)"

        fun init(context: Context, version: Int) {
            val dataBasePath = context.getDatabasePath("shareData_$version.db").absolutePath
            database = context.openOrCreateDatabase(dataBasePath, Context.MODE_PRIVATE, null)
            database?.execSQL(table)
        }

        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ShareData()
        }
    }

    fun put(key: String, value: String, owner: String) {
        val action = "replace into shareData  VALUES ('${owner.formatQuery()}:${key.formatQuery()}','${value.formatQuery()}','${owner.formatQuery()}',${System.currentTimeMillis()})"
        database?.execSQL(action)
    }

    fun put(dataModel: DataModel) {
        val action = "replace into shareData  VALUES ('${dataModel.owner.formatQuery()}:${dataModel.key.formatQuery()}','${dataModel.value.formatQuery()}','${dataModel.owner.formatQuery()}',${System.currentTimeMillis()})"
        database?.execSQL(action)
    }

    /**
     * 清空指定owner下的指定记录
     */
    fun delete(key: String, owner: String) {
        val action = "delete from shareData where keyName = '${owner.formatQuery()}:${key.formatQuery()}' and owner = '${owner.formatQuery()}'"
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
        val action = "delete from shareData where owner = '${owner.formatQuery()}'"
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
                            this.value = it.getString(1)
                            this.owner = it.getString(2)
                            this.timeMillis = it.getLong(3)
                            this.key = it.getString(0).replace("$owner:", "")
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

    fun getASC(): List<DataModel> {
        val array = ArrayList<DataModel>()
        val action = "select * from shareData order by timeMillis asc"
        val cursor: Cursor? = database?.rawQuery(action, null)
        try {
            cursor?.let {
                if (it.moveToFirst()) {
                    do {
                        array.add(DataModel().apply {
                            this.value = it.getString(1)
                            this.owner = it.getString(2)
                            this.timeMillis = it.getLong(3)
                            this.key = it.getString(0).replace("$owner:", "")
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
    fun get(key: String, owner: String): List<DataModel> {
        val array = ArrayList<DataModel>()
        val action = "select * from shareData where keyName = '${owner.formatQuery()}:${key.formatQuery()}' and owner = '${owner.formatQuery()}' order by timeMillis desc"
        val cursor: Cursor? = database?.rawQuery(action, null)
        try {
            cursor?.let {
                if (it.moveToFirst()) {
                    do {
                        array.add(DataModel().apply {
                            this.value = it.getString(1)
                            this.owner = it.getString(2)
                            this.timeMillis = it.getLong(3)
                            this.key = it.getString(0).replace("$owner:", "")
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
    fun getASC(key: String, owner: String): List<DataModel> {
        val array = ArrayList<DataModel>()
        val action = "select * from shareData where keyName = '${owner.formatQuery()}:${key.formatQuery()}' and owner = '${owner.formatQuery()}' order by timeMillis asc"
        val cursor: Cursor? = database?.rawQuery(action, null)
        try {
            cursor?.let {
                if (it.moveToFirst()) {
                    do {
                        array.add(DataModel().apply {
                            this.value = it.getString(1)
                            this.owner = it.getString(2)
                            this.timeMillis = it.getLong(3)
                            this.key = it.getString(0).replace("$owner:", "")
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
        val action = "select * from shareData where owner = '${owner.formatQuery()}' order by timeMillis desc"
        val cursor: Cursor? = database?.rawQuery(action, null)
        try {
            cursor?.let {
                if (it.moveToFirst()) {
                    do {
                        array.add(DataModel().apply {
                            this.value = it.getString(1)
                            this.owner = it.getString(2)
                            this.timeMillis = it.getLong(3)
                            this.key = it.getString(0).replace("$owner:", "")
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

    fun getASC(owner: String): List<DataModel> {
        val array = ArrayList<DataModel>()
        val action = "select * from shareData where owner = '${owner.formatQuery()}' order by timeMillis asc"
        val cursor: Cursor? = database?.rawQuery(action, null)
        try {
            cursor?.let {
                if (it.moveToFirst()) {
                    do {
                        array.add(DataModel().apply {
                            this.value = it.getString(1)
                            this.owner = it.getString(2)
                            this.timeMillis = it.getLong(3)
                            this.key = it.getString(0).replace("$owner:", "")
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
        val action = "select * from shareData where keyName = '${owner.formatQuery()}:${key.formatQuery()}' and owner = '${owner.formatQuery()}'"
        val cursor: Cursor? = database?.rawQuery(action, null)
        try {
            cursor?.let {
                if (it.moveToFirst()) {
                    do {
                        value = it.getString(1)
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

    fun isExist(key: String, owner: String): Boolean {
        val action = "select count(*) from shareData where keyName = '${owner.formatQuery()}:${key.formatQuery()}' and owner = '${owner.formatQuery()}'"

        val cursor = database?.rawQuery(action, null)
        var count = 0
        cursor?.let {
            if (it.moveToNext()) {
                count = it.getInt(0)
            }
        }
        cursor?.close()
        return count > 0
    }

    fun getCount(owner: String): Int {
        val action = "select count(*) from shareData where owner = '${owner.formatQuery()}'"
        val cursor = database?.rawQuery(action, null)
        var count = 0
        cursor?.let {
            if (it.moveToNext()) {
                count = it.getInt(0)
            }
        }
        cursor?.close()
        return count
    }

    class DataModel : Serializable {
        var key = ""
        var value = ""
        var owner = ""
        var timeMillis: Long = 0
    }

    private fun String.formatQuery(): String {
        return this.replace("'", "''").trim()
    }
}