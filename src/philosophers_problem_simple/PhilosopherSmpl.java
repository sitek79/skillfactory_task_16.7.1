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
            // пока количество приемов пищи не достигнет 3
            while(num<3) {
                System.out.println("Этот поток называется: " + Thread.currentThread().getName());
                //Запрашиваем у семафора разрешение на выполнение
                sem.acquire();
                System.out.println ("Философ " + id + " садится за стол");
                // философ ест
                System.out.println("Помимо того, что Философ ест, он еще успевает решать математические задачки");
                // например, последовательность Фибоначчи
                FibonacciNumbers fn = new FibonacciNumbers();
                //fn.run(10, FibonacciNumbers.Kind.Naive);
                //fn.run(22, FibonacciNumbers.Kind.Memoized);
                //fn.run(33, FibonacciNumbers.Kind.Dynamic);
                //fn.run(44, FibonacciNumbers.Kind.Recursive);
                System.out.println(fn.run(10, FibonacciNumbers.Kind.Naive));
                System.out.println(fn.run(22, FibonacciNumbers.Kind.Memoized));
                System.out.println(fn.run(33, FibonacciNumbers.Kind.Dynamic));
                System.out.println(fn.run(44, FibonacciNumbers.Kind.Recursive));

                sleep(1000);
                num++;

                System.out.println ("Философ " + id + " выходит из-за стола");
                sem.release();

                // философ гуляет
                sleep(2000);
                //
                try {
                    Thread.currentThread().join(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch(InterruptedException e) {
            System.out.println ("у философа " + id + " проблемы со здоровьем");
        }
    }
}
