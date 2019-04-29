package com.zwl.jyq.mvvm_stark.entity

data class UpdateBean(
        val version: String,
        val platform: String,
        val showupdate: Int,
        val needupdate: Int,
        val updateurl: String,
        val updatecontent: String,
        val invitestring: String,
        val informstring: String,
        val hasupdate: Int,
        val apkurl: String,
        val alipayenable: Int
)
