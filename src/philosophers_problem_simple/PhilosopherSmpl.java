package philosophers_problem_simple;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.concurrent.Semaphore;

public class PhilosopherSmpl extends Thread {
    Semaphore sem; // семафор. ограничивающий число философов
    // кол-во приемов пищи
    int num = 0;
    // условный номер философа
    int id;
    // в качестве параметров конструктора передаем идентификатор философа и семафор
    PhilosopherSmpl(Semaphore sem, int id) {
        this.sem=sem;
        this.id=id;
    }

    public void run() {
        try {
            while(num<3)// пока количество приемов пищи не достигнет 3
            {
                //Запрашиваем у семафора разрешение на выполнение
                sem.acquire();
                System.out.println ("Философ " + id + " садится за стол");
                // философ ест
                LocalDate localDateNow = LocalDate.now(); // получаем текущую дату
                String now = "Сегодня: ";
                int year = localDateNow.getYear();
                int month = localDateNow.getMonthValue();
                int dayOfMonth = localDateNow.getDayOfMonth();
                DayOfWeek dayOfWeek = localDateNow.getDayOfWeek();
                System.out.printf("%s %s %d.%d.%d \n", now, dayOfWeek, dayOfMonth, month, year);
                System.out.println("А Куликовская битва была в: ");
                LocalDate date = LocalDate.of(1380, 9, 8);
                System.out.println(date); // конкретная дата
                //Calendar calendar = new Calendar();
                //calendar.calendarPrint(1, 2);
                sleep(500);
                num++;

                System.out.println ("Философ " + id + " выходит из-за стола");
                sem.release();

                // философ гуляет
                sleep(500);
            }
        } catch(InterruptedException e) {
            System.out.println ("у философа " + id + " проблемы со здоровьем");
        }
    }
}
