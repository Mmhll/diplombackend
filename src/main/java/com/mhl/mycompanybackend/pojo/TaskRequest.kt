package com.mhl.mycompanybackend.pojo

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

class TaskRequest {
    @JvmField
    var task_name: String? = null
    @JvmField
    var creator_id: Long? = null

    @JvmField
    @JsonFormat(pattern = "yyyy-MM-dd")
    var creation_date: Date? = null
    @JvmField
    var description: String? = null

    @JvmField
    @JsonFormat(pattern = "yyyy-MM-dd")
    var deadline: Date? = null
    @JvmField
    var executor_id: Long? = null
    @JvmField
    var members: List<Long>? = null
}
