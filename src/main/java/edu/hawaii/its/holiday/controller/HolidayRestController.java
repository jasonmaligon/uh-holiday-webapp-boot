package edu.hawaii.its.holiday.controller;

import java.util.List;
import java.util.Optional;

import edu.hawaii.its.holiday.util.Dates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.hawaii.its.holiday.service.HolidayService;
import edu.hawaii.its.holiday.type.Holiday;
import edu.hawaii.its.holiday.type.Type;

@RestController
public class HolidayRestController {

    private static final Logger logger = LoggerFactory.getLogger(HolidayRestController.class);

    @Autowired
    private HolidayService holidayService;

    @GetMapping(value = "/api/holidays")
    public ResponseEntity<JsonData<List<Holiday>>> holidays() {
        logger.debug("Entered REST holidays...");
        List<Holiday> holidays = holidayService.findHolidays();
        holidayService.findClosestHolidayByDate(Dates.newLocalDate().toString(), true);
        JsonData<List<Holiday>> data = new JsonData<>(holidays);
        return ResponseEntity
                .ok()
                .body(data);
    }

    @GetMapping(value = "/api/holidays/{id}")
    public ResponseEntity<JsonData<Holiday>> holiday(@PathVariable Integer id) {
        logger.debug("Entered REST holiday(" + id + ") ...");
        Holiday holiday = holidayService.findHoliday(id);
        JsonData<Holiday> data = new JsonData<>(holiday);
        return ResponseEntity
                .ok()
                .body(data);
    }

    @GetMapping(value = "/api/types")
    public ResponseEntity<JsonData<List<Type>>> types() {
        logger.debug("Entered REST types...");
        List<Type> types = holidayService.findTypes();
        JsonData<List<Type>> data = new JsonData<>(types);
        return ResponseEntity
                .ok()
                .body(data);
    }

    @GetMapping(value = "/api/holidays/year/{year}")
    public ResponseEntity<JsonData<List<Holiday>>> holidaysByYear(
            @PathVariable Integer year,
            @RequestParam (value = "type", defaultValue = "uh") String type) {
        logger.debug("Entered REST holidays/year... year: " + year);
        List<Holiday> holidays = holidayService.findHolidaysByYear(year);
        holidays = holidayService.findHolidaysByType(holidays, type);
        JsonData<List<Holiday>> data = new JsonData<>(holidays);
        return ResponseEntity
                .ok()
                .body(data);
    }

    @GetMapping(value = "/api/holidays/month/{month}", params = {"year", "type"})
    public ResponseEntity<JsonData<List<Holiday>>> holidaysByMonth(
            @PathVariable Integer month,
            @RequestParam Optional<Integer> year,
            @RequestParam(value = "type", defaultValue = "uh") String type) {
        Integer newYear = year.isPresent() ? year.get() : Dates.currentYear();
        List<Holiday> holidays = holidayService.findHolidaysByMonth(month, newYear);
        holidays = holidayService.findHolidaysByType(holidays, type);
        JsonData<List<Holiday>> data = new JsonData<>(holidays);
        return ResponseEntity
                .ok()
                .body(data);
    }

    @GetMapping(value = "api/holidays/range", params = {"begin-date", "end-date", "inclusive", "type"})
    public ResponseEntity<JsonData<List<Holiday>>> holidaysByRange(
            @RequestParam("begin-date") String beginDate,
            @RequestParam("end-date") String endDate,
            @RequestParam(value = "inclusive", defaultValue = "true", required = false) boolean include,
            @RequestParam(value = "type", defaultValue = "uh") String type) {
        List<Holiday> holidays = holidayService.findHolidaysByRange(beginDate, endDate, include);
        holidays = holidayService.findHolidaysByType(holidays, type);
        JsonData<List<Holiday>> data = new JsonData<>(holidays);
        return ResponseEntity
                .ok()
                .body(data);
    }

    @GetMapping(value = "api/holidays/exists", params = {"date", "type"})
    public ResponseEntity<JsonData<List<Holiday>>> holidaysByExists(
            @RequestParam("date") String date,
            @RequestParam(value = "type", defaultValue = "uh") String type) {
        List<Holiday> holidays = holidayService.findHolidaysByRange(date, date, true);
        holidays = holidayService.findHolidaysByType(holidays, type);
        JsonData<List<Holiday>> data = new JsonData<>(holidays);
        return ResponseEntity
                .ok()
                .body(data);
    }

    @GetMapping(value = "api/holidays/closest", params = {"date", "search-forward", "type"})
    public ResponseEntity<JsonData<Holiday>> holidaysByClosest(
            @RequestParam("date") String date,
            @RequestParam(value = "search-forward", defaultValue = "true", required = false) boolean forward,
            @RequestParam(value = "type", defaultValue = "uh") String type) {
        Holiday holiday = holidayService.findClosestHolidayByDate(date, forward, type);
        JsonData<Holiday> data = new JsonData<>(holiday);
        return ResponseEntity
                .ok()
                .body(data);
    }

    @GetMapping(value = "rest/inYear", params = {"year"})
    public ResponseEntity<JsonData<List<Holiday>>> holidaysByYearParam(
            @RequestParam("year") Integer year,
            @RequestParam(name = "type", defaultValue = "uh") String type,
            @RequestParam(name = "isObserved", defaultValue = "false") Boolean isObserved) {
        List<Holiday> holidays = holidayService.findHolidaysByYear(year);
        holidays = holidayService.findHolidaysByType(holidays, type);
        JsonData<List<Holiday>> data = new JsonData<>(holidays);
        return ResponseEntity
                .ok()
                .body(data);
    }
}
