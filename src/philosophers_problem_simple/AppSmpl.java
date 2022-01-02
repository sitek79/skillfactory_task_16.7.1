package philosophers_problem_simple;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;

public class AppSmpl {
    public static void main(String[] args) {
        String startTime = String.valueOf(new Date(ManagementFactory.getRuntimeMXBean().getStartTime()));
        System.out.println("Время запуска: " + startTime);

        // вывод параметров запуска JVM
        runtimeParameters();

        System.out.println("Старт главного потока ->");

        // используем класс ManagementFactory для отслеживания использования памяти.
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        System.out.println(String.format("Initial memory: %.2f GB", (double)memoryMXBean.getHeapMemoryUsage().getInit() /1073741824));
        System.out.println(String.format("Used heap memory: %.2f GB", (double)memoryMXBean.getHeapMemoryUsage().getUsed() /1073741824));
        System.out.println(String.format("Max heap memory: %.2f GB", (double)memoryMXBean.getHeapMemoryUsage().getMax() /1073741824));
        System.out.println(String.format("Committed memory: %.2f GB", (double)memoryMXBean.getHeapMemoryUsage().getCommitted() /1073741824));
        //

        Instant start = Instant.now();

        long pid = ProcessHandle.current().pid();
        System.out.println("Идентификатор процесса: " + pid);

        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = runtimeMxBean.getInputArguments();
        System.out.println(arguments);

        System.out.println(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);

        Semaphore sem = new Semaphore(2);
        for (int i = 1; i < 10; i++) {
            new PhilosopherSmpl(sem, i).start();
        }

        System.out.println("Поток " + Thread.currentThread().getName() + " завершен.");

        // измеряем время
        Instant finish = Instant.now();
        long elapsed = Duration.between(start, finish).toMillis();
        System.out.println("Прошло времени, мс: " + elapsed);
    }

    // перечислим все доступные параметры jvm
    public static void runtimeParameters() {
        System.out.println("Перечислим все доступные параметры jvm");
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        List<String> aList = bean.getInputArguments();
        for (int i = 0; i < aList.size(); i++) {
            System.out.println(aList.get(i));
        }
    }
}