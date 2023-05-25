package com.mhl.mycompanybackend.pojo

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

class UpdateTaskRequest {
    @JvmField
    var task_id: Long? = null
    @JvmField
    var name: String? = null
    @JvmField
    var description: String? = null

    @JvmField
    @JsonFormat(pattern = "yyyy-MM-dd")
    var deadline: Date? = null
}
