package edu.hawaii.its.holiday.type;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.hawaii.its.holiday.util.Dates;

public class YearHolidayHolderTest {

    private YearHolidayHolder holder;

    @Before
    public void setUp() {
        holder = new YearHolidayHolder();
    }

    @Test
    public void construction() {
        assertNotNull(holder);
    }

    @Test
    public void isEmpty() {
        assertTrue(holder.isEmpty());

        Date date = Dates.toDate(Dates.firstDateOfYear(2017));
        Holiday h = new Holiday(date, date);
        List<Holiday> holidays = new ArrayList<Holiday>();
        holidays.add(h);
        holder = new YearHolidayHolder(holidays);

        assertFalse(holder.isEmpty());
    }

    @Test
    public void getYears() {
        assertTrue(holder.isEmpty());

        List<Holiday> holidays = new ArrayList<Holiday>();
        for (int year = 2000; year <= 2015; year++) {
            Date date = Dates.toDate(Dates.firstDateOfYear(year));
            holidays.add(new Holiday(date, date));
        }

        holder = new YearHolidayHolder(holidays);
        assertFalse(holder.isEmpty());

        Set<Integer> years = holder.getYears();
        assertThat(years.size(), equalTo(16));
    }

    @Test
    public void getYear() {
        holder = new YearHolidayHolder() {
            protected Integer currentYear() {
                return 2016;
            }
        };
        assertTrue(holder.isEmpty());

        List<Holiday> holidays = new ArrayList<Holiday>();
        for (int year = 2000; year <= 2017; year++) {
            Date date = Dates.toDate(Dates.firstDateOfYear(year));
            holidays.add(new Holiday(date, date));
        }

        holder = new YearHolidayHolder(holidays);
        assertFalse(holder.isEmpty());

        Set<Integer> years = holder.getYears();
        assertThat(years.size(), equalTo(18));

        // The year will be the current year if
        // the years range spans accross it. 
        assertThat(holder.getYear(), equalTo(2016));

        holidays = new ArrayList<Holiday>();
        for (int year = 2000; year <= 2014; year++) {
            Date date = Dates.toDate(Dates.firstDateOfYear(year));
            holidays.add(new Holiday(date, date));
        }

        holder = new YearHolidayHolder(holidays);
        assertFalse(holder.isEmpty());

        // The year will be the highest year if the 
        // current year is outside the range of years.
        assertThat(holder.getYear(), equalTo(2014));
    }
}
