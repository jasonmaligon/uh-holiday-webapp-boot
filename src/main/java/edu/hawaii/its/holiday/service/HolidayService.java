package edu.hawaii.its.holiday.service;

import java.util.List;

import edu.hawaii.its.holiday.type.Designation;
import edu.hawaii.its.holiday.type.Holiday;
import edu.hawaii.its.holiday.type.Type;
import edu.hawaii.its.holiday.type.UserRole;

public interface HolidayService {

    List<Designation> findDesignations();

    Designation findDesignation(Integer id);

    Holiday findHoliday(Integer id);

    List<Holiday> findHolidays();

    List<Holiday> findHolidaysByYear(Integer year);

    List<Holiday> findHolidaysByMonth(Integer month, Integer year);

    List<Holiday> findHolidaysByRange(String beginDate, String endDate, boolean include);

    Holiday findClosestHolidayByDate(String date, boolean forward);

    Holiday findClosestHolidayByDate(String date, boolean forward, String type);

    List<UserRole> findUserRoles();

    List<Holiday> findHolidaysByType(List<Holiday> holidays, String type);

    Type findType(Integer id);

    List<Type> findTypes();

    List<String> findAllDescriptions();

}
