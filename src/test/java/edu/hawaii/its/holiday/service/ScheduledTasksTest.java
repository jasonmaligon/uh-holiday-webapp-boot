package edu.hawaii.its.holiday.service;

import edu.hawaii.its.holiday.configuration.SpringBootWebApplication;
import edu.hawaii.its.holiday.type.Holiday;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootWebApplication.class })
public class ScheduledTasksTest {

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private ScheduledTasks scheduledTasks;

    @Test
    public void testCacheResetOfEvictHolidays() {

        List<Holiday> holidays = holidayService.findHolidays();
        int size = holidays.size();
        assertTrue(size > 0);

        Holiday holiday = holidays.get(size - 1);
        Integer id = holiday.getId();

        Holiday h1 = holidayService.findHoliday(id);
        Holiday h2 = holidayService.findHoliday(id);
        assertSame(h1, h2);

        scheduledTasks.evictHolidaysCache();

        Holiday h3 = holidayService.findHoliday(id);
        assertNotSame(h2, h3);

        Holiday h4 = holidayService.findHoliday(id);
        assertSame(h3, h4);

    }
}

