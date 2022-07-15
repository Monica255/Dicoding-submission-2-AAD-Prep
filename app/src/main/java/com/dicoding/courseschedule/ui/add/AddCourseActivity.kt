package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener  {
    private lateinit var viewModel: AddCourseViewModel
    private var startTime: String = ""
    private var endTime: String = ""
    private var courseName: String = ""
    private var lecturer: String = ""
    private var note: String = ""
    private var day:Int=0

    private lateinit var etCourseName: EditText
    private lateinit var etLecturer: EditText
    private lateinit var etNote: EditText
    private lateinit var spDay:Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        val factory = AddViewModelfactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)

        supportActionBar?.title = getString(R.string.add_course)

        viewModel.saved.observe(this){
            if (it.getContentIfNotHandled()==true) finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_insert -> {
                etCourseName = findViewById(R.id.add_ed_courseName)
                etLecturer = findViewById(R.id.add_ed_lecturer)
                etNote = findViewById(R.id.add_ed_note)
                spDay=findViewById(R.id.add_sp_day)

                courseName = etCourseName.text.trim().toString()
                lecturer = etLecturer.text.trim().toString()
                note = etNote.text.trim().toString()
                day=spDay.selectedItemPosition
                    viewModel.insertCourse(
                        courseName = courseName,
                        day = day ,
                        startTime = startTime,
                        endTime = endTime,
                        lecturer = lecturer,
                        note = note
                    )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun showStartTimePicker(view: View) {
        val timePicker = TimePickerFragment()
        timePicker.show(supportFragmentManager, START_TIME_TAG)
    }

    fun showEndTimePicker(view: View) {
        val timePicker = TimePickerFragment()
        timePicker.show(supportFragmentManager, END_TIME_TAG)
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val newHour=if(hour<10){
            "0$hour"
        }else {
            hour.toString()
        }

        val newMinute=if(minute<10){
            "0$minute"
        }else{
            minute.toString()
        }
        val time = StringBuilder(newHour).append(":").append(newMinute).toString()
        if (tag == START_TIME_TAG) {
            startTime = time
            findViewById<TextView>(R.id.add_tv_startTime).text = startTime
        } else {
            endTime = time
            findViewById<TextView>(R.id.add_tv_endTime).text = endTime
        }
    }

    companion object {
        const val START_TIME_TAG = "startTimePicker"
        const val END_TIME_TAG = "endTimePicker"

    }

}