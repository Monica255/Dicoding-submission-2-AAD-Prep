package com.dicoding.courseschedule.paging

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.util.DayName.Companion.getByNumber

class CourseViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private lateinit var course: Course
    private val timeString = itemView.context.resources.getString(R.string.time_format)

    //TODO 7 : Complete ViewHolder to show item
    fun bind(course: Course?, clickListener: (Course) -> Unit) {
        val tvCourse: TextView = itemView.findViewById(R.id.tv_course)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        val tvLecturer: TextView = itemView.findViewById(R.id.tv_lecturer)
        if (course != null) {
            this.course = course
        }

        course?.apply {
            val dayName = getByNumber(day)
            val timeFormat = String.format(timeString, dayName, startTime, endTime)
            tvCourse.text=courseName
            tvTime.text=timeFormat
            tvLecturer.text=lecturer
        }

        itemView.setOnClickListener {
            course?.let { it1 -> clickListener(it1) }
        }
    }

    fun getCourse(): Course = course
}