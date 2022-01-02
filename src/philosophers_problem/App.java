package philosophers_problem;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class App {
    static final int philosopherCount = 5; //  token +2 behavior good for odd #s
    static final int runSeconds = 120;
    static ArrayList<Fork> forks = new ArrayList<Fork>();
    static ArrayList<Philosopher> philosophers = new ArrayList<Philosopher>();

    public static void main(String[] args) {
        // используем класс ManagementFactory для отслеживания использования памяти.
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        System.out.println(String.format("Initial memory: %.2f GB", (double)memoryMXBean.getHeapMemoryUsage().getInit() /1073741824));
        System.out.println(String.format("Used heap memory: %.2f GB", (double)memoryMXBean.getHeapMemoryUsage().getUsed() /1073741824));
        System.out.println(String.format("Max heap memory: %.2f GB", (double)memoryMXBean.getHeapMemoryUsage().getMax() /1073741824));
        System.out.println(String.format("Committed memory: %.2f GB", (double)memoryMXBean.getHeapMemoryUsage().getCommitted() /1073741824));
        //

        Instant start = Instant.now();

        System.out.println("Старт главного потока ->");

        for (int i = 0 ; i < philosopherCount ; i++) forks.add(new Fork());
        for (int i = 0 ; i < philosopherCount ; i++)
            philosophers.add(new Philosopher());
        for (Philosopher p : philosophers) new Thread(p).start();
        long endTime = System.currentTimeMillis() + (runSeconds * 1000);

        do {                                                    //  печатаем статус
            StringBuilder sb = new StringBuilder("|");

            for (Philosopher p : philosophers) {
                sb.append(p.state.toString());
                sb.append("|");            //  This is a snapshot at a particular
            }                              //  instant.  Plenty happens between.

            sb.append("     |");

            for (Fork f : forks) {
                int holder = f.holder.get();
                sb.append(holder==-1?"   ":String.format("P%02d",holder));
                sb.append("|");
            }

            System.out.println(sb.toString());
            try {Thread.sleep(1);} catch (Exception ex) {}
        } while (System.currentTimeMillis() < endTime);

        for (Philosopher p : philosophers) p.end.set(true);
        for (Philosopher p : philosophers)
            System.out.printf("P%02d: Поел %,d раз, %,d/сек\n", p.id, p.timesEaten, p.timesEaten/runSeconds);

        // измеряем время
        Instant finish = Instant.now();
        long elapsed = Duration.between(start, finish).toMillis();
        System.out.println("Прошло времени, мс: " + elapsed);

        System.out.println("Главный поток завершен.");
    }
}
