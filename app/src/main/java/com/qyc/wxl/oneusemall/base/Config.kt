package com.qyc.wxl.oneusemall.base

class Config {

    companion object {

        /**
         * 是否为调试  true 为线下   false 为线上
         */
        private val isDebug = false

        val IP = if (isDebug) {
            "http://bagua.59156.cn/api"
        } else {
            "http://bagua.59156.cn/api"
        }
        val IMG = "http://bagua.59156.cn"
        /**
         * 注册
         */
        val REGISTER_URL = "$IP/login/register"
        val REGISTER_CODE = 2

        /**
         * 登录
         */
        val LOGIN_URL = "$IP/login/login"
        val LOGIN_CODE = 3
        /**
         * 发送邮箱验证码
         */
        val SEND_EMAIL_URL = "$IP/login/setemail"
        val SEND_EMAIL_CODE = 4
        /**
         * 发送短信验证码
         */
        val SEND_PHONE_URL = "$IP/login/set_sms"
        /**
         * 修改密码
         */
        val FORGET_URL = "$IP/login/forget_password"
        val FORGET_CODE = 5
        /**
         * 热门历史收搜数据
         */
        val SEARCH_LIST_URL = "$IP/index/search_list"
        val SEARCH_LIST_CODE = 6
        /**
         * 	我关注的和关注我的
         */
        val FOLLOW_LIST_URL = "$IP/follow/follow_list"
        val FOLLOW_LIST_CODE = 7
        /**
         * 	关注、取消关注（会员）
         */
        val FOLLOW_SET_URL = "$IP/follow/set_follow"
        val FOLLOW_SET_CODE = 8
        /**
         * 	获取用户信息
         */
        val USER_INDEX_URL = "$IP/member/index"
        val USER_INDEX_CODE = 9
        /**
         * 	会员积分明细
         */
        val USER_INTEGRAL_URL = "$IP/member/member_integral"
        val USER_INTEGRAL_CODE = 10
        /**
         * 	修改会员资料
         */
        val USER_EDIT_URL = "$IP/member/member_edit"
        val USER_EDIT_CODE = 11
        /**
         * 	上传图片
         */
        val UPLOAD_IMAGE_URL = "$IP/uploads/upload"
        val UPLOAD_CODE = 12
        /**
         * 	修改手机号或邮箱
         */
        val EDIT_PHONE_URL = "$IP/member/bind_number"
        val EDIT_PHONE_CODE = 13
        /**
         * 	获取系统消息
         */
        val SYSTEM_LIST_URL = "$IP/member/notice"
        val SYSTEM_LIST_CODE = 14
        /**
         * 	管理员用户列表
         */
        val MANAGER_LIST_URL = "$IP/member/member_admin"
        val MANAGER_LIST_CODE = 15
        /**
         * 	个人中心排名
         */
        val RANKING_LIST_URL = "$IP/member/ranking"
        val RANKING_LIST_CODE = 16
        /**
         * 	管理员获取举报列表
         */
        val REPORT_LIST_URL = "$IP/member/report_admin"
        val REPORT_LIST_CODE = 17
        /**
         * 	举报详情
         */
        val REPORT_DETAIL_URL = "$IP/member/report_details"
        val REPORT_DETAIL_CODE = 18
        /**
         * 	管理员举报操作
         */
        val REPORT_CHANGE_URL = "$IP/member/operation_report"
        val REPORT_CHANGE_CODE = 19
        /**
         * 	会员帖子申请恢复
         */
        val RECOVERY_CHANGE_URL = "$IP/member/recovery"
        val REPORTRECOVERY_CHANGE_CODE = 20
        /**
         * 	首页收搜
         */
        val MAIN_SERCH_URL = "$IP/index/search"
        val MAIN_SERCH_CODE = 21
        /**
         * 	绑定手机号
         */
        val BIND_TEL_URL = "$IP/login/bind_tel"
        val BIND_TEL_CODE = 22
        /**
         * 	第三方登录
         */
        val OTHER_LOGIN_URL = "$IP/login/other_login"
        val OTHER_LOGIN_CODE = 23
    }

}