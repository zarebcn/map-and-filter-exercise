import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.joda.time.LocalDateTime;

public class MapAndFilterExercise {

    public static void main(String[] args) throws Exception {


        List<String> dates = Arrays.asList("21-01-2016", "08-04-2015", "01-10-2017");
        System.out.println("fechas: " + dates);

        List<String> modifiedDates = stringMap(dates, new StringFunction() {
            @Override
            public String apply(String date) throws ParseException {

                return addMonths(date, 1);
            }
        });
        System.out.println("fechas (modificadas): " + modifiedDates);

        List<String> modifiedDates2 = stringMap(dates, date -> addMonths(date, 1));

        System.out.println("fechas (modificadas): " + modifiedDates2);


        // map usando una lista de Dates
        Date date = new Date();
        Date date2 = new Date();
        Date date3 = new Date();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        //Calendar calendar = Calendar.getInstance();
        //calendar.setTime(date);
        //calendar.set(Calendar.YEAR, 2017);
        //calendar.set(Calendar.DAY_OF_MONTH, 25);
        //calendar.set(Calendar.MONTH, 2);
        Date modifiedDate = new LocalDateTime(date).withYear(2017).withDayOfMonth(25).withMonthOfYear(2).toDate(); //25-02-2017

        //calendar.setTime(date2);
        //calendar.set(Calendar.YEAR, 2016);
        //calendar.set(Calendar.DAY_OF_MONTH, 10);
        //calendar.set(Calendar.MONTH, 1);
        Date modifiedDate2 = new LocalDateTime(date2).withYear(2016).withDayOfMonth(10).withMonthOfYear(1).toDate(); //10-1-2016

        //calendar.setTime(date3);
        //calendar.set(Calendar.YEAR, 2010);
        //calendar.set(Calendar.DAY_OF_MONTH, 15);
        //calendar.set(Calendar.MONTH, 10);
        Date modifiedDate3 = new LocalDateTime(date2).withYear(2010).withDayOfMonth(15).withMonthOfYear(10).toDate(); //15-10-2010

        List<Date> dates2 = Arrays.asList(modifiedDate, modifiedDate2, modifiedDate3);

        List<Date> modifiedDates3 = map(dates2, new genericMapFunction<Date, Date>() {
            @Override
            public Date apply(Date date) {

               return addMonthsToDateUsingJoda(date);
            }
        });

        List<Date> modifiedDates4 = map(dates2, fecha -> addMonthsToDateUsingJoda(fecha));

        System.out.println("Dates: " + printDates(dates2, dateFormat));
        System.out.println("Dates (modified): " + printDates(modifiedDates3, dateFormat));
        System.out.println("Dates (modified): " + printDates(modifiedDates4, dateFormat));

        // filter
        List<String> text = Arrays.asList("barca", "casa", "perro", "bolsa", "burro", "agua", "coche");

        System.out.println("palabras: " + text);

        List<String> filteredText = stringFilter(text, new filterFunction() {
            @Override
            public boolean apply(String word) {

                return word.toLowerCase().startsWith("b");
            }
        });

        System.out.println("palabras (filtradas): " + filteredText);

        List<String> filteredText2 = stringFilter(text, word -> word.toLowerCase().startsWith("b"));

        System.out.println("palabras (filtradas): " + filteredText2);

        List<String> filteredText3 = filter(text, new genericFilterFunction<String>() {
            @Override
            public boolean apply(String word) {
                return word.toLowerCase().startsWith("b");
            }
        });

        System.out.println(filteredText3);

        List<Integer> numbers = Arrays.asList(12, 5, 7, 10, 15, 2);

        List<Integer> numbers2 = filter(numbers, new genericFilterFunction<Integer>() {
            @Override
            public boolean apply(Integer number) {

                if (number % 2 == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        System.out.println("numeros: " + numbers);
        System.out.println("numeros (pares): " + numbers2);
    }

    @FunctionalInterface
    interface StringFunction {
        String apply(String arg) throws ParseException;
    }

    @FunctionalInterface
    interface genericMapFunction<Arg, Return> {
        Return apply(Arg arg);
    }

    @FunctionalInterface
    interface genericStringFunction<Arg, Return> {
        Return apply(Arg arg);
    }

    @FunctionalInterface
    interface filterFunction {
        boolean apply(String arg);
    }

    @FunctionalInterface
    interface genericFilterFunction<Arg> {
        boolean apply(Arg arg);
    }

    static List<String> stringMap(List<String> list, StringFunction f) throws ParseException {

        List<String> result = new ArrayList<>();

        for (String elem : list) {
            String modified = f.apply(elem);
            result.add(modified);
        }

        return result;
    }

    static <In, Out> List<Out> map(List<In> list, genericMapFunction<In, Out> f) {

        List<Out> result = new ArrayList<>();

        for (In elem : list) {
            Out modified = f.apply(elem);
            result.add(modified);
        }

        return result;
    }

    static List<String> stringFilter(List<String> list, filterFunction f) {

        List<String> result = new ArrayList<>();

        for (String elem : list) {
            if (f.apply(elem)) {
                result.add(elem);
            }
        }

        return result;
    }

    static <In> List<In> filter(List<In> list, genericFilterFunction<In> f) {

        List<In> result = new ArrayList<>();

        for (In elem : list) {
            if (f.apply(elem)) {
                result.add(elem);
            }
        }

        return result;
    }

    static String addMonths(String date, int amount) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date fecha = dateFormat.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MONTH, amount);
        Date fechaModificada = calendar.getTime();

        return dateFormat.format(fechaModificada);
    }

    static String printDates(List<Date> dates, DateFormat dateFormat) {

        String fechas = "";

        for (int i = 0; i < dates.size(); i++) {

            Date fecha = dates.get(i);
            fechas += dateFormat.format(fecha);

            if (i < dates.size() - 1) {

                fechas += ", ";
            }
        }

        return fechas;
    }

    static Date addMonthsToDate(Date date, Calendar calendar) {

        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    static Date addMonthsToDateUsingJoda(Date date) {

        Date fecha = new LocalDateTime(date).plusMonths(1).toDate();
        return fecha;
    }
}