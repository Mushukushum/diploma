package com.example.diploma.dayduty.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.diploma.dayduty.data.dao.DayDutiesDao
import com.example.diploma.dayduty.domain.model.DayDuty
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.util.*
import com.google.common.truth.Truth.assertThat


@RunWith(AndroidJUnit4::class)
internal class DayDutiesDatabaseTest : TestCase(){

    private lateinit var dayDutiesDatabase: DayDutiesDatabase
    private lateinit var dayDutiesDao: DayDutiesDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dayDutiesDatabase = Room.inMemoryDatabaseBuilder(context, DayDutiesDatabase::class.java).build()
        dayDutiesDao = dayDutiesDatabase.dayDutiesDao()
    }

    @After
    fun closeDb(){
        dayDutiesDatabase.close()
    }

    @Test
    fun writeAndReadDayDuty() = runBlocking{
        val dayDuty = DayDuty(1, "Some test titile", LocalDate.now(), "Some test description")
        dayDutiesDao.insertData(dayDuty)
        dayDutiesDao.deleteData(dayDuty)
        val getDayDuty = dayDutiesDao.getDayWithDuties(dayDuty.localDate.toString())
        assertThat(getDayDuty.contains(dayDuty)).isFalse()
    }


}