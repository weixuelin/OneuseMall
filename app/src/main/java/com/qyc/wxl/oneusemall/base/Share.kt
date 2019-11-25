package com.qyc.wxl.oneusemall.base

import android.content.Context


class Share {

    companion object {

        val USER: String = "user"
        var STATE_USER = "state"

        fun getUid(context: Context): Int {
            return context.getSharedPreferences(USER, Context.MODE_PRIVATE).getInt("uid", 0)

        }

        /**
         * 保存用户uid
         */
        fun saveUid(context: Context, uid: Int) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putInt("uid", uid)
            edit.apply()
        }

        fun getAccount(context: Context): String {
            return context.getSharedPreferences(USER, Context.MODE_PRIVATE).getString("account", "").toString()

        }

        /**
         * 保存用户uid
         */
        fun saveAccount(context: Context, uid: String) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putString("account", uid)
            edit.apply()
        }


        fun getToken(context: Context): String = context.getSharedPreferences(USER, Context.MODE_PRIVATE).getString("token", "").toString()

        /**
         * 保存用户token
         */
        fun saveToken(context: Context, token: String) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.putString("token", token)
            edit.apply()

        }


        /**
         * 清除uid和token数据
         */
        fun clearUidOrToken(context: Context) {
            val edit = context.getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
            edit.remove("token")
            edit.remove("uid")
            edit.commit()

        }
    }
}